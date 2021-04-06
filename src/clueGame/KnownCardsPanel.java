package clueGame;

import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class KnownCardsPanel extends JPanel{
	private ArrayList<JTextField> peopleHand;
	private ArrayList<JTextField> roomHand;
	private ArrayList<JTextField> roomSeen;
	private ArrayList<JTextField> peopleSeen;
	private ArrayList<JTextField> weaponHand;
	private ArrayList<JTextField> weaponSeen;
	private JPanel people;
	private JPanel rooms;
	private JPanel weapons;

	public KnownCardsPanel() {
		setLayout(new GridLayout(3,1));
		setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));

		peopleHand = new ArrayList<>();
		roomHand = new ArrayList<>();
		weaponHand = new ArrayList<>();

		peopleSeen = new ArrayList<>();
		roomSeen = new ArrayList<>();
		weaponSeen = new ArrayList<>();

		people = new JPanel();
		createPeoplePanel();
		add(people);

		rooms = new JPanel();
		createRoomPanel();
		add(rooms);

		weapons = new JPanel();
		createWeaponPanel();
		add(weapons);
	}

	public static void main(String[] args) {
		KnownCardsPanel panel = new KnownCardsPanel();
		JFrame frame = new JFrame();
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(200, 800);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible

		//test filling in the data
		ArrayList<Card> rooms = new ArrayList<Card>();
		rooms.add(new Card("Coolbaugh", CardType.ROOM));
		rooms.add(new Card("Brown", CardType.ROOM));
		panel.setRoomHand(rooms);

		ArrayList<Card> rooms2 = new ArrayList<Card>();
		rooms2.add(new Card("Green Hall", CardType.ROOM));
		panel.setRoomSeen(rooms2);

		ArrayList<Card> peopleHand = new ArrayList<>();
		peopleHand.add(new Card("John", CardType.PERSON));
		panel.setPeopleHand(peopleHand);

		ArrayList<Card> weaponHand = new ArrayList<>();
		weaponHand.add(new Card("knife", CardType.WEAPON));
		panel.setWeaponsHand(weaponHand);

		panel.update();
		
		frame.pack();
		frame.setVisible(true);
	}

	private void createPeoplePanel() {
		JLabel peopleHandLabel = new JLabel("In Hand:");
		JLabel peopleSeenLabel = new JLabel("Seen:");

		people.add(peopleHandLabel);
		if(peopleHand.isEmpty()) {
			people.add(new JTextField("None", 15));
		}else {
			addTextFields(people, peopleHand);
		}

		people.add(peopleSeenLabel);
		if(peopleSeen.isEmpty()) {
			people.add(new JTextField("None", 15));
		}else {
			addTextFields(people, peopleSeen);
		}

		people.setLayout(new BoxLayout(people, BoxLayout.Y_AXIS));	
		people.setBorder(new TitledBorder (new EtchedBorder(), "People"));
	}

	private void createRoomPanel() {
		rooms.setLayout(new GridLayout(4,1));
		rooms.setBorder(new TitledBorder(new EtchedBorder(), "Room"));

		JLabel roomHandLabel = new JLabel("In Hand:");
		JLabel roomSeenLabel = new JLabel("Seen:");
		
		rooms.add(roomHandLabel);
		if(roomHand.isEmpty()) {
			rooms.add(new JTextField("None",15));
		}else {
			addTextFields(rooms, roomHand);
		}

		rooms.add(roomSeenLabel);
		if(roomSeen.isEmpty()) {
			rooms.add(new JTextField("None", 15));
		}else {
			addTextFields(rooms, roomSeen);
		}
		
		rooms.setLayout(new BoxLayout(rooms, BoxLayout.Y_AXIS));	
		rooms.setBorder(new TitledBorder(new EtchedBorder(), "Room"));

	}

	private void createWeaponPanel() {
		

		JLabel weaponHandLabel = new JLabel("In Hand:");
		JLabel weaponSeenLabel = new JLabel("Seen:");
		
		weapons.add(weaponHandLabel);
		if(weaponHand.isEmpty()) {
			weapons.add(new JTextField("None", 15));
		}else {
			addTextFields(weapons, weaponHand);
		}

		weapons.add(weaponSeenLabel);
		if(weaponSeen.isEmpty()) {
			weapons.add(new JTextField("None", 15));
		}else {
			addTextFields(weapons, weaponSeen);
		}
		
		weapons.setLayout(new BoxLayout(weapons, BoxLayout.Y_AXIS));	
		weapons.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
	}

	public void update() {
		people.removeAll();
		rooms.removeAll();
		weapons.removeAll();
		
		createPeoplePanel();
		createRoomPanel();
		createWeaponPanel();
	}

	public void setPeopleHand(ArrayList<Card> people) {
		for(Card p : people) {
			peopleHand.add(new JTextField(p.getName(), 15));	
		}
	}

	public void setPeopleSeen(ArrayList<Card> people) {
		for(Card p : people) {
			peopleSeen.add(new JTextField(p.getName(), 15));	
		}
	}

	public void setRoomHand(ArrayList<Card> rooms) {
		for(Card r : rooms) {
			roomHand.add(new JTextField(r.getName(), 15));
		}
	}

	public void setRoomSeen(ArrayList<Card> rooms) {
		for(Card r : rooms) {
			roomSeen.add(new JTextField(r.getName(), 15));
		}
	}

	public void setWeaponsHand(ArrayList<Card> weapons) {
		for(Card w : weapons) {
			weaponHand.add(new JTextField(w.getName(), 15));
		}
	}

	public void setWeaponSeen(ArrayList<Card> weapons) {
		for(Card w : weapons) {
			weaponSeen.add(new JTextField(w.getName(), 15));
		}
}

	public void addTextFields(JPanel panel, ArrayList<JTextField> fields) {
		for(JTextField f : fields) {
			panel.add(f);
		}
	}
}


