package mapeditor.panels;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;

import mapeditor.CData;
import mapeditor.LImage;
import mapeditor.MapManager;

public class ImagePopupViewer extends JFrame{
	private JButton addImage;
	private JButton removeImage;
	private JList list;
	
	public ImagePopupViewer() {
		list = new JList();
		addImage = new JButton("Add image");
		removeImage = new JButton("Remove image");
		
		setLayout(new FlowLayout());
		setResizable(false);
		
		addComponents();
		addActionListeners();
	}


	private void addComponents() {
		JScrollPane listScrollPane = new JScrollPane(list);
		listScrollPane.setPreferredSize(new Dimension(100, 200));
		add(listScrollPane);
		add(addImage);
		add(removeImage);
	}
	
	
	private void addActionListeners() {
		addImage.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				addImages();
			}
		});
		
		removeImage.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				removeImage();
			}
		});
	}
	
	private void removeImage() {
		LImage image = (LImage) list.getSelectedValue();
		MapManager.removeImage(image);
		updateList();
	}
	
	private void addImages(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setCurrentDirectory(new File("."));
		fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
		fileChooser.setApproveButtonText("Add");
		fileChooser.setDialogTitle("Add image");
		fileChooser.setMultiSelectionEnabled(true);
		
		int returnVal = fileChooser.showOpenDialog(CData.mainFrame);
		if(returnVal == JFileChooser.APPROVE_OPTION){
			File[] files = fileChooser.getSelectedFiles();
			MapManager.addImages(files);
		}
		updateList();
	}
	
	public void updateList(){
		list.setListData(CData.images.values().toArray());
		validate();
	}
	
	@Override
	public Dimension getPreferredSize() {
		Dimension d = super.getPreferredSize();
		d.height = 400;
		d.width = 200;
		return d;
	}
}
