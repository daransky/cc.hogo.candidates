package com.hogo.portal.statistics;

import com.hogo.portal.AbstractTableLabelProvider;

public class StatisticsTableLabelProvider extends AbstractTableLabelProvider {

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof StatisticsEntry) {
			StatisticsEntry e = (StatisticsEntry) element;
			switch (columnIndex) {
			case 0:
				return e.getSkill();
			case 1:
				return Integer.toString(e.sum);
			case 2:
				return Integer.toString(e.free);
			case 3:
				return Integer.toString(e.reserved);
			case 4:
				return Integer.toString(e.busy);
			default:
			}
		}
		return null;
	}

}
