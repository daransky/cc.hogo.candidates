package com.hogo.portal.candidates;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Display;

public enum Status {

	Free("Frei", null),
	Reserved("Reserviert", new Color(Display.getCurrent(), 255, 215, 180)),
	Busy("Besetzt", new Color(Display.getCurrent(), 255, 206, 231) );
	
	private final String text;
	private final Color	color;
	
	Status(String arg, Color color) { 
		this.text = arg;
		this.color = color;
		
		;
	}
	
	@Override
	public String toString() {
		return text;
	}

	public Color getColor() {
		return color;
	}
}
