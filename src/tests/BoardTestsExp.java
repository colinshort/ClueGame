package tests;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import experiment.TestBoard;
import experiment.TestBoardCell;

public class BoardTestsExp {
	TestBoard board;
	
	@BeforeEach // Run before each test
	public void setUp() {
		board = new TestBoard();
	}
	
	@Test
	public void testAdjTopLeft() {
		TestBoardCell cell = board.getCell(0,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(1,0)));
		assertTrue(testList.contains(board.getCell(0,1)));
		assertEquals(2, testList.size());
	}
	
	
	@Test
	public void testAdjBottomRight() {
		TestBoardCell cell = board.getCell(3,3);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(3,2)));
		assertTrue(testList.contains(board.getCell(2,3)));
		assertEquals(2, testList.size());
	}
	
	@Test
	public void testAdjRightEdge() {
		TestBoardCell cell = board.getCell(1,3);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(3,0)));
		assertTrue(testList.contains(board.getCell(2,1)));
		assertTrue(testList.contains(board.getCell(3,2)));
		assertEquals(3, testList.size());
	}
	
	@Test
	public void testAdjLeftEdge() {
		TestBoardCell cell = board.getCell(3,0);
		Set<TestBoardCell> testList = cell.getAdjList();
		assertTrue(testList.contains(board.getCell(2,0)));
		assertTrue(testList.contains(board.getCell(3,1)));
		assertEquals(3, testList.size());
	}
	
	
	
	
}
