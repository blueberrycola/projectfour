import java.io.*;
import java.util.*;
import java.lang.*;

public class Mix {

	private LinkedList<Character> message;

	private String undoCommands;
	private Hashtable<Integer, String> clipBoards;
	//This String array is responsible for knowing what commands and the strings affected
	private ArrayList<String> commandMemory;


	private String userMessage;
	private Scanner scan;

	/****************************************************************************
	 * Constructor initializes necessary objects needed to complete the objective
	 ***************************************************************************/
	public Mix() {
		scan = new Scanner(System.in);

		message = new LinkedList<>();
		clipBoards = new Hashtable<>();
		commandMemory = new ArrayList<>();

		undoCommands = "";
	}

	/***********************************************************
	 * method responsible for getting starter input(the message)
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
		mix.DisplayMessage();
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
				String command = scan.next("[QLHCbrpcxdz]");
				switch (command) {
				case "Q":
					save(scan.next());
					System.out.println ("Final mixed up message: \"" + formatLL(message.toString())+"\"");

					System.exit(0);
				case "b":
					insertbefore(scan.next(), scan.nextInt());
					break;
				case "r":
					if(scan.hasNextInt()) {
						remove(scan.nextInt(), scan.nextInt());
					} else {
						replace(scan.next(), scan.next());
					}

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
				case "H":
					helpPage();
					break;
				case "d":
					deleteChars(scan.next());
					break;
				case "z":
					spazCommand();
					break;
				case "L":
					leetSpeak();
					break;
				case "C":
					countChar(scan.next());
					break;

				}
				undoCommands = message.toString();
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
		String unmixInstructions = "";
		for(int i = 0; i < message.size(); i++) {
			if(message.get(i) == character) {
				unmixInstructions = unmixInstructions + i;
				message.remove(i);
				i -= 1;
			}
		}
		commandMemory.add("d " + c + " '" + unmixInstructions + "'");
		//unmixInstructions: insert the char into the elements specified by the index
	}

	/******************************************************************************
	 * A method that simulates random user input by doing an assortment of commands
	 * all being determined by the random object, which is used in the for loop to
	 * know how many times we should use commands (5 to 10) and what commands we
	 * should do (1 to 4) countChar is excluded since it isnt useful for unmix.
	 *****************************************************************************/
	private void spazCommand(){

		Random rng = new Random();
		//ASCII_RANDOM: generates a random char decimal between hex values 32 to 126
		int ASCII_RANDOM = rng.nextInt(126 - 32) + 32;
		for(int i = 1; i <= rng.nextInt(9 - 1) + 1; i++) {
			int temp = rng.nextInt(4 - 1) + 1;
			//insert
			if(temp == 1) {
				String randStr = "";
				int stringLength = rng.nextInt(5 - 1) + 1;
				//random string builder
				for(int j = 0; i < stringLength; i++) {
					randStr = randStr + (char)ASCII_RANDOM;
				}
				//llSize made to ensure no out of bounds is used
				int llSize = message.size() - 1;
				System.out.println(llSize);
				insertbefore(randStr, rng.nextInt(llSize - 1) + 1);

			}
			//replace
			else if(temp == 2) {
				String charOne = "" + (char)ASCII_RANDOM;
				String charTwo = "" + (char)ASCII_RANDOM;
				replace(charOne, charTwo);
			}
			//remove
			else if(temp == 3) {
				int min = rng.nextInt((message.size() - 1) + 1);
				int max = rng.nextInt((message.size() - 1) + 1);
				//Used to switch min and max in parameters
				boolean bigMin = false;

				if(min > max) {
					bigMin = true;
				}

				if(bigMin) {
					remove(max, min);
				} else {
					remove(min, max);
				}
			}
			//delete
			else if(temp == 4) {
				String randomChar = "" + (char)ASCII_RANDOM;
				deleteChars(randomChar);
			}
			//Chase's special touch: 1 out of 100 chance to leetspeak message
			if(rng.nextInt(100 - 1) + 1 == rng.nextInt(100 - 1) + 1) {
				leetSpeak();
			}

		}
	}

	/**
	 * Changes message and translates it to leetspeak (the secret language of the DIY/Hacker group: Cult of The Dead Cow)
	 * multi char letters are separated by a space on both sides
	 */
	private void leetSpeak() {
		String str = message.toString();

		for(int i = 0; i < str.length(); i++) {
			if (str.contains("A") || str.contains("a")) {
				replace("A", "@");
				replace("a", "@");
			}
			if(str.contains("C") || str.contains("c")) {
				replace("C", "(");
				replace("c", "(");
			}

			if(str.contains("E") || str.contains("e")) {
				replace("E", "3");
				replace("e", "3");
			}

			if(str.contains("I") || str.contains("i")) {
				replace("I", "!");
				replace("i", "!");
			}
			if(str.contains("S") || str.contains("s")) {
				replace("S", "$");
				replace("s", "$");
			}
			if(str.contains("T") || str.contains("t")) {
				replace("T", "7");
				replace("t", "7");
			}
		}

	}
	private void countChar(String c) {
		char character = c.charAt(0);
		int count = 0;
		for(int i = 0; i < message.size(); i++) {
			if(message.get(i) == character) {
				count++;
			}
		}
		System.out.println("SYSTEM: There are currently " + count + " '" + character + "' character(s)");

	}



	/**
	 * Replaces the char requested to a new one that is also requested
	 * @param remove
	 * @param placeholder
	 */
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
			//unmix instructions: switch the actual parameters
			commandMemory.add("r " + remove + " " + placeholder);

		}
	}

	/**
	 * Removes all characters from start to stop
	 * @param start
	 * @param stop
	 */
	private void remove(int start, int stop) {
		String removedStr = "";
		for(int i = start; i <= stop; i++) {
			removedStr = removedStr + message.get(start);
			message.remove(start);
		}
		commandMemory.add("r " + start + " " + stop + " '" + removedStr + "'");
	}
	/**
	 * Cuts a sequence of char elements in the linked list and copies it to the clipboard HashTable
	 * from the message
	 * @param start
	 * @param stop
	 * @param clipNum the key for the cut string
	 */
	private void cut(int start, int stop, int clipNum) {
		String cut = "";
		for(int i = start; i <= stop; i++) {
			cut = cut + message.get(start);
			message.remove(start);
		}
		clipBoards.put(clipNum, cut);
		System.out.println("Command: Char sequence saved in clipboard key: " + clipNum);
		System.out.println("	String: [" + cut + "]");
		commandMemory.add("x " + start + " " + stop + " " + clipNum + " '" + cut + "'");
	}

	/*****************************************************************************************
	 * Copies the requested range (start to stop) and records it in the hash table as a string
	 * @param start
	 * @param stop
	 * @param clipNum
	 ****************************************************************************************/
	private void copy(int start, int stop, int clipNum) {
		String copy = "";
		for(int i = start; i <= stop; i++) {
			copy = copy + message.get(i);
		}
		clipBoards.put(clipNum, copy);
		System.out.println("Command: Char sequence saved in clipboard key: " + clipNum);
		System.out.println(" 	String: [" + copy + "]");
		//Copy is not allocated into the hashtable because using it wouldnt have any use for unmix

	}

	/******************************************************************************
	 * Places what you have for the given hashtable key by recycling insertbefore()
	 * @param index
	 * @param clipNum
	 *****************************************************************************/
	private void paste( int index, int clipNum) {
		//LAZY MODE ACTIVATE:
		insertbefore(clipBoards.get(clipNum), index);


	}
         
	private void insertbefore(String token, int index) {
		//Takes token and makes it a series of char's reversed so it can pop into the message LinkedList nice and easily
		for(int i = 0; i < token.length(); i++) {
			message.add(index, token.charAt(token.length() - 1 - i));
		}
		commandMemory.add("b '" + token + "' " + index);



		//unmix instructions remove inserted
	}

	/**
	 * Prints the message you typed in main
	 */
	private void DisplayMessage() {
		System.out.print ("Message:\n");
		String temp = formatLL(message.toString());
		System.out.println(temp);
		userMessage = temp;

		for (int i = 0; i < userMessage.length(); i++) {
			System.out.format("%3d", i);
		}

		System.out.format ("\n");
		for (char c : userMessage.toCharArray()) {
			System.out.format("%3c", c);
		}
		System.out.format ("\n");
	}

	/*************************************************************************************************************
	 * When the linked list gets converted to a string with the toString() java.lang.api version it includes ", "
	 * which messes with the index in DisplayMessage(). This method fixes that
	 * @param str
	 * @return
	 ************************************************************************************************************/
	public String formatLL(String str) {
		String temp = "";
		for(int i = 1; i < str.length() - 1; i++) {
			//This if statement makes sure it doesnt skip over commas or spaces included in the message
			if(str.charAt(i) == ',' || str.charAt(i) == ' ') {
				if(str.charAt(i) == ',') {
					if(str.charAt(i + 1) == ',') {
						temp = temp + str.charAt(i);
					}
				}else if(str.charAt(i) == ' ') {
					if(str.charAt(i + 1) == ' ') {
						temp = temp + str.charAt(i);
					}
				}
			//Checker for any other char in the LinkedList
			} else if(str.charAt(i) != ',' && str.charAt(i + 1) == ',') {
				temp = temp + str.charAt(i);
			}
		}
		//Needed for the last char
		temp = temp + str.charAt(str.length() - 2);
		return temp;
	}

	/**********************************************************
	 * Saves the message, with all augments included
	 * @param filename (the name you want the file to be named)
	 *********************************************************/
	public void save(String filename) {


		PrintWriter out = null;
		try {
			out = new PrintWriter(new BufferedWriter(new FileWriter(filename)));

		} catch (IOException e) {
			e.printStackTrace();
		}

		out.println(formatLL(undoCommands));
		for(int i = 0; i < commandMemory.size(); i++) {
			out.println(commandMemory.get(i));
		}
		out.close();
	}


	/***********************************************************************************************
	 * A method that prints information on the commands we have for the application and what they do
	 **********************************************************************************************/
	private void helpPage() {
		System.out.println("Commands:");
		System.out.println("\tQ filename	means, quit! " + " save to filename" );
		System.out.println("\tH\tmeans to show this help page");
		System.out.println("L means translate your message into leetspeak");
		System.out.println("b means insert, next string is what you insert, next num is the index you want it at");
		System.out.println("r means either replace or remove");
		System.out.println("	r with 2 numbers is remove, r with 2 chars is replace");
		System.out.println("d means delete, next char is what is removed from the whole linked list");
		System.out.println("z randomizes user input to scramble your message");
		System.out.println("p means paste, next two ints are the range(start-end) and the next int is the key for the" +
				"hashtable");
		System.out.println("c means copy, next two ints are the range(start-end) and next int is key for the hashtable");
		System.out.println("x means cut, next two ints are the range(start-end) and the next int is key for the hashtable");
	}
}
