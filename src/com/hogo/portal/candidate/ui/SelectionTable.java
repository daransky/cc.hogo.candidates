package com.hogo.portal.candidate.ui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.daro.common.ui.TableContentProvider;
import org.eclipse.jface.fieldassist.AutoCompleteField;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.hogo.portal.AbstractTableLabelProvider;

public class SelectionTable extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Table table;
	private TableViewer tableViewer;
	private Text field;
	private Label lblChooseText;
	private ContentProvider content;
	private Collection<Consumer<String>> listeners = new ArrayList<>();
	
	class ContentProvider extends TableContentProvider<String> {

		private HashSet<String> values = new HashSet<>();
		
		public ContentProvider(TableViewer viewer) {
			super(viewer);
		}

		@Override
		public void add(String arg) {
			if (!values.contains(arg)) {
				values.add(arg);
				super.add(arg);
			}
		}

		@Override
		public void remove(String arg) {
			values.remove(arg);
			super.remove(arg);
		}
	}

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public SelectionTable(Composite parent, Collection<String> proposals) {
		super(parent, SWT.NONE);
		addDisposeListener(e -> toolkit.dispose());
		
		final Set<String> values = new TreeSet<>(proposals);
		
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(new GridLayout(2, false));

		lblChooseText = toolkit.createLabel(this, "Test Text", SWT.NONE);
		GridData gd = new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1);
		gd.widthHint = 121;
		gd.minimumWidth = 50;
		lblChooseText.setLayoutData(gd);

		field = new Text(this, SWT.BORDER);
		String[] proposalsArray = proposals.toArray( new String[proposals.size()] ) ;
		AutoCompleteField at = new AutoCompleteField(field, new TextContentAdapter(), proposalsArray);
		at.setProposals(proposalsArray);

		field.addKeyListener(new KeyListener() {
			
			@Override
			public void keyReleased(KeyEvent e) {
				//
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if( e.character == '\r' ) {
					String text = field.getText();
					content.add(text);
					values.remove(text);
					field.setText("");
					
					fireEvents();
				}
			}
		});

		gd = new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1);
		gd.minimumWidth = 200;
		field.setLayoutData(gd);
		toolkit.adapt(field, true, true);
		new Label(this, SWT.NONE);

		tableViewer = new TableViewer(this, SWT.BORDER | SWT.FULL_SELECTION);
		content = new ContentProvider(tableViewer);
		table = tableViewer.getTable();

		table.setLinesVisible(true);
		table.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		table.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
				//
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.DEL) {
					TableItem[] item = table.getSelection();
					if( item != null && item.length > 0 ) { 
						values.add(item[0].getText());
						at.setProposals(proposals.toArray(new String[values.size()]));
						content.remove(item[0].getText());
						
						fireEvents();
					}
				}
			}

		});
		toolkit.paintBordersFor(table);

		TableViewerColumn tableViewerColumn = new TableViewerColumn(tableViewer, SWT.NONE);
		TableColumn columnValue = tableViewerColumn.getColumn();
		columnValue.setMoveable(true);
		columnValue.setWidth(300);
		columnValue.setText("Value");
		tableViewer.setLabelProvider(new AbstractTableLabelProvider() {

			@Override
			public String getColumnText(Object element, int columnIndex) {
				return element.toString();
			}
		});
		tableViewer.setContentProvider(content);
	}

	public Text getField() {
		return field;
	}

	public void setChooserText(String text) {
		lblChooseText.setText(text);
	}

	public Collection<String> getValues() {
		return content.values;
	}

	public void setValues(String arg) {
		if (arg == null)
			return;
		StringTokenizer st = new StringTokenizer(arg, ",");
		while( st.hasMoreElements() )
			tableViewer.add(st.nextElement());
	}

	public void addModifyListener (Consumer<String> listener) {
		if( !listeners.contains(listener))
			listeners.add(listener);
	}
	
	public void removeModifyListener (Consumer<String> listener) {
		listeners.remove(listener);
	}

	private void fireEvents() {
		listeners.forEach(listener -> listener.accept( getValues().stream().collect(Collectors.joining(", ")) ));		
	}
}
