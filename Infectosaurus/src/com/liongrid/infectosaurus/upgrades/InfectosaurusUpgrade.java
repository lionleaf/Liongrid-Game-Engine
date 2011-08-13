package com.liongrid.infectosaurus.upgrades;

import com.liongrid.gameengine.LUpgrade;
import com.liongrid.infectosaurus.Infectosaurus;

public enum InfectosaurusUpgrade {
	HealthUpgrade(new HealthUpgrade()),
	SpeedUpgrade(new SpeedUpgrade()),
	MeleeDamageUpgrade(new MeleeDamageUpgrade()),
	InfectUpgrade(new InfectUpgrade()),
	ReachUpgrade(new ReachUpgrade()),
	MeleeSpeedUpgrade(new MeleeSpeedUpgrade());
	
	
	LUpgrade<Infectosaurus> upgrade;
	
	
	private InfectosaurusUpgrade(LUpgrade<Infectosaurus> u) {
		upgrade = u;
	}
	
	/**
	 * @return the upgrade objecct associated with this upgrade.
	 */
	public LUpgrade<Infectosaurus> get(){
		return upgrade;
	}
}
