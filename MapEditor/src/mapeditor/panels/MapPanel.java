package mapeditor.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
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
import mapeditor.Square;
import mapeditor.Tile;

public class MapPanel extends JPanel {
	JCheckBox showCoordinates = new JCheckBox();
	JCheckBox snapToGrid;
	private int tilesY;
	private int tilesX;
	private int tileSize;
	private boolean mapReady = false;
	
	private MapScetchPanel mapScetch;
	private HashMap<Integer, Tile> tiles;
	private Square[][] level;
	
	
	public MapPanel(){
		add(showCoordinates);
		showCoordinates.addActionListener(new ActionListener() {
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
				int x = MapData.inverseTransformX(e.getX(), e.getY());
				int y = MapData.inverseTransformY(e.getX(), e.getY());
				CData.level[x][y].setTileID(CData.curTile.getIDbyte());
				panel.repaint();
				
			}
			public void mouseReleased(MouseEvent arg0) {}
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			private int[] lastTile = {-1,-1};
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = MapData.inverseTransformX(e.getX(), e.getY());
				int y = MapData.inverseTransformY(e.getX(), e.getY());
				int[] tile  = {x, y};
				if(lastTile[0] != tile[0] || lastTile[1] != tile[1]){
					MapPanel panel = (MapPanel) e.getSource();
					CData.level[tile[0]][tile[1]].setTileID(CData.curTile.getIDbyte());
					panel.repaint();
					lastTile = tile;
				}
				
			}
			public void mouseMoved(MouseEvent arg0) {}
			
		});
	}


	@Override
	public Dimension getPreferredSize() {
		//Overide this to make the scrolling appear as we want it
		
		Dimension d = super.getPreferredSize();
		d.height = IsometricTransformation.getY(tilesX, tilesY);
		d.width = - IsometricTransformation.getX(0, tilesY) 
				  + IsometricTransformation.getX(tilesX, 0);
		return d;
	}


	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(!mapReady) return;
		
		Graphics2D g2d = (Graphics2D) g;
		
//		if(tiles == null || tiles.size() == 0) return;
//		
//		for(int tileY = 0; tileY < tilesY; tileY++){
//			for(int tileX = 0; tileX < tilesX; tileX++){
//				Integer in = new Integer(level[tileX][tileY].getTileID());
//				Tile tile = tiles.get(in);
//				if(tile == null){
//					continue;
//				}
//				
//				int x = MapAffineTrans.transformX(tileX, tileY);
//				int y = MapAffineTrans.transformY(tileX, tileY);
//				
//				g2d.drawImage(tiles.get(in).getImage(), x, y - tileSize, tileSize, tileSize, null);
//			}
//		}

		if(showCoordinates.isSelected()){
			paintGrid(g2d, tileSize);
			int x = MapData.transformX(0, 0);
			int y = MapData.transformY(0, 0);
			g2d.drawOval(x - 10, y - 10, 20, 20);
		}
	}

	private void paintGrid(Graphics2D g2d, int tileSize) {
		int lengthX = MapData.getTilesX();
	}

	public void loadMap() {
		tileSize = CData.tileSize;
		tiles = CData.tiles;
		level = CData.level;
		tilesX = CData.getLevelSizeX();
		tilesY = CData.getLevelSizeY();
		mapReady = true;
	}


	public void setMapScetch(MapScetchPanel mapScetch) {
		this.mapScetch = mapScetch;
	}


}
