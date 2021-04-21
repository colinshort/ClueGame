package clueGame;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class AccusationDialog extends JDialog{
	private JLabel roomLabel;
	private JLabel personLabel;
	private JLabel weaponLabel;
	
	private JComboBox<String> roomSelect;
	private JComboBox<String> personSelect;
	private JComboBox<String> weaponSelect;
	
	private JButton submit;
	private JButton cancel;
	
	private Card room;
	private Card person;
	private Card weapon;
	
	public AccusationDialog() {
		setTitle("Make an Accusation");
		setSize(300, 200);
		setLayout(new GridLayout(4, 2));
		
		roomLabel = new JLabel("Room");
		add(roomLabel);
		
		roomSelect = new JComboBox<String>();
		roomSelect.addItem("Coolbaugh");
		roomSelect.addItem("Alderson");
		roomSelect.addItem("Hill");
		roomSelect.addItem("Brown");
		roomSelect.addItem("Student Center");
		roomSelect.addItem("Green");
		roomSelect.addItem("CoorsTek");
		roomSelect.addItem("Marquez");
		roomSelect.addItem("Engineering Hall");
		roomSelect.addActionListener(new ComboListener());
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
		
		room = new Card("Coolbaugh", CardType.ROOM);	
		person = new Card("John", CardType.PERSON);
		weapon = new Card("spoon", CardType.WEAPON);
	}
	
	private class ComboListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == roomSelect) {
				room.setName(roomSelect.getSelectedItem().toString());
			}else if(e.getSource() == personSelect) {
				person.setName(personSelect.getSelectedItem().toString());	
			}else if(e.getSource() == weaponSelect) {
				weapon.setName(weaponSelect.getSelectedItem().toString());
			}
		}
	}
	
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(e.getSource() == submit) {
				Solution accusation = new Solution();
				accusation.setSolution(person, room, weapon);
				Board b = Board.getInstance();
				if(b.checkAccusation(b.getAnswer(), accusation)) {
					JOptionPane.showMessageDialog(null, "You Win!");
					System.exit(0);
				}else {
					JOptionPane.showMessageDialog(null, "Incorrect!\nYou lose!");
					System.exit(0);
				}
				
			}else if(e.getSource() == cancel) {
				dispose();
			}
		}
	}
}
