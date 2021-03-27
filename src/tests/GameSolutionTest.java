package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.Player;

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
		
	}
	
	
}
