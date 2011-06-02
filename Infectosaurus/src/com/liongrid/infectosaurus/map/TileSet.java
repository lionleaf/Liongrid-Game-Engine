package com.liongrid.infectosaurus.map;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.Panel;
import com.liongrid.gameengine.tools.MovementType;
import com.liongrid.infectosaurus.R;

/**
 * The set of TileTypes. This should be loaded from a xml upon start, 
 * and should probably be initiated before level and start of rendering
 *
 */
public class TileSet {
	
	/**
	 * The tileTypes. Index should be tileID.
	 * Made public for speed. Please only read 
	 */
	public TileType[] tileTypes;
	
	public HashMap<Integer,Integer> tileIDtoIndexMap = new HashMap<Integer,Integer>();
	
	public TileSet(){
		initTileTypes();
	}
	/**
	 * Load the tileSet! Must be called before any tiles can be drawn 
	 */
	public void initTileTypes(){
		loadTileSet(R.raw.tileset);
		/*Panel panel = BaseObject.gamePointers.panel;
		int tile1 = R.drawable.tile1;
		int tile2 = R.drawable.tile2;
		int tile3 = R.drawable.tile3;
		int tile4 = R.drawable.tile4;
		int tile5 = R.drawable.tile5;
		int tile6 = R.drawable.tile6;
		
		
		int[] bitmaps = {tile1,tile2,tile3,tile4,tile5,tile6};
		tileTypes = new TileType[bitmaps.length];
		for(int i = 0; i < tileTypes.length; i++){
			tileTypes[i] = 
				new TileType(bitmaps[i], new boolean[2][2][MovementType.values().length]);
		}*/
	}
	
	public void loadTileSet(int res){
		Panel panel = BaseObject.gamePointers.panel;
		InputStream inputStream = panel.getResources().openRawResource(res);
		SAXParserFactory factory = SAXParserFactory.newInstance();
	    try {
	            SAXParser parser = factory.newSAXParser();
	            TileSetSaxParser handler = new TileSetSaxParser(this);
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
