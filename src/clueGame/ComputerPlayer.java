package clueGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class ComputerPlayer extends Player{
	private static Random rn = new Random();
	private Solution currentSoln;
	private Board b;
	
	public ComputerPlayer(String name, String color, int row, int col) {
		super(name, color, row, col);
	}

	//creates a suggestion for computer
	//room is contrainted to be room computer player is in
	//pick a weapon and person card
	public Solution createSuggestion(ArrayList<Card> deck, Room room) {
		b = Board.getInstance();
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

				else if((myCard.getCardType() == CardType.PERSON) && !personFound && !myCard.getName().equals(this.getName())) {
					personFound = true;
					person = myCard;
				}
			}
			myCard = deck.get(rn.nextInt(deck.size()));
		}
		
		Card roomCard = b.getCard(room.getName(), CardType.ROOM);
		Solution solution = new Solution();
		solution.setSolution(person, roomCard, weapon);
		return solution;
	}
	
	//select a move from possible targets. Prioritize rooms
	public BoardCell selectMove(Set<BoardCell> targets) {
		ArrayList<BoardCell> myTargets = new ArrayList<BoardCell>(targets);
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
		
		return myTargets.get(rn.nextInt(myTargets.size()));
	}

	//helper method to determine if Card is in a list of Cards
	public boolean findCard(ArrayList<Card> list, Card card) {
		for(Card c : list) {
			if(c.equals(card)) {
				return true;
			}
		}
		return false;
	}
	
	public Solution createAccusation() {
		return currentSoln;
	}
	
	public void setCurrentSolution(Solution s) {
		currentSoln = s;
	}
	
	public Solution getCurrentSolution() {
		return currentSoln;
	}
}
