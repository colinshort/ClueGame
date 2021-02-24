package experiment;

import java.util.*;

public class TestBoardCell {
	private int row;
	private int col;
	private boolean isRoom;
	private boolean isOccupied;
	
	private Set <TestBoardCell> mycells = new HashSet<TestBoardCell>();
	
	public TestBoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	
	public void addAdjacency(TestBoardCell cell) {
		mycells.add(cell);
	}
	
	public Set<TestBoardCell> getAdjList(){
		return mycells;
	}
	
	public void setRoom(boolean room) {
		isRoom = room;
	}
	
	public boolean isRoom() {
		return isRoom;
	}
	
	public void setOccupied(boolean occupied) {
		isOccupied = occupied;
	}
	
	public boolean getOccupied() {
		return isOccupied;
	}
}
