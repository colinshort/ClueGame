package clueGame;

import java.util.HashSet;
import java.util.Set;

import experiment.TestBoardCell;

public class BoardCell {
	private int row;
	private int col;
	private char initial;
	private DoorDirection doorDirection;
	private boolean doorWay = false;
	private boolean roomLabel;
	private boolean roomCenter;
	private char secretPassage;
	private Set<BoardCell> adjList;
	
	//intialize board cell at given row and column
	public BoardCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}
	
	
	public void addAdj(BoardCell adj) {
		adjList.add(adj);
	}
	
	public boolean isDoorway() {
		return doorWay;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public boolean isLabel() {
		return roomLabel;
	}
	
    public boolean isRoomCenter() {
		return roomCenter;
	}

	public char getSecretPassage() {
		return secretPassage;
	}
	
	public char getInitial() {
		return initial;
	}

	public void setInitial(char initial) {
		this.initial = initial;
	}
	
	public void setDoorWay(boolean isDoor) {
		this.doorWay = isDoor;
	}
	
	public void setDoorDirection(DoorDirection doorDir) {
		this.doorDirection = doorDir;
	}
	
	public void setRoomLabel(boolean label) {
		this.roomLabel = label;
	}
	
	public void setCenter(boolean center) {
		this.roomCenter = center;
	}
	
	public void setSecretPassage(char passage) {
		this.secretPassage = passage;
	}
	
	
	

}

