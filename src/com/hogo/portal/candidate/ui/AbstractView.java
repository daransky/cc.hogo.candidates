package com.hogo.portal.candidate.ui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.dialogs.FilteredTree;
import org.eclipse.ui.part.ViewPart;

import com.hogo.portal.Activator;
import com.hogo.portal.FilteredTableFilter;

public abstract class AbstractView extends ViewPart {

	FilteredTree table;
	TreeViewer viewer;

	Menu contextMenu;

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, true));

		table = new FilteredTree(parent, SWT.BORDER | SWT.FULL_SELECTION, new FilteredTableFilter(), true);
		viewer = table.getViewer();

		final Tree tree = viewer.getTree();

		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);

		TreeViewerColumn[] columns = createColumns(viewer);
		createTableColumnsMenu(columns);
		createMenu();

		tree.addKeyListener(new KeyListener() {

			@Override
			public void keyReleased(KeyEvent e) { //
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.keyCode == SWT.DEL) {
					deleteAction();
				}
			}
		});

		tree.addMouseListener(new MouseListener() {
			@Override
			public void mouseUp(MouseEvent e) { //
			}

			@Override
			public void mouseDown(MouseEvent e) {//
			}

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				TreeItem[] items = tree.getSelection();
				if (items != null) {
					openAction();
				}
			}
		});

		viewer.setContentProvider(createViewContent());
	}

	@Override
	public void setFocus() {
	}

	void createMenu() {
		CustomAction add = new CustomAction("Hinzufügen", Activator.getImageDescriptor("icons/add.gif"),
				() -> addAction());
		CustomAction delete = new CustomAction("Löschen", Activator.getImageDescriptor("icons/delete.png"),
				() -> deleteAction());
		CustomAction refresh = new CustomAction("Laden", Activator.getImageDescriptor("icons/refresh.png"),
				() -> refreshAction());

		IToolBarManager toolBar = getViewSite().getActionBars().getToolBarManager();
		if (isAddAllowed())
			toolBar.add(add);
		if (isDeleteAllowed())
			toolBar.add(delete);
		if (isRefreshAllowed())
			toolBar.add(refresh);
	}

	final class HideColumnAction extends Action {
		final TreeViewerColumn col;
		int width;

		public HideColumnAction(TreeViewerColumn col) {
			super(col.getColumn().getText(), Action.AS_CHECK_BOX);
			this.col = col;
			width = col.getColumn().getWidth();
		}

		@Override
		public boolean isChecked() {
			return col.getColumn().getWidth() != 0;
		}

		@Override
		public void run() {
			col.getColumn().setWidth(isChecked() ? 0 : width);
		}
	}

	protected void createTableColumnsMenu(TreeViewerColumn[] columns) {
		IMenuManager menu = getViewSite().getActionBars().getMenuManager();
		for (TreeViewerColumn col : columns) {
			menu.add(new HideColumnAction(col));
		}
	}

	protected abstract TreeViewerColumn[] createColumns(TreeViewer viewer);

	protected abstract void addAction();

	protected abstract void deleteAction();

	protected abstract void refreshAction();

	protected abstract void openAction();

	public TreeViewer getViewer() {
		return viewer;
	}

	protected abstract ITreeContentProvider createViewContent();

	protected boolean isAddAllowed() {
		return true;
	}

	protected boolean isRefreshAllowed() {
		return true;
	}

	protected boolean isDeleteAllowed() {
		return true;
	}

	public Menu getContextMenu() {
		return contextMenu;
	}

	public void setContextMenu(Menu contextMenu) {
		this.contextMenu = contextMenu;

		getViewer().getTree().setMenu(contextMenu);
	}

}
