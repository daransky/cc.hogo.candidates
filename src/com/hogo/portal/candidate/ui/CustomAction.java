package com.hogo.portal.candidate.ui;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;

public class CustomAction extends Action implements IWorkbenchAction {

	public static final String ID = "com.hogo.portal.candidate.ui.CustomAction";
	final Runnable action;

	public CustomAction(String text, ImageDescriptor icon, Runnable action) {
		this.action = action;
		setText(text);
		setImageDescriptor(icon);
	}
	
	@Override
	public void run() {
		action.run();
	}
	
	@Override
	public void dispose() {
		//
	}

}
