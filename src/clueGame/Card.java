package clueGame;

public class Card {
	private String cardName;
	private CardType type;
	
	public Card(String cardName) {
		super();
		this.cardName = cardName;
	}

	public boolean equals(Card target) {
		return true;
	}
	
	public CardType getCardType() {
		return type;
	}
}
