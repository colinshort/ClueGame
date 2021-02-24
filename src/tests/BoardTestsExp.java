package tests;
import experiment.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class BoardTestsExp {
	private TestBoard board;
	
	@Before // Run before each test
	public void setUp() {
		board = new TestBoard();
	}
	
	//test adjacency list creation with start at top left
	@Test
	public void testAdjTopLeft() {
		TestBoardCell cell = board.getCell(0,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(1,0)));
		assertTrue(testList.contains(board.getCell(0,1)));
		assertEquals(2, testList.size());
		
	}
	
	//test adjacency list creation with start at bottom right
	@Test
	public void testAdjBottomRight() {
		TestBoardCell cell = board.getCell(3,3);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(3,2)));
		assertTrue(testList.contains(board.getCell(2,3)));
		assertEquals(2, testList.size());
	}
	
	//test adjacency list creation with start at right edge
	@Test
	public void testAdjRightEdge() {
		TestBoardCell cell = board.getCell(1,3);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(3,0)));
		assertTrue(testList.contains(board.getCell(2,1)));
		assertTrue(testList.contains(board.getCell(3,2)));
		assertEquals(3, testList.size());
	}
	
	//test adjacency list creation with start at left edge
	@Test
	public void testAdjLeftEdge() {
		TestBoardCell cell = board.getCell(3,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(2,0)));
		assertTrue(testList.contains(board.getCell(3,1)));
		assertEquals(3, testList.size());
	}
	
	/*
	 * Test target creation with empty board
	 * start location equals (0,0)
	 * die roll equals 3
	 */
	
	@Test
	public void testTargetsNormal() {
		TestBoardCell cell = board.getCell(0, 0);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(6, targets.size());
		assertTrue(targets.contains(board.getCell(3, 0)));
		assertTrue(targets.contains(board.getCell(2, 1)));
		assertTrue(targets.contains(board.getCell(0, 1)));
		assertTrue(targets.contains(board.getCell(1, 2)));
		assertTrue(targets.contains(board.getCell(0, 3)));
		assertTrue(targets.contains(board.getCell(1, 0)));
	}
	
	/*
	 * Test target creation with mixed occupied and rooms
	 * start location equals (0,3)
	 * die roll equals 3
	 */
	@Test
	public void testTargetsMixed() {
		board.getCell(0, 2).setOccupied(true);
		board.getCell(1, 2).setRoom(true);
		TestBoardCell cell = board.getCell(0,3);
		board.calcTargets(cell, 3);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCell(1,2)));
		assertTrue(targets.contains(board.getCell(2, 2)));
		assertTrue(targets.contains(board.getCell(3, 3)));
	}
	
	
	/*
	 * Test target creation with occupied 
	 * start location equals (2,1)
	 * die roll equals 4
	 */
	@Test
	public void TestTargetsOccupied() {
		board.getCell(1, 0).setOccupied(true);
		board.getCell(1, 1).setOccupied(true);
		board.getCell(2, 2).setOccupied(true);
		TestBoardCell cell = board.getCell(2,1);
		board.calcTargets(cell, 4);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCell(3,2)));
		assertTrue(targets.contains(board.getCell(2,3)));
		
	}
	
	/*
	 * Test target creation with rooms
	 * start location equals (2,1)
	 * die roll equals 5
	 */
	
	@Test
	public void TestTargetsRoom() {
		board.getCell(1, 1).setRoom(true);
		board.getCell(2, 0).setRoom(true);
		board.getCell(3, 1).setRoom(true);
		board.getCell(2, 2).setRoom(true);
		TestBoardCell cell = board.getCell(2,1);
		board.calcTargets(cell, 5);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCell(1,1)));
		assertTrue(targets.contains(board.getCell(2,0)));
		assertTrue(targets.contains(board.getCell(3,1)));
		assertTrue(targets.contains(board.getCell(2,2)));
	}
	
	/* test target creation with rooms and occupied cells
	 * starting in bottom right corner (3,3),
	 * blocked on left side (3,2) and a room above (2,3)
	 * die roll equals 6
	 */
	
	@Test
	public void TestTargetsMixed2() {
		board.getCell(3, 2).setOccupied(true);
		board.getCell(2, 3).setRoom(true);
		TestBoardCell cell = board.getCell(3, 3);
		board.calcTargets(cell, 6);
		Set<TestBoardCell> targets = board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCell(2,3)));
	}
}
