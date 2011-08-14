package com.liongrid.infectosaurus.upgrades;

import com.liongrid.gameengine.LUpgrade;
import com.liongrid.infectosaurus.Infectosaurus;
import com.liongrid.infectosaurus.R;
import com.liongrid.infectosaurus.components.IMeleeAttackComponent;

public class InfectUpgrade extends LUpgrade<Infectosaurus> {

	public InfectUpgrade() {
		super(20);
	}

	@Override
	public void apply(Infectosaurus target) {
		IMeleeAttackComponent c = 
			(IMeleeAttackComponent) target.findComponentOfType
			(IMeleeAttackComponent.class);	
		
		if(c == null) return;
		
		if(mRank > 0){
			c.setInfectChance(5*mRank/100f);
		}
	}

	@Override
	public int getDescriptionRes() {
		return R.string.infectUpgradeDescription;
	}

	@Override
	public String getCurrentStateDescription() {
		return "Current infect chance: "+5*mRank+"%";
	}

	@Override
	public int getUpgradePrice() {
		return 100*(mRank+1)*(mRank+1);
	}

}
