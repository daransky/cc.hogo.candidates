package com.hogo.portal;

import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.ui.dialogs.PatternFilter;

public class FilteredTableFilter extends PatternFilter {
	@Override
	protected boolean isLeafMatch(final Viewer viewer, final Object element) {
		TreeViewer treeViewer = (TreeViewer) viewer;
		int numberOfColumns = treeViewer.getTree().getColumnCount();
		ITableLabelProvider labelProvider = (ITableLabelProvider) treeViewer.getLabelProvider();
		boolean isMatch = false;
		for (int columnIndex = 0; columnIndex < numberOfColumns; columnIndex++) {
			String labelText = labelProvider.getColumnText(element, columnIndex);
			isMatch |= wordMatches(labelText);
		}
		return isMatch;
	}
}
