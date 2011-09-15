package mapeditor.panels;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import mapeditor.CData;
import mapeditor.LImage;
import mapeditor.MapObject;

public class MapOPanel extends JPanel {
	private MapObjectView mapObjView = new MapObjectView();

	
	public MapOPanel(){
		setMinimumSize(new Dimension(200, 400));
		setLayout(null);
		
		
		JComboBox curState = new JComboBox(CData.tileTypes);
		curState.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
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
		
		JScrollPane scrollPane = new JScrollPane(mapObjView);
		scrollPane.setPreferredSize(new Dimension(200, 200));
		add(scrollPane);
		
		
		Insets insets = getInsets();
		Dimension size = curState.getPreferredSize();
		curState.setBounds(50 + insets.left, 10 + insets.top,
		             size.width, size.height);
		
		size = scrollPane.getPreferredSize();
		
		scrollPane.setBounds(0 + insets.left, 200 + insets.top,
	             size.width, size.height);		
		
	}
	
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
	}
	
	
	class MapObjectView extends JPanel{
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			MapObject mapO = CData.curMapObj;
			if(mapO != null && mapO.getLImage() != null){
				Image img = mapO.getLImage().getImage();
				g2d.drawImage(img, 0, 0, null);
			}
			
		}
		
		@Override
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			MapObject mapO = CData.curMapObj;
			if(mapO != null && mapO.getLImage() != null){
				LImage lImg = mapO.getLImage();
				d.width = lImg.getWidth();
				d.height = lImg.getHeigth();
			}
			return d;
		}
		
	}
	
	class MapObjectScetch extends JPanel{
		
	}
	
}
