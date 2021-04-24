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
	private ArrayList<Card> peopleHandCards;
	private ArrayList<Card> roomHandCards;
	private ArrayList<Card> weaponHandCards;
	private ArrayList<Card> peopleSeenCards;
	private ArrayList<Card> roomSeenCards;
	private ArrayList<Card> weaponSeenCards;
	private JPanel people;
	private JPanel rooms;
	private JPanel weapons;
	private Board board;

	private static KnownCardsPanel kcp = new KnownCardsPanel();
	
	public KnownCardsPanel() {
		board = Board.getInstance();

		setLayout(new GridLayout(3,1));
		setBorder(new TitledBorder(new EtchedBorder(), "Known Cards"));

		peopleHand = new ArrayList<>();
		roomHand = new ArrayList<>();
		weaponHand = new ArrayList<>();

		peopleSeen = new ArrayList<>();
		roomSeen = new ArrayList<>();
		weaponSeen = new ArrayList<>();
		
		roomHandCards = new ArrayList<>();
		peopleHandCards = new ArrayList<>();
		weaponHandCards = new ArrayList<>();
		
		roomSeenCards = new ArrayList<>();
		peopleSeenCards = new ArrayList<>();
		weaponSeenCards = new ArrayList<>();

		people = new JPanel();
		createPeoplePanel();
		add(people);

		rooms = new JPanel();
		createRoomPanel();
		add(rooms);

		weapons = new JPanel();
		createWeaponPanel();
		add(weapons);
		
		update();
	}
	
	public static KnownCardsPanel getInstance() {
		return kcp;
	}

	public static void main(String[] args) {
		KnownCardsPanel panel = new KnownCardsPanel();
		JFrame frame = new JFrame();
		frame.setContentPane(panel); // put the panel in the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.pack();
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
		//create people panel labels
		JLabel peopleHandLabel = new JLabel("In Hand:");
		JLabel peopleSeenLabel = new JLabel("Seen:");

		//add labels and text fields
		people.add(peopleHandLabel);
		if(peopleHand.isEmpty()) {
			//if no people in hand, display none
			people.add(new JTextField("None", 15));
		}else {
			//add text field for each person in hand
			addTextFields(people, peopleHand);
		}

		//add labels and text fields
		people.add(peopleSeenLabel);
		if(peopleSeen.isEmpty()) {
			//if no people seen, display none
			people.add(new JTextField("None", 15));
		}else {
			//add text field for each person seen
			addTextFields(people, peopleSeen);
		}

		people.setLayout(new BoxLayout(people, BoxLayout.Y_AXIS));	
		people.setBorder(new TitledBorder (new EtchedBorder(), "People"));
	}

	private void createRoomPanel() {
		rooms.setLayout(new GridLayout(4,1));
		rooms.setBorder(new TitledBorder(new EtchedBorder(), "Room"));
		//create room panels

		JLabel roomHandLabel = new JLabel("In Hand:");
		JLabel roomSeenLabel = new JLabel("Seen:");

		//add labels and text fields
		rooms.add(roomHandLabel);
		if(roomHand.isEmpty()) {
			//if no rooms in hand, display none
			rooms.add(new JTextField("None",15));
		}else {
			//add text field for each room in hand
			addTextFields(rooms, roomHand);
		}
		//add labels and text fields
		rooms.add(roomSeenLabel);
		if(roomSeen.isEmpty()) {
			//if no room seen, display none
			rooms.add(new JTextField("None", 15));
		}else {
			//add text field for each room seen
			addTextFields(rooms, roomSeen);
		}

		rooms.setLayout(new BoxLayout(rooms, BoxLayout.Y_AXIS));	
		rooms.setBorder(new TitledBorder(new EtchedBorder(), "Room"));

	}

	private void createWeaponPanel() {
		JLabel weaponHandLabel = new JLabel("In Hand:");
		JLabel weaponSeenLabel = new JLabel("Seen:");

		//add labels and text fields
		weapons.add(weaponHandLabel);
		if(weaponHand.isEmpty()) {
			//if not weapons in hand, display none
			weapons.add(new JTextField("None", 15));
		}else {
			//add text field for each weapon in hand
			addTextFields(weapons, weaponHand);
		}

		//add labels and text fields
		weapons.add(weaponSeenLabel);
		if(weaponSeen.isEmpty()) {
			//if not weapons seen, display none
			weapons.add(new JTextField("None", 15));
		}else {
			//add text field for each weapon seen
			addTextFields(weapons, weaponSeen);
		}

		weapons.setLayout(new BoxLayout(weapons, BoxLayout.Y_AXIS));	
		weapons.setBorder(new TitledBorder(new EtchedBorder(), "Weapons"));
	}

	//clears the people, rooms, and weapons panels to prepare for update
	//recreates panels with updated functionality
	public void update() {
		people.removeAll();
		rooms.removeAll();
		weapons.removeAll();

		updateLists();
		
		createPeoplePanel();
		createRoomPanel();
		createWeaponPanel();
		
		revalidate();
	}

	//create list of text fields for each person in hand
	public void setPeopleHand(ArrayList<Card> people) {
		for(Card p : people) {
			if(p.getCardType() == CardType.PERSON && peopleHandCards.indexOf(p) == -1) {
				JTextField f = new JTextField(p.getName(), 15);
				f.setBackground(p.getSource().colorConvert(p.getSource().getColor()));
				peopleHandCards.add(p);
				peopleHand.add(f);	
			}
		}
	}

	//create list of text fields for each person seen
	public void setPeopleSeen(ArrayList<Card> people) {
		for(Card p : people) {
			if(p.getCardType() == CardType.PERSON && peopleSeenCards.indexOf(p) == -1) {
				JTextField f = new JTextField(p.getName(), 15);
				f.setBackground(p.getSource().colorConvert(p.getSource().getColor()));
				peopleSeenCards.add(p);
				peopleSeen.add(f);	
			}
		}
	}

	//create list of text fields for each room in hand
	public void setRoomHand(ArrayList<Card> rooms) {
		for(Card r : rooms) {
			if(r.getCardType() == CardType.ROOM && roomHandCards.indexOf(r) == -1) {
				JTextField f = new JTextField(r.getName(), 15);
				f.setBackground(r.getSource().colorConvert(r.getSource().getColor()));
				roomHandCards.add(r);
				roomHand.add(f);	
			}
		}
	}

	//create list of text fields for each room seen
	public void setRoomSeen(ArrayList<Card> rooms) {
		for(Card r : rooms) {
			if(r.getCardType() == CardType.ROOM && roomSeenCards.indexOf(r) == -1) {
				JTextField f = new JTextField(r.getName(), 15);
				f.setBackground(r.getSource().colorConvert(r.getSource().getColor()));
				roomSeenCards.add(r);
				roomSeen.add(f);	
			}		
		}
	}

	//create list of text fields for each weapon in hand
	public void setWeaponsHand(ArrayList<Card> weapons) {
		for(Card w : weapons) {
			if(w.getCardType() == CardType.WEAPON && weaponHandCards.indexOf(w) == -1) {
				JTextField f = new JTextField(w.getName(), 15);
				f.setBackground(w.getSource().colorConvert(w.getSource().getColor()));
				weaponHandCards.add(w);
				weaponHand.add(f);	
			}
		}
	}
	//create list of text fields for each weapon seen
	public void setWeaponSeen(ArrayList<Card> weapons) {
		for(Card w : weapons) {
			if(w.getCardType() == CardType.WEAPON && weaponSeenCards.indexOf(w) == -1) {
				JTextField f = new JTextField(w.getName(), 15);
				f.setBackground(w.getSource().colorConvert(w.getSource().getColor()));
				weaponSeenCards.add(w);
				weaponSeen.add(f);	
			}
		}
	}

	//add list of text fields to given panel
	public void addTextFields(JPanel panel, ArrayList<JTextField> fields) {
		for(JTextField f : fields) {
			panel.add(f);
		}
	}
	
	public void updateLists() {
		ArrayList<Card> hand = board.getHumanPlayer().getHand();
		ArrayList<Card> seen = board.getHumanPlayer().getSeenCards();
		
		setRoomHand(hand);
		setPeopleHand(hand);
		setWeaponsHand(hand);
		
		setRoomSeen(seen);
		setPeopleSeen(seen);
		setWeaponSeen(seen);
	}
}


