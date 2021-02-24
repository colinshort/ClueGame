package experiment;

import java.util.Set;

public class TestBoard {
	private TestBoardCell[][] testBoard = new TestBoardCell[2][2];
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	
	public TestBoard() {
		testBoard[0][0] = new TestBoardCell(0,0);
		testBoard[0][1] = new TestBoardCell(0,1);
		testBoard[1][0] = new TestBoardCell(1,0);
		testBoard[1][1] = new TestBoardCell(1,1);
	}
	
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		visited.add(startCell);
		findAllTargets(startCell, pathlength);
	}
	
	public void findAllTargets(TestBoardCell cell, int pathlength) {
		Set<TestBoardCell> temp = cell.getadjList();
		
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
	
	Set<TestBoardCell> getTargets(){
		return targets;
	}
	
	TestBoardCell getCell(int row, int col) {
		return testBoard[row][col];
	}
}
