package com.hogo.portal.candidates;

public enum Sex {

	Man,
	Wife;
	
	public static Sex valueOf(char arg) { 
		return (Character.toUpperCase(arg) == 'M') ? Man : Wife;
	}
	
}
