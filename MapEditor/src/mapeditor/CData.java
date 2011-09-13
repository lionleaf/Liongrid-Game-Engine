package mapeditor;

import java.util.HashMap;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import mapeditor.MapObject.StaticObject;
import mapeditor.panels.MapPanel;
import mapeditor.panels.MapScetchPanel;
import mapeditor.panels.ImageChoosePanel;
import mapeditor.panels.MapObjectPanel;

public class CData {

	private static final int MAX_SIZE = 500;
	private static final int MAX_MAP_OBJECTS = 500;
	
	public static MapObject curMapObj;
	
	public volatile static String[] tileTypes = {"Background Tile", "Static Object"};
	public volatile static HashMap<Integer,LImage> images = new HashMap<Integer,LImage>();
	public volatile static HashMap<Integer,MapObject> mapObjects = new HashMap<Integer,MapObject>();
	
	public volatile static byte[][] backgroundObjectsIDs = new byte[MAX_SIZE][MAX_SIZE];
	public volatile static StaticObject[] staticObjects = new StaticObject[MAX_MAP_OBJECTS];
	
	public volatile static ImageChoosePanel imgChoosePanel;
	public volatile static MapObjectPanel mapObjPanel;
	public volatile static MapPanel mapPanel;
	public volatile static MapScetchPanel mapScetchPanel;
	public volatile static MFrame mainFrame;
	public volatile static JScrollPane mapScroller;
	public volatile static JScrollPane mapScetchScroller;
	public volatile static JTabbedPane tabPane;
	
	public static int getArraySizeX() {
		return MapData.arrayWidth;
	}
	
	public static int getArraySizeY() {
		return MapData.arrayWidth;
	}
	
	public static void loadLevel(int mapWidth, int mapHeight, int a, int b){
		MapData.setUp(mapWidth, mapHeight, a, b);
		changeLevelSize(mapWidth, mapHeight);
	}
	
	public static void changeLevelSize(int mapWidth, int mapHeight) {
		MapData.changeMap(mapWidth, mapHeight);
		imgChoosePanel.xField.setText(""+mapWidth);
		imgChoosePanel.yField.setText(""+mapHeight);
		CData.mapPanel.loadMap();
		CData.mapScetchPanel.loadMap();
		mainFrame.repaint();
		mapScroller.updateUI();
		mapScetchScroller.updateUI();
	}
	
}
