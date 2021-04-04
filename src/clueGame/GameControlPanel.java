package clueGame;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class GameControlPanel extends JPanel {
	private JTextField theGuess;
	private JTextField theResult;
	private JTextField turnField;
	private JTextField rollField;
	
	public GameControlPanel()  {
		setLayout(new GridLayout(2,0));
		JPanel panel = createControlPanel();
		add(panel);
		panel = createGuessPanel();
		add(panel);
		
	}

	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

		// test filling in the data
		panel.setTurn(new ComputerPlayer("Mark", "blue", 12, 6), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}

	private JPanel createControlPanel() {
		setLayout(new GridLayout(1,4));
		//upper panel
		JPanel controlPanel = new JPanel();
		
		//inside upper panel
		JPanel turnPanel = new JPanel();
		JLabel turnLabel = new JLabel("Whose turn?");
		turnField = new JTextField(20);
		turnPanel.add(turnLabel);
		turnPanel.add(turnField);
		controlPanel.add(turnPanel);
		
		JPanel rollPanel = new JPanel();
		JLabel roll = new JLabel("Roll: ");
		rollField = new JTextField(20);
		rollPanel.add(roll);
		rollPanel.add(rollField);
		controlPanel.add(rollPanel);
		
		JButton accusation = new JButton("Make Accusation");
		JButton next = new JButton("NEXT!");
		controlPanel.add(accusation);
		controlPanel.add(next);
		
		return controlPanel;
	}

	private JPanel createGuessPanel() {
		setLayout(new GridLayout(0,2));
		//lower panel
		JPanel guessPanel = new JPanel();
		//inside lower panel
		JPanel myGuessPanel = new JPanel();
		guessPanel.add(myGuessPanel);
		JPanel guessResultPanel = new JPanel();
		guessPanel.add(guessResultPanel);
		
		return guessPanel;
	}

	public void setTurn(ComputerPlayer player, int roll) {
		turnField.setText(player.getName());
		rollField.setText("" + roll);
	}

	public void setGuess(String guess) {
		theGuess.setText(guess);
	}

	public void setGuessResult(String result) {
		theResult.setText(result);
	}
}
