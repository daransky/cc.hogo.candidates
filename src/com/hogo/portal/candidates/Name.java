package com.hogo.portal.candidates;

import java.util.function.Function;

public class Name {

	String	firstname;
	String 	surname;
	
	Function<String,String> nameAdapter =  name -> {
		return ( name != null && !name.isEmpty()) ? Character.toUpperCase(name.charAt(0))+name.substring(1) : name;
	};
	 
	public Name() {}
	
	public Name(String surname) {
		this(null, surname);
	}
	
	public Name(String firstname, String surname) {
		this.firstname = nameAdapter.apply(firstname);
		this.surname = nameAdapter.apply(surname);
	}

	public	String	getFirstName() {
		return firstname;
	}

	public	String	getSurName() {
		return surname;
	}
	
	@Override
	public String toString() {
		if( firstname != null )
			return firstname + ' ' + surname;
		return surname;
	}

	public static Name fromString(String string) {
		String[] tmp = string.split(" ");
		if( tmp != null && tmp.length > 1)
			return new Name(tmp[0].trim(), tmp[1].trim());
		return new Name(string);
	}
}
