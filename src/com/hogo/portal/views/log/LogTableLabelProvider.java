package com.hogo.portal.views.log;

import java.time.format.DateTimeFormatter;

import org.eclipse.swt.graphics.Image;

import com.hogo.portal.AbstractTableLabelProvider;
import com.hogo.portal.Activator;

public class LogTableLabelProvider extends AbstractTableLabelProvider {
	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy,hh:mm:ss");
	private Image info, warning, error;

	@Override
	public String getColumnText(Object element, int columnIndex) {
		LogEntry e = (LogEntry) element;
		switch (columnIndex) {
		case 1:
			return FORMATTER.format(e.getTime());
		case 2:
			return e.getMessage();
		default:
		}
		return null;
	}

	@Override
	public Image getColumnImage(Object element, int columnIndex) {
		if (columnIndex == 0) {
			LogEntry e = (LogEntry) element;
			switch (e.getType()) {
			case INFO:
				if (info == null)
					info = Activator.getImageDescriptor("icons/info_tsk.png").createImage();
				return info;
			case WARNING:
				if (warning == null)
					warning = Activator.getImageDescriptor("icons/warn_tsk.png").createImage();
				return warning;
			case ERROR:
				if (error == null)
					error = Activator.getImageDescriptor("icons/error_tsk.png").createImage();
				return error;
			}
		}
		return null;
	}
}
