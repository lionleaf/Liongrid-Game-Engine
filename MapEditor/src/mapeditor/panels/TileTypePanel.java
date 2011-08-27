package mapeditor.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import mapeditor.CData;
import mapeditor.Square;
import mapeditor.Tile;

public class TileTypePanel extends JPanel {
	private boolean[][][] tileStates;
	private int selectedState = 0;
	private Tile selectedTile;
	private static final int OFFSET_Y = 100;

	
	public TileTypePanel(){
		
		
		
		JComboBox curState = new JComboBox(CData.moveTypes);
		curState.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				setState(cb.getSelectedIndex());
				repaint();
			}

			
		});
		add(curState);
		
		addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent arg0) {}
			public void mousePressed(MouseEvent e){
				if(tileStates == null) return;
				int[] i = getBlock(e.getX(), e.getY()-OFFSET_Y);
				boolean s = tileStates[i[0]][i[1]][selectedState];
				tileStates[i[0]][i[1]][selectedState] = (s) ? false:true;
				repaint();
			}
			
			public void mouseExited(MouseEvent arg0){} 
			public void mouseEntered(MouseEvent arg0){}
			public void mouseClicked(MouseEvent arg0){}
		});
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		if(CData.curTile == null) return;
		selectedTile = CData.curTile;
		if(selectedTile == null) return;
		tileStates = selectedTile.getTileStates();
	
		int tileSize = CData.tileSize;
		int tileBlocks = CData.TILE_BLOCKS;
		int tileBlockSize = tileSize / tileBlocks;
		
		
		g2d.setStroke(new BasicStroke(1));
		g2d.setColor(new Color(0f,0f,0f));
		
		g2d.drawImage(selectedTile.getImage(),0,OFFSET_Y,tileSize,tileSize,null);
		
		for (int i = 0; i < tileBlocks + 1; i++) {
			//Horizontal lines:
			g2d.drawLine(0,OFFSET_Y+tileBlockSize*i, tileSize, OFFSET_Y+tileBlockSize*i);
			
			//Vertical lines:
			g2d.drawLine(tileBlockSize*i, OFFSET_Y, tileBlockSize*i, OFFSET_Y+tileSize);
		}
		
		g2d.setStroke(new BasicStroke(4));
		g2d.setColor(new Color(1f, 0f, 0f));
		
		for(int x = 0; x < tileBlocks; x++){
			for(int y = 0; y < tileBlocks; y++){
				if(tileStates[x][y][selectedState]){
					g2d.drawLine(x*tileBlockSize, y*tileBlockSize+OFFSET_Y, 
							(x+1)*tileBlockSize-1, (y+1)*tileBlockSize-1+OFFSET_Y);
					g2d.drawLine((x+1)*tileBlockSize-1, y*tileBlockSize+OFFSET_Y, 
							x*tileBlockSize, (y+1)*tileBlockSize-1+OFFSET_Y);
				}
			}
		}
	}
	
	
	
	private void setState(int state) {
		selectedState = state;

	}
	
	public void setCurTile(int index){
		this.selectedTile = CData.tiles.get(index);
		this.tileStates = selectedTile.getTileStates();
		repaint();
	}
	
	private int[] getBlock(int x, int y){
		int tileBlocks = CData.TILE_BLOCKS;
		int tileBlockSize = CData.tileSize/tileBlocks;
		
		int[] i = new int[2];
		i[0] = x/tileBlockSize;
		i[1] = y/tileBlockSize;
		if(i[0] > tileBlocks - 1) i[0] = tileBlocks - 1;
		if(i[1] > tileBlocks - 1) i[1] = tileBlocks - 1;
		if(i[0] < 0) i[0] = 0;
		if(i[1] < 0) i[1] = 0;
		return i;
	}
	
}
