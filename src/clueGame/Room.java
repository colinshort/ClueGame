//Authors:Cameron Fitzgerald, Colin Short
package cluegame;

public class Room {
	private String name;
	private BoardCell centerCell;
	private BoardCell labelCell;
	private char secretPassage;
	
	
	public Room(String name) {
		super();
		this.name = name;
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
}
