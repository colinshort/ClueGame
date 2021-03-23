package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	private boolean dealt;
	
	public Card(String cardName, CardType type) {
		super();
		this.cardName = cardName;
		this.type = type;
	}

	public boolean equals(Card target) {
		return true;
	}
	
	public CardType getCardType() {
		return type;
	}
	
	public void setDealt(boolean b) {
		this.dealt = b;
	}
	
	public boolean getDealt() {
		return dealt;
	}
}
