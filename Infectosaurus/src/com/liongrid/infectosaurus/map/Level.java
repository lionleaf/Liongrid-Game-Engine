package com.liongrid.infectosaurus.map;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Panel;

public class Level {
	
	public Map map;
	public int difficulty;
	public int population;
	
	
	public void loadLevelFromFile(int res){
		Panel panel = BaseObject.gamePointers.panel;
		InputStream inputStream = panel.getResources().openRawResource(res);
		SAXParserFactory factory = SAXParserFactory.newInstance();
	    try {
	            SAXParser parser = factory.newSAXParser();
	            LevelSaxParser handler = new LevelSaxParser(this);
	            parser.parse(inputStream, handler);
	            
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
