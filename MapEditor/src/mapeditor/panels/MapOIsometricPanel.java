package mapeditor.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.liongrid.gameengine.tools.LVector2Int;

import mapeditor.CData;
import mapeditor.IsometricTransformation;
import mapeditor.LImage;
import mapeditor.MapData;
import mapeditor.MapObject;

public class MapOIsometricPanel extends JPanel{
	
	private float scale = 1;
	private int offsetX;
	private int offsetY;
	private JTextField scaleField;
	private MapObject curMapO;

	public MapOIsometricPanel() {
		JLabel scaleLabel = new JLabel("Scale: ");
		scaleField = new JTextField(""+scale);
		scaleField.setColumns(3);
		
		add(scaleLabel);
		add(scaleField);
		
		scaleField.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					scale = Float.parseFloat(scaleField.getText());
					CData.mapOSplitView.repaint();
				}catch (Exception e) {
					scaleField.setText(""+ scale);
				}
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		if(CData.curMapO == null) return;
		curMapO = CData.curMapO;
		Graphics2D g2d = (Graphics2D) g;
		drawImage(g2d);
		drawSquares(g2d);
	}
	
	private void drawSquares(Graphics2D g2d) {
		for(int x = 0; x < curMapO.arraySizeX; x++){
			for(int y = 0; y < curMapO.arraySizeY; y++){
				drawSquare(g2d, x, y);
			}
		}
	}

	private void drawSquare(Graphics2D g2d, int x, int y) {
		int x1; int x2; int y1; int y2;
		x1 = toWindowX(x, y);
		y1 = toWindowY(x, y);
		x2 = toWindowX(x+1, y);
		y2 = toWindowY(x+1, y);
		g2d.drawLine(x1, y1, x2, y2);
		x1 = toWindowX(x+1, y+1);
		y1 = toWindowY(x+1, y+1);
		g2d.drawLine(x1, y1, x2, y2);
		x2 = toWindowX(x, y+1);
		y2 = toWindowY(x, y+1);
		g2d.drawLine(x1, y1, x2, y2);
		x1 = toWindowX(x, y);
		y1 = toWindowY(x, y);
		g2d.drawLine(x1, y1, x2, y2);
	}

	private void drawImage(Graphics2D g2d) {
		LImage lImg = curMapO.getLImage();
		if(lImg == null || lImg.getImage() == null) return;
		Image img = lImg.getImage();
		LVector2Int center = curMapO.getCenter();
		int x = toWindowX(0, 0) - center.x;
		int y = toWindowY(0, 0) + center.y;
		System.out.println("OffsetX = " + offsetX);
		System.out.println("OffsetY = " + offsetY);
		g2d.drawImage(img, x, y - curMapO.getHeight(), null);
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
		
		float arrayWidth = -IsometricTransformation.getX(0, mapO.arraySizeY);
		System.out.println("ArrayWidth = " + arrayWidth);
		int imgOffsetX = mapO.getCenter().x;
		System.out.println("ImgOffsetX = " + imgOffsetX);
		offsetX =(int)((imgOffsetX > arrayWidth) ? imgOffsetX : arrayWidth);
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
		return (int) (-IsometricTransformation.getY(x, y) + offsetY);
	}
}
