package mapeditor.panels;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import mapeditor.CData;
import mapeditor.CollisionCircle;
import mapeditor.CollisionObject;
import mapeditor.CollisionSquare;
import mapeditor.LCollision;
import mapeditor.LImage;
import mapeditor.LShape;
import mapeditor.MapData;
import mapeditor.MapObject;

public class PropertiesPanel extends JPanel {
	
	private JComboBox mapOType;
	private JComboBox imageChooser;
	private JTextField centerX;
	private JTextField centerY;
	private JTextField mapOSizeX;
	private JTextField mapOSizeY;
	private JComboBox collisionChooser;
	
	private String selectedShape;
	// Shape relyant components
	private JLabel circleLabel;
	private JTextField circleRadius;
	private JLabel squareLabel;
	private JTextField squareWidth;
	private JTextField squareHeight;
	
	public PropertiesPanel(){
		setMinimumSize(new Dimension(200, 400));
		addComponents();
		addListeners();
	}
	
	private void addComponents() {
		mapOType = new JComboBox(CData.mapOTypes);
		imageChooser = new JComboBox(CData.images.values().toArray());
		JLabel centerLabel = new JLabel("Center: (x,y)");
		centerX = new JTextField("" + 0);
		centerY = new JTextField("" + 0);
		JLabel titleLabel = new JLabel("MapO properties tab");
		mapOSizeX = new JTextField("" + 0);
		mapOSizeY = new JTextField("" + 0);
		JLabel mapOSizeLabel = new JLabel("MapO size:");

		titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
		centerX.setColumns(3);
		centerY.setColumns(3);
		mapOSizeX.setColumns(3);
		mapOSizeY.setColumns(3);
		mapOType.setPreferredSize(new Dimension(150, 24));
		imageChooser.setPreferredSize(new Dimension(150, 24));
		
		JLabel collisionLabel = new JLabel("Collision Object");
		collisionChooser = new JComboBox(CData.shapes);
		collisionChooser.setPreferredSize(new Dimension(150, 24));
		
		// Shape relyant components
		circleLabel = new JLabel("Circle radius:  ");
		circleRadius = new JTextField("" + 0);
		squareLabel = new JLabel("Square width and height:     ");
		squareWidth = new JTextField("" + 0);
		squareHeight = new JTextField("" + 0);
		circleRadius.setColumns(3);
		squareWidth.setColumns(3);
		squareHeight.setColumns(3);
		
		add(titleLabel);
		
		add(centerLabel);
		
		add(centerX);
		
		add(centerY);
		
		add(mapOType);
		
		add(mapOSizeLabel);
		
		add(mapOSizeX);
		
		add(mapOSizeY);
		
		add(imageChooser);
		
		add(collisionLabel);
		
		add(collisionChooser);
	}
	

	private void addListeners() {
		mapOType.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				if(cb.getSelectedItem() == CData.mapOTypes[0])
					CData.staticObjectMode = false;
				else if(cb.getSelectedItem() == CData.mapOTypes[1])
					CData.staticObjectMode = true;
				repaint();
				CData.updateMaps();
			}
		});
		
		imageChooser.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				if(CData.curMapO == null) return;
				LImage img = (LImage) cb.getSelectedItem();
				if(img == null) return;
				CData.curMapO.setLImageID(img.getIDbyte());
				System.out.println("Set new image. ID = " + img.getIDbyte());
				repaint();
				CData.updateMapOPanels();
			}
		});
		
		mapOSizeX.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg) {
				if(CData.curMapO == null) return;
				try{
					CData.curMapO.arraySizeX = Integer.parseInt(mapOSizeX.getText());
					CData.curMapO.arraySizeY = Integer.parseInt(mapOSizeY.getText());
					CData.updateMapOPanels();
				}catch (Exception e) {
					update();
				}
			}
		});
		
		mapOSizeY.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg) {
				if(CData.curMapO == null) return;
				try{
					CData.curMapO.arraySizeX = Integer.parseInt(mapOSizeX.getText());
					CData.curMapO.arraySizeY = Integer.parseInt(mapOSizeY.getText());
					CData.updateMapOPanels();
				}catch (Exception e) {
					update();
				}
			}
		});
		
		centerX.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					if(CData.curMapO == null) return;
					CData.curMapO.setCenter(Integer.parseInt(centerX.getText()), 
											Integer.parseInt(centerY.getText()));
					CData.updateMapOPanels();
				}catch (Exception e) {
					update();
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
					CData.updateEverything();
				}catch (Exception e) {
					update();
				}
			}
		});
		
		collisionChooser.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				selectedShape = (String) cb.getSelectedItem();
				addShapeButtons();
				repaint();
			}
		});
	}
	
	public CollisionObject createCollisionObject(){
		String point = CData.shapes[0];
		String circle = CData.shapes[1];
		String square = CData.shapes[2];
		CollisionObject returnO = null;
		if(selectedShape.equals(point)){
		}
		else if(selectedShape.equals(circle)){
			try{
				returnO = new CollisionCircle(0, 0, Float.parseFloat(circleRadius.getText()));
			}
			catch (Exception e) {
				return null;
			}
		}
		else if(selectedShape.equals(square)){
			try{
				returnO = new CollisionSquare(0, 0, 
					Float.parseFloat(squareWidth.getText()), 
					Float.parseFloat(squareHeight.getText()));
			}
			catch (Exception e) {
				return null;
			}
		}
		return returnO;
	}
	
	private void addShapeButtons(){
		String point = CData.shapes[0];
		String circle = CData.shapes[1];
		String square = CData.shapes[2];
		removeCollisionComponents();
		if(selectedShape.equals(point)){
		}
		else if(selectedShape.equals(circle)){
			add(circleLabel);
			add(circleRadius);
		}
		else if(selectedShape.equals(square)){
			add(squareLabel);
			add(squareWidth);
			add(squareHeight);
		}
		revalidate();
		update();
	}
	
	private void removeCollisionComponents() {
		remove(circleLabel);
		remove(circleRadius);
		remove(squareLabel);
		remove(squareWidth);
		remove(squareHeight);
	}

	public boolean contains(ComboBoxModel comboBoxModel, Object o) {
		int size = comboBoxModel.getSize();
		for(int i = 0; i < size; i++) {
			Object obj = comboBoxModel.getElementAt(i);
			if(obj.equals(o)) {
					return true;
			}
		}
		return false;
	}
	
	public void addImage(LImage img){
		if(contains(imageChooser.getModel(), img)) return;
		imageChooser.addItem(img);
	}
	
	public void removeImage(LImage img){
		imageChooser.removeItem(img);
	}
	
	public void disableAllProperties(){
		mapOType.setEnabled(false);
		imageChooser.setEnabled(false);
		centerX.setEnabled(false);
		centerY.setEnabled(false);
	}
	
	public void enableAllProperties(){
		mapOType.setEnabled(true);
		imageChooser.setEnabled(true);
		centerX.setEnabled(true);
		centerY.setEnabled(true);
	}

	public void update() {
		if(CData.curMapO == null) return;
		centerX.setText(""+CData.curMapO.getCenter().x);
		centerY.setText(""+CData.curMapO.getCenter().y);
		mapOSizeY.setText(""+CData.curMapO.arraySizeY);
		mapOSizeX.setText(""+CData.curMapO.arraySizeX);
		imageChooser.setSelectedItem(CData.curMapO.getLImage());
		if(CData.staticObjectMode) mapOType.setSelectedItem(CData.mapOTypes[1]);
		else				   mapOType.setSelectedItem(CData.mapOTypes[0]);
		repaint();
	}

}
