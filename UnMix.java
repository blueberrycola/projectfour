import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class UnMix {
	private LinkedList<Character> message;

	public UnMix() {
		message = new LinkedList<>();
	}

	/***************************************************************************************************************
	 * Takes string arguments as actual parameters that being two things: 1. the filename, 2. Double quoted encoded
	 * message (The first line in the filename.file) and sends it to unMixture
	 * @param args
	 *********************************************************************************************************A****/
	public static void main(String[] args) {
	    UnMix v = new UnMix();
		v.unMixture(args[0], args[1]);

	}

	/***
	 *
	 * @param command
	 * @return
	 */
	public String processCommand(String command) {
		Scanner scan = new Scanner(command);
		char charInput;

		try {
			command = scan.next();
			charInput = command.charAt(0);
			switch (charInput) {
				case 'b':
					//In the case of the b enigma we remove the string at the starting the insert index minus str.length
					String insertStr = scan.next();
					int stop = scan.nextInt();
					int start = stop - insertStr.length();
					for(int i = start; i <= stop; i++) {
						message.remove(start);
					}
					break;
				case 'r':
					if(scan.hasNextInt()) {
						//simply insert the removed index
						//fixme make insert method
						//remove start stop str
						String num = "0";
						String str = "";


						insert(scan.next(), scan.nextInt());
					} else {
						//reverse replace() algorithm
						//fixme make replace method
						reverse(scan.next(), scan.next());
					}
					break;
				case 'x':
					//fixme adjustments needed for cut
					//start, stop, clipNum, str
					int count = 0;
					String num = "0";
					String str = "";
					while(scan.hasNext()) {

					}

					Integer parseint = new Integer(num);
					insert(str, parseint);
					break;
				case 'd':
					// reinsert chars at the saved index's
					//fixme implement for loop and index saving in replace for mix.java
					//insert(scan.next());
					break;

			}
			// put undo commands here
			} catch (Exception e) {
			System.out.println("Error in command!  Problem!!!! in undo commands");
			System.exit(0);
		}
		finally {
			scan.close();
		}

		return message.toString();
	}

	/**
	 * Mimics Mix.java's insertbefore method for any commands that remove sequences of chars or a char
	 * @param token
	 * @param placeholder
	 */
	private void insert(String token, int placeholder) {
		//Takes token and makes it a series of char's reversed so it can pop into the message LinkedList nice and easily
		for(int i = 0; i < token.length(); i++) {
			message.add(placeholder, token.charAt(token.length() - 1 - i));
		}
	}

	/**
	 * Mimics Mix.java's remove method for any commands that use insertbefore in mix()
	 * @param start
	 * @param stop
	 */
	private void remove(int start, int stop) {
		for(int i = start; i <= stop; i++) {
			message.remove(start);
		}
	}

	/**
	 * Mimics Mix.java's replace method but is reversed
	 * @param c
	 * @param d
	 */
	private void reverse(String c, String d) {

	}

	/**
	 * Takes filename and userMessage param and sends it to unMixUsingFile() and returns a string to print
	 * @param filename
	 * @param userMessage
	 */
	private void unMixture(String filename, String userMessage) {
		String original = UnMixUsingFile (filename, userMessage);
		//fixme: unMixture()
		System.out.println ("The Original message was: " + original);
	}
	//fixme COMMIT TO GITHUB!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

	/**
	 * Reads the first line and stores it as userMessage
	 * @param filename
	 * @param userMessage
	 * @return
	 */
	public String UnMixUsingFile(String filename, String userMessage) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File(filename));
			String str = scanner.nextLine();
			userMessage = str;
			for(int i = 0; i < str.length(); i++) {
				message.add(str.charAt(0));
			}
			ArrayList<String> commands = new ArrayList<>();
			while (scanner.hasNext()) {
				commands.add(scanner.nextLine());
			}
			for(int i = commands.size() - 1; i >= 0; i--) {
				userMessage = processCommand(commands.get(i));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		return userMessage;
	}
}
