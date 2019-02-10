package com.hogo.portal.candidates;

import java.sql.SQLException;

import org.daro.common.ui.UIError;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.ui.part.EditorPart;

import com.hogo.portal.candidate.ui.Address;
import com.hogo.portal.candidate.ui.BasicInfo;
import com.hogo.portal.candidate.ui.Comment;
import com.hogo.portal.candidate.ui.Knowledge;
import com.hogo.portal.core.Binder;


public class CandidateEditor extends EditorPart {

	public static final String ID = "com.hogo.portal.candidates.CandidateEditor"; //$NON-NLS-1$
	private final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	private Binder<CandidateEntry> binder = null;
	private boolean isNew;
	private BasicInfo basicInfo;
	private Address address;
	private Knowledge knowledge;
	private Comment commentText;
	private CandidateModel model;

	public CandidateEditor() {
		binder = new Binder<>(e -> firePropertyChange(IEditorPart.PROP_DIRTY));
	}

	private void createBasicDataControl(Composite parent) {
		Section section = formToolkit.createSection(parent, Section.TITLE_BAR);
		section.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.paintBordersFor(section);
		section.setText("Grundinformationen");
		section.setExpanded(true);
		basicInfo = new BasicInfo(section, SWT.NONE);

		binder.bind(basicInfo.getTxtVorname(), CandidateEntry::getVorname, CandidateEntry::setVorname);
		binder.bind(basicInfo.getTxtNachname(), CandidateEntry::getNachname, CandidateEntry::setNachname);
		binder.bind(basicInfo.getTxtTel1(), CandidateEntry::getTel1, CandidateEntry::setTel1);
		binder.bind(basicInfo.getTxtTel2(), CandidateEntry::getTel2, CandidateEntry::setTel2);
		binder.bind(basicInfo.getTxtEmail(), CandidateEntry::getEmail, CandidateEntry::setEmail);
		binder.bind(basicInfo.getChkBlacklist(), CandidateEntry::isBlacklist, CandidateEntry::setBlacklist);
		binder.bind(basicInfo.getDatBirth(), CandidateEntry::getBirthDay, CandidateEntry::setBirthDay);
		binder.bind(basicInfo.getDatVerfuegbar(), CandidateEntry::getVerfuegbar, CandidateEntry::setVerfuegbar);
		binder.bind(basicInfo.getBtnMaenlich(), CandidateEntry::isSexMale, CandidateEntry::setSexMale);
		binder.bind(basicInfo.getBtnWeiblich(), CandidateEntry::isSexFemale, CandidateEntry::setSexFemale);
		
		basicInfo.getCmbDeutsch().select(binder.getBean().deutsch.ordinal());
		basicInfo.getCmbDeutsch().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				binder.getBean().deutsch = LanguageKnowledge.values()[basicInfo.getCmbDeutsch().getSelectionIndex()];
				binder.onChange(this);
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		basicInfo.getCmbStatus().select(binder.getBean().status.ordinal());
		basicInfo.getCmbStatus().addSelectionListener(new SelectionListener() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				binder.getBean().status = Status.values()[basicInfo.getCmbStatus().getSelectionIndex()];
				binder.onChange(this);
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});

		section.setClient(basicInfo);
	}

	private void createAddressDataControl(Composite parent) {
		Section section = formToolkit.createSection(parent, Section.TWISTIE | Section.TITLE_BAR);
		section.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.paintBordersFor(section);
		section.setText("Adresse");
		address = new Address(section, SWT.NONE);
		section.setClient(address);
		section.setExpanded(false);

		binder.bind(address.getTxtStrasse(), CandidateEntry::getStrasse, CandidateEntry::setStrasse);
		binder.bind(address.getTxtPLZ(), CandidateEntry::getPlz, CandidateEntry::setPlz);
		binder.bind(address.getTxtStadt(), CandidateEntry::getStadt, CandidateEntry::setStadt);
		binder.bind(address.getTxtCountry(), CandidateEntry::getLand, CandidateEntry::setLand);

	}

	private void createKnowledgeDataControl(Composite parent) {
		Section section = formToolkit.createSection(parent, Section.TWISTIE | Section.TITLE_BAR);
		section.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.paintBordersFor(section);
		section.setText("Kentnisse");
		knowledge = new Knowledge(section, SWT.NONE);
		section.setClient(knowledge);
		section.setExpanded(true);

		binder.bind(knowledge.getBtnHochregal(), CandidateEntry::isHochregal, CandidateEntry::setHochregal);
		binder.bind(knowledge.getBtnMobil(), CandidateEntry::isMobil, CandidateEntry::setMobil);
		binder.bind(knowledge.getBtnSeitenhub(), CandidateEntry::isSeitenhub, CandidateEntry::setSeitenhub);
		binder.bind(knowledge.getBtnStaplerschein(), CandidateEntry::isStaplerschein, CandidateEntry::setStaplerschein);
		knowledge.getKnowledgeTable().setValues(binder.getBean().getGelernt());
		knowledge.getUsageTable().setValues(binder.getBean().getEinsetzbar());

		knowledge.getKnowledgeTable().addModifyListener(value -> {
			binder.getBean().setGelernt(value);
			binder.onChange(this);
		});

		knowledge.getUsageTable().addModifyListener(value -> {
			binder.getBean().setEinsetzbar(value);
			binder.onChange(this);
		});
	}

	private void createCommentDataControl(Composite parent) {
		Section section = formToolkit.createSection(parent, Section.TWISTIE | Section.TITLE_BAR);
		section.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		formToolkit.paintBordersFor(section);
		section.setText("Kommentar");

		commentText = new Comment(section);
		commentText.setLayoutData(new GridData(GridData.FILL_HORIZONTAL | GridData.FILL_VERTICAL));
		formToolkit.adapt(commentText);
		formToolkit.paintBordersFor(commentText);

		binder.bind(commentText.getText(), CandidateEntry::getKommentar, CandidateEntry::setKommentar);

		section.setClient(commentText);
		section.setExpanded(true);
	}

	/**
	 * Create contents of the editor part.
	 * 
	 * @param parent
	 */
	@Override
	public void createPartControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		container.setLayout(new FillLayout(SWT.HORIZONTAL));

		ScrolledForm form = formToolkit.createScrolledForm(container);
		formToolkit.paintBordersFor(form);
		form.getBody().setLayout(new GridLayout(1, false));

		createBasicDataControl(form.getBody());
		createAddressDataControl(form.getBody());
		createKnowledgeDataControl(form.getBody());
		createCommentDataControl(form.getBody());
	}

	@Override
	public void setFocus() {
		//
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		try {
			if (isNew)
				model.add(binder.getBean());
			else
				model.update(binder.getBean());
			
			CandidateEditorInput input = (CandidateEditorInput)getEditorInput();
			if( input != null && input.getParent() != null )
				input.getParent().refresh();
			
			binder.setChanged(false);
			isNew = false;
			firePropertyChange(PROP_DIRTY);
		} catch (SQLException e) {
			UIError.showError("DB Fehler", e);
		}
	}

	@Override
	public void doSaveAs() {
		//
	}

	@SuppressWarnings("deprecation")
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setInput(input);
		CandidateEntry bean = input.getAdapter(CandidateEntry.class);

		try {
			if (model == null)
				model = CandidateModel.open();
		} catch (SQLException e1) {
			UIError.showError("DB Fehler", e1);
		}

		if (bean == null) {
			bean = new CandidateEntry();
			isNew = true;
		} else {
			setTitle(bean.toString());
			try {
				model.read(bean.getId());
			} catch (SQLException e) {
				UIError.showError("DB Fehler", e);
				return;
			}
		}

		binder.setBean(bean);
		setSite(site);
	}
	
	public CandidateEntry	getEntry() {
		return binder.getBean();
	}

	@Override
	public boolean isDirty() {
		return isNew || binder.isChanged();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}
}