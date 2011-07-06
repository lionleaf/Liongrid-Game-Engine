package com.liongrid.infectosaurus.upgrades;

import com.liongrid.gameengine.Upgrade;
import com.liongrid.infectosaurus.Infectosaurus;

public enum InfectosaurusUpgrade {
	HealthUpgrade(new HealthUpgrade()),
	SpeedUpgrade(new SpeedUpgrade()),
	MeleeDamageUpgrade(new MeleeDamageUpgrade()),
	InfectUpgrade(new InfectUpgrade()),
	ReachUpgrade(new ReachUpgrade()),
	MeleeSpeedUpgrade(new MeleeSpeedUpgrade());
	
	
	Upgrade<Infectosaurus> upgrade;
	
	
	private InfectosaurusUpgrade(Upgrade<Infectosaurus> u) {
		upgrade = u;
	}
	
	/**
	 * @return the upgrade objecct associated with this upgrade.
	 */
	public Upgrade<Infectosaurus> get(){
		return upgrade;
	}
}
