package com.hogo.portal.candidates;

public enum LanguageKnowledge {

	None(0, "Keine"), Basic(1, "Baustelle"), Average(2, "Grundkentnisse"), Good(3, "Gut"), NativeSpeaker(4, "Muttersprache");

	final short value;
	final String text;

	private LanguageKnowledge(int arg, String text) {
		this.value = (short) arg;
		this.text = text;
	}

	public static LanguageKnowledge parse(String arg) {
		switch(arg) { 
		case	"Keine":	return None;
		case	"Baustelle":return Basic;
		case 	"Grundkentnisse":		return Average;
		case	"Gut":		return Good;
		case	"Muttersprache": return NativeSpeaker;
		}
		return null;
	}

	public static LanguageKnowledge valueOf(short arg) {
		switch (arg) {
		case 0:
			return None;
		case 1:
			return Basic;
		case 2:
			return Average;
		case 3:
			return Good;
		case 4:
			return NativeSpeaker;
		}
		return None;
	}

	@Override
	public String toString() {
		return text;
	}
}
