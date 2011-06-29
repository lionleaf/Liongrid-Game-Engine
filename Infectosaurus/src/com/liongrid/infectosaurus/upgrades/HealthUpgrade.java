package com.liongrid.infectosaurus.upgrades;

import com.liongrid.gameengine.Upgrade;
import com.liongrid.infectosaurus.Infectosaurus;
import com.liongrid.infectosaurus.R;

public class HealthUpgrade extends Upgrade<Infectosaurus> {
	
	public HealthUpgrade() {
		super(Integer.MAX_VALUE);
	}

	@Override
	public void apply(Infectosaurus target) {
		target.hp += 5*mRank;
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
		return "Current hp-bonus: " + 5*mRank;
	}

}
