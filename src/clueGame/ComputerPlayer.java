package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

public class ComputerPlayer extends Player{
	private static Random rn = new Random();
	
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
	
	
	public BoardCell selectMove(ArrayList<BoardCell> targets) {
		ArrayList<BoardCell> rooms = new ArrayList<>();
		
		for(BoardCell bc : targets) {
			if(bc.isRoom()) {
				rooms.add(bc);
			}
		}
		
		if(!rooms.isEmpty()) {
			if(rooms.size() == 1) {
				return rooms.get(0);
			}else {
				return rooms.get(rn.nextInt(rooms.size()));
			}
		}
		
		return targets.get(rn.nextInt(targets.size()));
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
