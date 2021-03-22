package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {
	private String name;
	private Color color;
	protected int row;
	protected int column;
	private ArrayList<Card> hand;
	
	public Player(String name, Color color, int row, int col) {
		this.name  = name;
		this.color = color;
		this.row = row;
		this.column = col;
	}
	
	public void updateHand(Card card) {
		
	}
}
