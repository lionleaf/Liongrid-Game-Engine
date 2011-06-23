package com.liongrid.infectosaurus.upgrades;

import com.liongrid.gameengine.Upgrade;
import com.liongrid.infectosaurus.Infectosaurus;

public class SpeedUpgrade extends Upgrade<Infectosaurus> {

	public SpeedUpgrade() {
		super(Integer.MAX_VALUE);
	}

	@Override
	public void apply(Infectosaurus target) {
		target.speed += 5*rank;
		
	}

	@Override
	public int getUpgradePrice() {
		return (rank+1)*100;
	}

}
