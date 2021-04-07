package clueGame;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

public abstract class Player {
	private String name;
	private Color color;
	private String colorName;
	protected int row;
	protected int column;
	private ArrayList<Card> hand;
	private boolean isHuman;
	private ArrayList<Card> seenCards;
	
	public Player(String name, String color, int row, int col) {
		this.name  = name;
		this.colorName = color;
		this.color = colorConvert(color);
		this.row = row;
		this.column = col;
		this.hand = new ArrayList<Card>();
		this.seenCards = new ArrayList<>();
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
		}else if(colorName.equals("cyan")) {
			return Color.CYAN;
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
	
	//looks for card in a players hand that can disprove a suggestion
	public Card disproveSuggestion(Solution suggestion) {
		ArrayList<Card> myHand = this.getHand();
		Card person = suggestion.getPerson();
		Card room = suggestion.getRoom();
		Card weapon = suggestion.getWeapon();
		
		if(myHand.indexOf(person) == -1 && myHand.indexOf(room) == -1 && myHand.indexOf(weapon) == -1) {
			return null;
		}else {
			ArrayList<Card> matches = new ArrayList<>();
			for(Card c : myHand) {
				if(c.equals(room) || c.equals(weapon) || c.equals(person)) {
					matches.add(c);
				}
			}
			
			if(matches.size() == 1) {
				return matches.get(0);
			}else {
				Random rn = new Random();
				return matches.get(rn.nextInt(matches.size()));
			}
		}
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
	
	public void updateSeen(Card seen) {
		this.seenCards.add(seen);
	}
	
	public ArrayList<Card> getSeenCards(){
		return seenCards;
	}
	
	public String getColor() {
		return colorName;
	}
	
	
	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}

	public void draw(Graphics g, int width, int height, int x, int y) {
		Color color = Color.YELLOW;
		switch(getName()){
			case "John": 
				color = Color.RED;
				break;
			case "Mark":
				color = Color.CYAN;
				break;
			case "Adam":
				color = Color.YELLOW;
				break;
			case "Cameron":
				color = Color.BLUE;
				break;
			case "Colin":
				color = Color.WHITE;
				break;
			case "Henry":
				color = Color.ORANGE;
				break;
			default:
				color = Color.YELLOW;
				break;
			}
		
		g.setColor(color);
		g.drawOval(x, y, width, height);
		g.setColor(color);
		g.fillOval(x, y, width, height);
		
	}
}
