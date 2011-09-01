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

import mapeditor.panels.MapPanel;
import mapeditor.panels.TileChoosePanel;
import mapeditor.panels.TileTypePanel;

public class MFrame extends JFrame{
	private TileChoosePanel leftPanel;
	private MapPanel mapPanel;
	private TileTypePanel rightPanel;

	MFrame(){
		initPanels();
		initUI();
		setSize(700,700);
	}

	private void initPanels() {
		leftPanel = new TileChoosePanel();
		CData.tileChoosePanel = leftPanel;
		mapPanel = new MapPanel();
		CData.mapPanel = mapPanel;
		rightPanel = new TileTypePanel();
		CData.propertiesPanel = rightPanel;
		CData.mapScroller = new JScrollPane(mapPanel);
		
		setPreferredSize(new Dimension(100,400));
		leftPanel.setPreferredSize(new Dimension(100,400));
		mapPanel.setPreferredSize(new Dimension(500,500));
		rightPanel.setPreferredSize(new Dimension(100,400));
		
        leftPanel.setMinimumSize(new Dimension(100,400));
        mapPanel.setMinimumSize(new Dimension(200,400));
        rightPanel.setMinimumSize(new Dimension(210, 400));
	}

	private void initUI() {
		JMenuBar menubar = new JMenuBar();
		JMenu fileBar= new JMenu("File");
		fileBar.setMnemonic(KeyEvent.VK_F);

		JMenuItem eMenuItem = new JMenuItem("Exit");
        eMenuItem.setMnemonic(KeyEvent.VK_C);
        eMenuItem.setToolTipText("Exit application");
        eMenuItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                System.exit(0);
            }
        });

       fileBar.add(eMenuItem);

        menubar.add(fileBar);

        setJMenuBar(menubar);
        
                
        
        add(leftPanel, BorderLayout.WEST);
        
        add(CData.mapScroller, BorderLayout.CENTER);
        
        add(rightPanel, BorderLayout.EAST);
        

        setTitle("Map Editor");
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
