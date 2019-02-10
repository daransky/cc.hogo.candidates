package com.hogo.portal.candidates;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

import com.hogo.portal.AbstractTableLabelProvider;
import com.hogo.portal.Activator;
import com.hogo.portal.candidate.ui.PhoneLabelProvider;
import com.hogo.portal.candidates.Categories.Category;

public class CandidateTableLabelProvider extends AbstractTableLabelProvider implements ITableColorProvider {

	private Image autoImage;
	private Image blacklist;
	private final PhoneLabelProvider phone = new PhoneLabelProvider();
	private final DateTimeFormatter germanFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
			.withLocale(Locale.GERMAN);

	@Override
	public String getColumnText(Object element, int columnIndex) {
		if (element instanceof Category) {
			if( columnIndex > 0)
				return "";
			
			return ((Category) element).getName();
		}

		CandidateEntry e = (CandidateEntry) element;

		switch (columnIndex) {
		case 0:
			return e.getName().toString();
		case 1:
			return phone.apply(e.getTel1());
		case 2:
			return e.getDeutsch().toString();
		case 3:
			LocalDate date = e.getVerfuegbar();
			return date != null ? date.format(germanFormatter) : null;
		case 4:
			return e.getEinsetzbar();
		case 5:
			return e.getAdresse();
		case 6:
			return e.getGelernt();
		case 7:
			return e.getKommentar();
		default:
			return null;
		}
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if (element instanceof Category) {
			return null;
		}
		
		CandidateEntry e = (CandidateEntry) element;
		if (columnIndex == 0) {
			if (!e.blacklist && e.mobil) {
				if (autoImage == null) {
					ImageDescriptor imgd = Activator.getImageDescriptor("icons/auto_obj.png");
					autoImage = imgd.createImage();
				}
				return autoImage;
			}
			if (e.blacklist) {
				if (blacklist == null) {
					ImageDescriptor imgd = Activator.getImageDescriptor("icons/attention.png");
					blacklist = imgd.createImage();
				}
				return blacklist;
			}
		}
		return null;
	}

	@Override
	public Color getForeground(Object element, int columnIndex) {
		return null;
	}

	@Override
	public Color getBackground(Object element, int columnIndex) {
		if (element instanceof Category) {
			return null;
		}
		CandidateEntry e = (CandidateEntry) element;
		return e.getStatus().getColor(); 
	}

	@Override
	public void dispose() {
		if (autoImage != null) {
			autoImage.dispose();
			autoImage = null;
		}
		if (blacklist != null) {
			blacklist.dispose();
			blacklist = null;
		}
		super.dispose();
	}

	public static TreeViewerColumn[] createColumns(TreeViewer viewer) {
		TreeViewerColumn col[] = new TreeViewerColumn[8];

		col[0] = new TreeViewerColumn(viewer, SWT.NONE);
		col[0].getColumn().setText("Name");
		col[0].getColumn().setWidth(280);

		col[1] = new TreeViewerColumn(viewer, SWT.NONE);
		col[1].getColumn().setText("Tel");
		col[1].getColumn().setWidth(180);

		col[2] = new TreeViewerColumn(viewer, SWT.NONE);
		col[2].getColumn().setText("Deutsch");
		col[2].getColumn().setWidth(140);

		col[3] = new TreeViewerColumn(viewer, SWT.NONE);
		col[3].getColumn().setText("Verfügbar");
		col[3].getColumn().setWidth(100);

		col[4] = new TreeViewerColumn(viewer, SWT.NONE);
		col[4].getColumn().setText("Einsetzbar");
		col[4].getColumn().setWidth(200);
				
		col[5] = new TreeViewerColumn(viewer, SWT.NONE);
		col[5].getColumn().setText("Adresse");
		col[5].getColumn().setWidth(200);
		
		col[6] = new TreeViewerColumn(viewer, SWT.NONE);
		col[6].getColumn().setText("Gelernt");
		col[6].getColumn().setWidth(200);

		col[7] = new TreeViewerColumn(viewer, SWT.NONE);
		col[7].getColumn().setText("Kommentar");
		col[7].getColumn().setWidth(300);

		return col;
	}
}
