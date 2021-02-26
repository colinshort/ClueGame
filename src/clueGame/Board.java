package clueGame;

import java.util.Map;

public class Board {
	private BoardCell[][] grid;
	private int numRows;
	private int numColumns;
	private String layoutConfigFile;
	private String setupConfigFile;
	private Map<Character, Room> roomMap;
	
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
		loadConfigFiles();
		grid = new BoardCell[100][100];
	}
	
	public void loadConfigFiles() {
		loadSetupConfig();
		loadLayoutConfig();
	}
	
	public void loadSetupConfig() {
		
	}
	
	public void loadLayoutConfig() {
		
	}
	public void setConfigFiles(String str, String str2) {
		layoutConfigFile = str;
		setupConfigFile = str2;
		
	}
	public Room getRoom(BoardCell cell) {
		return new Room();
	}
	public Room getRoom(char initial) {
		return new Room();
	}
	public int getNumRows() {
		return numRows;
	}
	public int getNumColumns() {
		return numColumns;
	}
	
	public BoardCell getCell(int row, int col) {
		return new BoardCell();
	}
	
	
	
	
	
	
}