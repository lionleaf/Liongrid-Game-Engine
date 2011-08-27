package mapeditor.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JCheckBox;
import javax.swing.JPanel;

public class MapScetchPanel extends JPanel{
	JCheckBox showCoordinates = new JCheckBox();
	
	public MapScetchPanel() {
		add(showCoordinates);
		showCoordinates.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				repaint();
			}
		});
	}
}
