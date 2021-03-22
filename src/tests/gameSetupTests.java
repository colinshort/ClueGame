package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.*;

class gameSetupTests {
	private static Board board;
	
	@BeforeEach
	public void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueBoard.csv", "ClueSetup.txt");
		board.initialize();
	}

	@Test
	public void testPlayers() {
		ArrayList<Player> myPlayers = board.getPlayers();
		assertEquals(6, myPlayers.size());
		int numHuman = 0;
		int numComputer = 0;
		for(Player p : myPlayers) {
			if(p.isHuman()) {
				numHuman ++;
			}else {
				numComputer ++;
			}
		}
		
		assertEquals(numHuman, 1);
		assertEquals(numComputer, 5);
	}
	
	@Test
	public void testDeck() {
		ArrayList <Card> myCards = board.getDeck();
		assertEquals(21, myCards.size());
		int rooms = 0;
		int weapons = 0;
		int people = 0;
		for(Card c : myCards) {
			if(c.getCardType() == CardType.ROOM) {
				rooms++;
			}
			else if(c.getCardType() == CardType.WEAPON) {
				weapons++;
			}
			else if (c.getCardType() == CardType.PERSON) {
				people++;
			}
		}
		
		assertEquals(9, rooms);
		assertEquals(6, weapons);
		assertEquals(6, people);
	}
	
	@Test
	public void testSolution() {
		Solution solution = board.getAnswer();
		assertEquals(CardType.PERSON, solution.getPerson().getCardType());
		assertEquals(CardType.ROOM, solution.getRoom().getCardType());
		assertEquals(CardType.WEAPON, solution.getWeapon().getCardType());
	}

}
