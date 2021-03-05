//Authors:Cameron Fitzgerald, Colin Short
package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception {
	//default
	public BadConfigFormatException() {
		super("Error: Improper configuration file format.");
	}
	
	//specific message
	public BadConfigFormatException(String message) throws FileNotFoundException {
		super(message);
		
		//print message to log file
		PrintWriter out = new PrintWriter("logfile.txt");
		out.println(message);
		out.close();
	}
}
