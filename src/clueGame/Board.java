//Authors:Cameron Fitzgerald, Colin Short
package cluegame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;

	//Stores Character as key and Room as entry
	private Map<Character, Room> roomMap = new HashMap<>();

	//variable and methods used for singleton pattern
	private static Board theInstance = new Board();

	// constructor is private to ensure only one can be created
	private Board() {
		super() ;
		this.targets = new HashSet<>();
		this.visited = new HashSet<>();
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
		FileReader reader = new FileReader(setupConfigFile);
		Scanner in = new Scanner(reader);
		try {
			while(in.hasNextLine()) {
				String line = in.nextLine();

				if(!line.startsWith("/")) {
					String[] setUp = line.split(",");

					//if card is not "Room" or "Space", throw Exception
					if (!setUp[0].equals("Room") && !setUp[0].equals("Space")) {
						throw new BadConfigFormatException("Error: Invalid card type");
					}

					Room room = new Room(setUp[1].trim());
					roomMap.put(setUp[2].charAt(1), room);
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
					}

					//handle doorways, room label cells, room center cells, and secret passage cells
					if(setUp[i].length()==2) {
						if(setUp[i].charAt(1) == '<') { 
							grid[count][i].setDoorWay(true);
							grid[count][i].setDoorDirection(DoorDirection.LEFT);
						}
						else if(setUp[i].charAt(1) == '>') { 
							grid[count][i].setDoorWay(true);
							grid[count][i].setDoorDirection(DoorDirection.RIGHT);
						}
						else if(setUp[i].charAt(1) == 'v') { 
							grid[count][i].setDoorWay(true);
							grid[count][i].setDoorDirection(DoorDirection.DOWN);
						}
						else if(setUp[i].charAt(1) == '^') { 
							grid[count][i].setDoorWay(true);
							grid[count][i].setDoorDirection(DoorDirection.UP);
						}
						else if(setUp[i].charAt(1) == '#') { 
							grid[count][i].setRoomLabel(true);
							roomMap.get(setUp[i].charAt(0)).setLabelCell(grid[count][i]);
						}
						else if(setUp[i].charAt(1) == '*') { 
							grid[count][i].setCenter(true);
							roomMap.get(setUp[i].charAt(0)).setCenterCell(grid[count][i]);
						}
						else {
							grid[count][i].setSecretPassage(setUp[i].charAt(1));
							roomMap.get(setUp[i].charAt(0)).setSecretPassage(setUp[i].charAt(1));
						}
					}
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



		for(int i = 0; i < numColumns; i++) {
			for(int j = 0; j < numRows; j++) {
				calcAdjacencies(grid[j][i]);
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
		if(targets.isEmpty()) {
			targets.clear();
		}
		if(visited.isEmpty()) {
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
					}
					else{
						findAllTargets(c, pathlength - 1);
					}
					visited.remove(c);
				}
			}
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
}