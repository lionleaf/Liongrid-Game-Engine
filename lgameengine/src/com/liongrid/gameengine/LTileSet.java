package com.liongrid.gameengine;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

/**
 * The set of TileTypes. This should be loaded from a xml upon start, 
 * and should probably be initiated before level and start of rendering
 *
 */
public class LTileSet {
	
	/**
	 * The tileTypes. Index should be tileID.
	 * Made public for speed. Please only read 
	 */
	public LTileType[] tileTypes;
	
	public HashMap<Integer,Integer> tileIDtoIndexMap = new HashMap<Integer,Integer>();
	
	public LTileSet(int tileset){
	}
	/**
	 * Load the tileSet! Must be called before any tiles can be drawn 
	 */
	public void initTileTypes(int tileset){
		loadTileSet(tileset);
	}
	
	public void loadTileSet(int res){
		if(res < 0) return;
		LSurfaceViewPanel panel = LGamePointers.panel;
		InputStream inputStream = panel.getResources().openRawResource(res);
		SAXParserFactory factory = SAXParserFactory.newInstance();
	    try {
	            SAXParser parser = factory.newSAXParser();
	            LTileSetSaxParser handler = new LTileSetSaxParser(this);
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
