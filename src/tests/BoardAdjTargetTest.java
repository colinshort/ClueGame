package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class BoardAdjTargetTest { 
		private static Board board;
		
		@BeforeAll
		public static void setUp() {
			board = Board.getInstance();
			board.setConfigFiles("ClueBoard.csv", "ClueSetup.txt");		
			board.initialize();
		}

		// Test board cells adjacent to rooms
		// These cells are LIGHT ORANGE on the planning spreadsheet
		@Test
		public void testAdjacenciesRooms()
		{
			// test Alderson with 2 door and secret passage
			Set<BoardCell> testList = board.getAdjList(22, 2);
			assertEquals(3, testList.size());
			//doors
			assertTrue(testList.contains(board.getCell(21, 4)));
			assertTrue(testList.contains(board.getCell(24, 4)));
			//secret passage: center of Brown
			assertTrue(testList.contains(board.getCell(4, 10)));
			
			// test Coolbaugh
			testList = board.getAdjList(2, 1);
			assertEquals(1, testList.size());
			//door
			assertTrue(testList.contains(board.getCell(2, 4)));
			
			// test Hill
			testList = board.getAdjList(10, 1);
			assertEquals(4, testList.size());
			//doors
			assertTrue(testList.contains(board.getCell(7, 5)));
			assertTrue(testList.contains(board.getCell(8, 5)));
			assertTrue(testList.contains(board.getCell(13, 1)));
			//secret passage: center of Green
			assertTrue(testList.contains(board.getCell(5, 21)));
		}

		
		// test adjacencies for door cells
		// These cells are LIGHT ORANGE on the planning spreadsheet
		@Test
		public void testAdjacencyDoor()
		{
			Set<BoardCell> testList = board.getAdjList(24, 9);
			assertEquals(3, testList.size());
			//room
			assertTrue(testList.contains(board.getCell(22, 12)));
			//walkway
			assertTrue(testList.contains(board.getCell(25, 9)));
			assertTrue(testList.contains(board.getCell(24, 8)));

			testList = board.getAdjList(21, 17);
			assertEquals(4, testList.size());
			//room
			assertTrue(testList.contains(board.getCell(22, 20)));
			//walkway
			assertTrue(testList.contains(board.getCell(20, 17)));
			assertTrue(testList.contains(board.getCell(21, 16)));
			assertTrue(testList.contains(board.getCell(22, 17)));
			
			testList = board.getAdjList(2, 17);
			assertEquals(4, testList.size());
			//room
			assertTrue(testList.contains(board.getCell(5, 21)));
			//walkway
			assertTrue(testList.contains(board.getCell(1, 17)));
			assertTrue(testList.contains(board.getCell(2, 16)));
		}
		
		// test adjacency to edges, walkways, and unused spaces
		// test cells near doors, but not adjacent
		// These tests are Dark Orange on the planning spreadsheet
		@Test
		public void testAdjacencyWalkways()
		{
			// adjacent to left edge, mostly surrounded
			Set<BoardCell> testList = board.getAdjList(19, 0);
			assertEquals(1, testList.size());
			assertTrue(testList.contains(board.getCell(19, 1)));
			
			// near 3 doors, not adjacent
			testList = board.getAdjList(19, 18);
			assertEquals(4, testList.size());
			assertTrue(testList.contains(board.getCell(18, 18)));
			assertTrue(testList.contains(board.getCell(20, 18)));
			assertTrue(testList.contains(board.getCell(19, 17)));
			assertTrue(testList.contains(board.getCell(19, 19)));

			// adjacent to door cell, near another door cell
			testList = board.getAdjList(20, 18);
			assertEquals(3, testList.size());
			assertTrue(testList.contains(board.getCell(19, 18)));
			assertTrue(testList.contains(board.getCell(20, 17)));
			assertTrue(testList.contains(board.getCell(20, 19)));

			// adjacent to unused cells
			testList = board.getAdjList(20,15);
			assertEquals(2, testList.size());
			assertTrue(testList.contains(board.getCell(19, 15)));
			assertTrue(testList.contains(board.getCell(21, 15)));
		}
		
		
		// test targets from Green
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsInGreen() {
			// test a roll of 1
			board.calcTargets(board.getCell(5, 21), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(3, targets.size());
			// center of Hill
			assertTrue(targets.contains(board.getCell(10, 1)));
			//doors
			assertTrue(targets.contains(board.getCell(2, 17)));
			assertTrue(targets.contains(board.getCell(3, 17)));
			
			// test a roll of 3
			board.calcTargets(board.getCell(5, 21), 3);
			targets= board.getTargets();
			assertEquals(10, targets.size());
			//walkway
			assertTrue(targets.contains(board.getCell(3, 15)));
			assertTrue(targets.contains(board.getCell(2, 15)));	
			//center of Hill
			assertTrue(targets.contains(board.getCell(10, 1)));	
			
			// test a roll of 4
			board.calcTargets(board.getCell(5, 21), 4);
			targets= board.getTargets();
			assertEquals(18, targets.size());
			//walkway
			assertTrue(targets.contains(board.getCell(5, 16)));
			assertTrue(targets.contains(board.getCell(1, 17)));	
			assertTrue(targets.contains(board.getCell(4, 17)));
			//center of Hill
			assertTrue(targets.contains(board.getCell(10, 1)));	
		}
		
		// test targets from Coolbaugh
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsInCoolbaugh() {
			// test a roll of 1
			board.calcTargets(board.getCell(2, 1), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(1, targets.size());
			assertTrue(targets.contains(board.getCell(2, 4)));
			
			// test a roll of 3
			board.calcTargets(board.getCell(2, 1), 3);
			targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCell(1, 5)));
			assertTrue(targets.contains(board.getCell(2, 6)));	
			assertTrue(targets.contains(board.getCell(4, 4)));
			
			// test a roll of 4
			board.calcTargets(board.getCell(2, 1), 4);
			targets= board.getTargets();
			assertEquals(6, targets.size());
			assertTrue(targets.contains(board.getCell(1, 6)));
			assertTrue(targets.contains(board.getCell(2, 5)));	
			assertTrue(targets.contains(board.getCell(2, 7)));
			assertTrue(targets.contains(board.getCell(4, 5)));	
		}

		// test target from door
		// These are LIGHT BLUE on the planning spreadsheet
		@Test
		public void testTargetsAtDoor() {
			// test a roll of 1, at door
			board.calcTargets(board.getCell(19, 9), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCell(22, 12)));
			assertTrue(targets.contains(board.getCell(19, 8)));	
			assertTrue(targets.contains(board.getCell(19, 10)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(19, 9), 3);
			targets= board.getTargets();
			assertEquals(11, targets.size());
			assertTrue(targets.contains(board.getCell(22, 12)));
			assertTrue(targets.contains(board.getCell(20, 7)));
			assertTrue(targets.contains(board.getCell(17, 8)));	
			assertTrue(targets.contains(board.getCell(18, 7)));
			assertTrue(targets.contains(board.getCell(19, 12)));	
			
			// test a roll of 4
			board.calcTargets(board.getCell(19, 9), 4);
			targets= board.getTargets();
			assertEquals(15, targets.size());
			assertTrue(targets.contains(board.getCell(22, 12)));
			assertTrue(targets.contains(board.getCell(22, 8)));
			assertTrue(targets.contains(board.getCell(20, 6)));	
			assertTrue(targets.contains(board.getCell(16, 8)));
			assertTrue(targets.contains(board.getCell(17, 7)));	
		}

		@Test
		public void testTargetsInWalkway1() {
			// test a roll of 1
			board.calcTargets(board.getCell(19, 2), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCell(19, 1)));
			assertTrue(targets.contains(board.getCell(19, 3)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(19, 2), 3);
			targets= board.getTargets();
			assertEquals(5, targets.size());
			assertTrue(targets.contains(board.getCell(16, 1)));
			assertTrue(targets.contains(board.getCell(18, 4)));
			assertTrue(targets.contains(board.getCell(19, 5)));
			assertTrue(targets.contains(board.getCell(20, 4)));	
			
			// test a roll of 4
			board.calcTargets(board.getCell(19, 2), 4);
			targets= board.getTargets();
			assertEquals(8, targets.size());
			assertTrue(targets.contains(board.getCell(16, 1)));
			assertFalse(targets.contains(board.getCell(22, 2)));
			assertTrue(targets.contains(board.getCell(20, 5)));
			assertTrue(targets.contains(board.getCell(18, 5)));
			assertTrue(targets.contains(board.getCell(17, 4)));	
		}

		@Test
		public void testTargetsInWalkway2() {
			// test a roll of 1
			board.calcTargets(board.getCell(1, 6), 1);
			Set<BoardCell> targets= board.getTargets();
			assertEquals(3, targets.size());
			assertTrue(targets.contains(board.getCell(1, 5)));
			assertTrue(targets.contains(board.getCell(2, 6)));	
			
			// test a roll of 3
			board.calcTargets(board.getCell(1, 6), 3);
			targets= board.getTargets();
			assertEquals(6, targets.size());
			assertTrue(targets.contains(board.getCell(2, 4)));
			assertTrue(targets.contains(board.getCell(2, 6)));
			assertTrue(targets.contains(board.getCell(2, 8)));	
			
			// test a roll of 4
			board.calcTargets(board.getCell(1, 6), 4);
			targets= board.getTargets();
			assertEquals(8, targets.size());
			assertTrue(targets.contains(board.getCell(4, 7)));
			assertTrue(targets.contains(board.getCell(2, 7)));
			assertTrue(targets.contains(board.getCell(1, 8)));	
		}

		@Test
		// test to make sure occupied locations do not cause problems
		public void testTargetsOccupied() {
			// test a roll of 4 blocked 2 down
			board.getCell(10, 7).setOccupied(true);
			board.calcTargets(board.getCell(8, 7), 2);
			board.getCell(10, 7).setOccupied(false);
			Set<BoardCell> targets = board.getTargets();
			assertEquals(7, targets.size());
			assertFalse(targets.contains(board.getCell(10, 1)));
			assertTrue(targets.contains(board.getCell(9, 6)));
			assertTrue(targets.contains(board.getCell(7, 6)));	
			assertFalse( targets.contains(board.getCell(10, 7)));
			assertFalse( targets.contains( board.getCell(8, 10))) ;
		
			// we want to make sure we can get into a room, even if flagged as occupied
			board.getCell(2, 1).setOccupied(true);
			board.getCell(2, 4).setOccupied(true);
			board.calcTargets(board.getCell(2, 4), 1);
			board.getCell(2, 1).setOccupied(false);
			board.getCell(2, 4).setOccupied(false);
			targets= board.getTargets();
			assertEquals(4, targets.size());
			assertTrue(targets.contains(board.getCell(2, 1)));	
			assertFalse(targets.contains(board.getCell(1, 5)));		
			
			// check leaving a room with a blocked doorway
			board.getCell(3, 8).setOccupied(true);
			board.calcTargets(board.getCell(2, 8), 1);
			board.getCell(3, 8).setOccupied(false);
			targets= board.getTargets();
			assertEquals(2, targets.size());
			assertTrue(targets.contains(board.getCell(1, 8)));
			assertFalse(targets.contains(board.getCell(4, 10)));	
			assertTrue(targets.contains(board.getCell(2, 7)));

		}
}