package com.liongrid.infectomancer;

import android.content.Context;
import android.util.AttributeSet;

import com.liongrid.gameengine.LTalentIcon;
import com.liongrid.gameengine.LUpgrade;
import com.liongrid.infectomancer.upgrades.IUpgrade;

public class ITalentIcon extends LTalentIcon {

	public ITalentIcon(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	public ITalentIcon(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected LUpgrade getUpgradeFromStr(String upgradeValue) {
		return IUpgrade.valueOf(upgradeValue).get();
	}

}
