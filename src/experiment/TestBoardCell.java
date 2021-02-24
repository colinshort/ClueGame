package experiment;

import java.util.*;

public class TestBoardCell {
	private int row;
	private int col;
	private boolean isRoom;
	private boolean isOccupied;
	
	private Set <TestBoardCell> mycells = new HashSet<TestBoardCell>();
	
	//intialize board cell at given row and column
	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	
	//add adjacent cell to set of adjacent cells
	public void addAdjacency(TestBoardCell cell) {
		mycells.add(cell);
	}
	
	//return empty adjacency list
	public Set<TestBoardCell> getAdjList(){
		return new HashSet<TestBoardCell>();
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
	
}
