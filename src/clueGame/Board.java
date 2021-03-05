package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import experiment.TestBoardCell;


public class Board {
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	
	//Stores Character as key and Room as entry
	private Map<Character, Room> roomMap = new HashMap<Character,Room>();
	
	/*
     * variable and methods used for singleton pattern
     */
     private static Board theInstance = new Board();
     
     // constructor is private to ensure only one can be created
     private Board() {
            super() ;
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
	
	
	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException  {
		FileReader reader = new FileReader(setupConfigFile);
		Scanner in = new Scanner(reader);
		while(in.hasNextLine()) {
			String line = in.nextLine();
 			if(!line.startsWith("/")) {
				String[] setUp = line.split(",");
				if (!setUp[0].equals("Room") && !setUp[0].equals("Space")) {
					throw new BadConfigFormatException("Error: Invalid card type");
				}
				Room room = new Room(setUp[1].trim());
				roomMap.put(setUp[2].charAt(1), room);
			}
		}
		in.close();
	}
	
	
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
			if(setUp.length != numColumns) {
				throw new BadConfigFormatException("Error: Invalid number of columns on row " + count);
			}
			for(int i = 0; i < numColumns; i++) {
				if(!roomMap.containsKey(setUp[i].charAt(0))) {
					throw new BadConfigFormatException("Error: Invalid board cell initial");
				}
				grid[count][i] = new BoardCell(count,i);
				grid[count][i].setInitial(setUp[i].charAt(0));
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
	
	
	public void setConfigFiles(String str, String str2) {
		layoutConfigFile = str;
		setupConfigFile = str2;
		
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