package clueGame;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class BadConfigFormatException extends Exception {
	public BadConfigFormatException() {
		super("Error: Improper configuration file format.");
	}
	
	public BadConfigFormatException(String message) throws FileNotFoundException {
		super(message);
		PrintWriter out = new PrintWriter("logfile.txt");
		out.println(message);
		out.close();
	}
}
