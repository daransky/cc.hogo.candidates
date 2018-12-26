package com.hogo.portal.statistics;

import org.daro.common.ui.TableContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Table;

import com.hogo.portal.candidates.Status;

public class StatisticsTable extends TableViewer {

	public StatisticsTable(Table table) {
		super(table);
		table.setLinesVisible(true);
		table.setHeaderVisible(true);

		addColumn("Beruf", 200, SWT.LEFT);
		addColumn("Insgesamt", 180, SWT.NONE);
		addColumn(Status.Free.toString(), 150, SWT.NONE);
		addColumn(Status.Reserved.toString(), 150, SWT.NONE);
		addColumn(Status.Busy.toString(), 150, SWT.NONE);

		setContentProvider(new TableContentProvider<>(this));
		setLabelProvider(new StatisticsTableLabelProvider());
	}
	
	TableViewerColumn addColumn(String text, int width, int swt) { 
		TableViewerColumn col = new TableViewerColumn(this, swt);
		col.getColumn().setText(text);
		col.getColumn().setWidth(width);
		return col;
	}

}
