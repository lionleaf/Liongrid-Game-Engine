package com.liongrid.infectosaurus;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.RadioButton;

import com.liongrid.gameengine.Upgrade;
import com.liongrid.infectosaurus.upgrades.InfectosaurusUpgrade;

public class TalentIcon extends RadioButton {


	private Upgrade<?> mUpgrade = InfectosaurusUpgrade.SpeedUpgrade.get();
	private boolean mUpgradeable = true;
	private Upgrade.OnRankChangedListener mOnRankChangedListener;

	private rankChangeListener mRankListener = new rankChangeListener();


	public TalentIcon(Context context, AttributeSet attrs) {
		super(context, attrs, android.R.attr.radioButtonStyle);
		init(attrs);
	}

	public TalentIcon(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}



	public Upgrade<?> getUpgrade(){
		return mUpgrade;
	}


	private void init(AttributeSet attrs){
		setButtonDrawable(R.drawable.gressbusk2);
		TypedArray a = 
			getContext().obtainStyledAttributes(attrs,R.styleable.UpgradeTreeButton);

		String upgradeValue = a.getString(R.styleable.UpgradeTreeButton_upgrade);
		try {
			InfectosaurusUpgrade infUp = InfectosaurusUpgrade.valueOf(upgradeValue);
			mUpgrade = infUp.get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		mUpgrade.setOnRankChangedListener(mRankListener);

	}

	/**
	 * @return true if the Upgrade associated with this button 
	 * should be allowed to be upgraded by the player
	 */
	public boolean isUpgradeable(){
		return mUpgradeable;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		if(isChecked()){
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
	
	/**
	 * Set`s a listener for rank changes in the associated upgrade!
	 * 
	 * @param listener - An instance of the listener class
	 */
	public void setOnRankChangedListener(Upgrade.OnRankChangedListener listener){
		mOnRankChangedListener = listener;
	}

	@Override
	public void toggle() {
		super.toggle();
	}

	private class rankChangeListener implements 
	Upgrade.OnRankChangedListener{

		public void onRankChanged(Upgrade<?> upgrade, int newRank) {
			//cause a full repaint of the view when rank is changed.
			if(upgrade == mUpgrade){
				postInvalidate();
			}
			
			if(mOnRankChangedListener != null){
				mOnRankChangedListener.onRankChanged(upgrade, newRank);
			}

		}

	}
}
