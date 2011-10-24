package mapeditor.panels;

import java.util.Iterator;

import mapeditor.CData;
import mapeditor.CollisionObject;
import mapeditor.LCollision;

public class MapOMutualVariables {
	public static final int MIN_WIDTH = 30;
	public static final int MIN_HEIGHT = 30;
	public static CollisionObject selectedCollisionObject;
	
	public static void selectCollisionObject(CollisionObject o){
		selectedCollisionObject = o;
		CData.updateMapOPanels();
	}
	
	public static void removeSelectedCollisionObject(){
		if(CData.curMapO == null) return;
		CData.curMapO.removeCollideable(selectedCollisionObject);
		selectedCollisionObject = null;
		CData.updateMapOPanels();
	}

}
