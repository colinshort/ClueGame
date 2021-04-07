package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class ClueGame extends JFrame {
	private static Board board;
	private static KnownCardsPanel kcp;
	private static GameControlPanel gcp;

	public ClueGame() {
		//create and add board panel
		board = Board.getInstance();
		board.setPreferredSize(new Dimension(800, 800));
		board.setConfigFiles("ClueBoard.csv", "ClueSetup.txt");		
		board.initialize();
		add(board, BorderLayout.CENTER);
		
		//create and add Known Cards Panel
		kcp = new KnownCardsPanel();
		add(kcp, BorderLayout.EAST);
		
		//create and add Game Control Panel
		gcp = new GameControlPanel();
		add(gcp, BorderLayout.SOUTH);
	}
	
	
	public static void main(String[] args) {
		//Create and display frame
		ClueGame frame = new ClueGame();
		frame.setTitle("Clue!");
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}
}

