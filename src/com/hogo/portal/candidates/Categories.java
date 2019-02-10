package com.hogo.portal.candidates;

import java.util.Collection;
import java.util.LinkedList;

public class Categories {
	
	static class Category {

		final String name;
		final Collection<CandidateEntry> children;
		
		public Category(String name, Collection<CandidateEntry> children) {
			super();
			this.name = name;
			this.children = children;
		}

		public String getName() {
			return name;
		}


		public Collection<CandidateEntry> getChildren() {
			return children;
		}
		
		public boolean hasChildren() { 
			return children != null && !children.isEmpty();
		}

	}

	Collection<Category> categories = new LinkedList<>();
	
	public void add(Category category) {
		categories.add(category);
	}
	
	public boolean hasChildren() { 
		return categories != null && !categories.isEmpty();
	}
}
