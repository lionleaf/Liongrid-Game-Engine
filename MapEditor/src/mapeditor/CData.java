package mapeditor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JScrollPane;

import mapeditor.panels.MapPanel;
import mapeditor.panels.TileChoosePanel;
import mapeditor.panels.TileTypePanel;

public class CData {

	private static final int MAX_SIZE = 500;
	
	public static boolean coordinateSnap = true;
	
	public static Tile curTile;
	public volatile static int tileSize = 64;
	public volatile static int TILE_BLOCKS = 2;
	public volatile static String[] moveTypes = {"Walk", "Fly", "Swim"};
	public volatile static HashMap<Integer,Tile> tiles = new HashMap<Integer,Tile>();
	public volatile static Square[][] level = new Square[MAX_SIZE][MAX_SIZE];
	
	public volatile static TileChoosePanel leftPanel;
	public volatile static TileTypePanel rightPanel;
	public volatile static MapPanel centerPanel;
	
	public volatile static MFrame mainFrame;
	
	public static JScrollPane mapScroller;
	
	private volatile static int levelSizeX = 5;
	private volatile static int levelSizeY = 5;

	
	public static int getLevelSizeX() {
		return levelSizeX;
	}
	
	public static void setLevelSizeX(int levelSizeX) {
		CData.levelSizeX = levelSizeX;
		leftPanel.xField.setText(""+levelSizeX);
		mainFrame.repaint();
		mapScroller.updateUI();
	}
	public static int getLevelSizeY() {
		return levelSizeY;
	}
	public static void setLevelSizeY(int levelSizeY) {
		CData.levelSizeY = levelSizeY;
		leftPanel.yField.setText(""+levelSizeY);
		mainFrame.repaint();
		mapScroller.updateUI();
	}

}
