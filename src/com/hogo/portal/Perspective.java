package com.hogo.portal;

import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import com.hogo.portal.candidates.CandidateView;
import com.hogo.portal.settings.SettingsView;
import com.hogo.portal.skills.SkillsView;
import com.hogo.portal.views.log.LogView;

public class Perspective implements IPerspectiveFactory {

	/**
	 * The ID of the perspective as specified in the extension.
	 */
	public static final String ID = "Personal-Portal.perspective";

	@Override
	public void createInitialLayout(IPageLayout layout) {
		String editorArea = layout.getEditorArea();
		layout.setEditorAreaVisible(false);
		layout.addView(CandidateView.ID, IPageLayout.LEFT, 0.7f, editorArea);
		layout.addView(SettingsView.ID, IPageLayout.LEFT, 0.7f, editorArea);
		layout.addView(LogView.ID, IPageLayout.LEFT, 0.7f, editorArea);
		layout.addView(SkillsView.ID, IPageLayout.LEFT, 0.7f, editorArea);
	}
}
