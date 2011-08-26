package com.liongrid.infectomancer.upgrades;

import com.liongrid.gameengine.LUpgrade;
import com.liongrid.infectomancer.Infectosaurus;
import com.liongrid.infectomancer.R;

public class IHealthUpgrade extends LUpgrade<Infectosaurus> {
	
	public IHealthUpgrade() {
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
