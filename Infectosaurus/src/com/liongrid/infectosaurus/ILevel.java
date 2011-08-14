package com.liongrid.infectosaurus;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.liongrid.gameengine.LBaseObject;
import com.liongrid.gameengine.LGamePointers;
import com.liongrid.gameengine.LSurfaceViewPanel;
import com.liongrid.gameengine.LMap;
import com.liongrid.infectosaurus.R;

public class ILevel {
	
	public LMap map;
	public int difficulty;
	public int population;
	public int id;
	public String name;
	
	public ILevel() {
		loadLevelFromFile(1);
	}
	
	
	public void loadLevelFromFile(int level){
		LSurfaceViewPanel panel = LGamePointers.panel;
		InputStream inputStream = panel.getResources().openRawResource(R.raw.levels);
		SAXParserFactory factory = SAXParserFactory.newInstance();
	    try {
	            SAXParser parser = factory.newSAXParser();
	            ILevelSaxParser handler = new ILevelSaxParser(this);
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
