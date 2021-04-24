package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class ClueGame extends JFrame {
	private static Board board;
	private static KnownCardsPanel kcp;
	private static GameControlPanel gcp;

	public ClueGame() {
		setLayout(new BorderLayout());
		
		//create Game Control Panel
		gcp = GameControlPanel.getInstance();
		
		//create  board panel
		board = Board.getInstance();
		board.setConfigFiles("ClueBoard.csv", "ClueSetup.txt");		
		board.initialize();
		board.firstTurnSetup(gcp);

		//create Known Cards Panel
		kcp = KnownCardsPanel.getInstance();
		
		//add panels
		add(board, BorderLayout.CENTER);
		add(gcp, BorderLayout.SOUTH);
		add(kcp, BorderLayout.EAST);
		
		
	}


	public static void main(String[] args) {
		//Create and display frame
		ClueGame frame = new ClueGame();
		frame.setSize(new Dimension(1000, 1000));
		frame.setTitle("Clue!");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

		//show splash screen
		JOptionPane.showMessageDialog(null, "You are John.\nCan you find the solution before the Computer players?", "Welcome to Clue!", JOptionPane.INFORMATION_MESSAGE);

	}
}

