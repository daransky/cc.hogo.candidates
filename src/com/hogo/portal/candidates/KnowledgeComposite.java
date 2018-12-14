package com.hogo.portal.candidates;

import java.util.Collection;
import java.util.HashSet;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;

import com.hogo.portal.Activator;

public class KnowledgeComposite extends Composite {
	private Combo 	cmbKnowledge;
	private Button 	bAdd;
	private TableViewer knowledgeTable;
	private HashSet<String> content = new HashSet<>();

	public KnowledgeComposite(String label, Composite parent) {
		super(parent, SWT.NONE);
		setLayout(new GridLayout(3, false));

		Label lblKenntnisse = new Label(this, SWT.NONE);
		lblKenntnisse.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblKenntnisse.setText(label);

		cmbKnowledge = new Combo(this, SWT.NONE);
		cmbKnowledge.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		bAdd = new Button(this, SWT.NONE);
		bAdd.setImage(Activator.getImage("icons/add.gif"));
		new Label(this, SWT.NONE);

		Table table_1 = new Table(this, SWT.BORDER | SWT.FULL_SELECTION);
		table_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true, 1, 1));
		knowledgeTable = new TableViewer(table_1);
		knowledgeTable.getTable().setLinesVisible(true);
		knowledgeTable.getTable().setHeaderVisible(false);
		knowledgeTable.setContentProvider(new IStructuredContentProvider() {
			@Override
			public Object[] getElements(Object inputElement) {
				return new String[] { (String) inputElement };
			}
		});
		knowledgeTable.getTable().addKeyListener(new KeyListener() {
			@Override
			public void keyReleased(KeyEvent e) {}

			@Override
			public void keyPressed(KeyEvent e) {
				final Table table = knowledgeTable.getTable();
				if (e.keyCode == SWT.DEL && table.getSelectionCount() > 0) {
					int[] items = table.getSelectionIndices();
					table.remove(items);
				}
			}
		});
		new TableViewerColumn(knowledgeTable, SWT.LEAD);
		new Label(this, SWT.NONE);
	}
	
	public void setKnowledgeSet(String[] arg) { 
		cmbKnowledge.removeAll();
		cmbKnowledge.setItems(arg);
	}
	
	public void setEditable(boolean arg) {
		bAdd.setEnabled(!arg);
		cmbKnowledge.setEnabled(!arg);
		knowledgeTable.getTable().setEnabled(!arg);
	}

	public void addKnowledge(String knowledge) {
		if (content.add(knowledge))
			knowledgeTable.add(knowledge);
	}

	public void addKnowledge(Collection<String> kwnowledge) {
		if (content.addAll(kwnowledge)) {
			knowledgeTable.getTable().removeAll();
			knowledgeTable.setInput(content);
		}
	}

	public Collection<String> getKnowledge() {
		return content;
	}

}
