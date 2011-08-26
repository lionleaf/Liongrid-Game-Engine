package com.liongrid.gameengine;

import java.lang.reflect.Field;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

import com.liongrid.gameengine.tools.LMovementType;
import com.liongrid.infectomancer.R;

public class LTileSetSaxParser extends DefaultHandler {
	
	LTileType[] tileTypes;
	int currentIndex;
	int blockDimensions;
	int currentRes;
	boolean[][][] currentBlocked;
	int currentID;
	LTileSet tileSet;
	LMovementType currentMType = null;
	
	
	
	public LTileSetSaxParser(LTileSet set){
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
				new boolean[LMovementType.values().length][blockDimensions][blockDimensions];
			
		}else if(localName.equalsIgnoreCase("TileSet")){
			//block_dimension="2" nr_of_movetypes="3" nr_of_tiles="2"
			blockDimensions = Integer.parseInt(
					attributes.getValue("block_dimension"));
			int nrTiles = Integer.parseInt(
					attributes.getValue("nr_of_tiles"));
			
			tileTypes = new LTileType[nrTiles];	
			
		}
		
		if(localName.equalsIgnoreCase("state")){
			String mTypeName = attributes.getValue("name");
			try{
				currentMType = LMovementType.valueOf(mTypeName);		
			}catch(IllegalArgumentException e){
				//If we don`t have the movementType, skip it.
				currentMType = null;
			}
		}else{
			currentMType = null;
		}
		
	}
	
	
	@Override
	public void endElement(String uri, String localName, String qName)
			throws SAXException {
		super.endElement(uri, localName, qName);
		
		if(localName.equalsIgnoreCase("tile")){
			LTextureLibrary texLib = LGamePointers.textureLib;		
			//checkTrue(currentBlocked);
			tileTypes[currentIndex] = new LTileType(texLib.allocateTexture(currentRes),currentBlocked);
			tileSet.tileIDtoIndexMap.put(currentID, currentIndex);
			currentIndex++;
		}
	}
	
	private void checkTrue(boolean[][][] arr) {
		for(boolean[][] barr : arr){
			for(boolean[] ba : barr){
				for(boolean b : ba){
					if(b){
						Log.d("True", "yeah");
					}
				}
			}
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
		
		
		//Seems to be a lot of whitespace etc, get rid of all noise>
		
		char[] realch = String.copyValueOf(ch).replaceAll(
				"[^01]\\s", "").toCharArray();
		
		int l = realch.length;
		
		for (int i = start; i < l + start; i++) {
			currentBlocked[currentMType.ordinal()][i%blockDimensions][i/blockDimensions]
			                = (ch[i] == '1');
			
		}
		
		
	}
}
