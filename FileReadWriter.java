package hangman;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FileReadWriter {

	private ObjectOutputStream output;
	private ObjectInputStream input;
	ArrayList<Players> myArr = new ArrayList<Players>();

	public void openFileToWite() {
		try // open file
		{
			output = new ObjectOutputStream(new FileOutputStream("players.ser",
					true));
		} catch (IOException ioException) {
			//show error
			System.err.println("Error opening file.");
		}
	}

	// add records to file
	public void addRecords(int scores, String name) {
		Players players = new Players(name, scores); // object to be written to file

		try { // output values to file
			output.writeObject(players); // output players
		} catch (IOException ioException) {
			//show error
			System.err.println("Error writing to file.");
			return;
		}
	}

	public void closeFileFromWriting() {
		try // close file
		{
			if (output != null) {
			  output.close();
			}
		} catch (IOException ioException) {
			//show error
			System.err.println("Error closing file.");
			//exit
			System.exit(1);
		}
	}

	public void openFiletoRead() {
		try {
			input = new ObjectInputStream(new FileInputStream("players.ser"));

		} catch (IOException ioException) {
			//show error
			System.err.println("Error opening file.");
		}
	}

	public void readRecords() {
		try // input the values from the file
		{
			Object obj = null;


			while (!(obj = input.readObject()).equals(null)) {
				checkPlayer(obj);
			}

		} // end try
		catch (EOFException endOfFileException) {
			return; // end of file was reached
		} catch (ClassNotFoundException classNotFoundException) {
			//show error
			System.err.println("Unable to create object.");
		} catch (IOException ioException) {
			//show error
			System.err.println("Error during reading from file.");
		}
	}

	public void checkPlayer (Object obj) {
		Players records;
		if (obj instanceof Players) {
			records = (Players) obj;
			myArr.add(records);
			System.out.printf("DEBUG: %-10d%-12s\n", records.getScores(), records.getName());
		}
	}

	public void printAndSortScoreBoard() {
		int n = myArr.size();
		for (int pass = 1; pass < n; pass++) {
			myArr = sortScoreBoard(myArr, n, pass);
		}

		System.out.println("Scoreboard:");
		for (int i = 0; i < myArr.size(); i++) {
			System.out.printf("%d. %s ----> %d", i, myArr.get(i).getName(), myArr.get(i).getScores());
		}
	}

	public ArrayList<Players> sortScoreBoard(ArrayList <Players> myArr, int n, int pass) {
		Players temp;
		for (int i = 0; i < n - pass; i++) {
			if (myArr.get(i).getScores() > myArr.get(i + 1).getScores()) {

				temp = myArr.get(i);
				myArr.set(i, myArr.get(i + 1));
				myArr.set(i + 1, temp);
			}
		}
		return myArr;
	}

	public void closeFileFromReading() {
		try {
			if (input != null){
				input.close();
			}
			// exit
			System.exit(0);

		} catch (IOException ioException) {
			//show error
			System.err.println("Error closing file.");
			System.exit(1);
		}
	}
}
