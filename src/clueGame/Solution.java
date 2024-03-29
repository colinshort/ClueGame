package clueGame;

public class Solution {
	public Card person;
	public Card room;
	public Card weapon;
	
	public void setSolution(Card person, Card room, Card weapon) {
		this.person = person;
		this.room = room;
		this.weapon = weapon;
	}
	
	public Card getPerson() {
		return person;
	}
	public Card getRoom() {
		return room;
	}
	public Card getWeapon() {
		return weapon;
	}
	
	public String toString() {
		return person.getName() + ", " + room.getName() + ", " + weapon.getName();
	}
}
