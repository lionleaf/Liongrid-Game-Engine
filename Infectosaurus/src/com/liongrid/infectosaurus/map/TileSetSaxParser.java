package com.liongrid.infectosaurus.map;

import java.lang.reflect.Field;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.DrawableBitmap;
import com.liongrid.gameengine.TextureLibrary;
import com.liongrid.infectosaurus.R;

public class TileSetSaxParser extends DefaultHandler {
	
	TileType[] tileTypes;
	int currentIndex;
	int blockDimensions;
	int currentRes;
	int moveTypes;
	boolean[][][] currentBlocked;
	int currentID;
	TileSet tileSet;
	
	
	
	public TileSetSaxParser(TileSet set){
		this.tileSet = set;
	}
	
	
	@Override
	public void startElement(String uri, String localName, String qName,
			Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		
		if(localName.equalsIgnoreCase("Tile")){
			currentID = Integer.parseInt(attributes.getValue("id"));
			currentRes = getResId(
					attributes.getValue("resource"), R.drawable.class);
			
			currentBlocked = 
				new boolean[moveTypes][blockDimensions][blockDimensions];
			
		}else if(localName.equalsIgnoreCase("TileSet")){
			//block_dimension="2" nr_of_movetypes="3" nr_of_tiles="2"
			blockDimensions = Integer.parseInt(
					attributes.getValue("block_dimension"));
			moveTypes = Integer.parseInt(
					attributes.getValue("nr_of_movetypes"));
			int nrTiles = Integer.parseInt(
					attributes.getValue("nr_of_tiles"));
			
			tileTypes = new TileType[nrTiles];	
			
		}else if(localName.equalsIgnoreCase("state")){
			
		}
		
	}
	
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		
		if(localName.equalsIgnoreCase("Tile")){
			TextureLibrary texLib = BaseObject.gamePointers.textureLib;		
			tileTypes[currentIndex] = new TileType(texLib.allocateTexture(currentRes),currentBlocked);
			tileSet.tileIDtoIndexMap.put(currentID, currentIndex);
			currentIndex++;
		}
	}
	
	@Override
	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		super.endDocument();
		
		tileSet.tileTypes = tileTypes;
	}
	
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
