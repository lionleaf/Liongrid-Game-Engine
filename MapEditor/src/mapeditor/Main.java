package mapeditor;

import javax.swing.SwingUtilities;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				CData.mainFrame = new MFrame();
				CData.mainFrame.setVisible(true);
			}
			
		});
		
		for (int i = 0; i < CData.level.length; i++) {
			for (int j = 0; j < CData.level[0].length; j++) {
				CData.level[i][j] = new Square();
			}
		}
		/*
		
		while(CData.leftPanel == null){
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
			
		}
		TileManager.loadTiles();
		//CData.leftPanel.updateList();*/
	}

}
