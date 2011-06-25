package com.liongrid.infectosaurus.upgrades;

import com.liongrid.gameengine.Upgrade;
import com.liongrid.infectosaurus.Infectosaurus;
import com.liongrid.infectosaurus.R;

public class SpeedUpgrade extends Upgrade<Infectosaurus> {

	public SpeedUpgrade() {
		super(Integer.MAX_VALUE);
		mDescriptionResID = R.string.speedUpgradeDescription;
	}

	@Override
	public void apply(Infectosaurus target) {
		target.speed += 5*mRank;
		
	}

	@Override
	public int getUpgradePrice() {
		return (mRank+1)*100;
	}

}
