package com.liongrid.infectosaurus.map;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LPanel;
import com.liongrid.infectosaurus.R;

public class Level {
	
	public Map map;
	public int difficulty;
	public int population;
	public int id;
	public String name;
	
	public Level() {
		loadLevelFromFile(1);
	}
	
	
	public void loadLevelFromFile(int level){
		LPanel panel = LBaseObject.gamePointers.panel;
		InputStream inputStream = panel.getResources().openRawResource(R.raw.levels);
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
