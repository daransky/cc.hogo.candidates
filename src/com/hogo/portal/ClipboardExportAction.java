package com.hogo.portal;

import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.HTMLTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.PlatformUI;

import com.hogo.portal.candidate.ui.CustomAction;
import com.hogo.portal.candidates.CandidateEditor;
import com.hogo.portal.candidates.CandidateEntry;
import com.hogo.portal.candidates.CandidateHtmlConverter;

public class ClipboardExportAction extends CustomAction {

	public ClipboardExportAction() {
		super("Save to clipboard", Activator.getImageDescriptor("icons/copy.gif"), () -> {
			IEditorPart editor = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor();
			if (editor == null)
				return;
			CandidateEditor cd = (CandidateEditor) editor;
			CandidateEntry entry = cd.getEntry();

			Clipboard clipboard = new Clipboard(Display.getCurrent());
			HTMLTransfer htmlTransfer = HTMLTransfer.getInstance();
			Transfer[] transfers = new Transfer[] { htmlTransfer };

			clipboard.setContents(new Object[] { new CandidateHtmlConverter().apply(entry) }, transfers);
			clipboard.dispose();
		});

	}

	@Override
	public String getId() {
		return "com.hogo.portal.ClipboardExportAction";
	}
}
