package com.hogo.portal.views.log;

import org.daro.common.ui.UIError;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.hogo.portal.candidate.ui.AbstractTreeContentProvider;
import com.hogo.portal.candidate.ui.AbstractView;

public class LogView extends AbstractView {

	public static final String ID = "com.hogo.portal.views.log.LogView";
	private LogModel	model;
	
	public void refreshAction() {
		getViewer().getTree().removeAll();
		
		try {
			getViewer().setInput(model.select());
		} catch (Exception e) {
			UIError.showError("DB Fehler", e);
		}
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		getViewer().setLabelProvider(new LogTableLabelProvider());
		try {
			model = LogModel.open();
			refreshAction();
		} catch (Exception e) {
			UIError.showError("DB Fehler", e);
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		try {
			model.close();
		} catch (Exception e) {
			UIError.showError("DB Fehler", e);
		}
	}
	
	@Override
	protected boolean isAddAllowed() {
		return false;
	}
	
	@Override
	protected boolean isDeleteAllowed() {
		return false;
	}

	@Override
	protected TreeViewerColumn[] createColumns(TreeViewer viewer) {
		TreeViewerColumn[] cols = new TreeViewerColumn[2];
		cols[0] = new TreeViewerColumn(viewer, SWT.LEFT);
		cols[0].getColumn().setText("Datum");
		cols[0].getColumn().setWidth(150);
		
		cols[1] = new TreeViewerColumn(viewer, SWT.LEFT);
		cols[1].getColumn().setText("Text");
		cols[1].getColumn().setWidth(400);
		
		return cols;
		
	}

	@Override
	protected void addAction() {//
	}

	@Override
	protected void deleteAction() {//
	}

	@Override
	protected void openAction() {//
	}

	@Override
	protected ITreeContentProvider createViewContent() {
		return new AbstractTreeContentProvider<LogEntry>( size -> new LogEntry[size] ) {
			@Override
			public Object[] getChildren(Object parentElement) {
				LogEntry e = (LogEntry)parentElement;
				return new Object[] { e.getTime(), e.getMessage() };
			}
		};
	}
	
}
