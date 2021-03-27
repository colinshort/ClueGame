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
		return (target.getCardType() == this.getCardType() && target.getName().equals(this.getName()));
	}
	
	public CardType getCardType() {
		return type;
	}
	
	public void setDealt(boolean b) {
		this.dealt = b;
	}
	
	public String getName() {
		return cardName;
	}
	
	public boolean getDealt() {
		return dealt;
	}
}
