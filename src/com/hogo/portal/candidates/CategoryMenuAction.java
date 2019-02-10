package com.hogo.portal.candidates;

import org.eclipse.jface.action.Action;

public class CategoryMenuAction extends Action {
	final Runnable action;
	boolean checked;
	
	public CategoryMenuAction(String title, Runnable action) {
		this(title, action, false);
	}

	public CategoryMenuAction(String title, Runnable action, boolean checked) {
		super(title, Action.AS_RADIO_BUTTON);
		this.action = action;
		this.checked = checked;
		setChecked(checked);
	}
	
	@Override
	public void run() {
		action.run();
	}
}
