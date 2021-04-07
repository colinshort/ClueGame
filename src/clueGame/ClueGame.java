package clueGame;

import java.awt.Dimension;

import javax.swing.JFrame;

public class ClueGame extends JFrame {
	
	private static Board board;

	public ClueGame() {
		setSize(1000,1000);
		board = Board.getInstance();
		board.setPreferredSize(new Dimension(1000, 1000));
		board.setConfigFiles("ClueBoard.csv", "ClueSetup.txt");		
		board.initialize();
		add(board);
	}
	
	
	public static void main(String[] args) {
		ClueGame frame = new ClueGame();
		frame.setContentPane(board); // put the panel in the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		//frame.pack();
		frame.setVisible(true); // make it visible
	}
}
