package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.HumanPlayer;
import clueGame.Player;
import clueGame.Solution;

class GameSolutionTest {

	private static Board board;
	private static Solution testSoln;

	@BeforeEach
	public void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueBoard.csv", "ClueSetup.txt");
		board.initialize();

		testSoln = new Solution();
	}

	//test checkAccusation method
	@Test
	public void testCheckAccusation() {
		testSoln.setSolution(new Card("John", CardType.PERSON), new Card("Brown", CardType.ROOM), new Card("Knife", CardType.WEAPON));

		Solution accusation = new Solution();

		accusation.setSolution(new Card("Cameron", CardType.PERSON), new Card("Coolbaugh", CardType.ROOM), new Card("Sword", CardType.WEAPON));
		assertFalse(board.checkAccusation(testSoln, accusation));

		accusation.setSolution(new Card("John", CardType.PERSON), new Card("Brown", CardType.ROOM), new Card("Sword", CardType.WEAPON));
		assertFalse(board.checkAccusation(testSoln, accusation));

		accusation.setSolution(new Card("John", CardType.PERSON), new Card("Brown", CardType.ROOM), new Card("Knife", CardType.WEAPON));
		assertTrue(board.checkAccusation(testSoln, accusation));
	}

	@Test 
	public void testDisproveSuggestion() {
		Solution testSoln = new Solution();
		Card person = new Card("John", CardType.PERSON);
		Card room = new Card("Brown", CardType.ROOM);
		Card weapon = new Card("Knife", CardType.WEAPON);
		testSoln.setSolution(person, room, weapon);
		HumanPlayer player = new HumanPlayer("Cameron", "red", 7, 8);
	
		player.updateHand(person);
		player.updateHand(room);
		player.updateHand(weapon);

		//multiple matching cards: check that each matching card is returned once
		ArrayList<Card> testHand = player.getHand();
		
		for(int i = 0; i < 100; i ++) {
			Card temp = player.disproveSuggestion(testSoln);
			int idx = testHand.indexOf(temp);
			if(idx != -1) {
				testHand.remove(idx);
			}else {
				break;	
			}
		}
		
		assert(testHand.isEmpty());

		//assert that the matching card is returned if only one
		player.clearHand();
		player.updateHand(person);
		Card room2 = new Card("Green Center", CardType.ROOM);
		player.updateHand(room2);
		Card weapon2 = new Card("Spoon", CardType.WEAPON);
		player.updateHand(weapon2);
		assertEquals(person, player.disproveSuggestion(testSoln));


		//assert that null is returned when no matching cards
		player.clearHand();
		player.updateHand(new Card("Cameron", CardType.PERSON));
		player.updateHand(new Card("Coolbaugh", CardType.ROOM));
		player.updateHand(new Card("Spoon", CardType.WEAPON));
		assertEquals(null, player.disproveSuggestion(testSoln));
	}
}
