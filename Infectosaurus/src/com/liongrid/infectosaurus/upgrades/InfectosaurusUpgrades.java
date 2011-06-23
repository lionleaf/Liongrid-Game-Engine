package com.liongrid.infectosaurus.upgrades;

import com.liongrid.gameengine.Upgrade;
import com.liongrid.infectosaurus.Infectosaurus;

public enum InfectosaurusUpgrades {
	healthUpgrade(new HealthUpgrade()),
	speedUpgrade(new SpeedUpgrade()),
	meleeDmgUpgrade(new MeleeDamageUpgrade());
	
	Upgrade<Infectosaurus> upgrade;
	
	
	private InfectosaurusUpgrades(Upgrade<Infectosaurus> u) {
		upgrade = u;
	}
	
	public Upgrade<Infectosaurus> get(){
		return upgrade;
	}
}
