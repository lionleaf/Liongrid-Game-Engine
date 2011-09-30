package mapeditor.panels;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import mapeditor.CData;
import mapeditor.MapData;

public class MapOCartesianPanel extends JPanel{
	private final int tileSize = 64;
	private int mousePosX = 0;
	private int mousePosY = 0;
	private int offsetX;
	private int offsetY;
	private float scale = 1;
	private JTextField scaleField;
	
	public MapOCartesianPanel() {
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
		
		addMouseMotionListener(new MouseMotionListener() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mousePosX = e.getX();
				mousePosY = e.getY();
				float x = fromWindowX(e.getX());
				float y = fromWindowY(e.getY());
				CData.mapOIsoPanel.setCursorCarth(x, y);
				CData.mapOSplitView.repaint();
			}
			
			@Override
			public void mouseDragged(MouseEvent e) {
				
			}
		});
	}

	/**
	 * Remember to repaint
	 * @param x
	 * @param y
	 */
	protected void setCursorCarth(float x, float y) {
		mousePosX = toWindowX(x);
		mousePosY = toWindowY(y);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		if(CData.curMapO == null) return;
		
		Graphics2D g2d = (Graphics2D) g;
		drawSquares(g2d);
		drawCursorPosition(g2d);
	}
	
	private void drawCursorPosition(Graphics2D g2d) {
		int x = mousePosX-3;
		int y = mousePosY-3;
		g2d.setColor(new Color(1f, 0f, 0f));
		g2d.fillOval(x, y, 6, 6);
		g2d.drawOval(x, y, 6, 6);
		g2d.setColor(new Color(0,0,0));
	}

	private void drawSquares(Graphics2D g2d) {
		for(int x = 0; x < CData.curMapO.arraySizeX; x++){
			for(int y = 0; y < CData.curMapO.arraySizeY; y++){
				drawSquare(g2d, x, y);
			}
		}
	}

	private void drawSquare(Graphics2D g2d, int x, int y){
		int x1 = toWindowX(x);
		int y1 = toWindowY(y);
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
	
	private float fromWindowX(int x){
		return (x - offsetX)/(scale * tileSize);
	}
	
	private float fromWindowY(int y){
		return (offsetY - y)/(scale * tileSize);
	}
	
	private int toWindowX(float x){
		int result =  (int) (x*(scale * tileSize) + offsetX);
		return result;
	}

	private int toWindowY(float y){
		int result = (int) (offsetY - y*(scale * tileSize));
		return result;
	}
	
	public void load(){
		offsetX = 30;
		offsetY = (int) (CData.curMapO.arraySizeY*scale*tileSize) + 30;
	}
	
	@Override
	public Dimension getPreferredSize() {
		Dimension d = super.getPreferredSize();
		return d;
	}
}
