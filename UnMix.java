import java.io.*;
import java.util.LinkedList;
import java.util.Scanner;

public class UnMix {
	private LinkedList<Character> message;

	public UnMix() {
		message = new LinkedList();
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
			switch (command.charAt(0)) {

			// put undo commands here
			}
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
	 * Takes filename and userMessage param and sends it to unMixUsingFile() and returns a string to print
	 * @param filename
	 * @param userMessage
	 */
	private void unMixture(String filename, String userMessage) {
		String original = UnMixUsingFile (filename, userMessage);
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

			while (scanner.hasNext()) {
				//String responsible for remembering the string between white lines and parses to int when needed
				String temp = scanner.nextLine();


				System.out.println(temp);




			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		while (scanner.hasNext()) {
			String command = scanner.nextLine();
			userMessage = processCommand(command);
		} 
		return userMessage;
	}
}
