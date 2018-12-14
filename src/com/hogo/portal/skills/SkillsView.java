package com.hogo.portal.skills;

import java.sql.SQLException;
import java.util.Collection;

import org.daro.common.ui.UIError;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.part.ViewPart;

import com.hogo.portal.AbstractTableLabelProvider;
import com.hogo.portal.Activator;
import com.hogo.portal.FilteredTableFilter;
import com.hogo.portal.candidate.ui.CustomAction;

public class SkillsView extends ViewPart {

	public static final String ID = "com.hogo.portal.skills.SkillsView";
	private SkillsModel model;
	private TreeViewer viewer;
	private FilteredTree skillsTable;

	private FilteredTree createTable(Composite parent) {
		final FilteredTree ftree = new FilteredTree(parent, SWT.BORDER | SWT.FULL_SELECTION, new FilteredTableFilter(),
				true);
		viewer = ftree.getViewer();

		final Tree tree = viewer.getTree();

		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);

		TreeViewerColumn col;
		col = new TreeViewerColumn(viewer, SWT.NONE);
		col.getColumn().setText("Name");
		col.getColumn().setWidth(500);

		viewer.getTree().addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) {
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.DEL) {
					runDelete();
				}
			}
		});

		viewer.setContentProvider(new ITreeContentProvider() {

			@Override
			public boolean hasChildren(Object element) {
				return false;
			}

			@Override
			public Object getParent(Object element) {
				return null;
			}

			@Override
			public Object[] getElements(Object inputElement) {
				if (inputElement instanceof Collection) {
					return ((Collection<?>) inputElement).toArray();
				}
				return new Object[0];
			}

			@Override
			public Object[] getChildren(Object parentElement) {
				return new Object[0];
			}
		});
		viewer.setLabelProvider(new AbstractTableLabelProvider() {

			@Override
			public String getColumnText(Object element, int columnIndex) {
				return element.toString();
			}
		});

		return ftree;
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, true));

		skillsTable = createTable(parent);
		createMenu();

		try {
			model = SkillsModel.open();
			refresh();
		} catch (SQLException e) {
			UIError.showError("DB Fehler", e);
		}
	}

	@Override
	public void setFocus() {
	}

	public void refresh() {
		skillsTable.getViewer().getTree().removeAll();
		try {
			Collection<SkillsEntry> result = model.select();
			skillsTable.getViewer().setInput(result);
		} catch (SQLException e) {
			UIError.showError("DB Fehler", e);
		}
	}

	void runDelete() {
		TreeItem[] items = viewer.getTree().getSelection();
		if (items == null || items.length == 0) {
			return;
		}
		try {
			for (TreeItem i : items)
				model.delete(i.getData().toString());
			refresh();
		} catch (SQLException e) {
			UIError.showError("DB Fehler", e);
		}
	}

	void runAdd() {
		SkillsDialog dialog = new SkillsDialog(getSite().getShell());
		if (dialog.open() == SkillsDialog.OK) {
			String name = dialog.getName();
			try {
				model.add(new SkillsEntry(name));
				refresh();
			} catch (SQLException e) {
				UIError.showError(dialog.getShell(), "DB Fehler",
						"Eintrag '" + name + "' konnte nicht hinzugefügt werden.", e);
			}
		}
	}

	void createMenu() {
		CustomAction add = new CustomAction("Hinzufügen", Activator.getImageDescriptor("icons/add.gif"),
				() -> runAdd());
		CustomAction delete = new CustomAction("Löschen", Activator.getImageDescriptor("icons/delete.png"),
				() -> runDelete());

		getViewSite().getActionBars().getToolBarManager().add(add);
		getViewSite().getActionBars().getToolBarManager().add(delete);
		getViewSite().getActionBars().getToolBarManager()
				.add(new CustomAction("Laden", Activator.getImageDescriptor("icons/refresh.png"), () -> refresh()));
	}

	@Override
	public void dispose() {
		try {
			if (model != null)
				model.close();
		} catch (Exception e) {
			UIError.showError("DB Fehler", e);
		}
		super.dispose();
	}
}
