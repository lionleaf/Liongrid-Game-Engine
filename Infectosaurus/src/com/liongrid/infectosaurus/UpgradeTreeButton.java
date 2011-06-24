package com.liongrid.infectosaurus;

import com.liongrid.gameengine.Upgrade;
import com.liongrid.infectosaurus.upgrades.InfectosaurusUpgrades;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.PaintDrawable;
import android.util.AttributeSet;
import android.widget.CompoundButton;
import android.widget.RadioButton;

public class UpgradeTreeButton extends RadioButton {
	
	Upgrade mUpgrade = InfectosaurusUpgrades.speedUpgrade.get();
	private boolean mUpgradeable;
	
	
	public UpgradeTreeButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	
	
	public UpgradeTreeButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init(){
		setButtonDrawable(R.drawable.gressbusk2);
	}
		
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if(mUpgradeable){
			Rect r = new Rect();
			getDrawingRect(r);
			
			Paint p = new Paint();
			p.setColor(Color.RED);
			p.setStyle(Paint.Style.STROKE);
			p.setStrokeWidth(5f);
			
			canvas.drawRect(r,p);
		}
		
		String txt = mUpgrade.getRank()+"/";
		txt += mUpgrade.getMaxRank() == Integer.MAX_VALUE ? "inf" : mUpgrade.getMaxRank();
		
		Paint p = new Paint();
		p.setColor(Color.WHITE);
		p.setTextSize(20);
		p.setTypeface(Typeface.DEFAULT_BOLD);
		canvas.drawText(txt,10, getHeight() ,p );
	}
	
	
	@Override
	public void toggle() {
		mUpgrade.incrementRank();
		setChecked(!isChecked());
		if(isChecked()){
			setEnabled(false);
		}
	}
	
	/**
	 * @return true if the Upgrade associated with this button 
	 * should be allowed to be upgraded by the player
	 */
	public boolean isUpgradeable(){
		return mUpgradeable;
	}
	
	/**
	 * Changes the upgradeable state of this button
	 * 
	 * @param upgradeable true to make it upgradeable, 
	 * 	false to make it unupgradeable
	 */
	public void setUpgradeable(boolean upgradeable){
		if (mUpgradeable != upgradeable) {
			mUpgradeable = upgradeable;
			refreshDrawableState();
		}
	}
	
}
