package com.liongrid.gameengine;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.liongrid.infectomancer.R;

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
	
	public LTileSet(){
		initTileTypes();
	}
	/**
	 * Load the tileSet! Must be called before any tiles can be drawn 
	 */
	public void initTileTypes(){
		loadTileSet(R.raw.tileset);
		/*LSurfaceViewPanel panel = LBaseObject.gamePointers.panel;
		int tile1 = R.drawable.tile1;
		int tile2 = R.drawable.tile2;
		int tile3 = R.drawable.tile3;
		int tile4 = R.drawable.tile4;
		int tile5 = R.drawable.tile5;
		int tile6 = R.drawable.tile6;
		
		
		int[] bitmaps = {tile1,tile2,tile3,tile4,tile5,tile6};
		tileTypes = new LTileType[bitmaps.length];
		for(int i = 0; i < tileTypes.length; i++){
			tileTypes[i] = 
				new LTileType(bitmaps[i], new boolean[2][2][LMovementType.values().length]);
		}*/
	}
	
	public void loadTileSet(int res){
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
