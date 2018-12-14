package com.hogo.portal.candidates;

import java.util.Collection;
import java.util.Iterator;

import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class PersonContentProvider implements ITreeContentProvider, Collection<CandidateEntry> {

	Collection<CandidateEntry> all; 
	
	public int size() {
		return all.size();
	}

	public boolean isEmpty() {
		return all.isEmpty();
	}

	public boolean contains(Object o) {
		return all.contains(o);
	}

	public Iterator<CandidateEntry> iterator() {
		return all.iterator();
	}

	public Object[] toArray() {
		return all.toArray();
	}

	public <T> T[] toArray(T[] a) {
		return all.toArray(a);
	}

	public boolean add(CandidateEntry e) {
		return all.add(e);
	}

	public boolean remove(Object o) {
		return all.remove(o);
	}

	public boolean containsAll(Collection<?> c) {
		return all.containsAll(c);
	}

	public boolean addAll(Collection<? extends CandidateEntry> c) {
		return all.addAll(c);
	}

	public boolean removeAll(Collection<?> c) {
		return all.removeAll(c);
	}

	public boolean retainAll(Collection<?> c) {
		return all.retainAll(c);
	}

	public void clear() {
		all.clear();
	}


	@SuppressWarnings("unchecked")
	@Override
	public Object[] getElements(Object inputElement) {
		if (inputElement instanceof Collection<?>) {
			all = ((Collection<CandidateEntry>)inputElement);
			return all.toArray();
		}
		if (inputElement instanceof CandidateEntry) {
			CandidateEntry entry = (CandidateEntry)inputElement;
			return entry.toArray();
		}
		return new Object[0];
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if (parentElement instanceof Collection<?>) {
			return ((Collection<?>)parentElement).toArray();
		}
		if (parentElement instanceof CandidateEntry) {
		}
		return new Object[0];
	}

	@Override
	public Object getParent(Object element) {
		if (element instanceof Collection<?>) {
			return all;
		}
		
		return null;
	}

	@Override
	public boolean hasChildren(Object inputElement) {
		if (inputElement instanceof Collection<?>) {
			return !((Collection<?>)inputElement).isEmpty();
		}
		return false;
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}
	
}
