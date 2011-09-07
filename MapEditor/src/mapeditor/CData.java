package mapeditor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import mapeditor.panels.MapPanel;
import mapeditor.panels.MapScetchPanel;
import mapeditor.panels.TileChoosePanel;
import mapeditor.panels.TileTypePanel;

public class CData {

	private static final int MAX_SIZE = 500;
	
	public static boolean coordinateSnap = true;
	
	public static Tile curTile;
	public volatile static String[] moveTypes = {"Walk", "Fly", "Swim"};
	public volatile static HashMap<Integer,Tile> tiles = new HashMap<Integer,Tile>();
	public volatile static Square[][] level = new Square[MAX_SIZE][MAX_SIZE];
	
	public volatile static TileChoosePanel tileChoosePanel;
	public volatile static TileTypePanel propertiesPanel;
	public volatile static MapPanel mapPanel;
	public volatile static MapScetchPanel mapScetchPanel;
	public volatile static MFrame mainFrame;
	public volatile static JScrollPane mapScroller;
	public volatile static JScrollPane mapScetchScroller;
	public static JTabbedPane tabPane;
	
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
		tileChoosePanel.xField.setText(""+mapWidth);
		tileChoosePanel.yField.setText(""+mapHeight);
		CData.mapPanel.loadMap();
		CData.mapScetchPanel.loadMap();
		mainFrame.repaint();
		mapScroller.updateUI();
		mapScetchScroller.updateUI();
	}
	
}
