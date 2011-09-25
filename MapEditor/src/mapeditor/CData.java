package mapeditor;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import mapeditor.MapObject.StaticObject;
import mapeditor.panels.MapPanel;
import mapeditor.panels.MapScetchPanel;
import mapeditor.panels.MapSetupPanel;
import mapeditor.panels.PropertiesPanel;

public class CData {

	private static final int MAX_SIZE = 500;
	
	public volatile static MapObject curMapO;
	public volatile static boolean staticObject = false; 
	
	public volatile static String[] tileTypes = {"Background Tile", "Static Object"};
	public volatile static HashMap<Integer,LImage> images = new HashMap<Integer,LImage>();
	public volatile static HashMap<Integer,MapObject> mapObjects = new HashMap<Integer,MapObject>();
	
	public volatile static short[][] backgroundObjectsIDs = new short[MAX_SIZE][MAX_SIZE];
	public volatile static ArrayList<StaticObject> staticObjects = new ArrayList<StaticObject>();
	
	public volatile static PropertiesPanel propertiesPanel;
	public volatile static MapPanel mapPanel;
	public volatile static MapScetchPanel mapScetchPanel;
	public volatile static MFrame mainFrame;
	public volatile static JScrollPane mapScroller;
	public volatile static JScrollPane mapScetchScroller;
	public volatile static JTabbedPane mapTabPane;
	public volatile static MapSetupPanel mapSetupPanel;
	
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
		CData.mapPanel.loadMap();
		CData.mapScetchPanel.loadMap();
		mainFrame.repaint();
		mapScroller.updateUI();
		mapScetchScroller.updateUI();
	}
	
}
