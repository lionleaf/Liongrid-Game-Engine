package mapeditor.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import mapeditor.CData;
import mapeditor.IsometricTransformation;
import mapeditor.MapData;
import mapeditor.Tile;
import mapeditor.LImage;

public class MapPanel extends JPanel {
	JCheckBox grid = new JCheckBox();
	JCheckBox snapToGrid;
	private int mapWidth;
	private int mapHeight;
	private int tileSize = 64;
	private boolean mapReady = false;
	
	private HashMap<Integer, LImage> tiles;
	private Tile[][] level;
	private int[][] mapIndices;
	private int offsetY;
	private int offsetX;
	
	
	public MapPanel(){
		add(grid);
		grid.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				repaint();
			}
		});
		
		addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent arg0) {}
			
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			@Override
			public void mousePressed(MouseEvent e) {
				MapPanel panel = (MapPanel) e.getSource();
				int x = (int) MapData.fromIsoToCartX(e.getX(), e.getY());
				int y = (int) MapData.fromIsoToCartY(e.getX(), e.getY());
				System.out.println("pressed tile x = " + x + " y = " + y);
				panel.repaint();
			}
			public void mouseReleased(MouseEvent arg0) {}
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			private int[] lastTile = {-1,-1};
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = (int) MapData.fromIsoToCartX(e.getX(), e.getY());
				int y = (int) MapData.fromIsoToCartY(e.getX(), e.getY());
				int[] tile  = {x, y};
				if(lastTile[0] != tile[0] || lastTile[1] != tile[1]){
					MapPanel panel = (MapPanel) e.getSource();
					panel.repaint();
					lastTile = tile;
				}
				
			}
			public void mouseMoved(MouseEvent arg0) {}
			
		});
	}


	@Override
	public Dimension getPreferredSize() {
		//Override this to make the scrolling appear as we want it
		
		Dimension d = super.getPreferredSize();
		d.height = mapHeight;
		d.width = mapWidth;
		return d;
	}


	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(!mapReady) return;
		
		Graphics2D g2d = (Graphics2D) g;
		
		drawTiles(g2d);
		
		if(grid.isSelected()){
			paintGrid(g2d, tileSize);
		}
	}

	private void drawTiles(Graphics2D g2d) {
		for(int x = 0; x < mapIndices.length; x++){
			for(int y = mapIndices[x][0]; y <= mapIndices[x][1]; y++){
				LImage tile = CData.level[x][y].getLImage();
				if(tile == null) return;
				Image img = tile.getImage();
				g2d.drawImage(img, x, y, null);
			}
		}
	}


	private void paintGrid(Graphics2D g2d, int tileSize) {
		for(int x = 0; x < mapIndices.length; x++){
			for(int y = mapIndices[x][0]; y <= mapIndices[x][1]; y++){
				drawSquare(g2d, x, y);
			}
		}
		drawBounds(g2d);
	}
	
	private void drawBounds(Graphics2D g2d) {
		g2d.drawRect(0 + offsetX, -mapHeight + offsetY, mapWidth, mapHeight);
	}


	private void drawSquare(Graphics2D g2d, int x, int y){
		int x1 = toWindowX(x, y);
		int y1 = toWindowY(x, y);
		int x2 = toWindowX(x+1, y);
		int y2 = toWindowY(x+1, y);
		g2d.drawLine(x1, y1, x2, y2);
		x1 = toWindowX(x+1, y+1);
		y1 = toWindowY(x+1, y+1);
		g2d.drawLine(x2, y2, x1, y1);
		x2 = toWindowX(x, y+1);
		y2 = toWindowY(x, y+1);
		g2d.drawLine(x1, y1, x2, y2);
		x1 = toWindowX(x, y);
		y1 = toWindowY(x, y);
		g2d.drawLine(x2, y2, x1, y1);
	}
	
	private int fromWindowX(int x, int y){
		return (int) MapData.fromIsoToCartX(x + offsetX, -y + offsetY);
	}
	
	private int fromWindowY(int x, int y){
		return (int) MapData.fromIsoToCartY(x + offsetX, -y + offsetY);
	}
	
	private int toWindowX(float x, float y){
		return (int) (MapData.fromCartToIsoX(x, y) + offsetX);
	}

	private int toWindowY(float x, float y){
		return (int) (-MapData.fromCartToIsoY(x, y) + offsetY);
	}

	public void loadMap() {
		tiles = CData.images;
		level = CData.level;
		mapIndices = MapData.getMapIndices();
		mapHeight = MapData.mapHeight;
		mapWidth = MapData.mapWidth;
		offsetX = 0;
		offsetY = mapHeight;
		mapReady = true;
	}

}
