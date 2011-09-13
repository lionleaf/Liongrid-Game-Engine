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
	}

}
