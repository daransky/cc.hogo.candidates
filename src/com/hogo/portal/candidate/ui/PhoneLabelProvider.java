package com.hogo.portal.candidate.ui;

import java.util.function.Function;

public class PhoneLabelProvider implements Function<String,String> {

	@Override
	public String apply(String t) {
		if( t.isEmpty() )
			return t;
		if(t.startsWith("+"))
			t = t.replace("+", "00");
		t = t.replaceAll("\\s+","").replaceAll("/", "");	
		
		Country c = Countries.getByCode(t);
		if( c != null ) {
			int countryCode =c.getCountryCode().length();
			String num1 = t.substring(2+countryCode,  2+countryCode+3);
			String num2 = t.substring(countryCode+3);
			return String.format("+%s %s/%s", c.getCountryCode(),  num1, num2);
		} 
		
		String num1 = t.substring(0, 4);
		if( num1.charAt(0) == '0')
			num1 = num1.substring(1);
		
		return String.format("+%s %s/%s", Countries.OWN.getCountryCode(), num1, t.substring(4) ); 
	}
}
