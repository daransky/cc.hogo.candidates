package com.hogo.portal.candidate.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.wb.swt.SWTResourceManager;

import com.hogo.portal.candidates.LanguageKnowledge;
import com.hogo.portal.candidates.Status;

import org.eclipse.swt.widgets.DateTime;

public class BasicInfo extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private Text txtVorname;
	private Text txtNachname;
	private Text txtTel1;
	private Text txtTel2;
	private Text txtEmail;
	private Button chkBlacklist, btnWeiblich, btnMaenlich;
	private Combo cmbDeutsch;
	private Combo cmbStatus;
	private Label lblVerfgbar;
	private DateTime datVerfuegbar;
	private DateTime datBirth;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public BasicInfo(Composite parent, int style) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		addDisposeListener( e -> toolkit.dispose() );
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(new GridLayout(2, false));

		Label lblVorname = new Label(this, SWT.NONE);
		lblVorname.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblVorname, true, true);
		lblVorname.setText("Vorname :");

		txtVorname = new Text(this, SWT.BORDER);
		txtVorname.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		toolkit.adapt(txtVorname, true, true);

		Label lblNachname = new Label(this, SWT.NONE);
		lblNachname.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblNachname, true, true);
		lblNachname.setText("Nachname :");

		txtNachname = new Text(this, SWT.BORDER);
		txtNachname.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(txtNachname, true, true);

		Label lblTel = new Label(this, SWT.NONE);
		lblTel.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblTel, true, true);
		lblTel.setText("Tel1 :");

		txtTel1 = new Text(this, SWT.BORDER);
		txtTel1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(txtTel1, true, true);

		Label lblTel_1 = new Label(this, SWT.NONE);
		lblTel_1.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblTel_1, true, true);
		lblTel_1.setText("Tel2 :");

		txtTel2 = new Text(this, SWT.BORDER);
		txtTel2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(txtTel2, true, true);

		Label lblEmail = new Label(this, SWT.NONE);
		lblEmail.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblEmail, true, true);
		lblEmail.setText("email :");

		txtEmail = new Text(this, SWT.BORDER);
		txtEmail.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1, 1));
		toolkit.adapt(txtEmail, true, true);

		Label lblGeschlecht = new Label(this, SWT.NONE);
		lblGeschlecht.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblGeschlecht, true, true);
		lblGeschlecht.setText("Geschlecht :");

		Composite comSex = toolkit.createComposite(this, SWT.NONE);
		toolkit.paintBordersFor(comSex);
		comSex.setLayout(new GridLayout(2, false));

		btnMaenlich = new Button(comSex, SWT.RADIO);
		toolkit.adapt(btnMaenlich, true, true);
		btnMaenlich.setText("Mänlich");

		btnWeiblich = new Button(comSex, SWT.RADIO);
		toolkit.adapt(btnWeiblich, true, true);
		btnWeiblich.setText("Weiblich");
		
		Label lblGeburtsdatum = new Label(this, SWT.NONE);
		lblGeburtsdatum.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblGeburtsdatum, true, true);
		lblGeburtsdatum.setText("Geburtsdatum :");
		
		datBirth = new DateTime(this, SWT.BORDER);
		toolkit.adapt(datBirth);
		toolkit.paintBordersFor(datBirth);

		Label lblDeutsch = new Label(this, SWT.NONE);
		lblDeutsch.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblDeutsch, true, true);
		lblDeutsch.setText("Deutsch :");

		cmbDeutsch = new Combo(this, SWT.READ_ONLY);
		cmbDeutsch.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		for(LanguageKnowledge e : LanguageKnowledge.values())
			cmbDeutsch.add(e.toString());
		
		cmbDeutsch.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(cmbDeutsch);
		toolkit.paintBordersFor(cmbDeutsch);
		
		Label lblStatus = new Label(this, SWT.NONE);
		lblStatus.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblStatus, true, true);
		lblStatus.setText("Status :");
		
		cmbStatus = new Combo(this, SWT.READ_ONLY);
		cmbStatus.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_WHITE));
		for(Status e : Status.values())
			cmbStatus.add(e.toString());
		cmbStatus.select(0);
		cmbStatus.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(cmbStatus);
		toolkit.paintBordersFor(cmbStatus);
		
		lblVerfgbar = new Label(this, SWT.NONE);
		lblVerfgbar.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblVerfgbar, true, true);
		lblVerfgbar.setText("Verfügbar ab :");
		
		datVerfuegbar = new DateTime(this, SWT.BORDER);
		datVerfuegbar.setTime(0, 0, 0);
		
		toolkit.adapt(datVerfuegbar);
		toolkit.paintBordersFor(datVerfuegbar);

		Label lblBlacklist = new Label(this, SWT.NONE);
		lblBlacklist.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		toolkit.adapt(lblBlacklist, true, true);
		lblBlacklist.setText("Blacklist :");

		chkBlacklist = new Button(this, SWT.CHECK);
		toolkit.adapt(chkBlacklist, true, true);
	}

	public Text getTxtVorname() {
		return txtVorname;
	}

	public Text getTxtNachname() {
		return txtNachname;
	}

	public Text getTxtTel2() {
		return txtTel2;
	}

	public Text getTxtTel1() {
		return txtTel1;
	}

	public Text getTxtEmail() {
		return txtEmail;
	}

	public Button getChkBlacklist() {
		return chkBlacklist;
	}

	public void setMaenlich(boolean arg) {
		btnMaenlich.setSelection(arg);
		btnWeiblich.setSelection(!arg);
	}

	public boolean isMaenlich() {
		return btnMaenlich.getSelection();
	}

	public Button getBtnMaenlich() {
		return btnMaenlich;
	}

	public Button getBtnWeiblich() {
		return btnWeiblich;
	}

	public void setBtnWeiblich(Button btnWeiblich) {
		this.btnWeiblich = btnWeiblich;
	}

	public Combo getCmbDeutsch() {
		return cmbDeutsch;
	}
	
	public DateTime	getDatVerfuegbar() {
		return datVerfuegbar;
	}

	public DateTime getDatBirth() {
		return datBirth;
	}

	public Combo getCmbStatus() {
		return cmbStatus;
	}
}
