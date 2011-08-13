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

import com.liongrid.gameengine.LUpgrade;
import com.liongrid.infectosaurus.upgrades.InfectosaurusUpgrade;

public class TalentIcon extends RadioButton {


	private LUpgrade<?> mUpgrade = InfectosaurusUpgrade.SpeedUpgrade.get();
	private boolean mUpgradeable = true;
	private LUpgrade.OnRankChangedListener mOnRankChangedListener;

	private rankChangeListener mRankListener = new rankChangeListener();
	private int mIconRes;
	private int mDefaultIcon = R.drawable.gressbusk1;


	public TalentIcon(Context context, AttributeSet attrs) {
		super(context, attrs, android.R.attr.radioButtonStyle);
		init(attrs);
	}

	public TalentIcon(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(attrs);
	}



	public LUpgrade<?> getUpgrade(){
		return mUpgrade;
	}


	private void init(AttributeSet attrs){
		TypedArray a = 
			getContext().obtainStyledAttributes(attrs,R.styleable.TalentIcon);

		String upgradeValue = a.getString(R.styleable.TalentIcon_upgrade);
		try {
			InfectosaurusUpgrade infUp = InfectosaurusUpgrade.valueOf(upgradeValue);
			mUpgrade = infUp.get();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		int iconRes = a.getResourceId(R.styleable.TalentIcon_icon, -1);
		if(iconRes != -1){
			mIconRes = iconRes;
		}else{
			mIconRes = mDefaultIcon ;
		}
		setButtonDrawable(mIconRes);
		
		mUpgrade.setOnRankChangedListener(mRankListener);

	}

	/**
	 * @return true if the LUpgrade associated with this button 
	 * should be allowed to be upgraded by the player
	 */
	public boolean isUpgradeable(){
		return mUpgradeable;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		if(isChecked()){
			Paint p = new Paint();
			p.setColor(Color.RED);
			p.setStyle(Paint.Style.STROKE);
			p.setStrokeWidth(5f);
			Rect r = new Rect();
			getDrawingRect(r);

			canvas.drawRect(r,p);
		}
		
		if(isFocused()){
			Paint p = new Paint();
			p.setColor(Color.YELLOW);
			p.setStyle(Paint.Style.STROKE);
			p.setStrokeWidth(2.5f);
			Rect r = new Rect();
			getDrawingRect(r);

			canvas.drawRect(r,p);
		}
		
		if(!isUpgradeable()){
			Paint p = new Paint();
			p.setColor(Color.RED);
			p.setStyle(Paint.Style.STROKE);
			
			//Paint cross
			canvas.drawLine(getScrollX(), getScrollY(), getWidth(), getHeight(), p);
			canvas.drawLine(getScrollX(), getHeight(), getWidth(), getScrollY(), p);
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
			postInvalidate();
		}
	}
	
	/**
	 * Set`s a listener for rank changes in the associated upgrade!
	 * 
	 * @param listener - An instance of the listener class
	 */
	public void setOnRankChangedListener(LUpgrade.OnRankChangedListener listener){
		mOnRankChangedListener = listener;
	}
	
	@Override
	public void setChecked(boolean checked) {
		super.setChecked(checked);
		postInvalidate();
	}

	private class rankChangeListener implements 
	LUpgrade.OnRankChangedListener{

		public void onRankChanged(LUpgrade<?> upgrade, int newRank) {
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
