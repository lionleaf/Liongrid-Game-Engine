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
import mapeditor.LImage;
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
	
	public PropertiesPanel(){
		setMinimumSize(new Dimension(200, 400));
		addComponents();
		addListeners();
	}
	
	private void addComponents() {
		mapOType = new JComboBox(CData.tileTypes);
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
		
		collisionChooser = new JComboBox(CData.shapes);
		
		add(titleLabel);
		
		add(centerLabel);
		
		add(centerX);
		
		add(centerY);
		
		add(mapOType);
		
		add(mapOSizeLabel);
		
		add(mapOSizeX);
		
		add(mapOSizeY);
		
		add(imageChooser);
		
		
		
	}
	

	private void addListeners() {
		mapOType.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox)e.getSource();
				if(cb.getSelectedItem() == CData.tileTypes[0])
					CData.staticObject = false;
				else if(cb.getSelectedItem() == CData.tileTypes[1])
					CData.staticObject = true;
				repaint();
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
					updateComponents();
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
					updateComponents();
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
					updateComponents();
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
					updateComponents();
				}
			}
		});
		
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
		updateComponents();
	}

	public void updateComponents() {
		if(CData.curMapO == null) return;
		centerX.setText(""+CData.curMapO.getCenter().x);
		centerY.setText(""+CData.curMapO.getCenter().y);
		mapOSizeY.setText(""+CData.curMapO.arraySizeY);
		mapOSizeX.setText(""+CData.curMapO.arraySizeX);
		imageChooser.setSelectedItem(CData.curMapO.getLImage());
		if(CData.staticObject) mapOType.setSelectedItem(CData.tileTypes[1]);
		else				   mapOType.setSelectedItem(CData.tileTypes[0]);
		repaint();
	}

}
