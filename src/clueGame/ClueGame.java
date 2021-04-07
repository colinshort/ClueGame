package clueGame;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class ClueGame extends JFrame {
	private static Board board;
	private static KnownCardsPanel kcp;
	private static GameControlPanel gcp;

	public ClueGame() {
		//setSize(1000,1000);
		board = Board.getInstance();
		board.setPreferredSize(new Dimension(800, 800));
		board.setConfigFiles("ClueBoard.csv", "ClueSetup.txt");		
		board.initialize();
		add(board, BorderLayout.CENTER);
		
		kcp = new KnownCardsPanel();
		add(kcp, BorderLayout.EAST);
		
		gcp = new GameControlPanel();
		add(gcp, BorderLayout.SOUTH);
	}
	
	
	public static void main(String[] args) {
		ClueGame frame = new ClueGame();
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
	}
}

