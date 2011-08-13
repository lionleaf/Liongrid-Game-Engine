package com.liongrid.infectosaurus.upgrades;

import com.liongrid.gameengine.LUpgrade;
import com.liongrid.infectosaurus.Infectosaurus;
import com.liongrid.infectosaurus.R;

public class HealthUpgrade extends LUpgrade<Infectosaurus> {
	
	public HealthUpgrade() {
		super(Integer.MAX_VALUE);
	}

	@Override
	public void apply(Infectosaurus target) {
		target.mMaxHp += 2*mRank;
	}

	@Override
	public int getUpgradePrice() {
		return (mRank+1)*100;
	}

	@Override
	public int getDescriptionRes() {
		return R.string.healthUpgradeDescription;
	}

	@Override
	public String getCurrentStateDescription() {
		return "Current mHp-bonus: " + 2*mRank;
	}

}
