package clueGame;

import java.awt.Color;

public class ComputerPlayer extends Player{
	public ComputerPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
	}
	
	public Solution createSuggestion(Room room) {
		Solution test = new Solution();
		test.setSolution(new Card("Cameron", CardType.PERSON), new Card("Green", CardType.ROOM), new Card("Bazooka", CardType.WEAPON));
		return test;
	}
	
	public void selectMove() {
		
	}
}
