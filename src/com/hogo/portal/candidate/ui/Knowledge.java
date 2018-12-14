package com.hogo.portal.candidate.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.hogo.portal.skills.SkillsModel;

public class Knowledge extends Composite {

	private final FormToolkit toolkit = new FormToolkit(Display.getCurrent());
	private final SelectionTable knowledgeTable, usageTable;
	private Button btnMobil, btnStaplerschein, btnHochregal, btnSeitenhub;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public Knowledge(Composite parent, int style) {
		super(parent, style);
		addDisposeListener( e -> toolkit.dispose() );
		toolkit.adapt(this);
		toolkit.paintBordersFor(this);
		setLayout(new GridLayout(1, false));

		Composite group = new Composite(this, SWT.NONE);
		group.setLayout(new GridLayout(4, false));
		group.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		toolkit.adapt(group);
		toolkit.paintBordersFor(group);

		btnMobil = new Button(group, SWT.CHECK);
		toolkit.adapt(btnMobil, true, true);
		btnMobil.setText("Ist mobil");

		btnStaplerschein = new Button(group, SWT.CHECK);
		toolkit.adapt(btnStaplerschein, true, true);
		btnStaplerschein.setText("Staplerschein");

		btnHochregal = new Button(group, SWT.CHECK);
		toolkit.adapt(btnHochregal, true, true);
		btnHochregal.setText("Hochregal");

		btnSeitenhub = new Button(group, SWT.CHECK);
		toolkit.adapt(btnSeitenhub, true, true);
		btnSeitenhub.setText("Seitenhub");

		knowledgeTable = new SelectionTable(this, SkillsModel.getCachedSkills());
		knowledgeTable.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		knowledgeTable.setChooserText("Fähigkeiten");
		
		usageTable = new SelectionTable(this,SkillsModel.getCachedSkills());
		usageTable.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		usageTable.setChooserText("Einsetzbar");
	}

	public SelectionTable getKnowledgeTable() {
		return knowledgeTable;
	}

	public SelectionTable getUsageTable() {
		return usageTable;
	}

	public Button getBtnMobil() {
		return btnMobil;
	}

	public Button getBtnStaplerschein() {
		return btnStaplerschein;
	}

	public Button getBtnHochregal() {
		return btnHochregal;
	}

	public Button getBtnSeitenhub() {
		return btnSeitenhub;
	}
}
