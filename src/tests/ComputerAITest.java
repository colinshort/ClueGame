package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.ComputerPlayer;
import clueGame.Room;
import clueGame.Solution;

class ComputerAITest {

	private static Board board;
	
	@BeforeEach
	public void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueBoard.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	@Test
	public void testCreateSuggestion() {
		ComputerPlayer computer = new ComputerPlayer("Colin", "red", 9, 11);
		computer.updateSeen(new Card("Cameron", CardType.PERSON));
		computer.updateSeen(new Card("CoorsTek", CardType.ROOM));
		computer.updateSeen(new Card("Spoon", CardType.WEAPON));
		
		computer.updateHand(new Card("Mark", CardType.PERSON));
		computer.updateHand(new Card("Alderson", CardType.ROOM));
		computer.updateHand(new Card("Nuke", CardType.WEAPON));
		
		Room room = new Room("Green Center");
		
		Solution suggestion = computer.createSuggestion(board.getDeck(), room);
		
		Card person = suggestion.getPerson();
		Card weapon = suggestion.getWeapon();
		
		int matches = 0;
		
		for(Card handCard : computer.getHand()) {
			if(handCard.equals(person) || handCard.equals(weapon)) {
				matches ++;
			}
		}
		
		
		for(Card seenCard : computer.getSeenCards()) {
			if(seenCard.equals(person) || seenCard.equals(weapon)) {
				matches ++;
			}
		}
		
		assertEquals(0, matches);
	}
}
