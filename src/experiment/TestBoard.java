package experiment;

import java.util.HashSet;
import java.util.Set;

public class TestBoard {
	private TestBoardCell[][] grid;
	private Set<TestBoardCell> targets;
	private Set<TestBoardCell> visited;
	
	final static int COLS = 4;
	final static int ROWS = 4;
	
	//set up empty 4x4 board
	public TestBoard() {
		this.grid = new TestBoardCell[ROWS][COLS];
		this.targets = new HashSet<TestBoardCell>();
		this.visited = new HashSet<TestBoardCell>();
		this.grid[0][0] = new TestBoardCell(0,0);
		this.grid[0][1] = new TestBoardCell(0,1);
		this.grid[1][0] = new TestBoardCell(1,0);
		this.grid[1][1] = new TestBoardCell(1,1);
		this.grid[0][2] = new TestBoardCell(0,2);
		this.grid[2][0] = new TestBoardCell(2,0);
		this.grid[1][2] = new TestBoardCell(1,2);
		this.grid[2][1] = new TestBoardCell(2,1);
		this.grid[0][3] = new TestBoardCell(0,3);
		this.grid[3][0] = new TestBoardCell(3,0);
		this.grid[2][2] = new TestBoardCell(2,2);
		this.grid[3][1] = new TestBoardCell(3,1);
		this.grid[3][2] = new TestBoardCell(3,2);
		this.grid[3][3] = new TestBoardCell(3,3);
		this.grid[2][3] = new TestBoardCell(2,3);
		this.grid[1][3] = new TestBoardCell(1,3);
	}
	
	public void calcAdjacencies(TestBoardCell cell) {
//		for(int i= 0; i < ROWS; i++) {
//			for (int j = 0; j < COLS; j++) {
//				if(i - 1 >= 0 && j - 1 >= 0) {
//					grid[i][j].addAdj(grid[i - 1][j - 1]);
//				}
//				if(i - 1 >= 0 && j + 1 <= 3) {
//					grid[i][j].addAdj(grid[i - 1][j + 1]);
//				}
//				if(i + 1 <= 3 && j -1 >= 0) {
//					grid[i][j].addAdj(grid[i + 1][j - 1]);
//				}
//				if(i + 1 <= 3 && j +1 <= 3) {
//					grid[i][j].addAdj(grid[i + 1][j + 1]);
//				}
//				
//			}
//		}
		if(cell.getRow() - 1 >= 0) {
			cell.addAdj(grid[cell.getRow() - 1][cell.getCol()]);
		}
		
		if(cell.getCol() - 1 >= 0) {
			cell.addAdj(grid[cell.getRow()][cell.getCol() - 1]);
		}
		
		if(cell.getRow() + 1 <= 3) {
			cell.addAdj(grid[cell.getRow() + 1][cell.getCol()]);
		}
		
		if(cell.getCol() + 1 <= 3) {
			cell.addAdj(grid[cell.getRow()][cell.getCol() + 1]);
		}
	}
	
	//empty calc targets method
	public void calcTargets(TestBoardCell startCell, int pathlength) {
		visited.add(startCell);
		findAllTargets(startCell, pathlength);
	}
	
	//empty find all targets method
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
	
	
	//return empty set targets
	public Set<TestBoardCell> getTargets(){
		return targets;
	}
	
	//return empty board cell at [4][4]
	public TestBoardCell getCell(int row, int col) {
		return grid[row][col];
	} 
	
	public static void main(String[] args) {
		
	}
}
