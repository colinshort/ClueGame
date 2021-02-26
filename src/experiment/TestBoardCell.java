package experiment;

import java.util.*;

public class TestBoardCell {
	private int row, col;
	private boolean isRoom, isOccupied;
	private Set <TestBoardCell> mycells;
	
	
	//intialize board cell at given row and column
	public TestBoardCell(int row, int col) {
		super();
		this.mycells = new HashSet<TestBoardCell>();
		this.row = row;
		this.col = col;
	}
	
	//add adjacent cell to set of adjacent cells
	public void addAdj(TestBoardCell cell) {
		mycells.add(cell);
	}
	
	//return set of adjacent cells
	public Set<TestBoardCell> getAdjList(){
		return mycells;
	}
	
	//set isRoom to true/false
	public void setRoom(boolean room) {
		isRoom = room;
	}
	
	//check value of isRoom
	public boolean isRoom() {
		return isRoom;
	}
	
	//set isOccupied to true/false
	public void setOccupied(boolean occupied) {
		isOccupied = occupied;
	}
	
	//check value of isOccupied
	public boolean getOccupied() {
		return isOccupied;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	
}
