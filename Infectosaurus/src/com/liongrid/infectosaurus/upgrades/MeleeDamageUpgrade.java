package com.liongrid.infectosaurus.upgrades;

import com.liongrid.gameengine.Upgrade;
import com.liongrid.infectosaurus.Infectosaurus;
import com.liongrid.infectosaurus.R;
import com.liongrid.infectosaurus.components.InfMeleeAttackComponent;

public class MeleeDamageUpgrade extends Upgrade<Infectosaurus> {

	public MeleeDamageUpgrade() {
		super(Integer.MAX_VALUE);
	}

	@Override
	public void apply(Infectosaurus target) {
		InfMeleeAttackComponent c = 
			(InfMeleeAttackComponent) target.findComponentOfType(InfMeleeAttackComponent.class);	
		if(c == null) return;
		
		c.addToDamage(mRank);
	}

	@Override
	public int getUpgradePrice() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDescriptionRes() {
		return R.string.meleeDamageUpgradeDescription;
	}

}
