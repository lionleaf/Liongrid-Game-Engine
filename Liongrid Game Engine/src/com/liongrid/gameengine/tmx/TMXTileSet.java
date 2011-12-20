package com.liongrid.gameengine.tmx;

import com.liongrid.gameengine.LDrawableBitmap;
import com.liongrid.gameengine.LTexture;
import com.liongrid.gameengine.LTextureLibrary;
import com.liongrid.gameengine.tmx.util.constants.TMXConstants;
import com.liongrid.gameengine.tmx.util.exception.TMXParseException;
import com.liongrid.gameengine.tools.ResIdReflector;
import com.liongrid.gameengine.tools.SAXUtils;
import com.liongrid.gameengine.LGamePointers;
import org.xml.sax.Attributes;

import android.R;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.util.SparseArray;

/**
 * (c) 2010 Nicolas Gramlich 
 * (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 19:03:24 - 20.07.2010
 */
public class TMXTileSet implements TMXConstants {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	private final int mFirstGlobalTileID;
	private final String mName;
	private final int mTileWidth;
	private final int mTileHeight;

	private String mImageSource;
	private LTexture mBitmapTexture;
	//private final TextureOptions mTextureOptions;

	private int mTilesHorizontal;
	@SuppressWarnings("unused")
	private int mTilesVertical;

	private final int mSpacing;
	private final int mMargin;

	private final SparseArray<TMXProperties<TMXTileProperty>> mTMXTileProperties = new SparseArray<TMXProperties<TMXTileProperty>>();
	// ===========================================================
	// Constructors
	// ===========================================================

	TMXTileSet(final Attributes pAttributes) {
		this(SAXUtils.getIntAttribute(pAttributes, TAG_TILESET_ATTRIBUTE_FIRSTGID, 1), pAttributes);
	}

	TMXTileSet(final int pFirstGlobalTileID, final Attributes pAttributes) {
		this.mFirstGlobalTileID = pFirstGlobalTileID;
		this.mName = pAttributes.getValue("", TAG_TILESET_ATTRIBUTE_NAME);
		this.mTileWidth = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_TILESET_ATTRIBUTE_TILEWIDTH);
		this.mTileHeight = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_TILESET_ATTRIBUTE_TILEHEIGHT);
		this.mSpacing = SAXUtils.getIntAttribute(pAttributes, TAG_TILESET_ATTRIBUTE_SPACING, 0);
		this.mMargin = SAXUtils.getIntAttribute(pAttributes, TAG_TILESET_ATTRIBUTE_MARGIN, 0);
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public final int getFirstGlobalTileID() {
		return this.mFirstGlobalTileID;
	}

	public final String getName() {
		return this.mName;
	}

	public final int getTileWidth() {
		return this.mTileWidth;
	}

	public final int getTileHeight() {
		return this.mTileHeight;
	}

	public LTexture getBitmapTextureAtlas() {
		return this.mBitmapTexture;
	}

	public void setImageSource(final Context pContext, final Attributes pAttributes) throws TMXParseException {
		this.mImageSource = pAttributes.getValue("", TAG_IMAGE_ATTRIBUTE_SOURCE);

		// Allocate texture, it will be loaded at some point later
		LTextureLibrary texLib = LGamePointers.textureLib;
		String strippedImgSource = mImageSource.substring(0, mImageSource.indexOf("."));
		int resID = ResIdReflector.getResId(strippedImgSource, R.drawable.class);
		LTexture texture = texLib.allocateTexture(resID);
		this.mBitmapTexture = texture;
		
		BitmapDrawable bd =(BitmapDrawable) LGamePointers.context.getResources().getDrawable(resID);
		int height = bd.getBitmap().getHeight();
		int width = bd.getBitmap().getWidth();
		this.mTilesHorizontal = TMXTileSet.determineCount(width, this.mTileWidth, this.mMargin, this.mSpacing);
		this.mTilesVertical = TMXTileSet.determineCount(height, this.mTileHeight, this.mMargin, this.mSpacing);

		final String transparentColor = SAXUtils.getAttribute(pAttributes, TAG_IMAGE_ATTRIBUTE_TRANS, null);
		if(transparentColor == null) {
			//BitmapTextureAtlasTextureRegionFactory.createFromSource(this.mBitmapTextureAtlas, assetBitmapTextureAtlasSource, 0, 0);
		} else {
			try{
				final int color = Color.parseColor((transparentColor.charAt(0) == '#') ? transparentColor : "#" + transparentColor);
				//BitmapTextureAtlasTextureRegionFactory.createFromSource(this.mBitmapTextureAtlas, new ColorKeyBitmapTextureAtlasSourceDecorator(assetBitmapTextureAtlasSource, RectangleBitmapTextureAtlasSourceDecoratorShape.getDefaultInstance(), color), 0, 0);
			} catch (final IllegalArgumentException e) {
				throw new TMXParseException("Illegal value: '" + transparentColor + "' for attribute 'trans' supplied!", e);
			}
		}
		//pTextureManager.loadTexture(this.mBitmapTextureAtlas);
	}

	public String getImageSource() {
		return this.mImageSource;
	}

	public SparseArray<TMXProperties<TMXTileProperty>> getTMXTileProperties() {
		return this.mTMXTileProperties;
	}

	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	public TMXProperties<TMXTileProperty> getTMXTilePropertiesFromGlobalTileID(final int pGlobalTileID) {
		final int localTileID = pGlobalTileID - this.mFirstGlobalTileID;
		return this.mTMXTileProperties.get(localTileID);
	}

	public void addTMXTileProperty(final int pLocalTileID, final TMXTileProperty pTMXTileProperty) {
		final TMXProperties<TMXTileProperty> existingProperties = this.mTMXTileProperties.get(pLocalTileID);
		if(existingProperties != null) {
			existingProperties.add(pTMXTileProperty);
		} else {
			final TMXProperties<TMXTileProperty> newProperties = new TMXProperties<TMXTileProperty>();
			newProperties.add(pTMXTileProperty);
			this.mTMXTileProperties.put(pLocalTileID, newProperties);
		}
	}

	public LDrawableBitmap getTextureRegionFromGlobalTileID(final int pGlobalTileID) {
		final int localTileID = pGlobalTileID - this.mFirstGlobalTileID;
		final int tileColumn = localTileID % this.mTilesHorizontal;
		final int tileRow = localTileID / this.mTilesHorizontal;

		final int texturePositionX = this.mMargin + (this.mSpacing + this.mTileWidth) * tileColumn;
		final int texturePositionY = this.mMargin + (this.mSpacing + this.mTileHeight) * tileRow;
		
		return new LDrawableBitmap(this.mBitmapTexture, texturePositionX, texturePositionY, this.mTileWidth, this.mTileHeight);
	}

	private static int determineCount(final int pTotalExtent, final int pTileExtent, final int pMargin, final int pSpacing) {
		int count = 0;
		int remainingExtent = pTotalExtent;

		remainingExtent -= pMargin * 2;

		while(remainingExtent > 0) {
			remainingExtent -= pTileExtent;
			remainingExtent -= pSpacing;
			count++;
		}

		return count;
	}

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
