package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	private TestBoardCell[][] testBoard;;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	
	//set up empty 4x4 board
	public TestBoard() {
		this.testBoard = new TestBoardCell[4][4];
		targets = new HashSet<TestBoardCell>();
		visited = new HashSet<TestBoardCell>();
		this.testBoard[0][0] = new TestBoardCell(0,0);
		this.testBoard[0][1] = new TestBoardCell(0,1);
		this.testBoard[1][0] = new TestBoardCell(1,0);
		this.testBoard[1][1] = new TestBoardCell(1,1);
		this.testBoard[0][2] = new TestBoardCell(0,2);
		this.testBoard[2][0] = new TestBoardCell(2,0);
		this.testBoard[1][2] = new TestBoardCell(1,2);
		this.testBoard[2][1] = new TestBoardCell(2,1);
		this.testBoard[0][3] = new TestBoardCell(0,3);
		this.testBoard[3][0] = new TestBoardCell(3,0);
		this.testBoard[2][2] = new TestBoardCell(2,2);
		this.testBoard[3][1] = new TestBoardCell(3,1);
		this.testBoard[3][2] = new TestBoardCell(3,2);
		this.testBoard[3][3] = new TestBoardCell(3,3);
		this.testBoard[2][3] = new TestBoardCell(2,3);
		this.testBoard[1][3] = new TestBoardCell(1,3);
	}
	
	//empty calc targets method
	public void calcTargets(TestBoardCell startCell, int pathlength) {

	}
	
	//empty find all targets method
	public void findAllTargets(TestBoardCell cell, int pathlength) {
	}
	
	//return empty set targets
	public Set<TestBoardCell> getTargets(){
		return targets;
	}
	
	//return empty board cell at [4][4]
	public TestBoardCell getCell(int row, int col) {
		return new TestBoardCell(4, 4);
	} 
}
