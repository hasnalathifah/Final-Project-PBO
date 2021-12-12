package id.ac.its.project;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Formatter;
import java.util.FormatterClosedException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class FileManager {
	private static Formatter output;
	private static Scanner input;
	
	public void saveHighScore(int highscore) {
		if(highscore > loadHighScore()) {
			writeHighScore(highscore);
		}
	}
	
	public int loadHighScore() {
		openFileInput();
		int i = readRecords();
		closeFileInput();
		return i;
	}
	
	private void writeHighScore(int highscore) {
		openFileOutput();
		writeRecords(highscore);
		closeFileOutput();
	}
	
	private void openFileOutput() {
		try {
			output = new Formatter("highscore.txt");
		}
		
		catch (FileNotFoundException fileNotFoundException) {
			System.err.println("Error opening file. Terminating.");
			System.exit(1); // terminate the program
		} 
	} 
	
	private void openFileInput() {
		try {
			input = new Scanner(Paths.get("highscore.txt"));
		}
		
		catch (FileNotFoundException fileNotFoundException) {
			writeHighScore(0);
			closeFileInput();
			openFileInput();
		} 
		catch (IOException ioException) {
			writeHighScore(0);
			closeFileInput();
			openFileInput();
		}
	} 
	
	private void writeRecords(int highscore) {
		try	{
			output.format("Highscore: " + highscore);
		} 
		catch (FormatterClosedException formatterClosedException) {
			System.err.println("Error writing to file. Terminating.");
		}
	}
	
	private int readRecords() {
		try {
			while (input.hasNext()) {
				String s = input.next();
				return input.nextInt();
			}
		}
		catch (NoSuchElementException elementException) {
			System.err.println("File improperly formed. Terminating.");
		} 
		catch (IllegalStateException stateException) {
			System.err.println("Error reading from file. Terminating.");
		} 
		return 0;
	}
	
	private void closeFileOutput() {
		if (output != null)
			output.close();
	}
	private void closeFileInput() {
		if (input != null)
			input.close();
	}
}
