package clueGame;

import java.awt.Color;

public class ComputerPlayer extends Player{
	public ComputerPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
	}
	
	public Solution createSuggestion(Room room) {
		return null;
	}
	
	public void selectMove() {
		
	}
}
