package clueGame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class SuggestionDialog extends JDialog{
	private JLabel roomLabel;
	private JLabel personLabel;
	private JLabel weaponLabel;
	
	private JTextField roomSelect;
	
	private JComboBox<String> personSelect;
	private JComboBox<String> weaponSelect;
	
	private JButton submit;
	private JButton cancel;
	
	private Card room;
	private Card person;
	private Card weapon;
	
	private Board board;
	
	private Room currentRoom;
	private GameControlPanel gcp;
	private KnownCardsPanel kcp;
	
	private ArrayList<Card> deck;
	
	public SuggestionDialog() {
		gcp = GameControlPanel.getInstance();
		board = Board.getInstance();
		kcp = KnownCardsPanel.getInstance();
		
		setTitle("Make an Suggestion");
		setSize(300, 200);
		setLayout(new GridLayout(4, 2));
		
		roomLabel = new JLabel("Room");
		add(roomLabel);
		
		currentRoom = board.getRoom(board.getCurrentPlayer().getRoom());
		room = board.getCard(currentRoom.getName(), CardType.ROOM);
		roomSelect = new JTextField(currentRoom.getName());
		roomSelect.setEditable(false);
		add(roomSelect);
		
		personLabel = new JLabel("Person");
		add(personLabel);
		
		personSelect = new JComboBox<String>();
		personSelect.addItem("John");
		personSelect.addItem("Mark");
		personSelect.addItem("Adam");
		personSelect.addItem("Cameron");
		personSelect.addItem("Colin");
		personSelect.addItem("Henry");
		personSelect.addActionListener(new ComboListener());
		add(personSelect);
		
		weaponLabel = new JLabel("Weapon");
		add(weaponLabel);
		
		weaponSelect = new JComboBox<String>();
		weaponSelect.addItem("knife");
		weaponSelect.addItem("sword");
		weaponSelect.addItem("slingshot");
		weaponSelect.addItem("spoon");
		weaponSelect.addItem("rocket");
		weaponSelect.addItem("nuke");
		weaponSelect.addActionListener(new ComboListener());
		add(weaponSelect);
		
		submit = new JButton("Submit");
		submit.addActionListener(new ButtonListener());
		add(submit);
		
		cancel = new JButton("Cancel");
		cancel.addActionListener(new ButtonListener());
		add(cancel);
		
		deck = board.getDeck();
		
		
		boolean p = false;
		boolean w = false;
		for(Card c : deck) {
			if(p && w) {break;}
			if(c.getCardType() == CardType.PERSON && !p) {
				person = c;
				p = true;
			}
			else if(c.getCardType() == CardType.WEAPON && !w){
				weapon = c;
				w = true;
			}
		}
	}
	
	private class ComboListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == personSelect) {
				person = board.getCard(personSelect.getSelectedItem().toString(), CardType.PERSON);	
			}else if(e.getSource() == weaponSelect) {
				weapon = board.getCard(weaponSelect.getSelectedItem().toString(), CardType.WEAPON);
			}
		}
	}
	
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == submit) {
				Solution suggestion = new Solution();
				suggestion.setSolution(person, room, weapon);
				Board b = Board.getInstance();
				Card result = (b.handleSuggestion(suggestion, b.getCurrentPlayer(), b.getPlayers()));
				if(result == null) {
					b.setDisproved(false);
					String m = "Not Disproven";
					String guess = person.getName() + ", " + room.getName() + ", " + weapon.getName();
					gcp.setGuessResult(m);
					gcp.setGuess(guess);
				}
				else {
					String p = result.getName();
					String guess = person.getName() + ", " + room.getName() + ", " + weapon.getName();
					gcp.setGuessResult(p);
					gcp.setGuess(guess);
					kcp.update();	
				}
				setVisible(false);
			}else if(e.getSource() == cancel) {
				dispose();
			}
		}
	}
}
