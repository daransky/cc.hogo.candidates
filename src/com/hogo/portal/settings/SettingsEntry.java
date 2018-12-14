package com.hogo.portal.settings;

import java.util.Map;

public class SettingsEntry implements Map.Entry<String, String> {

	private final String key;
	private String value;
	
	public SettingsEntry(String key) {
		this.key = key;
	}
	
	public SettingsEntry(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getValue() {
		return value;
	}

	@Override
	public String setValue(String value) {
		this.value = value;
		return value;
	}

}
