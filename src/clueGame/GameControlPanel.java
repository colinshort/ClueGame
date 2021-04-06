package clueGame;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

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
		panel.setTurn(new ComputerPlayer("Mark", "cyan", 12, 6), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}

	private JPanel createControlPanel() {
		//upper panel
		JPanel controlPanel = new JPanel();
		controlPanel.setLayout(new GridLayout(1,4));
		
		//inside upper panel
		//create panel to show whose turn it is
		JPanel turnPanel = new JPanel();
		JLabel turnLabel = new JLabel("Whose turn?");
		turnField = new JTextField(15);
		turnPanel.add(turnLabel);
		turnPanel.add(turnField);
		controlPanel.add(turnPanel);
		
		//create panel to display roll number
		JPanel rollPanel = new JPanel();
		JLabel roll = new JLabel("Roll: ");
		rollField = new JTextField(5);
		rollPanel.add(roll);
		rollPanel.add(rollField);
		controlPanel.add(rollPanel);
		
		//create buttons to make accusation or move to next turn
		JButton accusation = new JButton("Make Accusation");
		JButton next = new JButton("NEXT!");
		controlPanel.add(accusation);
		controlPanel.add(next);
		
		return controlPanel;
	}

	private JPanel createGuessPanel() {
		//lower panel
		JPanel guessPanel = new JPanel();
		guessPanel.setLayout(new GridLayout(0,2));
		
		//inside lower panel
		//create panel to show guess
		JPanel myGuessPanel = new JPanel();
		myGuessPanel.setLayout(new GridLayout(1,0));
		myGuessPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess"));
		//display guess
		theGuess = new JTextField(20);
		myGuessPanel.add(theGuess);
		guessPanel.add(myGuessPanel);
		
		//create panel to show guess result
		JPanel guessResultPanel = new JPanel();
		guessResultPanel.setLayout(new GridLayout(1,0));
		guessResultPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result"));
		guessPanel.add(guessResultPanel);
		//display result
		theResult = new JTextField(20);
		guessResultPanel.add(theResult);
		guessPanel.add(guessResultPanel);
		return guessPanel;
	}

	public void setTurn(ComputerPlayer player, int roll) {
		//set roll and player turn
		turnField.setText(player.getName());
		rollField.setText("" + roll);
		turnField.setBackground(player.colorConvert(player.getColor()));
	}

	public void setGuess(String guess) {
		//set guess
		theGuess.setText(guess);
	}

	public void setGuessResult(String result) {
		//set result of guess
		theResult.setText(result);
	}
}
