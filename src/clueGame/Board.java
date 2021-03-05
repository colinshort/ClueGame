//Authors:Cameron Fitzgerald, Colin Short
package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import experiment.TestBoardCell;


public class Board {
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Set<BoardCell> targets;
	private Set<BoardCell> visited;

	//Stores Character as key and Room as entry
	private Map<Character, Room> roomMap = new HashMap<Character,Room>();

	/*
	 * variable and methods used for singleton pattern
	 */
	private static Board theInstance = new Board();

	// constructor is private to ensure only one can be created
	private Board() {
		super() ;
		this.targets = new HashSet<BoardCell>();
		this.visited = new HashSet<BoardCell>();
	}
	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	/*
	 * initialize the board (since we are using singleton pattern)
	 */
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
	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException  {
		FileReader reader = new FileReader(setupConfigFile);
		Scanner in = new Scanner(reader);

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
		in.close();
	}

	//load in layout file
	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
		ArrayList<String> rows = new ArrayList<String>();
		FileReader reader = new FileReader(layoutConfigFile);
		Scanner in = new Scanner(reader);

		while(in.hasNextLine()) {
			String line = in.nextLine();
			rows.add(line);
		}

		in.close();

		String[] cols = rows.get(0).split(",");
		numColumns = cols.length;
		numRows = rows.size();

		grid = new BoardCell[numRows][numColumns];

		//add Boardcells to the grid
		FileReader reader2 = new FileReader(layoutConfigFile);
		Scanner in2 = new Scanner(reader2);
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

				//handle doorways, room label cells, room center cells, and secret passage cells
				if(setUp[i].length()==2) {
					if(setUp[i].charAt(1) == '<') { 
						grid[count][i].setDoorWay(true);
						grid[count][i].setDoorDirection(DoorDirection.LEFT);
					}
					if(setUp[i].charAt(1) == '>') { 
						grid[count][i].setDoorWay(true);
						grid[count][i].setDoorDirection(DoorDirection.RIGHT);
					}
					if(setUp[i].charAt(1) == 'v') { 
						grid[count][i].setDoorWay(true);
						grid[count][i].setDoorDirection(DoorDirection.DOWN);
					}
					if(setUp[i].charAt(1) == '^') { 
						grid[count][i].setDoorWay(true);
						grid[count][i].setDoorDirection(DoorDirection.UP);
					}
					if(setUp[i].charAt(1) == '#') { 
						grid[count][i].setRoomLabel(true);
						roomMap.get(setUp[i].charAt(0)).setLabelCell(grid[count][i]);
					}
					if(setUp[i].charAt(1) == '*') { 
						grid[count][i].setCenter(true);
						roomMap.get(setUp[i].charAt(0)).setCenterCell(grid[count][i]);
					}

					else {
						grid[count][i].setSecretPassage(setUp[i].charAt(1));
					}

				}
			}

			count++;
		}
		in2.close();	
	}

	//Set configuration file names
	public void setConfigFiles(String str, String str2) {
		layoutConfigFile = str;
		setupConfigFile = str2;	
	}

	//Calculate the adjacencies for a given cell and add them to adjacency list in BoardCell
	public void calcAdjacencies(BoardCell cell) {
		
		if(cell.isRoomCenter()) {
			
		}

		if(cell.getRow() - 1 >= 0) {
			cell.addAdj(grid[cell.getRow() - 1][cell.getCol()]);
		}

		if(cell.getCol() - 1 >= 0) {
			cell.addAdj(grid[cell.getRow()][cell.getCol() - 1]);
		}

		if(cell.getRow() + 1 <= numRows - 1) {
			cell.addAdj(grid[cell.getRow() + 1][cell.getCol()]);
		}

		if(cell.getCol() + 1 <= numColumns - 1) {
			cell.addAdj(grid[cell.getRow()][cell.getCol() + 1]);
		}
	}

	//Calculate valid target cells for a given cell and path length
	public void calcTargets(BoardCell startCell, int pathlength) {
		visited.add(startCell);
		findAllTargets(startCell, pathlength);
	}

	public void findAllTargets(BoardCell cell, int pathlength) {
		calcAdjacencies(cell);
		Set<BoardCell> Adjs = cell.getAdjList();

		for(BoardCell c : Adjs) {
			if(!visited.contains(c) && !c.getOccupied()) {
				visited.add(c);
				if(pathlength == 1 || c.isRoom()) {
					targets.add(c);
				}else{
					findAllTargets(c, pathlength - 1);
				}
				visited.remove(c);
			}
		}
	}

	public Set<BoardCell> getAdjList(int row, int col){
		Set<BoardCell> myAdjs = new HashSet<BoardCell>();
		calcAdjacencies(grid[row][col]);
		myAdjs = grid[row][col].getAdjList();
		return myAdjs;
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