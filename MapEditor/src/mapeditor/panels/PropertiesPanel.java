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
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import mapeditor.CData;
import mapeditor.CollisionObject;
import mapeditor.LImage;
import mapeditor.MapData;
import mapeditor.MapObject;

public class PropertiesPanel extends JPanel {
	private MapObjectView mapObjView = new MapObjectView();
	private JComboBox mapOType;
	private JComboBox imageChooser;

	
	public PropertiesPanel(){
		setMinimumSize(new Dimension(200, 400));
		setLayout(null);
		
		addComponents();
		addListeners();
	}
	
	
	private void addComponents() {
		JScrollPane scrollPane = new JScrollPane(mapObjView);
		scrollPane.setPreferredSize(new Dimension(200, 200));
		add(scrollPane);
		
		mapOType = new JComboBox(CData.tileTypes);
		add(mapOType);
		
		imageChooser = new JComboBox(CData.images.values().toArray());
		add(imageChooser);
		
		Insets insets = getInsets();
		Dimension size;
		//From top to bottom.
		size = scrollPane.getPreferredSize();
		scrollPane.setBounds(0 + insets.left, 10 + insets.top,
	             size.width, size.height);
		
		size = mapOType.getPreferredSize();
		mapOType.setBounds(30 + insets.left, 220 + insets.top,
				size.width, size.height);
		
		size = imageChooser.getPreferredSize();
		imageChooser.setBounds(30 + insets.left, 250 + insets.top,
	             size.width, size.height);
	}
	

	private void addListeners() {
		mapOType.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				repaint();
			}
		});
		
		imageChooser.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				if(CData.curMapO == null) return;
				LImage img = (LImage) cb.getSelectedItem();
				CData.curMapO.setLImageID(img.getIDbyte());
				System.out.println("Set new image. ID = " + img.getIDbyte());
				repaint();
			}
		});
		
	}
	
	public void addImage(LImage img){
		imageChooser.addItem(img);
	}
	
	public void removeImage(LImage img){
		imageChooser.removeItem(img);
	}
	
	class MapObjectView extends JPanel{
		
		int offsetX = 0;
		int offsetY = 0;
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2d = (Graphics2D) g;
			MapObject mapO = CData.curMapO;
			if(mapO != null && mapO.getLImage() != null){
				Image img = mapO.getLImage().getImage();
				g2d.drawImage(img, 10, 10, null);
			}
			
		}
		
		@Override
		public Dimension getPreferredSize() {
			Dimension d = super.getPreferredSize();
			MapObject mapO = CData.curMapO;
			if(mapO != null && mapO.getLImage() != null){
				LImage lImg = mapO.getLImage();
				d.width = lImg.getWidth();
				d.height = lImg.getHeigth();
			}
			return d;
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
		
	}

}
