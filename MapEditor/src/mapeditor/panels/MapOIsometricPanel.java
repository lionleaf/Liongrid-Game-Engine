package mapeditor.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;

import javax.swing.JPanel;

import mapeditor.CData;
import mapeditor.IsometricTransformation;
import mapeditor.LImage;
import mapeditor.MapData;
import mapeditor.MapObject;

public class MapOIsometricPanel extends JPanel{
	
	private float scale = 1;
	private int offsetX;
	private int offsetY;

	public MapOIsometricPanel() {
	
	}

	@Override
	public void paintComponent(Graphics g) {
		if(CData.curMapO == null) return;
		Graphics2D g2d = (Graphics2D) g;
		drawImage(g2d);
	}
	
	private void drawImage(Graphics2D g2d) {
		LImage lImg = CData.curMapO.getLImage();
		if(lImg == null || lImg.getImage() == null) return;
		Image img = lImg.getImage();
		int x = toWindowX(0, 0);
		int y = toWindowY(0, 0);
		g2d.drawImage(img, x, y, null);
	}

	@Override
	public Dimension getPreferredSize() {
		Dimension d = super.getPreferredSize();
		return d;
	}

	public void load() {
		if(CData.curMapO == null) return;
		MapObject mapO = CData.curMapO;
		
		float arrayHeight =IsometricTransformation.getY(mapO.arraySizeX, mapO.arraySizeY);
		offsetY =(int)((mapO.getHeight() > arrayHeight) ? mapO.getHeight() : arrayHeight);
		offsetY =+ 30;
		
		float arrayWidth = -IsometricTransformation.getX(0, CData.getArraySizeY());
		int imgOffsetX = mapO.getCenter().x; 
		offsetX =(int)((imgOffsetX > arrayWidth) ? imgOffsetX : arrayWidth);
		offsetX =+ 30;
	}
	
	private int fromWindowX(int x, int y){
		return (int) IsometricTransformation.getInversX(x - offsetX, offsetY - y);
	}
	
	private int fromWindowY(int x, int y){
		return (int) IsometricTransformation.getInversY(x - offsetX, offsetY - y);
	}
	
	private int toWindowX(float x, float y){
		return (int) IsometricTransformation.getX(x, y) + offsetX;
	}

	private int toWindowY(float x, float y){
		return (int) (IsometricTransformation.getY(x, y) + offsetY);
	}
}
