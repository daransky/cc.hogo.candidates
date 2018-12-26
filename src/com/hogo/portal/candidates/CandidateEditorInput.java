package com.hogo.portal.candidates;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

import com.hogo.portal.Activator;
import com.hogo.portal.candidate.ui.Refreshable;

public class CandidateEditorInput implements IEditorInput {
	private CandidateEntry	entry = null;
	private Refreshable 	parent = null;
	
	public CandidateEditorInput(CandidateEntry e, Refreshable parent) {
		this.entry = e;
		this.parent = parent;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T> T getAdapter(Class<T> adapter) {
		return ( CandidateEntry.class.isAssignableFrom(adapter) )  ? (T)entry : null;
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

	public Refreshable getParent() {
		return parent;
	}
}
