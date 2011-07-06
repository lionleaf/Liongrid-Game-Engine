package com.liongrid.infectosaurus.upgrades;

import com.liongrid.gameengine.Upgrade;
import com.liongrid.infectosaurus.Infectosaurus;
import com.liongrid.infectosaurus.R;
import com.liongrid.infectosaurus.components.InfMeleeAttackComponent;

public class MeleeSpeedUpgrade extends Upgrade<Infectosaurus> {
	
	public MeleeSpeedUpgrade() {
		super(Integer.MAX_VALUE);
	}

	@Override
	public void apply(Infectosaurus target) {
		InfMeleeAttackComponent c = 
			(InfMeleeAttackComponent) target.findComponentOfType(InfMeleeAttackComponent.class);	
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
		return (float) (2*Math.pow(0.8, mRank));
	}
	
	@Override
	public int getUpgradePrice() {
		return 100*mRank;
	}

}
