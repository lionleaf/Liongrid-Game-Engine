package mapeditor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JScrollPane;

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
	public volatile static MapScetchPanel mapScetch;
	public volatile static MFrame mainFrame;
	
	public static JScrollPane mapScroller;
	
	public static int getArraySizeX() {
		return MapData.arrayWidth;
	}
	
	public static int getArraySizeY() {
		return MapData.arrayWidth;
	}
	
	public static void loadLevel(int mapWidth, int mapHeight, int a, int b){
		MapData.setUp(mapWidth, mapHeight, a, b);
		tileChoosePanel.xField.setText(""+mapWidth);
		tileChoosePanel.yField.setText(""+mapHeight);
		CData.mapPanel.loadMap();
		mainFrame.repaint();
		mapScroller.updateUI();
	}
	
	public static void changeLevelSize(int mapWidth, int mapHeight) {
		MapData.changeMap(mapWidth, mapHeight);
		tileChoosePanel.xField.setText(""+mapWidth);
		tileChoosePanel.yField.setText(""+mapHeight);
		CData.mapPanel.loadMap();
		mainFrame.repaint();
		mapScroller.updateUI();
	}
	
}
