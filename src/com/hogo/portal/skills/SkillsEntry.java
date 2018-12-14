package com.hogo.portal.skills;

import java.util.Objects;

public class SkillsEntry {
	final String name;
	
	public SkillsEntry(String name) {
		Objects.requireNonNull(name);
		StringBuilder sb = new StringBuilder(name.length());
		sb.append(Character.toUpperCase(name.charAt(0))).append(name.substring(1).toLowerCase());
		this.name = sb.toString();
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object o) {
		return name.equals(o);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}
}
