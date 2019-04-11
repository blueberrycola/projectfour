import java.io.*;
import java.util.*;
import java.lang.*;

public class Mix {

	private LinkedList<Character> message;
	private String undoCommands;
	private Hashtable<Integer, String> clipBoards;

	private String userMessage;
	private Scanner scan;

	/****************************************************************************
	 * Constructor initializes necessary objects needed to complete the objective
	 ***************************************************************************/
	public Mix() {
		scan = new Scanner(System.in);

		message = new LinkedList<>();
		clipBoards = new Hashtable<>();

		undoCommands = "";
	}

	/***********************************************************
	 * method responcible for getting starter input(the message)
	 * @param args: What the user enters in the terminal
	 **********************************************************/
	public static void main(String[] args) {
		Mix mix = new Mix();
		mix.userMessage = args[0];
		System.out.println (mix.userMessage);
		for(int i = 0; i < mix.userMessage.length(); i++) {
			mix.message.add(mix.userMessage.charAt(i));
		}
		mix.mixture();
	}

	/***
	 * Method that is responsible for finding what command you are executing in the terminal, done by a switch statement
	 * that branches out to
	 */
	private void mixture() {
		do {
			DisplayMessage();
			System.out.print("Command: ");

			// save state
			LinkedList<Character> currMessage = new LinkedList<>();

			String currUndoCommands = undoCommands;

			try {
				String command = scan.next("[Qbrpcxh]");
				boolean scanForSpace = true;

				//checks to see if the command you typed contains two spaces
				//while(scanForSpace) {


				switch (command) {
				case "Q":
					save(scan.next());
					System.out.println ("Final mixed up message: \"" + message+"\"");
					System.exit(0);
				case "b":
					if(scan.hasNext()) {
						if(scan.hasNextInt()) {
							insertbefore(scan.next(), scan.nextInt());
						} else {
							replace(scan.next(), scan.next());
						}
					}
					insertbefore(scan.next(), scan.nextInt());
					break;
				case "r":
					//fixme: add if statement for different r commands
					remove(scan.nextInt(), scan.nextInt());
					break;
				case "c":
					copy(scan.nextInt(), scan.nextInt(), scan.nextInt());
					break;
				case "x":
					cut(scan.nextInt(), scan.nextInt(), scan.nextInt());
					break;
				case "p":
					paste(scan.nextInt(), scan.nextInt());
					break;
				case "h":
					helpPage();
					break;
				case "d":
					deleteChars(scan.next());
					break;
				case "z":
					spazCommand();
					break;

					// all the rest of the commands have not been done
					// No "real" error checking has been done.
				}
				scan.nextLine();   // should flush the buffer
				System.out.println("For demonstration purposes only:\n" + undoCommands);
			}
			catch (Exception e ) {
				System.out.println ("Error on input, previous state restored.");
				scan = new Scanner(System.in);  // should completely flush the buffer

				// restore state;
				undoCommands = currUndoCommands;
				message = currMessage;
			}

		} while (true);
	}

	/************************************************************************************************************
	 * Removes all of a specific character requested, if you enter a char that isnt there the command will simply
	 * not remove anything
	 * @param c: the char you want removed
	 ************************************************************************************************************/
	private void deleteChars(String c) {
		char character = c.charAt(0);
		for(int i = 0; i < message.size(); i++) {
			if(message.get(i) == character) {
				message.remove(i);
			}
		}
	}

	/******************************************************************************
	 * Does an assortment of commands randomly based on values in the Random object
	 *****************************************************************************/
	private void spazCommand(){
		//fixme:
	}

	private void replace(String remove, String placeholder) {
		if(remove.length() > 1 || placeholder.length() > 1) {
			throw new IllegalArgumentException();
		} else {
			char remChar = remove.charAt(0);
			char repChar = placeholder.charAt(0);
			for(int i = 0; i < message.size(); i++) {
				if(message.get(i) == remChar) {
					message.remove(i);
					message.add(i, repChar);
				}
			}
		}
	}

	private void remove(int start, int stop) {
		for(int i = 0; i <= stop - start; i++) {
			message.remove(start + i);
		}
	}

	private void cut(int start, int stop, int clipNum) {
		for(int i = start; i <= stop; i++) {
			clipBoards.put(clipNum, message.);
		}
		for(int i = start; i <= stop; i++) {
			message.remove(start);
		}
	}

	private void copy(int start, int stop, int clipNum) {
		for(int i = start; i <= stop; i++) {
			clipBoards.put(clipNum, message.get(i));
		}
	}

	private void paste( int index, int clipNum) {

	}
         
	private void insertbefore(String token, int index) {

		//Takes token and makes it a series of char's reversed so it can pop into message easily
		ArrayList<Character> charList = new ArrayList<>();
		for(int i = 0; i < token.length(); i++) {
			message.add(index, token.charAt(token.length() - 1 - i));
		}
	}

	private void DisplayMessage() {
		System.out.print ("Message:\n");
		//userMessage = message.toString();

		for (int i = 0; i < userMessage.length(); i++) 
			System.out.format ("%3d", i);
		System.out.format ("\n");
		for (char c : userMessage.toCharArray()) 
			System.out.format("%3c",c);
		System.out.format ("\n");
	}

	public void save(String filename) {

		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));

		} catch (IOException e) {
			e.printStackTrace();
		}

		out.println(undoCommands);
		out.close();
	}

	private void helpPage() {
		System.out.println("Commands:");
		System.out.println("\tQ filename	means, quit! " + " save to filename" );			
		System.out.println("\t  ~ is used for a space character" );		
		System.out.println("\t .... etc" );		
		System.out.println("\th\tmeans to show this help page");
	}
}
