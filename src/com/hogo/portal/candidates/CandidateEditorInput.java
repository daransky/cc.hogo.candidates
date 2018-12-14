package com.hogo.portal.candidates;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.hogo.portal.Activator;

public class CandidateEditorInput implements IEditorInput {

	private CandidateEntry	entry = null;
	
	public CandidateEditorInput(CandidateEntry e) {
		entry = e;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return (T)entry;
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return Activator.getImageDescriptor("icons/candidate.gif");
	}

	@Override
	public String getName() {
		return "Kandidaten Editor";
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@Override
	public String getToolTipText() {
		return null;
	}

}
