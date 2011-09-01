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
//				if(tileStates == null) return;
//				int[] i = getBlock(e.getX(), e.getY()-OFFSET_Y);
//				boolean s = tileStates[i[0]][i[1]][selectedState];
//				tileStates[i[0]][i[1]][selectedState] = (s) ? false:true;
//				repaint();
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
		
		g2d.drawImage(selectedTile.getImage(),0,OFFSET_Y, 100, 100, null);
		
	}
	
	
	
	private void setState(int state) {
		selectedState = state;

	}
	
	public void setCurTile(int index){
		this.selectedTile = CData.tiles.get(index);
		repaint();
	}
	
}
