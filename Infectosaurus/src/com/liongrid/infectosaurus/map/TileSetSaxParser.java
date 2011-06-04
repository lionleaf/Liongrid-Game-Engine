package com.liongrid.infectosaurus.map;

import java.lang.reflect.Field;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.liongrid.gameengine.BaseObject;
import com.liongrid.gameengine.TextureLibrary;
import com.liongrid.gameengine.tools.MovementType;
import com.liongrid.infectosaurus.R;

public class TileSetSaxParser extends DefaultHandler {
	
	TileType[] tileTypes;
	int currentIndex;
	int blockDimensions;
	int currentRes;
	boolean[][][] currentBlocked;
	int currentID;
	TileSet tileSet;
	MovementType currentMType = null;
	
	
	
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
				new boolean[MovementType.values().length][blockDimensions][blockDimensions];
			
		}else if(localName.equalsIgnoreCase("TileSet")){
			//block_dimension="2" nr_of_movetypes="3" nr_of_tiles="2"
			blockDimensions = Integer.parseInt(
					attributes.getValue("block_dimension"));
			int nrTiles = Integer.parseInt(
					attributes.getValue("nr_of_tiles"));
			
			tileTypes = new TileType[nrTiles];	
			
		}else if(localName.equalsIgnoreCase("state")){
			String mTypeName = attributes.getValue("name");
			try{
				currentMType = MovementType.valueOf(mTypeName);		
			}catch(IllegalArgumentException e){
				//If we don`t have the movementType, skip it.
				currentMType = null;
			}
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
	
	@Override
	public void characters(char[] ch, int start, int length)
			throws SAXException {
		super.characters(ch, start, length);
		
		//Parse the movementTypeData, it`s the only time characters is called
		
		//If it`s null, it means we should ignore it
		if(currentMType == null || ch == null || ch.length == 0){ 
			return;
		}
		
		currentBlocked[currentMType.ordinal()] = getBooleanArray(ch, blockDimensions);
		
	}


	private boolean[][] getBooleanArray(char[] ch, int blockDimensions) {
		int l = blockDimensions ^ blockDimensions;
		boolean[][] resArray = new boolean[blockDimensions][blockDimensions];
		for (int i = 0; i < l; i++) {
			resArray[i%blockDimensions][i/blockDimensions] = ch[i] == '1'? true: false;
		}
		return resArray;
	}
	
}
