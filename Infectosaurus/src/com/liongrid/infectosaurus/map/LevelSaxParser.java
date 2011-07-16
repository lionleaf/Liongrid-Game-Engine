package com.liongrid.infectosaurus.map;

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
}
