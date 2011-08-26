package com.liongrid.infectomancer.upgrades;

import com.liongrid.gameengine.LUpgrade;
import com.liongrid.infectomancer.Infectosaurus;
import com.liongrid.infectomancer.R;

public class ISpeedUpgrade extends LUpgrade<Infectosaurus> {

	public ISpeedUpgrade() {
		super(Integer.MAX_VALUE);
	}

	@Override
	public void apply(Infectosaurus target) {
		target.speed += 5*mRank;
		
	}

	@Override
	public int getUpgradePrice() {
		return (mRank+1)*100;
	}

	@Override
	public int getDescriptionRes() {
		return  R.string.speedUpgradeDescription;
	}

	@Override
	public String getCurrentStateDescription() {
		return "Current speed bonus: "+5*mRank;
	}

}
