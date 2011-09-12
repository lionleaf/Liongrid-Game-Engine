package mapeditor.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

import mapeditor.MapData;

public class MapScetchPanel extends JPanel{
	int scale = 64;
	int offsetX = 0;
	int offsetY = 0;
	
	
	JCheckBox showCoordinates = new JCheckBox();


	private boolean mapReady;
	private int[][] mapIndices;
	private int mapHeight;
	private int mapWidth;
	
	public MapScetchPanel() {
		add(showCoordinates);
		showCoordinates.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				repaint();
			}
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
		
		paintGrid(g2d);
		int x = toWindowX(0);
		int y = toWindowY(0);
		g2d.drawOval(x - 10, y - 10, 20, 20);
	}

	private void paintGrid(Graphics2D g2d) {
		for(int x = 0; x < mapIndices.length; x++){
			for(int y = mapIndices[x][0]; y <= mapIndices[x][1]; y++){
				drawSquare(g2d, x, y);
			}
		}
		drawMapBounds(g2d);
	}
	
	private void drawMapBounds(Graphics2D g2d) {
		int x1 = toWindowX(MapData.x1);
		int y1 = toWindowY(MapData.y1);
		int x2 = toWindowX(MapData.x2);
		int y2 = toWindowY(MapData.y2);
		int x3 = toWindowX(MapData.x3);
		int y3 = toWindowY(MapData.y3);
		int x4 = toWindowX(MapData.x4);
		int y4 = toWindowY(MapData.y4);
		
		g2d.drawLine(x1, y1, x2, y2);
		g2d.drawLine(x2, y2, x3, y3);
		g2d.drawLine(x3, y3, x4, y4);
		g2d.drawLine(x4, y4, x1, y1);
	}

	private void drawSquare(Graphics2D g2d, int x, int y){
		int x1 = toWindowX(x);
		int y1 = toWindowY(y);
		
		if(showCoordinates.isSelected())
				g2d.drawString("("+x+","+y+")", x1, y1);
		
		int x2 = toWindowX(x+1);
		int y2 = toWindowY(y);
		g2d.drawLine(x1, y1, x2, y2);
		
		x1 = toWindowX(x+1);
		y1 = toWindowY(y+1); 
		g2d.drawLine(x2, y2, x1, y1);
		
		x2 = toWindowX(x);
		y2 = toWindowY(y+1);
		g2d.drawLine(x1, y1, x2, y2);
		
		x1 = toWindowX(x);
		y1 = toWindowY(y); 
		g2d.drawLine(x2, y2, x1, y1);
	}
	
	private int fromWindowX(int x){
		return (offsetX - x)/scale;
	}
	
	private int fromWindowY(int y){
		return (offsetY - y)/scale;
	}
	
	private int toWindowX(float x){
		int result =  (int) (x*scale + offsetX);
		return result;
	}

	private int toWindowY(float y){
		int result = (int) (-y*scale + offsetY);
		return result;
	}
	
	public void loadMap() {
		mapHeight = MapData.arrayHeight * scale;
		mapWidth = MapData.arrayWidth * scale;
		offsetX = 0;
		offsetY = mapHeight;
		mapReady = true;
		mapIndices = MapData.getMapIndices();
	}
}
