//Authors:Cameron Fitzgerald, Colin Short
package clueGame;

import java.util.ArrayList;

public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private char secretPassage;
	private ArrayList<BoardCell> cells;
	private ArrayList<Player> playersInRoom;
	private char initial;
	
	
	public Room(String name, char initial) {
		super();
		this.name = name;
		this.cells = new ArrayList<BoardCell>();
		this.initial = initial;
		this.playersInRoom = new ArrayList<Player>();
	}
	
	public void addCell(BoardCell cell) {
		this.cells.add(cell);
	}
	
	public ArrayList<BoardCell> getCells(){
		return this.cells;
	}

	public String getName() {
		return name;
	}
	
	public BoardCell getLabelCell() {
		return labelCell;
	}
	
	public BoardCell getCenterCell() {
		return centerCell;
	}
	
	public void setLabelCell(BoardCell label) {
		this.labelCell = label;
	}
	
	public void setCenterCell(BoardCell center) {
		this.centerCell = center;
	}
	
	public char getSecretPassage() {
		return secretPassage;
	}
	
	public void setSecretPassage(char passage) {
		this.secretPassage = passage;
	}
	
	public void addPlayer(Player p) {
		playersInRoom.add(p);
	}
	
	public void removePlayer(Player p) {
		playersInRoom.remove(p);
	}
	
	public char getInitial() {
		return this.initial;
	}
	
	public int getPlayerCount() {
		return playersInRoom.size();
	}
	
	public ArrayList<Player> getPlayers(){
		return playersInRoom;
	}
}
