package mapeditor.panels;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import mapeditor.CData;
import mapeditor.LImage;
import mapeditor.MapData;
import mapeditor.MapObject;

public class PropertiesPanel extends JPanel {
	private MapObjectView mapObjView = new MapObjectView();
	private JComboBox mapOType;
	private JComboBox imageChooser;
	private JTextField centerX;
	private JTextField centerY;
	
	public PropertiesPanel(){
		setMinimumSize(new Dimension(200, 400));
		setLayout(null);
		
		addComponents();
		addListeners();
	}
	
	
	private void addComponents() {
		JScrollPane scrollPane = new JScrollPane(mapObjView);
		scrollPane.setPreferredSize(new Dimension(200, 200));
		mapOType = new JComboBox(CData.tileTypes);
		imageChooser = new JComboBox(CData.images.values().toArray());
		centerX = new JTextField("" + 0);
		centerY = new JTextField("" + 0);
		
		
		add(scrollPane);
		
		add(centerX);
		
		add(centerY);
		
		add(mapOType);
		
		add(imageChooser);
		
		Insets insets = getInsets();
		Dimension size;
		//From top to bottom.
		size = scrollPane.getPreferredSize();
		scrollPane.setBounds(0 + insets.left, 20 + insets.top,
	             size.width, size.height);
		
		centerX.setColumns(3);
		size = centerX.getPreferredSize();
		centerX.setBounds(50 + insets.left, 230 + insets.top,
	             size.width, size.height);
		
		centerY.setColumns(3);
		size = centerX.getPreferredSize();
		centerY.setBounds(90 + insets.left, 230 + insets.top,
	             size.width, size.height);
		
		size = mapOType.getPreferredSize();
		mapOType.setBounds(30 + insets.left, 260 + insets.top,
				150, size.height);
		
		size = imageChooser.getPreferredSize();
		imageChooser.setBounds(30 + insets.left, 290 + insets.top,
	            150, size.height);
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
		
		centerX.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					if(CData.curMapO == null) return;
					CData.curMapO.setCenter(Integer.parseInt(centerX.getText()), 
											Integer.parseInt(centerY.getText()));
				}catch (Exception e) {
					centerX.setText(""+CData.curMapO.getCenter().x);
					centerY.setText(""+CData.curMapO.getCenter().y);
				}
			}
		});
		
		centerY.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					if(CData.curMapO == null) return;
					CData.curMapO.setCenter(Integer.parseInt(centerX.getText()), 
											Integer.parseInt(centerY.getText()));
				}catch (Exception e) {
					centerX.setText(""+CData.curMapO.getCenter().x);
					centerY.setText(""+CData.curMapO.getCenter().y);
				}
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
				g2d.drawImage(img, 0, 0, 200, 200, null);
				g2d.drawOval(mapO.getCenter().x-5, mapO.getCenter().y-5, 10, 10);
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
