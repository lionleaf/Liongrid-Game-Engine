package com.liongrid.infectomancer.upgrades;

import com.liongrid.gameengine.LUpgrade;
import com.liongrid.infectomancer.Infectosaurus;
import com.liongrid.infectomancer.components.IMeleeAttackComponent;
import com.liongrid.infectomancer.R;

public class IMeleeSpeedUpgrade extends LUpgrade<Infectosaurus> {
	
	public IMeleeSpeedUpgrade() {
		super(Integer.MAX_VALUE);
	}

	@Override
	public void apply(Infectosaurus target) {
		IMeleeAttackComponent c = 
			(IMeleeAttackComponent) target.findComponentOfType(IMeleeAttackComponent.class);	
		if(c == null) return;
		
		c.setDelay(calculateDelay());
		
	}

	@Override
	public int getDescriptionRes() {
		return R.string.meleeSpeedUpgradeDescription;
	}

	@Override
	public String getCurrentStateDescription() {	
		float delay = calculateDelay();
		return "Current attack speed: " + delay + " delays between attack, giving "
		+ 1/delay + " attacks per sec";
	}

	
	private float calculateDelay(){
		return (float) (2*Math.pow(0.95, mRank));
	}
	
	@Override
	public int getUpgradePrice() {
		return 100*(mRank+1);
	}

}
