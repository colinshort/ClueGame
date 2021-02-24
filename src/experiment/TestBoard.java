package experiment;

import java.util.Set;

public class TestBoard {
	private TestBoardCell[][] testBoard = new TestBoardCell[4][4];
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	
	public TestBoard() {
		testBoard[0][0] = new TestBoardCell(0,0);
		testBoard[0][1] = new TestBoardCell(0,1);
		testBoard[1][0] = new TestBoardCell(1,0);
		testBoard[1][1] = new TestBoardCell(1,1);
		testBoard[0][2] = new TestBoardCell(0,2);
		testBoard[2][0] = new TestBoardCell(2,0);
		testBoard[1][2] = new TestBoardCell(1,2);
		testBoard[2][1] = new TestBoardCell(2,1);
		testBoard[0][3] = new TestBoardCell(0,3);
		testBoard[3][0] = new TestBoardCell(3,0);
		testBoard[2][2] = new TestBoardCell(2,2);
		testBoard[3][1] = new TestBoardCell(3,1);
		testBoard[3][2] = new TestBoardCell(3,2);
		testBoard[3][3] = new TestBoardCell(3,3);
		testBoard[2][3] = new TestBoardCell(2,3);
		testBoard[1][3] = new TestBoardCell(1,3);
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		visited.add(startCell);
		findAllTargets(startCell, pathlength);
	}
	
	public void findAllTargets(TestBoardCell cell, int pathlength) {
		Set<TestBoardCell> temp = cell.getAdjList();
		
		for(TestBoardCell c : temp) {
			if(!visited.contains(c)) {
				visited.add(c);
				if(pathlength == 1) {
					targets.add(c);
				}else{
					findAllTargets(c, pathlength - 1);
				}
				visited.remove(c);
			}
		}
	}
	
	public Set<TestBoardCell> getTargets(){
		return targets;
	}
	
	public TestBoardCell getCell(int row, int col) {
		return testBoard[row][col];
	}
}
