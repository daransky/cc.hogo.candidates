package com.hogo.portal;

import org.daro.common.ui.UIError;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.PartInitException;

public class OpenView extends Action {

	private final String viewId;

	public OpenView(String text, String view, ImageDescriptor image, String actionId) {
		setText(text);
		// The id is used to refer to the action in a menu or toolbar
		setId(actionId);
		// Associate the action with a pre-defined command, to allow key bindings.
		setActionDefinitionId(actionId);
		setImageDescriptor(image);
		this.viewId = view;
	}

	@Override
	public void run() {
		try {
			Activator.getDefault().getWorkbench().getActiveWorkbenchWindow().getActivePage().showView(viewId);
		} catch (PartInitException e) {
			UIError.showError("Error opening view:" + e.getMessage(), e);
		}
	}
}
