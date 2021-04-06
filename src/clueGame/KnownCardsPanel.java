package clueGame;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class KnownCardsPanel extends JPanel{
	public KnownCardsPanel() {
		
	}
	
	public static void main(String[] args) {
		KnownCardsPanel panel = new KnownCardsPanel();
		JFrame frame = new JFrame();
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(200, 1000);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

		// test filling in the data
		
	}
}
