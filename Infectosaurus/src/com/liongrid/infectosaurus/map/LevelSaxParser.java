package com.liongrid.infectosaurus.map;

import java.lang.reflect.Field;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class LevelSaxParser extends DefaultHandler {

	private Level level;

	public LevelSaxParser(Level level) {
		this.level = level;
	}

	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		
		super.startElement(uri, localName, qName, attributes);
		if(localName.equalsIgnoreCase("level")){
			level.id = Integer.parseInt(attributes.getValue("id"));
			level.population = Integer.parseInt(attributes.getValue("population"));
			level.difficulty = Integer.parseInt(attributes.getValue("difficulty"));
		}
		
	}
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		
		super.endElement(uri, localName, qName);
		
	}
	
	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		
	}
	
	/**
	 * Dangerous! Using undocumented API, might change!
	 * 
	 * @param variableName
	 * @param c
	 * @return
	 */
	private int getResId(String variableName, Class<?> c) {
	    try {
	        Field idField = c.getDeclaredField(variableName.toLowerCase());
	        return idField.getInt(idField);
	    } catch (Exception e) {
	        e.printStackTrace();
	        return -1;
	    } 
	}
}
