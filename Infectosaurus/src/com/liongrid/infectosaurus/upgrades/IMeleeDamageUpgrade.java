package com.liongrid.infectosaurus.upgrades;

import com.liongrid.gameengine.LUpgrade;
import com.liongrid.infectosaurus.Infectosaurus;
import com.liongrid.infectosaurus.R;
import com.liongrid.infectosaurus.components.IMeleeAttackComponent;

public class IMeleeDamageUpgrade extends LUpgrade<Infectosaurus> {

	public IMeleeDamageUpgrade() {
		super(Integer.MAX_VALUE);
	}

	@Override
	public void apply(Infectosaurus target) {
		IMeleeAttackComponent c = 
			(IMeleeAttackComponent) target.findComponentOfType(IMeleeAttackComponent.class);	
		if(c == null) return;
		
		c.addToDamage(mRank);
	}

	@Override
	public int getUpgradePrice() {
		// TODO Auto-generated method stub
		return 100*(mRank+1);
	}

	@Override
	public int getDescriptionRes() {
		return R.string.meleeDamageUpgradeDescription;
	}

	@Override
	public String getCurrentStateDescription() {
		return "Current damage bonus: "+mRank;
	}

}
