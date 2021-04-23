//Authors:Cameron Fitzgerald, Colin Short
package clueGame;


import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Board extends JPanel {
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;
	private Solution theAnswer;
	private ArrayList<Player> players;
	private ArrayList<Card> deck;
	private Player currentPlayer;
	private boolean accusationMade;
	private HumanPlayer humanPlayer;
	private SuggestionDialog suggestion;
	private boolean suggestionDisproved;
	
	//Stores Character as key and Room as entry
	private Map<Character, Room> roomMap = new HashMap<>();

	//variable and methods used for singleton pattern
	private static Board theInstance = new Board();

	// constructor is private to ensure only one can be created
	private Board() {
		super() ;
		this.suggestionDisproved = true;
		this.targets = new HashSet<>();
		this.visited = new HashSet<>();
		this.players = new ArrayList<>();
		this.deck = new ArrayList<>();
		this.theAnswer = new Solution();
		this.addMouseListener(new BoardListener());
	}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	//initialize the board (since we are using singleton pattern)
	public void initialize() {
		try {
			loadConfigFiles();
		} catch (BadConfigFormatException | FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void loadConfigFiles() throws FileNotFoundException, BadConfigFormatException {
		loadSetupConfig();
		loadLayoutConfig();
	}

	//load in setup file
	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {
		players.clear();
		deck.clear();
		FileReader reader = new FileReader(setupConfigFile);
		Scanner in = new Scanner(reader);
		try {
			while(in.hasNextLine()) {
				String line = in.nextLine();

				if(!line.startsWith("/")) {
					String[] setUp = line.split(", ");

					//if card is not "Room" or "Space", throw Exception
					if (!setUp[0].equals("Room") && !setUp[0].equals("Space") && !setUp[0].equals("Person") && !setUp[0].equals("Weapon")) {
						throw new BadConfigFormatException("Error: Invalid card type");
					}

					if(setUp[0].equals("Room") || setUp[0].equals("Space")) {
						Room room = new Room(setUp[1], setUp[2].charAt(0));
						roomMap.put(setUp[2].charAt(0), room);
						if(setUp[0].equals("Room")){
							Card c = new Card(setUp[1], CardType.ROOM);
							deck.add(c);
						}
					}

					else if(setUp[0].equals("Person")) {

						if(setUp[3].charAt(0) == 'H') {
							HumanPlayer human = new HumanPlayer(setUp[1], setUp[2], Integer.parseInt(setUp[4]), Integer.parseInt(setUp[5]));
							players.add(human);
							this.humanPlayer = human;
							human.setHuman(true);

						}else if(setUp[3].charAt(0) == 'C') {
							ComputerPlayer computer = new ComputerPlayer(setUp[1], setUp[2], Integer.parseInt(setUp[4]), Integer.parseInt(setUp[5]));
							players.add(computer);
							computer.setHuman(false);
						}

						Card c = new Card(setUp[1], CardType.PERSON);
						deck.add(c);
					}else if(setUp[0].equals("Weapon")) {
						Card c = new Card(setUp[1], CardType.WEAPON);
						deck.add(c);
					}
				}

			}
		}finally {
			in.close();
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if(deck.size() > 9) {
			deal();
		}
	}

	//load in layout file
	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
		ArrayList<String> rows = new ArrayList<>();
		FileReader reader = new FileReader(layoutConfigFile);
		Scanner in = new Scanner(reader);
		try {
			while(in.hasNextLine()) {
				String line = in.nextLine();
				rows.add(line);
			}
		}finally {
			in.close();
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		String[] cols = rows.get(0).split(",");
		numColumns = cols.length;
		numRows = rows.size();

		grid = new BoardCell[numRows][numColumns];

		//add Boardcells to the grid
		FileReader reader2 = new FileReader(layoutConfigFile);
		Scanner in2 = new Scanner(reader2);
		try{
			int count = 0;

			while(in2.hasNextLine()) {
				String line = in2.nextLine();
				String[] setUp = line.split(",");

				//row has improper number of columns, throw exception
				if(setUp.length != numColumns) {
					throw new BadConfigFormatException("Error: Invalid number of columns on row " + count);
				}

				for(int i = 0; i < numColumns; i++) {
					//if cell contains invalid initial, throw exception
					if(!roomMap.containsKey(setUp[i].charAt(0))) {
						throw new BadConfigFormatException("Error: Invalid board cell initial");
					}

					grid[count][i] = new BoardCell(count,i);
					grid[count][i].setInitial(setUp[i].charAt(0));
					if(setUp[i].charAt(0) != 'W' && setUp[i].charAt(0) != 'X') {
						grid[count][i].setIsRoom(true);
						roomMap.get(grid[count][i].getInitial()).addCell(grid[count][i]);
					}

					handleSpecialCells(setUp[i], count, i);
				}
				count++;
			}	

		}finally{
			in2.close();
			try {
				reader2.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		preprocessAdjs();

	}

	//calculate adjacencies for each cell on the board
	public void preprocessAdjs() {
		for(int i = 0; i < numColumns; i++) {
			for(int j = 0; j < numRows; j++) {
				calcAdjacencies(grid[j][i]);	
			}
		}
	}

	//handle doorways, room label cells, room center cells, and secret passage cells
	public void handleSpecialCells(String label, int row, int col) {
		if(label.length()==2) {
			if(label.charAt(1) == '<') { 
				grid[row][col].setDoorWay(true);
				grid[row][col].setDoorDirection(DoorDirection.LEFT);
			}
			else if(label.charAt(1) == '>') { 
				grid[row][col].setDoorWay(true);
				grid[row][col].setDoorDirection(DoorDirection.RIGHT);
			}
			else if(label.charAt(1) == 'v') { 
				grid[row][col].setDoorWay(true);
				grid[row][col].setDoorDirection(DoorDirection.DOWN);
			}
			else if(label.charAt(1) == '^') { 
				grid[row][col].setDoorWay(true);
				grid[row][col].setDoorDirection(DoorDirection.UP);
			}
			else if(label.charAt(1) == '#') { 
				grid[row][col].setRoomLabel(true);
				roomMap.get(label.charAt(0)).setLabelCell(grid[row][col]);
			}
			else if(label.charAt(1) == '*') { 
				grid[row][col].setCenter(true);
				roomMap.get(label.charAt(0)).setCenterCell(grid[row][col]);
			}
			else {
				grid[row][col].setSecretPassage(label.charAt(1));
				roomMap.get(label.charAt(0)).setSecretPassage(label.charAt(1));
			}
		}
	}

	//Set configuration file names
	public void setConfigFiles(String str, String str2) {
		layoutConfigFile = str;
		setupConfigFile = str2;	
	}

	/* Calculate the adjacencies for a given cell and add them to adjacency list in BoardCell
	Doorways are adjacent to room centers.
	Secret passages are adjacent to room centers */
	public void calcAdjacencies(BoardCell cell) {
		if(cell.getRow() - 1 >= 0) {
			BoardCell above = (grid[cell.getRow() - 1][cell.getCol()]);
			findAdjacentCells(cell, above);
		}

		if(cell.getCol() - 1 >= 0) {
			BoardCell left = (grid[cell.getRow()][cell.getCol() - 1]);
			findAdjacentCells(cell, left);
		}

		if(cell.getRow() + 1 <= numRows - 1) {
			BoardCell below = (grid[cell.getRow() + 1][cell.getCol()]);
			findAdjacentCells(cell, below);
		}

		if(cell.getCol() + 1 <= numColumns - 1) {
			BoardCell right = (grid[cell.getRow()][cell.getCol() + 1]);
			findAdjacentCells(cell, right);
		}

	}

	//Find adjacent doorways and secret passages and add corresponding room center cells to adjancency list
	public void findAdjacentCells(BoardCell current, BoardCell target) {
		if(current.isWalkway()) {
			if(target.isRoom() && current.isDoorway()) {
				if(current.getRow() > target.getRow() && current.getDoorDirection() == DoorDirection.UP) {
					current.addAdj(roomMap.get(target.getInitial()).getCenterCell());
					roomMap.get(target.getInitial()).getCenterCell().addAdj(current);
				}
				else if(current.getRow() < target.getRow() && current.getDoorDirection() == DoorDirection.DOWN) {
					current.addAdj(roomMap.get(target.getInitial()).getCenterCell());
					roomMap.get(target.getInitial()).getCenterCell().addAdj(current);
				}
				else if(current.getCol() < target.getCol() && current.getDoorDirection() == DoorDirection.RIGHT) {
					current.addAdj(roomMap.get(target.getInitial()).getCenterCell());
					roomMap.get(target.getInitial()).getCenterCell().addAdj(current);
				}
				else if(current.getCol() > target.getCol() && current.getDoorDirection() == DoorDirection.LEFT) {
					current.addAdj(roomMap.get(target.getInitial()).getCenterCell());
					roomMap.get(target.getInitial()).getCenterCell().addAdj(current);
				}
			}else if(target.isWalkway()) {
				current.addAdj(target);
			}
		}else if(current.isRoomCenter()) {
			char sP = roomMap.get(current.getInitial()).getSecretPassage();
			if(sP != '\0') {
				current.addAdj(roomMap.get(sP).getCenterCell());
			}
		}
	}

	/* Calculate valid target cells for a given cell and path length
	first clear targets and visited sets, add current cell to visited list
	make a call to findAllTargets*/
	public void calcTargets(BoardCell startCell, int pathlength) {
		if(!targets.isEmpty()) {
			targets.clear();
		}
		if(!visited.isEmpty()) {
			visited.clear();
		}
		visited.add(startCell);
		findAllTargets(startCell, pathlength);
	}

	//search for and store (in targets set) all cells that are a certain pathlength away
	public void findAllTargets(BoardCell cell, int pathlength) {
		Set<BoardCell> adjs = cell.getAdjList();
		if(pathlength >= 1) {
			for(BoardCell c : adjs) {
				if(!visited.contains(c) && (!c.getOccupied() || c.isRoomCenter())) {
					visited.add(c);
					if(pathlength == 1 || c.isRoomCenter()) {
						targets.add(c);
						c.setTarget(true);
					}
					else{
						findAllTargets(c, pathlength - 1);
					}
					visited.remove(c);
				}
			}
		}
	}

	//create the solution hand, and deal a hand of cards to each player
	public void deal() {
		ArrayList<Card> people = new ArrayList<Card>();
		ArrayList<Card> rooms = new ArrayList<Card>();
		ArrayList<Card> weapons = new ArrayList<Card>();

		for(int i = 0; i < deck.size(); i++) {
			if(deck.get(i).getCardType() == CardType.PERSON) {
				people.add(deck.get(i));
			}
			else if(deck.get(i).getCardType() == CardType.ROOM) {
				rooms.add(deck.get(i));
			}
			else if(deck.get(i).getCardType() == CardType.WEAPON) {
				weapons.add(deck.get(i));
			}
		}

		Card person = people.get((int)Math.random()%people.size());
		person.setDealt(true);
		Card room = rooms.get((int)Math.random()%rooms.size());
		room.setDealt(true);
		Card weapon = weapons.get((int)Math.random()%weapons.size());
		weapon.setDealt(true);

		theAnswer.setSolution(person, room, weapon);

		int j = 0;
		for(int i = 0; i < deck.size(); i++) {
			if (j >= players.size()) {
				j = 0;
			}
			if(deck.get(i).getDealt()== false) {
				players.get(j).updateHand(deck.get(i));
				deck.get(i).setDealt(true);
				deck.get(i).setSource(players.get(j));
				j++;
			}
		}
	}

	//returns true if accusation matches the answer
	public boolean checkAccusation(Solution answer, Solution solution) {
		boolean match = true;
		if(!answer.getWeapon().equals(solution.getWeapon())) {
			match = false;
		}
		if(!answer.getPerson().equals(solution.getPerson())) {
			match = false;
		}
		if(!answer.getRoom().equals(solution.getRoom())) {
			match = false;
		}
		return match;
	}

	//returns first card that can disprove a suggestion
	public Card handleSuggestion(Solution suggestion, Player accuser, ArrayList<Player> myPlayers) {
		Card givenCard;
		Player accused = getPlayer(suggestion.getPerson().getName());
		if(accused.getRoom() != accuser.getRoom() && !accuser.equals(accused)) {
			roomMap.get(accuser.getRoom()).addPlayer(accused);
			if(accused.getRoom() != '\0') {
				roomMap.get(accused.getRoom()).removePlayer(accused);
			}
			accused.setRoom(accuser.getRoom());
			accused.setRow(roomMap.get(accuser.getRoom()).getCenterCell().getRow());
			accused.setCol(roomMap.get(accuser.getRoom()).getCenterCell().getCol());
		}
		repaint();
		for(Player p : myPlayers) {
			if(!p.equals(accuser)) {
				givenCard = p.disproveSuggestion(suggestion);
				if(givenCard != null) {
					updateSeen(givenCard, p);
					return givenCard;
				}
			}
		}
		return null;
	}
	
	public void updateSeen(Card c, Player disprover) {
		for(Player p : players) {
			if(!p.equals(disprover)) {
				p.updateSeen(c);
			}
		}
	}

	//Paint the board
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//calculate dimensions of cells
		int cellWidth = (int)(getWidth() / getNumColumns());
		int cellHeight = (int)(getHeight() / getNumRows());

		//paint each cell
		int x = 0;
		int y = 0;
		for(BoardCell[] row : grid) {
			for(BoardCell cell : row) {
				if(cell.isRoom()) {
					if(roomMap.get(cell.getInitial()).getCenterCell().isTarget()) {
						cell.setTarget(true);
					}else {
						cell.setTarget(false);
					}
				}
				cell.draw(g, cellWidth, cellHeight, x, y, cell.isTarget(), currentPlayer.isHuman());
				x += cellWidth;
			}
			x = 0;
			y += cellHeight;
		}

		//paint room names
		for(Map.Entry<Character, Room> entry : roomMap.entrySet()) {
			Room r = entry.getValue();
			Font font = new Font("Dialog", Font.BOLD, 14);
			g.setFont(font);
			if(r.getLabelCell() != null) {
				g.drawString(r.getName(), r.getLabelCell().getCol() * cellWidth, r.getLabelCell().getRow() * cellHeight);
			}
		}

		//paint players
		@SuppressWarnings("unchecked")
		ArrayList<Player> toPaint = (ArrayList<Player>) players.clone();
		
		for(Entry<Character,Room> entry : roomMap.entrySet()) {
			Room test = (Room)entry.getValue();
			for(Player p : ((Room)entry.getValue()).getPlayers()) {
				int x1 = (p.getColumn() * cellWidth) + (((Room)entry.getValue()).getPlayers().indexOf(p) * cellWidth/4);
				int y1 = p.getRow() * cellHeight;
				p.draw(g, cellWidth - 3, cellHeight - 3, x1 + 1, y1 + 1);
				toPaint.remove(p);
			}
		}
		
		for(Player p : toPaint) {
			int x1 = p.getColumn() * cellWidth;
			int y1 = p.getRow() * cellHeight;
			if(p.getRoom() != '\0') {
				Room r = roomMap.get(p.getRoom());
				if(r.getPlayerCount() > 1) {
					x1 += r.getPlayers().indexOf(p) * cellWidth/4;
				}
			}
			p.draw(g, cellWidth - 3, cellHeight - 3, x1 + 1, y1 + 1);
		}

		//paint doors
		x = 0;
		y = 0;
		for(BoardCell[] row : grid) {
			for(BoardCell cell : row) {
				cell.drawDoor(g, cellWidth, cellHeight, x, y);
				x += cellWidth;
			}
			x = 0;
			y += cellHeight;
		}
	}

	public void executeTurn(GameControlPanel panel) {
		//check if turn is finished
		if(!currentPlayer.isFinished()) {
			JOptionPane.showMessageDialog(null, "You must finish your turn!", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		else {
			//move to next player
			currentPlayer = nextPlayer();
			//roll die
			Random random = new Random();
			int roll = random.nextInt(6) + 1;
			//calculate player targets
			calcTargets(grid[currentPlayer.getRow()][currentPlayer.getColumn()], roll);
			//update game control panel
			panel.setTurn(currentPlayer, roll);

			if(currentPlayer.isHuman()) {
				//if player is human repaint and wait for move
				repaint();
				currentPlayer.setFinished(false);
			}else {
				ComputerPlayer computer = (ComputerPlayer) currentPlayer;
				if(suggestionDisproved == false && computer.getCurrentSolution() != null) {
					if(checkAccusation(theAnswer, computer.createAccusation())) {
						JOptionPane.showMessageDialog(null, computer.getName() + " wins! The answer was " + theAnswer.getPerson().getName() 
								+ ", " + theAnswer.getRoom().getName() + ", " + theAnswer.getWeapon().getName());
						System.exit(0);
					}else {
						suggestionDisproved = true;
					}
				}
				//select computer move
				BoardCell position = computer.selectMove(targets);
				
				//deselect targets for highlighting
				for(BoardCell c : targets) {
					c.setTarget(false);
				}
				if(position.isRoom()) {
					Room r = roomMap.get(position.getInitial());
					
					//check if player is already in room
					if(currentPlayer.getRoom() != '\0') {
						//remove player from room's player list
						roomMap.get(currentPlayer.getRoom()).removePlayer(currentPlayer);
						currentPlayer.setRoom('\0');
					}				
					//add player to current room's player list
					r.addPlayer(currentPlayer);
					//set player room
					currentPlayer.setRoom(r.getInitial());
					
					Solution compSugg = computer.createSuggestion(deck, roomMap.get(position.getInitial()));
					GameControlPanel gcp = GameControlPanel.getInstance();
					Card result = handleSuggestion(compSugg, computer, players);
					
					if(result == null) {
						suggestionDisproved = false;
						String m = "Not Disproven";
						String guess = compSugg.getPerson().getName() + ", " + compSugg.getRoom().getName() + ", " + compSugg.getWeapon().getName();
						gcp.setGuessResult(m);
						gcp.setGuess(guess);
					}
					else {
						String p = result.getName();
						String guess = compSugg.getPerson().getName() + ", " + compSugg.getRoom().getName() + ", " + compSugg.getWeapon().getName();
						gcp.setGuessResult(p);
						gcp.setGuess(guess);
					}
					
					computer.setCurrentSolution(compSugg);
					
					for(Player p : players) {
						if(!p.equals(currentPlayer)) {
							Card givenCard = p.disproveSuggestion(compSugg);
							if(givenCard == null) {
								suggestionDisproved = false;
							}
						}
					}
					
					//move player
					currentPlayer.setRow(r.getCenterCell().getRow());
					currentPlayer.setCol(r.getCenterCell().getCol());
					
				}else {
					//check if player is already in room
					if(currentPlayer.getRoom() != '\0') {
						//remove player from room's player list
						roomMap.get(currentPlayer.getRoom()).removePlayer(currentPlayer);
						currentPlayer.setRoom('\0');	
					}				
					//move player
					currentPlayer.setRow(position.getRow());
					currentPlayer.setCol(position.getCol());
				}
				//player is finished with turn
				currentPlayer.setFinished(true);
				repaint();
			}
		}
	}
	
	//establishes the parameters for first turn of the game
	//current player is set to human player
	public void firstTurnSetup(GameControlPanel panel) {
		currentPlayer = players.get(0);
		Random random = new Random();
		int roll = random.nextInt(6) + 1;
		calcTargets(grid[currentPlayer.getRow()][currentPlayer.getColumn()], roll);
		panel.setTurn(currentPlayer, roll);
		repaint();

	}

	//returns the next player in the players arrayList
	//if index is 5, index returns to start of arrayList
	public Player nextPlayer() {
		int idx = players.indexOf(currentPlayer);
		if(idx == 5) {
			idx = -1;
		}
		return players.get(idx + 1);
	}

	//implements functionality for when the board is clicked on by user
	//if player is human board, updates position based on selection
	//if player is computer, moves player according to AI 
	private class BoardListener implements MouseListener{
		//  Empty definitions for unused event methods.
		public void mousePressed (MouseEvent e) {}
		public void mouseReleased (MouseEvent e) {}  
		public void mouseEntered (MouseEvent e) {}  
		public void mouseExited (MouseEvent e) {}  
		public void mouseClicked (MouseEvent e) {
			if(!currentPlayer.isHuman()) {
				return;
			}
			int x = e.getX();
			int y = e.getY();
			
			//calculate dimensions of cells
			int cellWidth = (int)(getWidth() / getNumColumns());
			int cellHeight = (int)(getHeight() / getNumRows());
			

			boolean selected = false;
			for(BoardCell c : targets) {
				if(selected) {
					break;
				}
				
				int cellX = c.getCol() * cellWidth;
				int cellY = c.getRow() * cellHeight;
				//checks to see if target is in room
				if(c.isRoom()) {
					Room r = roomMap.get(c.getInitial());
					//checking all the cells in a room
					for(BoardCell cell : r.getCells()) {
						int roomCellX = cell.getCol() * cellWidth;
						int roomCellY = cell.getRow() * cellHeight;
						//check that mouse click is in cell
						if(x > roomCellX && y > roomCellY && y < roomCellY + cellHeight && x < roomCellX + cellWidth){
							if(currentPlayer.getRoom() != '\0') {
								roomMap.get(currentPlayer.getRoom()).removePlayer(currentPlayer);
								currentPlayer.setRoom('\0');
							}	
							//update players location
							currentPlayer.setRow(r.getCenterCell().getRow());
							currentPlayer.setCol(r.getCenterCell().getCol());
							//set player to selected room
							currentPlayer.setRoom(r.getInitial());
							selected = true;
							
							suggestion = new SuggestionDialog();
							suggestion.setVisible(true);
							//finish turn
							currentPlayer.setFinished(true);
							r.addPlayer(currentPlayer);
							break;
						}
					}
				}else {
					//check that mouse click is in cell
					if(x > cellX && y > cellY && y < cellY + cellHeight && x < cellX + cellWidth){
						//move player
						currentPlayer.setRow(c.getRow());
						currentPlayer.setCol(c.getCol());
						//check if player is already in room
						if(currentPlayer.getRoom() != '\0') {
							//remove player from room's player list
							roomMap.get(currentPlayer.getRoom()).removePlayer(currentPlayer);			
							//set player room to no room
							currentPlayer.setRoom('\0');
						}
						selected = true;
						//finish turn
						currentPlayer.setFinished(true);
						break;
					}
				}
			}
			
			if(!selected) {
				//if invalid cell, show error message
				JOptionPane.showMessageDialog(null, "That is not a target", "Error", JOptionPane.ERROR_MESSAGE);
			}else {
				//deselect targets for highlighting
				for(BoardCell c : targets) {
					c.setTarget(false);
				}
			}
			repaint();
		} 
	}

	public Set<BoardCell> getAdjList(int row, int col){
		return grid[row][col].getAdjList();
	}

	public Set<BoardCell> getTargets(){
		return targets;
	}
	
	public Room getRoom(BoardCell cell) {
		return roomMap.get(cell.getInitial());
	}

	public Room getRoom(char initial) {
		return roomMap.get(initial);
	}

	public int getNumRows() {
		return numRows;
	}

	public int getNumColumns() {
		return numColumns;
	}

	public BoardCell getCell(int row, int col) {
		return grid[row][col];
	}

	public ArrayList<Player> getPlayers(){
		return players;
	}

	public ArrayList<Card> getDeck(){
		return deck;
	}

	public Solution getAnswer() {
		return theAnswer;
	}

	public Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public HumanPlayer getHumanPlayer() {
		return humanPlayer;
	}
	
	public void setAccusationMade(boolean b) {
		accusationMade = b;
	}
	
	public Card getCard(String name, CardType type) {
		for(Card c : deck) {
			if(c.getCardType() == type && c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	
	public Player getPlayer(String name) {
		for(Player p : players) {
			if(p.getName().equals(name)) {
				return p;
			}
		}
		return null;
	}
	
	public void setDisproved(boolean b) {
		suggestionDisproved = b;
	}
}