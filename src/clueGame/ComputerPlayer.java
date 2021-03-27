package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class ComputerPlayer extends Player{
	public ComputerPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
	}

	public Solution createSuggestion(ArrayList<Card> deck, Room room) {
		Random rn = new Random();
		boolean personFound = false;
		boolean weaponFound = false;
		Card weapon = null;
		Card person = null;

		Card myCard = deck.get(rn.nextInt(deck.size()));

		while(true) {
			if(personFound && weaponFound) {
				break;
			}

			if(!findCard(this.getHand(), myCard) && !findCard(this.getSeenCards(), myCard)){

				if((myCard.getCardType() == CardType.WEAPON) && !weaponFound) {
					weaponFound = true;
					weapon = myCard;
				}

				else if((myCard.getCardType() == CardType.PERSON) && !personFound) {
					personFound = true;
					person = myCard;
				}
			}
			myCard = deck.get(rn.nextInt(deck.size()));
		}
		
		Card roomCard = new Card(room.getName(), CardType.ROOM);
		Solution solution = new Solution();
		solution.setSolution(person, roomCard, weapon);
		return solution;
	}
	
	
	
	
	

	public void selectMove() {

	}

	public boolean findCard(ArrayList<Card> list, Card card) {
		for(Card c : list) {
			if(c.equals(card)) {
				return true;
			}
		}
		return false;
	}
}
