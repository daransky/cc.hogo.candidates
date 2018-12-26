package com.hogo.portal.candidate.ui;

import org.eclipse.jface.fieldassist.AutoCompleteField;
import org.eclipse.jface.fieldassist.TextContentAdapter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

public class Address extends Composite  {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Text txtStrasse;
	private Text txtStadt;
	private Text txtPLZ;
	private Text txtCountry;

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public Address(Composite parent, int style) {
		super(parent, style);
		addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				toolkit.dispose();
			}
		});
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(new GridLayout(2, false));
		
		Label lblStrasse = new Label(this, SWT.NONE);
		lblStrasse.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblStrasse, true, true);
		lblStrasse.setText("Strasse :");
		
		txtStrasse = new Text(this, SWT.BORDER);
		txtStrasse.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtStrasse.addModifyListener( e -> e = null );
		toolkit.adapt(txtStrasse, true, true);
		
		Label lblStadt = new Label(this, SWT.NONE);
		lblStadt.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblStadt, true, true);
		lblStadt.setText("Stadt :");
		
		txtStadt = new Text(this, SWT.BORDER);
		txtStadt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(txtStadt, true, true);
		
		Label lblPlz = new Label(this, SWT.NONE);
		lblPlz.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblPlz, true, true);
		lblPlz.setText("PLZ :");
		
		txtPLZ = new Text(this, SWT.BORDER);
		txtPLZ.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(txtPLZ, true, true);
		
		Label lblCountry = new Label(this, SWT.NONE);
		lblCountry.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblCountry, true, true);
		lblCountry.setText("Land :");
		txtCountry = new Text(this, SWT.BORDER);
		txtCountry.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(txtCountry, true, true);
		AutoCompleteField at = new AutoCompleteField(txtCountry, new TextContentAdapter(), Countries.getCountryNames());
		at.setProposals(Countries.getCountryNames());
	}

	public Text getTxtStrasse() {
		return txtStrasse;
	}

	public Text getTxtStadt() {
		return txtStadt;
	}

	public Text getTxtPLZ() {
		return txtPLZ;
	}

	public Text getTxtCountry() {
		return txtCountry;
	}

}
