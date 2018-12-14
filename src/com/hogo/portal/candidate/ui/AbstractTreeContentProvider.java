package com.hogo.portal.candidate.ui;

import java.util.Collection;
import java.util.function.IntFunction;

import org.eclipse.jface.viewers.ITreeContentProvider;

public abstract class AbstractTreeContentProvider<T> implements ITreeContentProvider {

	final IntFunction<T[]> constructor;

	public AbstractTreeContentProvider(IntFunction<T[]> constructor) {
		this.constructor = constructor;
	}

	@Override
	public boolean hasChildren(Object element) {
		return false;
	}

	@Override
	public Object getParent(Object element) {
		return null;
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Collection) {
			@SuppressWarnings("unchecked")
			Collection<T> content = (Collection<T>) inputElement;
			return content.toArray(constructor.apply(content.size()));
		}
		return new Object[0];
	}
}
