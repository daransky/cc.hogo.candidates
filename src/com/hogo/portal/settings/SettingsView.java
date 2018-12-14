package com.hogo.portal.settings;

import java.sql.SQLException;
import java.util.Collection;

import org.daro.common.ui.UIError;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;

import com.hogo.portal.AbstractTableLabelProvider;
import com.hogo.portal.candidate.ui.AbstractView;

public class SettingsView extends AbstractView {

	public static final String ID = "com.hogo.portal.settings.SettingsView";
	private SettingsModel model;

	protected TreeViewerColumn[] createColumns(TreeViewer viewer) {
		TreeViewerColumn[] col = new TreeViewerColumn[2];
		col[0] = new TreeViewerColumn(viewer, SWT.NONE);
		col[0].getColumn().setText("Name");
		col[0].getColumn().setWidth(300);

		col[1] = new TreeViewerColumn(viewer, SWT.NONE);
		col[1].getColumn().setText("Wert");
		col[1].getColumn().setWidth(300);

		return col;
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		getViewer().setLabelProvider(new AbstractTableLabelProvider() {

			@Override
			public String getColumnText(Object element, int columnIndex) {
				SettingsEntry e = (SettingsEntry) element;
				return columnIndex == 0 ? e.getKey() : e.getValue();
			}
		});
		try {
			model = SettingsModel.open();
			refreshAction();
		} catch (SQLException e) {
			UIError.showError("DB Fehler", e);
		}
	}

	protected void refreshAction() {
		getViewer().getTree().removeAll();
		try {
			Collection<SettingsEntry> result = model.select();
			getViewer().setInput(result);
		} catch (SQLException e) {
			UIError.showError("DB Fehler", e);
		}
	}

	protected void addAction() {
		SettingsDialog dialog = new SettingsDialog(getSite().getShell());
		if (dialog.open() == Dialog.OK) {
			try {
				model.add(dialog.getEntry());
			} catch (SQLException e1) {
				UIError.showError(getSite().getShell(), "DB Fehler", "Eintrag konnte nicht hinzugefügt werden.", e1);
			}
		}
	}

	@Override
	public void dispose() {
		try {
			model.close();
		} catch (Exception e) {
			UIError.showError("DB Fehler", e);
		}
		super.dispose();
	}

	@Override
	protected void deleteAction() {
		TreeItem[] items = getViewer().getTree().getSelection();
		if (items == null || items.length == 0) {
			return;
		}

		SettingsEntry e = (SettingsEntry) items[0].getData();
		try {
			model.delete(e.getKey());
		} catch (SQLException e1) {
			UIError.showError(getSite().getShell(), "DB Fehler", "Löschen ist fehlgeschlagen.", e1);
		}
	}

	@Override
	protected void openAction() {
		SettingsDialog dialog = new SettingsDialog(getSite().getShell());
		if (dialog.open() == Dialog.OK) {
			try {
				model.update(dialog.getEntry());
			} catch (SQLException e1) {
				UIError.showError(getSite().getShell(), "DB Fehler", "Eintrag konnte nicht hinzugefügt werden.", e1);
			}
		}

	}

	@Override
	protected ITreeContentProvider createViewContent() {
		return new ITreeContentProvider() {

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
					@SuppressWarnings("unchecked")
					Collection<SettingsEntry> content = (Collection<SettingsEntry>) inputElement;
					return content.toArray(new SettingsEntry[content.size()]);
				}
				return new Object[0];
			}

			@Override
			public Object[] getChildren(Object parentElement) {
				SettingsEntry e = (SettingsEntry) parentElement;
				return new String[] { e.getKey(), e.getValue() };
			}
		};
	}

}
