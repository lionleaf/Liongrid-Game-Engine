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
import mapeditor.IsometricProjection;
import mapeditor.Square;
import mapeditor.Tile;

public class MapPanel extends JPanel {
	JCheckBox showCoordinates = new JCheckBox();
	private int top;
	private int bottom;
	private int left;
	private int right;
	private int offsetX;
	private int offsetY;
	private int tileSize;
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
			@Override
			public void mousePressed(MouseEvent e) {
				MapPanel panel = (MapPanel) e.getSource();
				int x = inverseTransformX(e.getX(), e.getY());
				int y = inverseTransformY(e.getX(), e.getY());
				CData.level[x][y].setTileID(CData.curTile.getIDbyte());
				panel.repaint();
				
			}
			
			public void mouseReleased(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseClicked(MouseEvent arg0) {}
		});
		
		addMouseMotionListener(new MouseMotionListener() {
			private int[] lastTile = {-1,-1};
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = inverseTransformX(e.getX(), e.getY());
				int y = inverseTransformY(e.getX(), e.getY());
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
		d.height = IsometricProjection.getY(right, top);
		d.width = - IsometricProjection.getX(left, top) 
				  + IsometricProjection.getX(right, bottom);
		return d;
	}


	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		refreshPaintVariables();
		
		Graphics2D g2d = (Graphics2D) g;
		if(tiles == null || tiles.size() == 0) return;
		
		for(int tileY = 0; tileY < top; tileY++){
			for(int tileX = 0; tileX < right; tileX++){
				Integer in = new Integer(level[tileX][tileY].getTileID());
				Tile tile = tiles.get(in);
				if(tile == null){
					continue;
				}
				
				int x = transformX(tileX, tileY);
				int y = transformY(tileX, tileY);
				
				g2d.drawImage(tiles.get(in).getImage(), x, y - tileSize, tileSize, tileSize, null);
			}
		}

		if(showCoordinates.isSelected()){
			paintGrid(g2d, tileSize);
			int X0 = transformX(0, 0);
			int Y0 = transformY(0, 0);
			g2d.drawString("(0,0)", X0, Y0);
		}
	}
	
	private int inverseTransformX(int x, int y) {
		return IsometricProjection.getInversX(x - offsetX, - y + offsetY) ;
	}
	
	private int inverseTransformY(int x, int y) {
		return  IsometricProjection.getInversY(x - offsetX, - y + offsetY) ;
	}

	private int transformY(int x, int y) {
		return - IsometricProjection.getY(x, y) + offsetY;
	}

	private int transformX(int x, int y) {
		return IsometricProjection.getX(x, y) + offsetX;
	}

	private void refreshPaintVariables() {
		tiles = CData.tiles;
		level = CData.level;
		tileSize = CData.tileSize;
		right = CData.getLevelSizeX();
		top = CData.getLevelSizeY();
		bottom = 0;
		left = 0;
		
//		IsometricProjection.setMatrixX(tileSize, 0);
//		IsometricProjection.setMatrixY(0, tileSize);
		IsometricProjection.setMatrix(tileSize, tileSize/2);
		IsometricProjection.verify();
		
		offsetX = - IsometricProjection.getX(left, top); // Same as half total width
		offsetY = IsometricProjection.getY(right, top); // Same as total height
	}



	private void paintGrid(Graphics2D g2d, int tileSize) {
		
		for(int i = 0; i <= right; i++){ // Along the y axis
			int x1 = transformX(i, bottom);
			int y1 = transformY(i, bottom);
			int x2 = transformX(i, top);
			int y2 = transformY(i, top);
			g2d.drawLine(x1, y1, x2, y2);
		}
		
		for(int j = 0; j <= top; j++){ // Along the x axis
			int x1 = transformX(left, j);
			int y1 = transformY(left, j);
			int x2 = transformX(right, j);
			int y2 = transformY(right, j);
			g2d.drawLine(x1, y1, x2, y2);
		}
	}
}
