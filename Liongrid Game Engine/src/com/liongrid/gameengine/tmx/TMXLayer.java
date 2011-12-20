package com.liongrid.gameengine.tmx;


import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

import com.liongrid.gameengine.LCamera;
import com.liongrid.gameengine.LDrawableBitmap;
import com.liongrid.gameengine.LDrawableObject;
import com.liongrid.gameengine.LTexture;
import com.liongrid.gameengine.tmx.TMXLoader.ITMXTilePropertiesListener;
import com.liongrid.gameengine.tmx.util.constants.TMXConstants;
//import org.anddev.andengine.util.Base64;
//import org.anddev.andengine.util.Base64InputStream;
//import org.anddev.andengine.util.MathUtils;
import com.liongrid.gameengine.tools.Base64;
import com.liongrid.gameengine.tools.Base64InputStream;
import com.liongrid.gameengine.tools.LVector2Int;
import com.liongrid.gameengine.tools.SAXUtils;
//import org.anddev.andengine.util.StreamUtils;
import org.xml.sax.Attributes;

/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 20:27:31 - 20.07.2010
 */
public class TMXLayer implements TMXConstants {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final TMXTiledMap mTMXTiledMap;

	private final String mName;
	private final int mTileColumns;
	private final int mTileRows;
	private final TMXTile[][] mTMXTiles;

	private int mTilesAdded;
	private final int mGlobalTileIDsExpected;

	private final float[] mCullingVertices = new float[2 * 4];

	private final TMXProperties<TMXLayerProperty> mTMXLayerProperties = new TMXProperties<TMXLayerProperty>();

	private int mWidth;

	private int mHeight;

	// ===========================================================
	// Constructors
	// ===========================================================

	public TMXLayer(final TMXTiledMap pTMXTiledMap, final Attributes pAttributes) {

		this.mTMXTiledMap = pTMXTiledMap;
		this.mName = pAttributes.getValue("", TAG_LAYER_ATTRIBUTE_NAME);
		this.mTileColumns = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_LAYER_ATTRIBUTE_WIDTH);
		this.mTileRows = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_LAYER_ATTRIBUTE_HEIGHT);
		this.mTMXTiles = new TMXTile[this.mTileRows][this.mTileColumns];

		mWidth = pTMXTiledMap.getTileWidth() * this.mTileColumns;

		mHeight = pTMXTiledMap.getTileHeight() * this.mTileRows;

		this.mGlobalTileIDsExpected = this.mTileColumns * this.mTileRows;

	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public String getName() {
		return this.mName;
	}

	public int getTileColumns() {
		return this.mTileColumns;
	}

	public int getTileRows() {
		return this.mTileRows;
	}

	public TMXTile[][] getTMXTiles() {
		return this.mTMXTiles;
	}

	public TMXTile getTMXTile(final int pTileColumn, final int pTileRow) throws ArrayIndexOutOfBoundsException {
		return this.mTMXTiles[pTileRow][pTileColumn];
	}

	/**
	 * @param pX in relative Coordinates.
	 * @param pY in relative Coordinates.
	 * @return the {@link TMXTile} located at <code>pX/pY</code>.
	 */
	public TMXTile getTMXTileAt(final float pX, final float pY) {
		final TMXTiledMap tmxTiledMap = this.mTMXTiledMap;

		final int tileColumn = (int)(pX / tmxTiledMap.getTileWidth());
		if(tileColumn < 0 || tileColumn > this.mTileColumns - 1) {
			return null;
		}
		final int tileRow = (int)(pY / tmxTiledMap.getTileWidth());
		if(tileRow < 0 || tileRow > this.mTileRows - 1) {
			return null;
		}

		return this.mTMXTiles[tileRow][tileColumn];
	}

	public void addTMXLayerProperty(final TMXLayerProperty pTMXLayerProperty) {
		this.mTMXLayerProperties.add(pTMXLayerProperty);
	}

	public TMXProperties<TMXLayerProperty> getTMXLayerProperties() {
		return this.mTMXLayerProperties;
	}

	// ===========================================================
	// Methods
	// ===========================================================

	void initializeTMXTileFromXML(final Attributes pAttributes, final ITMXTilePropertiesListener pTMXTilePropertyListener) {
		this.addTileByGlobalTileID(SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_TILE_ATTRIBUTE_GID), pTMXTilePropertyListener);
	}

	void initializeTMXTilesFromDataString(final String pDataString, final String pDataEncoding, final String pDataCompression, final ITMXTilePropertiesListener pTMXTilePropertyListener) throws IOException, IllegalArgumentException {
		DataInputStream dataIn = null;
		InputStream in = new ByteArrayInputStream(pDataString.getBytes("UTF-8"));

		/* Wrap decoding Streams if necessary. */
		if(pDataEncoding != null && pDataEncoding.equals(TAG_DATA_ATTRIBUTE_ENCODING_VALUE_BASE64)) {
			in = new Base64InputStream(in, Base64.DEFAULT);
		}
		if(pDataCompression != null){
			if(pDataCompression.equals(TAG_DATA_ATTRIBUTE_COMPRESSION_VALUE_GZIP)) {
				in = new GZIPInputStream(in);
			} else {
				throw new IllegalArgumentException("Supplied compression '" + pDataCompression + "' is not supported yet.");
			}
		}
		dataIn = new DataInputStream(in);

		while(this.mTilesAdded < this.mGlobalTileIDsExpected) {
			final int globalTileID = this.readGlobalTileID(dataIn);
			this.addTileByGlobalTileID(globalTileID, pTMXTilePropertyListener);
		}
		dataIn.close();
	}

	private void addTileByGlobalTileID(final int pGlobalTileID, final ITMXTilePropertiesListener pTMXTilePropertyListener) {
		final TMXTiledMap tmxTiledMap = this.mTMXTiledMap;

		final int tilesHorizontal = this.mTileColumns;

		final int column = this.mTilesAdded % tilesHorizontal;
		final int row = this.mTilesAdded / tilesHorizontal;

		final TMXTile[][] tmxTiles = this.mTMXTiles;

		final LDrawableBitmap tmxTileTextureRegion;
		if(pGlobalTileID == 0) {
			tmxTileTextureRegion = null;
		} else {
			tmxTileTextureRegion = tmxTiledMap.getTextureRegionFromGlobalTileID(pGlobalTileID);
		}
		final TMXTile tmxTile = new TMXTile(pGlobalTileID, column, row, this.mTMXTiledMap.getTileWidth(), this.mTMXTiledMap.getTileHeight(), tmxTileTextureRegion);
		tmxTiles[row][column] = tmxTile;

		if(pGlobalTileID != 0) {
			/* Notify the ITMXTilePropertiesListener if it exists. */
			if(pTMXTilePropertyListener != null) {
				final TMXProperties<TMXTileProperty> tmxTileProperties = tmxTiledMap.getTMXTileProperties(pGlobalTileID);
				if(tmxTileProperties != null) {
					pTMXTilePropertyListener.onTMXTileWithPropertiesCreated(tmxTiledMap, this, tmxTile, tmxTileProperties);
				}
			}
		}

		this.mTilesAdded++;
	}

	private int readGlobalTileID(final DataInputStream pDataIn) throws IOException {
		final int lowestByte = pDataIn.read();
		final int secondLowestByte = pDataIn.read();
		final int secondHighestByte = pDataIn.read();
		final int highestByte = pDataIn.read();

		if(lowestByte < 0 || secondLowestByte < 0 || secondHighestByte < 0 || highestByte < 0) {
			throw new IllegalArgumentException("Couldn't read global Tile ID.");
		}

		return lowestByte | secondLowestByte <<  8 |secondHighestByte << 16 | highestByte << 24;
	}

	public void draw(GL10 gl, float scaleX, float scaleY, int offsetX, int offsetY) {
		final TMXTile[][] tmxTiles = this.mTMXTiles;

		final int tileColumns = this.mTileColumns;
		final int tileRows = this.mTileRows;

		boolean orthogonal = mTMXTiledMap.orientationOrthogonal();

		for(int row = 0; row < tileRows; row++) {
			final TMXTile[] tmxTileRow = tmxTiles[row];

			for(int column = 0; column < tileColumns; column++) {
				tmxTileRow[column].draw(gl, orthogonal, offsetX, offsetY);
			}
		}
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
