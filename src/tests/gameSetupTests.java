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

	//test for correct number of players
	//test for correct number of human players
	//test for correct number of computer players
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
	
	//test for correct number of cards and card types in the deck
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
	
	//test that the solution has a person, room, and weapon card
	@Test
	public void testSolution() {
		Solution solution = board.getAnswer();
		assertEquals(CardType.PERSON, solution.getPerson().getCardType());
		assertEquals(CardType.ROOM, solution.getRoom().getCardType());
		assertEquals(CardType.WEAPON, solution.getWeapon().getCardType());
	}
	
	//test for proper deal
	@Test
	public void testDeal() {
		ArrayList<Card> deck = board.getDeck();
		ArrayList<Player> players = board.getPlayers();
		int cardsLeft = deck.size() - 3; //number of cards not dealt (excludes solution cards)
		int min = cardsLeft / players.size(); //minimum possible cards per hand
		int count = 0; //count invalid hands
		int sum = 0; //sum of number of cards in each hand
		for(int i = 0; i < players.size(); i++) {
			ArrayList<Card> myCards = players.get(i).getHand();
			sum += myCards.size();
			//check for invalid hand
			//valid hands either have min or min + 1 cards
			if(myCards.size() > min + 1 || myCards.size() < min) {
				count++;
			}
		}
		//check that there are no invalid hands
		assert(count == 0);
		//check that correct number of cards were dealt (all cards dealt and no double-dealing)
		assert(sum == cardsLeft);
	}

}
