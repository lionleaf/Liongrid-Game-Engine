package com.liongrid.gameengine.tmx;

import javax.microedition.khronos.opengles.GL10;

import com.liongrid.gameengine.LCamera;
import com.liongrid.gameengine.LDrawableBitmap;

/**
 * (c) 2010 Nicolas Gramlich (c) 2011 Zynga Inc.
 * 
 * @author Nicolas Gramlich
 * @since 10:39:48 - 05.08.2010
 */
public class TMXTile {
	// ===========================================================
	// Constants
	// ===========================================================

	// ===========================================================
	// Fields
	// ===========================================================

	int mGlobalTileID;
	private final int mTileRow;
	private final int mTileColumn;
	private final int mTileWidth;
	private final int mTileHeight;
	LDrawableBitmap mTextureRegion;

	// ===========================================================
	// Constructors
	// ===========================================================

	public TMXTile(final int pGlobalTileID, final int pTileColumn,
			final int pTileRow, final int pTileWidth, final int pTileHeight,
			final LDrawableBitmap pTextureRegion) {
		this.mGlobalTileID = pGlobalTileID;
		this.mTileRow = pTileRow;
		this.mTileColumn = pTileColumn;
		this.mTileWidth = pTileWidth;
		this.mTileHeight = pTileHeight;
		this.mTextureRegion = pTextureRegion;
	}

	// ===========================================================
	// Getter & Setter
	// ===========================================================

	public int getGlobalTileID() {
		return this.mGlobalTileID;
	}

	public int getTileRow() {
		return this.mTileRow;
	}

	public int getTileColumn() {
		return this.mTileColumn;
	}

	public int getTileX() {
		return this.mTileColumn * this.mTileWidth;
	}

	public int getTileY() {
		return this.mTileRow * this.mTileHeight;
	}

	public int getTileWidth() {
		return this.mTileWidth;
	}

	public int getTileHeight() {
		return this.mTileHeight;
	}

	public LDrawableBitmap getTextureRegion() {
		return this.mTextureRegion;
	}

	/**
	 * Note this will also set the {@link TextureRegion} with the associated
	 * pGlobalTileID of the {@link TMXTiledMap}.
	 * 
	 * @param pTMXTiledMap
	 * @param pGlobalTileID
	 */
	public void setGlobalTileID(final TMXTiledMap pTMXTiledMap,
			final int pGlobalTileID) {
		this.mGlobalTileID = pGlobalTileID;
		this.mTextureRegion = pTMXTiledMap
				.getTextureRegionFromGlobalTileID(pGlobalTileID);
	}

	/**
	 * You'd probably want to call
	 * {@link TMXTile#setGlobalTileID(TMXTiledMap, int)} instead.
	 * 
	 * @param pTextureRegion
	 */
	public void setTextureRegion(final LDrawableBitmap pTextureRegion) {
		this.mTextureRegion = pTextureRegion;
	}

	public TMXProperties<TMXTileProperty> getTMXTileProperties(
			final TMXTiledMap pTMXTiledMap) {
		return pTMXTiledMap.getTMXTileProperties(this.mGlobalTileID);
	}

	public void draw(GL10 gl, boolean orthogonal, int offsetX, int offsetY) {

		if (orthogonal) {
			int x = mTileRow * mTileHeight + offsetX;
			int y = mTileColumn * mTileWidth + offsetY;
			if (LCamera.cull(x, y, mTileWidth, mTileHeight)) return;
			mTextureRegion.draw(gl, x - LCamera.pos.x, y - LCamera.pos.y, LCamera.scale,
					LCamera.scale);
		} else {
			int x = (-mTileRow * mTileWidth + mTileColumn * mTileWidth) / 2
					+ offsetX;
			int y = (mTileRow * mTileHeight + mTileColumn * mTileHeight) / 2
					+ offsetY;
			if (LCamera.cull(x, y, mTileWidth, mTileHeight)) return;
			mTextureRegion.draw(gl, x - LCamera.pos.x, y - LCamera.pos.y,
					LCamera.scale, LCamera.scale);
		}
	}
	// ===========================================================
	// Methods for/from SuperClass/Interfaces
	// ===========================================================

	// ===========================================================
	// Methods
	// ===========================================================

	// ===========================================================
	// Inner and Anonymous Classes
	// ===========================================================
}
