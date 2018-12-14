package com.hogo.portal.candidate.ui;

import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Label;

public class Comment extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private StyledText text;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public Comment(Composite parent) {
		super(parent, SWT.NONE);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(new GridLayout(2, false));

		Label lblNewLabel = new Label(this, SWT.NONE);
		toolkit.adapt(lblNewLabel, true, true);

		text = new StyledText(this, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 4));
		toolkit.adapt(text);
		toolkit.paintBordersFor(text);

		new Label(this, SWT.NONE);

		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		toolkit.adapt(lblNewLabel_1, true, true);

		Label lblNewLabel_2 = new Label(this, SWT.NONE);
		toolkit.adapt(lblNewLabel_2, true, true);

	}

	public StyledText getText() {
		return text;
	}

}
