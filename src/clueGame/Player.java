package clueGame;

import java.awt.Color;
import java.util.ArrayList;

public abstract class Player {
	private String name;
	private Color color;
	protected int row;
	protected int column;
	private ArrayList<Card> hand;
	
	public void updateHand(Card card) {
		
	}
}
