package com.hogo.portal;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IPersistableElement;

/**
 * @since 3.1
 */
public class NullEditorInput implements IEditorInput {

	/**
	 * Creates a <code>NullEditorInput</code>.
	 */
	public NullEditorInput() {
	}

    @Override
	public boolean exists() {
        return false;
    }

    @Override
	public ImageDescriptor getImageDescriptor() {
        return ImageDescriptor.getMissingImageDescriptor();
    }

    @Override
	public String getName() {
        return ""; //$NON-NLS-1$
    }

    @Override
	public IPersistableElement getPersistable() {
        return null;
    }

    @Override
	public String getToolTipText() {
        return ""; //$NON-NLS-1$
    }

    @Override
	public <T> T getAdapter(Class<T> adapter) {
        return null;
    }

}
