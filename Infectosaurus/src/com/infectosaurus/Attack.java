package com.infectosaurus;

public interface Attack {
	void useMeleeAttack(Creature creature);
	boolean hasMeleeAttack();
	void useRangedAttack(Creature creature);
	boolean hasRangedAttack();
}
