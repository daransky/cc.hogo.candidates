package com.hogo.portal.candidates;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Collection;

import org.daro.common.ui.UIError;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
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
import com.hogo.portal.candidates.Categories.Category;

public class CandidateView extends AbstractView implements Refreshable {
	public static final String ID = "com.hogo.portal.candidates.CandidateView";
	private CandidateModel model;
	private PersonContentProvider content;

	private enum CategoryType {
		NONE, DATE
	}

	private CategoryType categoryType = CategoryType.DATE;

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
		createCategoryMenu();

		try {
			model = CandidateModel.open();
			refreshAction();
		} catch (SQLException e) {
			UIError.showError("DB Fehler", e);
		}
	}

	class CategoryAction extends Action {

	}

	void createCategoryMenu() {
		IMenuManager menu = getViewSite().getActionBars().getMenuManager();
		IContributionItem[] items = menu.getItems();
		menu.removeAll();

		MenuManager m1 = new MenuManager("Spalten");
		for (IContributionItem item : items)
			m1.add(item);

		menu.add(m1);

		Runnable none = () -> {
			categoryType = CategoryType.NONE;
			refreshAction();
		};

		Runnable date = () -> {
			categoryType = CategoryType.DATE;
			refreshAction();
		};

		MenuManager m2 = new MenuManager("Grupieren");

		m2.add(new CategoryMenuAction("Keine", none, true));
		m2.add(new CategoryMenuAction("Datum", date));
		menu.add(m2);

		menu.update();
	}

	@Override
	protected void refreshAction() {
		getSite().getShell().getDisplay().asyncExec(() -> {
			getViewer().getTree().removeAll();
			try {
				Collection<CandidateEntry> result;
				switch (categoryType) {
				case NONE:
					result = model.select();
					getViewer().setInput(result);
					break;
				case DATE:
					Categories categories = new Categories();
					result = model.selectLastWeek();
					categories.add(new Category(String.format("Letzte Woche (%d)", result.size()), result));
					
					result = model.selectLastMonth();
					categories.add(new Category(String.format("Letzter Monat (%d)", result.size()), result));
					
					result = model.selectOlderThan(LocalDateTime.now().minusMonths(1));
					categories.add(new Category(String.format("�ltere (%d)", result.size()), result));

					getViewer().setInput(categories);

					TreeItem item = getViewer().getTree().getItem(0);
					getViewer().expandToLevel(item.getData(),TreeViewer.ALL_LEVELS, true);
					break;
					
				}
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
		final String title = String.format("Wollen Sie den Kandidaten (%s) wirklich l�schen?",
				items.length == 1 ? ((CandidateEntry) items[0].getData()).getName() : "...");
		final MessageDialog m = new MessageDialog(getSite().getShell(), "Kandidaten l�schen", null, title,
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
