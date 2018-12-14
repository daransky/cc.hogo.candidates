package com.hogo.portal.settings;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class SettingsDialog extends Dialog {
	private Text txtKey;
	private Text txtValue;

	private String[] entry = new String[2];

	protected SettingsDialog(Shell parentShell) {
		super(parentShell);
	}

	protected SettingsDialog(Shell parentShell, SettingsEntry entry) {
		super(parentShell);
		this.entry[0] = entry.getKey();
		this.entry[1] = entry.getValue();
	}

	@SuppressWarnings("unused")
	@Override
	protected Control createDialogArea(Composite parent) {
		Composite container = (Composite) super.createDialogArea(parent);
		container.setLayout(new GridLayout(4, false));
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);
		new Label(container, SWT.NONE);

		Label lblName = new Label(container, SWT.NONE);
		lblName.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblName.setText("Schlüssel:");

		txtKey = new Text(container, SWT.BORDER);
		txtKey.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtKey.addModifyListener(e -> entry[0] = txtKey.getText());
		if (entry[0] != null) {
			txtKey.setText(entry[0]);
			txtKey.setEditable(false);
		}

		Label lblNewLabel = new Label(container, SWT.NONE);

		Label label = new Label(container, SWT.NONE);

		Label lblValue = new Label(container, SWT.NONE);
		lblValue.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblValue.setText("Wert:");

		txtValue = new Text(container, SWT.BORDER);
		txtValue.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		txtValue.addModifyListener(e -> entry[1] = txtValue.getText());
		if (entry[1] != null) {
			txtValue.setText(entry[1]);
		}
		new Label(container, SWT.NONE);

		return container;
	}

	public SettingsEntry getEntry() {
		return new SettingsEntry(entry[0], entry[1]);
	}

}
