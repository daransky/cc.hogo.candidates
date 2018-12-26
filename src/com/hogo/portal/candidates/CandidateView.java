package com.hogo.portal.candidates;

import java.sql.SQLException;
import java.util.Collection;

import org.daro.common.ui.UIError;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuAdapter;
import org.eclipse.swt.events.MenuEvent;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.TreeItem;

import com.hogo.portal.Activator;
import com.hogo.portal.OpenCandidateEditor;
import com.hogo.portal.candidate.ui.AbstractView;
import com.hogo.portal.candidate.ui.Refreshable;

public class CandidateView extends AbstractView implements Refreshable {
	public static final String ID = "com.hogo.portal.candidates.CandidateView";
	private CandidateModel model;
	private PersonContentProvider content;

	@Override
	protected TreeViewerColumn[] createColumns(TreeViewer viewer) {
		return CandidateTableLabelProvider.createColumns(viewer);
	}

	@Override
	protected ITreeContentProvider createViewContent() {
		content = new PersonContentProvider();
		return content;
	}

	@Override
	protected void openAction() {
		TreeItem[] items = getViewer().getTree().getSelection();
		if (items != null) {
			OpenCandidateEditor open = new OpenCandidateEditor(
					Activator.getDefault().getWorkbench().getActiveWorkbenchWindow());
			for (TreeItem i : items) {
				open.run((CandidateEntry) i.getData(), this);
			}
		}
	}

	void createContextMenu() {
		Menu contextMenu = new Menu(getViewer().getTree());
		contextMenu.addMenuListener(new MenuAdapter() {
			public void menuShown(MenuEvent e) {
				MenuItem[] items = contextMenu.getItems();
				for (int i = 0; i < items.length; i++) {
					items[i].dispose();
				}

				final TreeItem item = getItemSelected();
				final CandidateEntry candidate = (CandidateEntry) item.getData();

				for (Status sts : Status.values()) {
					MenuItem newItem = new MenuItem(contextMenu, SWT.CHECK);
					newItem.setText(sts.toString());
					newItem.setData(sts);
					newItem.setSelection(sts == candidate.getStatus());
					newItem.addSelectionListener(new SelectionListener() {

						@Override
						public void widgetSelected(SelectionEvent e) {
							final CandidateEntry candidate = (CandidateEntry) getItemSelected().getData();
							final MenuItem mi = (MenuItem) e.getSource();
							
							candidate.setStatus((Status) mi.getData());
							try {
								model.updateStatus(candidate);
								refreshAction();
							} catch (SQLException e1) {
								UIError.showError("DB Fehler", e1);
							}
						}

						@Override
						public void widgetDefaultSelected(SelectionEvent e) {
						}
					});
				}
			}
		});

		setContextMenu(contextMenu);
	}

	TreeItem getItemSelected() {
		TreeItem[] selection = getViewer().getTree().getSelection();
		return selection[0];
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		getViewer().setLabelProvider(new CandidateTableLabelProvider());

		createContextMenu();

		try {
			model = CandidateModel.open();
			refreshAction();
		} catch (SQLException e) {
			UIError.showError("DB Fehler", e);
		}
	}

	@Override
	protected void refreshAction() {
		getSite().getShell().getDisplay().asyncExec(() -> {
			getViewer().getTree().removeAll();
			try {
				Collection<CandidateEntry> result = model.select();
				getViewer().setInput(result);
			} catch (SQLException e) {
				UIError.showError("DB Fehler", e);
			}
		});
	}

	protected void deleteAction() {
		TreeItem[] items = getViewer().getTree().getSelection();
		if (items == null || items.length == 0) {
			return;
		}
		final String title = String.format("Wollen Sie den Kandidaten (%s) wirklich löschen?",
				items.length == 1 ? ((CandidateEntry) items[0].getData()).getName() : "...");
		final MessageDialog m = new MessageDialog(getSite().getShell(), "Kandidaten löschen", null, title,
				MessageDialog.QUESTION, 0, "Ja", "Nein");
		if (m.open() == MessageDialog.OK) {
			for (TreeItem i : items) {
				CandidateEntry entry = (CandidateEntry) i.getData();
				try {
					model.delete(entry.getId());
					content.remove(entry);
					getViewer().refresh();
				} catch (SQLException err) {
					UIError.showError("DB Fehler", err);
				}
			}
			getViewer().refresh();
		}
	}

	@Override
	protected void addAction() {
	}

	@Override
	protected boolean isAddAllowed() {
		return false;
	}

	@Override
	public void refresh() {
		refreshAction();
	}

}
