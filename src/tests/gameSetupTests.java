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
		assertEquals(myPlayers.size(), 6);
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

}
