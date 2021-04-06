package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class KnownCardsPanel extends JPanel{
	private JTextField peopleHand;
	private JTextField roomHand;
	private JTextField roomSeen;
	private JTextField peopleSeen;
	private JTextField weaponHand;
	private JTextField weaponSeen;

	public KnownCardsPanel() {
		setLayout(new GridLayout(3,1));

		JPanel myPanel = createPeoplePanel();
		add(myPanel);

		myPanel = createRoomPanel();
		add(myPanel);

		myPanel = createWeaponPanel();
		add(myPanel);
	}

	public static void main(String[] args) {
		KnownCardsPanel panel = new KnownCardsPanel();
		JFrame frame = new JFrame();
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(200, 800);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		ArrayList<Card> rooms = new ArrayList();
		rooms.add(new Card("Coolbaugh", CardType.ROOM));
		panel.setRoomHand(rooms);
		
		ArrayList<Card> rooms2 = new ArrayList();
		rooms.add(new Card("Green Hall", CardType.ROOM));
		panel.setRoomH(rooms2);
		
		//test filling in the data
		ArrayList<Card> peopleHand = new ArrayList<>();
		peopleHand.add(new Card("John", CardType.PERSON));
		panel.setPeopleHand(peopleHand);
		
		ArrayList<Card> weaponHand = new ArrayList<>();
		weaponHand.add(new Card("knife", CardType.WEAPON));
		panel.setWeaponsHand(weaponHand);
	}

	private JPanel createPeoplePanel() {
		JPanel people = new JPanel();
		people.setLayout(new GridLayout(4,1));	
		people.setBorder(new TitledBorder (new EtchedBorder(), "People"));

		JLabel handLabel = new JLabel("In Hand:");
		peopleHand = new JTextField("None", 15);

		JLabel seenLabel = new JLabel("Seen:");
		peopleSeen = new JTextField("None", 15);

		people.add(handLabel);
		people.add(peopleHand);
		people.add(seenLabel);
		people.add(peopleSeen);

		return people;
	}

	private JPanel createRoomPanel() {
		JPanel room = new JPanel();
		room.setLayout(new GridLayout(4,1));
		room.setBorder(new TitledBorder(new EtchedBorder(), "Room"));

		JLabel handLabel = new JLabel("In Hand: ");
		roomHand = new JTextField("None", 15);

		JLabel seenLabel = new JLabel("Seen: ");
		roomSeen = new JTextField("None", 15);

		room.add(handLabel);
		room.add(roomHand);
		room.add(seenLabel);
		room.add(roomSeen);	

		return room;
	}

	private JPanel createWeaponPanel() {
		JPanel weapons = new JPanel();
		weapons.setLayout(new GridLayout(4,1));
		weapons.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));

		JLabel hand = new JLabel("In Hand:");
		weaponHand = new JTextField("None", 15);

		JLabel seen = new JLabel("Seen:");
		weaponSeen = new JTextField("None", 15);

		weapons.add(hand);
		weapons.add(weaponHand);
		weapons.add(seen);
		weapons.add(weaponSeen);

		return weapons;
	}

	public void setPeopleHand(ArrayList<Card> people) {
		for(Card p : people) {
			peopleHand.setText(p.getName());	
		}
	}
	
	public void setPeopleSeen(ArrayList<Card> people) {
		for(Card p : people) {
			peopleSeen.setText(p.getName());	
		}
	}

	public void setRoomHand(ArrayList<Card> rooms) {
		for(Card r : rooms) {
			roomHand.setText(r.getName());
		}
	}
	
	public void setRoomSeen(ArrayList<Card> rooms) {
		for(Card r : rooms) {
			roomSeen.setText(r.getName());
		}
	}
	
	public void setWeaponsHand(ArrayList<Card> weapons) {
		for(Card w : weapons) {
			weaponHand.setText(w.getName());
		}
	}
	
	public void setWeaponSeen(ArrayList<Card> weapons) {
		for(Card w : weapons) {
			weaponSeen.setText(w.getName());
		}
	}
	
}
