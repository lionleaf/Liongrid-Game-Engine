package mapeditor.panels;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import mapeditor.CData;
import mapeditor.Tile;
import mapeditor.TileManager;

public class TileChoosePanel extends JPanel  {
	public JTextField xField;
	public JTextField yField;
	
	private JList list;
	private JTextField tileSizeField;
	private JButton save;
	private JButton load;
	private JButton saveTileSet;
	private JButton loadTileSet;
	private JButton addTile;
	private JButton removeTile;

	public TileChoosePanel(){

		//Initialize objects
		list = new JList();	
		xField = new JTextField(""+CData.getLevelSizeX());
		yField = new JTextField(""+CData.getLevelSizeX());
		tileSizeField = new JTextField(""+CData.tileSize);
		save = new JButton("Save Map");
		load = new JButton("Load Map");
		saveTileSet = new JButton("Save Tileset");
		loadTileSet = new JButton("Load Tileset");
		addTile = new JButton("Add Tile");
		removeTile = new JButton("Remove Tile");
		
		
		xField.setColumns(3);
		yField.setColumns(3);
		list.setPreferredSize(new Dimension(80, 20));
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		tileSizeField.setColumns(3);

		addActionListeners();

		addComponents();

	}

	private void addComponents(){
		add(xField);
		
		add(new JLabel("x"));
		
		add(yField);
		
		add(addTile);
		
		add(removeTile);
		
		JScrollPane listScrollPane = new JScrollPane(list);
		add(listScrollPane);
		
		add(new JLabel("Tile size:"));
		
		add(tileSizeField);
		
		add(load);
		
		add(save);
		
		add(saveTileSet);
		
		add(loadTileSet);
	}

	private void addActionListeners(){
		list.addListSelectionListener(new ListSelectionListener() {

			@Override
			public void valueChanged(ListSelectionEvent e) {
				if(e.getValueIsAdjusting() == false){
					JList list = (JList)e.getSource();
					Tile tile = (Tile) list.getSelectedValue();
					if(tile == null) return;
					CData.curTile = tile;
					CData.mainFrame.repaint();
				}

			}
		});


		tileSizeField.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					CData.tileSize = Integer.parseInt(tileSizeField.getText());
					CData.mainFrame.repaint();
				}catch(Exception e){
					tileSizeField.setText(""+CData.tileSize);
				}

			}

		});

		xField.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					CData.setLevelSizeX(Integer.parseInt(xField.getText()));
				}catch (Exception e) {
					xField.setText(""+CData.getLevelSizeX());
				}

			}
		});

		yField.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try{
					CData.setLevelSizeY(Integer.parseInt(yField.getText()));
				}catch (Exception e) {
					yField.setText(""+CData.getLevelSizeX());
				}

			}
		});

		save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveMapFile();
			}
		});

		load.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				loadMapFile();
			}
		});

		saveTileSet.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				TileManager.writeTileSet("tileset.xml");

			}
		});

		loadTileSet.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				TileManager.loadTileSet(new File("tileset.xml"));

			}
		});
		
		addTile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addTiles();
				
			}
		});
		
		removeTile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				removeTile();
				
			}

			
		});
	}

	private void loadMapFile(){
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		fileChooser.setCurrentDirectory(new File("."));
		FileNameExtensionFilter filter = 
			new FileNameExtensionFilter("Infectosaurus map file *.ism","ism");
		fileChooser.setFileFilter(filter);
		fileChooser.setApproveButtonText("Load");
		fileChooser.setDialogTitle("Load map");


		int returnVal = fileChooser.showOpenDialog(CData.mainFrame);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			File file = fileChooser.getSelectedFile();

			TileManager.loadMap(file);
		}
	}
	
	private void removeTile() {
		Tile tile = (Tile) list.getSelectedValue();
		TileManager.removeTile(tile);
		
	}
	
	private void saveMapFile(){
		
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("."));
		fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
		FileNameExtensionFilter filter = 
			new FileNameExtensionFilter("Infectosaurus map file *.ism)","ism");
		fileChooser.setFileFilter(filter);
		fileChooser.setApproveButtonText("Save");
		fileChooser.setDialogTitle("Save map");


		int returnVal = fileChooser.showSaveDialog(CData.mainFrame);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			File file = fileChooser.getSelectedFile();

			//Append .ism if it`s not there
			String fName = file.getName();
			if(fName.indexOf('.') == -1){
				fName += "."+ filter.getExtensions()[0];
				file = new File(file.getParentFile(),fName);
			}

			TileManager.writeMap(file);
		}
	}

	private void addTiles(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("."));
		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		fileChooser.setApproveButtonText("Add");
		fileChooser.setDialogTitle("Add tile");
		fileChooser.setMultiSelectionEnabled(true);
		
		int returnVal = fileChooser.showOpenDialog(CData.mainFrame);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			File[] files = fileChooser.getSelectedFiles();

			TileManager.addTiles(files);
		}

	}
	
	public void valueChanged(ListSelectionEvent e) {
		if(e.getValueIsAdjusting() == false){
			JList list = (JList)e.getSource();
			int i = list.getSelectedIndex();
			if(i == -1) return;
			//ObjectPointers.centerPanel.setCurTile(i);
			CData.rightPanel.setCurTile(i);
		}
	}

	public void updateList(){
		list.setListData(CData.tiles.values().toArray());
		repaint();
	}
}
