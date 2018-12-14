package com.hogo.portal.candidate.ui;

public class Country {
	private final String name;
	private final String isoCode;
	private final String countryCode;
	
	public Country(String name, String countryCode, String isoCode) {
		super();
		this.name = name;
		this.isoCode = isoCode;
		this.countryCode = countryCode;
	}

	public String getName() {
		return name;
	}
	
	public String getIsoCode() {
		return isoCode;
	}
	
	public String getCountryCode() {
		return countryCode;
	}
	
	
}
