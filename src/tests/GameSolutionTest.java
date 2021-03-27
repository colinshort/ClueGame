package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Card;
import clueGame.CardType;
import clueGame.Player;
import clueGame.Solution;

class GameSolutionTest {

	private static Board board;
	
	@BeforeEach
	public void setUp() {
		board = Board.getInstance();
		board.setConfigFiles("ClueBoard.csv", "ClueSetup.txt");
		board.initialize();
	}
	
	//test checkAccusation method
	@Test
	public void testCheckAccusation() {
		Solution testSoln = new Solution();
		testSoln.setSolution(new Card("John", CardType.PERSON), new Card("Brown", CardType.ROOM), new Card("Knife", CardType.WEAPON));
		
		Solution accusation = new Solution();
		
		accusation.setSolution(new Card("Cameron", CardType.PERSON), new Card("Coolbaugh", CardType.ROOM), new Card("Sword", CardType.WEAPON));
		assertFalse(board.checkAccusation(testSoln, accusation));
		
		accusation.setSolution(new Card("John", CardType.PERSON), new Card("Brown", CardType.ROOM), new Card("Sword", CardType.WEAPON));
		assertFalse(board.checkAccusation(testSoln, accusation));
		
		accusation.setSolution(new Card("John", CardType.PERSON), new Card("Brown", CardType.ROOM), new Card("Knife", CardType.WEAPON));
		assertTrue(board.checkAccusation(testSoln, accusation));
	}
}
