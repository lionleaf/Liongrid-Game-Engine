package com.liongrid.infectomancer.upgrades;

import com.liongrid.gameengine.LUpgrade;
import com.liongrid.infectomancer.Infectosaurus;
import com.liongrid.infectomancer.components.IMeleeAttackComponent;
import com.liongrid.infectomancer.R;

public class IReachUpgrade extends LUpgrade<Infectosaurus>{

	public IReachUpgrade() {
		super(Integer.MAX_VALUE);
	}

	@Override
	public void apply(Infectosaurus target) {
		IMeleeAttackComponent attackComponent = (IMeleeAttackComponent) 
			target.findComponentOfType(IMeleeAttackComponent.class);
		attackComponent.addToReach(mRank*5);
	}

	@Override
	public int getDescriptionRes() {
		return R.string.reachUpgradeDescription;
	}

	@Override
	public String getCurrentStateDescription() {
		return "Currently increased reach by "+ 5*mRank;
	}

	@Override
	public int getUpgradePrice() {
		return 100*(mRank+1);
	}

}
