package com.liongrid.gameengine;

/**
 * @author Liongrid
 *	An abstract class for upgrades. 
 *	They should be applied before spawning upgradeable objects
 *	
 * It`s probably not necessary to pool this, 
 * and therefore it does not support it atm.
 * 
 * If the rank is 0, the upgrade should do nothing!
 * 
 * To make it "infinitely" upgradeable, set rank = Integer.MAX_VALUE;
 *  
 * @param <T> The type that the upgrade can be applied to
 */
public abstract class Upgrade<T extends BaseObject>{
	
	protected int rank = 0;
	protected int maxRank;
	
	public Upgrade(int maxRank){
		this.maxRank = maxRank;
	}
	
	/**
	 * @return the current rank of the upgrade
	 */
	public int getRank(){
		return rank;
	}
	
	/**
	 * Set`s the rank. BEWARE, if rank is out of bounds, it does nothing.
	 * @param rank - the new rank
	 * @return - True if the rank was set, 
	 * 		false if rank was greater than maxRank or less than zero
	 */
	public boolean setRank(int rank){
		if(rank > maxRank || rank < 0){
			return false;
		}
		
		this.rank = rank;
		return true;
	}
	
	/**
	 * Increases the current rank by 1, unless current rank == max rank
	 * @return true if the rank was changed
	 */
	public boolean incrementRank(){
		if(rank == maxRank) return false;
		
		rank++;
		return true;
	}
	
	/**
	 * Decreases the current rank by 1, unless current rank = 0
	 * @return true if the rank was changed
	 */
	public boolean decrementRank(){
		if(rank == 0) return false;
		
		rank--;
		return true;
	}
	
	/**
	 * Sets the rank back to 0 and thereby disables the upgrade
	 */
	public void resetRank(){
		rank = 0;
	}
	
	/**
	 * Should be called upon spawning the object 
	 * that the upgrade should be applied to.
	 * 
	 * (You can change hp, dmg, components etc etc)
	 * 
	 * @param target - Pointer to the target of the upgrade
	 */
	public abstract void apply(T target);
	
	
	/**
	 * @return the cost to upgrade this upgrade to the next rank.
	 */
	public abstract int getUpgradePrice();
	
}
