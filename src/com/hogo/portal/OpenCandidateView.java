package com.hogo.portal;

import org.daro.common.ui.UIError;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import com.hogo.portal.candidates.CandidateView;


public class OpenCandidateView extends Action {
	
	private final IWorkbenchWindow window;
	private int instanceNum = 0;
	
	public OpenCandidateView(IWorkbenchWindow window) {
		this.window = window;
        setText("Kandidatenansicht");
        // The id is used to refer to the action in a menu or toolbar
		setId(ICommandIds.CMD_OPEN);
        // Associate the action with a pre-defined command, to allow key bindings.
		setActionDefinitionId(ICommandIds.CMD_OPEN);
		setImageDescriptor(Activator.getImageDescriptor("icons/candidates.png"));
	}
	
	@Override
	public void run() {
		if(window != null) {	
			try {
				window.getActivePage().showView(CandidateView.ID, Integer.toString(instanceNum++), IWorkbenchPage.VIEW_ACTIVATE);
			} catch (PartInitException e) {
				UIError.showError("Error opening view:" + e.getMessage(), e);
			}
		}
	}
}
