package com.infectosaurus;

/**
 * @author Lastis
 * This class needs to hold all the GameObjects and sort the 
 * components in a way that they can be done in the right order 
 */
public class GameObjectHandler {
	
	private Infectosaurus infector;

	GameObjectHandler(Panel panel){
		infector = new Infectosaurus(panel);
	}

	public void update4Renderer() {
		infector.useComp4Game();
	}
}
