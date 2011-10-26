package mapeditor;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import mapeditor.MapObject.StaticObject;
import mapeditor.panels.MapOCartesianPanel;
import mapeditor.panels.MapOIsometricPanel;
import mapeditor.panels.MapPanel;
import mapeditor.panels.MapScetchPanel;
import mapeditor.panels.MapSetupPanel;
import mapeditor.panels.PropertiesPanel;

public class CData {

	private static final int MAX_SIZE = 500;
	
	public volatile static MapObject curMapO;
	/**
	 * If static object is selected
	 */
	public volatile static boolean staticObjectMode = false;
	public volatile static StaticObject selectedStaticObject;
	
	public volatile static String[] mapOTypes = {"Background Tile", "Static Object"};
	public volatile static String[] shapes = {"Point", "Circle", "Square"};
	public volatile static HashMap<Integer,LImage> images = new HashMap<Integer,LImage>();
	public volatile static HashMap<Integer,MapObject> mapObjects = new HashMap<Integer,MapObject>();
	
	public volatile static short[][] backgroundObjectsIDs = new short[MAX_SIZE][MAX_SIZE];
	public volatile static ArrayList<StaticObject> staticObjects = new ArrayList<StaticObject>();
	
	public volatile static MFrame mainFrame;
	//Left panel
	public volatile static MapSetupPanel mapSetupPanel;
	//Center panel
	public volatile static MapPanel mapPanel;
	public volatile static MapOCartesianPanel mapOCartPanel;
	public volatile static MapOIsometricPanel mapOIsoPanel;
	public volatile static MapScetchPanel mapScetchPanel;
	public volatile static JSplitPane mapOSplitView;
	//Right panel
	public volatile static PropertiesPanel propertiesPanel;
	
	public static int getArraySizeX() {
		return MapData.arrayWidth;
	}
	
	public static int getArraySizeY() {
		return MapData.arrayWidth;
	}
	
	public static void loadLevel(int mapWidth, int mapHeight, int a, int b){
		MapData.setUp(mapWidth, mapHeight, a, b);
		mapSetupPanel.updateAxes();
		changeLevelSize(mapWidth, mapHeight);
	}
	
	public static void loadLevel(int mapWidth, int mapHeight, int x1, int x2, int y1, int y2){
		MapData.setUp(mapWidth, mapHeight, x1, x2, y1, y2);
		mapSetupPanel.updateAxes();
		changeLevelSize(mapWidth, mapHeight);
	}

	public static void changeLevelSize(int mapWidth, int mapHeight) {
		MapData.changeMap(mapWidth, mapHeight);
		mapSetupPanel.xField.setText(""+mapWidth);
		mapSetupPanel.yField.setText(""+mapHeight);
		updateEverything();
	}
	
	public static void updateMaps(){
		if(CData.mapPanel != null) 		 CData.mapPanel.loadMap();
		if(CData.mapScetchPanel != null) CData.mapScetchPanel.loadMap();
	}
	
	public static void updateMapOPanels(){
		if(CData.propertiesPanel != null) CData.propertiesPanel.update();
		if(CData.mapOCartPanel != null) CData.mapOCartPanel.load();
		if(CData.mapOIsoPanel != null) CData.mapOIsoPanel.load();
		//The splitpane must be repainted instead of the two panels inside it
		if(CData.mapOSplitView != null) CData.mapOSplitView.repaint();
	}
	
	public static void updateEverything(){
		updateMaps();
		updateMapOPanels();
	}

}
