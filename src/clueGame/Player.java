package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public abstract class Player {
	private String name;
	private Color color;
	protected int row;
	protected int column;
	private ArrayList<Card> hand;
	private boolean isHuman;
	
	public Player(String name, String color, int row, int col) {
		this.name  = name;
		this.color = colorConvert(color);
		this.row = row;
		this.column = col;
		this.hand = new ArrayList<Card>();
	}
	
	public void updateHand(Card card) {
		hand.add(card);
	}
	
	public void clearHand() {
		hand.clear();
	}
	
	// converts color string to Color class
	public Color colorConvert(String colorName) {
		if(colorName.equals("red")) {
			return Color.RED;
		}else if(colorName.equals("blue")) {
			return Color.BLUE;
		}else if(colorName.equals("yellow")) {
			return Color.YELLOW;
		}else if(colorName.equals("purple")) {
			return Color.MAGENTA;
		}else if(colorName.equals("white")) {
			return Color.WHITE;
		}else if(colorName.equals("orange")) {
			return Color.ORANGE;
		}else {
			return null;
		}
	}
	
	public Card disproveSuggestion(Solution suggestion) {
		return null;
	}
	
	public String getName() {
		return name;
	}
	
	public void setHuman(boolean b){
		this.isHuman = b;
	}
	
	public boolean isHuman() {
		return this.isHuman;
	}
	
	public ArrayList<Card> getHand(){
		return hand;
	}
}
