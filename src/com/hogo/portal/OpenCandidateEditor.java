package com.hogo.portal;

import org.daro.common.ui.UIError;
import org.eclipse.jface.action.Action;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;

import com.hogo.portal.candidate.ui.Refreshable;
import com.hogo.portal.candidates.CandidateEditor;
import com.hogo.portal.candidates.CandidateEditorInput;
import com.hogo.portal.candidates.CandidateEntry;


public class OpenCandidateEditor extends Action {
	
	private final IWorkbenchWindow window;
	private IEditorInput input = new CandidateEditorInput(null, null);
	
	public OpenCandidateEditor(IWorkbenchWindow window) {
		this.window = window;
		setText("Neuer Kandidat");
		setId(ICommandIds.CMD_NEW_EDITOR);
		setActionDefinitionId(ICommandIds.CMD_NEW_EDITOR);
		setImageDescriptor(Activator.getImageDescriptor("icons/new.png"));
	}

	@Override
	public void run() {
		try {
			window.getActivePage().openEditor(input,CandidateEditor.ID);
		} catch (PartInitException e) {
			UIError.showError("Editor kann nicht geöffnet werden", e);
		}
	}
	
	public void run(CandidateEntry entry, Refreshable parent) { 
		this.input = new CandidateEditorInput(entry, parent);
		run();
	}
}
