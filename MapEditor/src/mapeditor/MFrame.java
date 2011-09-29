package mapeditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;

import mapeditor.panels.MapOCartesianPanel;
import mapeditor.panels.MapOIsometricPanel;
import mapeditor.panels.MapPanel;
import mapeditor.panels.MapScetchPanel;
import mapeditor.panels.MapSetupPanel;
import mapeditor.panels.PropertiesPanel;

public class MFrame extends JFrame{
	private MapSetupPanel mapSetupPanel;
	private MapPanel mapPanel;
	private PropertiesPanel propertiesPanel;
	private MapScetchPanel mapScetchPanel;
	private JTabbedPane mapTabPane;

	MFrame(){
		initPanels();
		initUI();
		setSize(700,700);
	}

	private void initPanels() {
		
		//Left panel
		mapSetupPanel = new MapSetupPanel();
		CData.mapSetupPanel = mapSetupPanel;
		mapSetupPanel.setMinimumSize(new Dimension(100,400));
		mapSetupPanel.setPreferredSize(new Dimension(100,400));
		
		//Center panel
			//First tab
		mapPanel = new MapPanel();
		CData.mapPanel = mapPanel;
		JScrollPane mapScroller = new JScrollPane(mapPanel);
		mapPanel.setMinimumSize(new Dimension(200,400));
		mapPanel.setPreferredSize(new Dimension(500,500));
		
			//Second tab
		mapScetchPanel = new MapScetchPanel();
		CData.mapScetchPanel = mapScetchPanel;
		JScrollPane mapScetchScroller = new JScrollPane(mapScetchPanel);
		mapScetchPanel.setMinimumSize(new Dimension(200, 400));
		mapScetchPanel.setPreferredSize(new Dimension(500,500));
		
			//Third tab
		CData.mapOCartPanel = new MapOCartesianPanel();
		CData.mapOIsoPanel = new MapOIsometricPanel();
		JScrollPane mapOIsoScroller = new JScrollPane(CData.mapOIsoPanel);
		JScrollPane mapOCartScroller = new JScrollPane(CData.mapOCartPanel);
		JSplitPane mapOView = new JSplitPane(JSplitPane.VERTICAL_SPLIT, 
											 mapOIsoScroller,
											 mapOCartScroller);
		mapOIsoScroller.setPreferredSize(new Dimension(200, 300));
		CData.mapOSplitView = mapOView;
		
		//Insert all tabs into a tab pane
		mapTabPane = new JTabbedPane();
		mapTabPane.addTab("Map View", mapScroller);
		mapTabPane.addTab("Map Scetch View", mapScetchScroller);
		mapTabPane.addTab("MapO View", mapOView);
		
		
		//Right panel
		propertiesPanel = new PropertiesPanel();
		CData.propertiesPanel = propertiesPanel;
		propertiesPanel.setPreferredSize(new Dimension(200,400));
		
		setPreferredSize(new Dimension(100,400));
	}

	private void initUI() {
		JMenuBar menubar = new JMenuBar();
		JMenu fileBar= new JMenu("File");
		fileBar.setMnemonic(KeyEvent.VK_F);

		JMenuItem eMenuItem = new JMenuItem("Exit");
        eMenuItem.setMnemonic(KeyEvent.VK_C);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener(new ActionListener() {
            @Override
			public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

       fileBar.add(eMenuItem);

        menubar.add(fileBar);

        setJMenuBar(menubar);
        
                
        
        add(mapSetupPanel, BorderLayout.WEST);
        
        add(mapTabPane, BorderLayout.CENTER);
        
        add(propertiesPanel, BorderLayout.EAST);
        

        setTitle("Map Editor");
        setSize(700, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
