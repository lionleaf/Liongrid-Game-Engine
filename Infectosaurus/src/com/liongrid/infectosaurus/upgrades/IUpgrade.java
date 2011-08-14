package com.liongrid.infectosaurus.upgrades;

import com.liongrid.gameengine.LUpgrade;
import com.liongrid.infectosaurus.Infectosaurus;

public enum IUpgrade {
	HealthUpgrade(new IHealthUpgrade()),
	SpeedUpgrade(new ISpeedUpgrade()),
	MeleeDamageUpgrade(new IMeleeDamageUpgrade()),
	InfectUpgrade(new InfectUpgrade()),
	ReachUpgrade(new IReachUpgrade()),
	MeleeSpeedUpgrade(new IMeleeSpeedUpgrade());
	
	
	LUpgrade<Infectosaurus> upgrade;
	
	
	private IUpgrade(LUpgrade<Infectosaurus> u) {
		upgrade = u;
	}
	
	/**
	 * @return the upgrade objecct associated with this upgrade.
	 */
	public LUpgrade<Infectosaurus> get(){
		return upgrade;
	}
}
