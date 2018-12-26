package com.hogo.portal.statistics;

import org.daro.common.ui.UIError;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;

import com.hogo.portal.candidate.ui.AbstractTreeContentProvider;
import com.hogo.portal.candidate.ui.AbstractView;

public class StatisticsView extends AbstractView {
	
	StatisticsModel model;

	public StatisticsView() {
	}

	public static final String ID = "com.hogo.portal.statistics.StatisticsView";
	
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
		getViewer().setLabelProvider(new StatisticsTableLabelProvider());
		
		try {
			model = StatisticsModel.open();
			refreshAction();
		} catch (Exception e) {
			UIError.showError("DB Fehler", e);
		}
	}
	
	protected boolean isAddAllowed() {
		return false;
	}
	
	@Override
	protected boolean isDeleteAllowed() {
		return false;
	}

	@Override
	protected TreeViewerColumn[] createColumns(TreeViewer viewer) {
		TreeViewerColumn[] cols = new TreeViewerColumn[5];
		cols[0] = new TreeViewerColumn(viewer, SWT.LEFT);
		cols[0].getColumn().setText("Beruf");
		cols[0].getColumn().setWidth(300);
		
		cols[1] = new TreeViewerColumn(viewer, SWT.RIGHT);
		cols[1].getColumn().setText("Summe");
		cols[1].getColumn().setWidth(150);
		
		cols[2] = new TreeViewerColumn(viewer, SWT.RIGHT);
		cols[2].getColumn().setText("Frei");
		cols[2].getColumn().setWidth(150);
		
		cols[3] = new TreeViewerColumn(viewer, SWT.RIGHT);
		cols[3].getColumn().setText("Reserviert");
		cols[3].getColumn().setWidth(150);
		
		cols[4] = new TreeViewerColumn(viewer, SWT.RIGHT);
		cols[4].getColumn().setText("Besetzt");
		cols[4].getColumn().setWidth(150);
		
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
		return new AbstractTreeContentProvider<StatisticsEntry>( size -> new StatisticsEntry[size] ) {
			@Override
			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof StatisticsEntry) {
					StatisticsEntry e = (StatisticsEntry)parentElement;
					return new Object[] { e.getSkill(), e.getSum(), e.getFree(), e.getReserved(), e.getBusy() };
				}
				return new Object[0];
			}
		};
	}
	
}
