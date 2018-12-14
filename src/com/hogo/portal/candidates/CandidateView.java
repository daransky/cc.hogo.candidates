package com.hogo.portal.candidates;

import java.sql.SQLException;
import java.util.Collection;

import org.daro.common.ui.UIError;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeItem;

import com.hogo.portal.Activator;
import com.hogo.portal.OpenCandidateEditor;
import com.hogo.portal.candidate.ui.AbstractView;

public class CandidateView extends AbstractView {

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
				open.run((CandidateEntry) i.getData());
			}
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		getViewer().setLabelProvider(new CandidateTableLabelProvider());
		try {
			model = CandidateModel.open();
			refreshAction();
		} catch (SQLException e) {
			UIError.showError("DB Fehler", e);
		}
	}

	@Override
	protected void refreshAction() {
		getViewer().getTree().removeAll();
		try {
			Collection<CandidateEntry> result = model.select();
			getViewer().setInput(result);
		} catch (SQLException e) {
			UIError.showError("DB Fehler", e);
		}
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

}
