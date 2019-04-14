import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;

public class UnMix {
	private LinkedList<Character> message;
	private ArrayList<String> messagePieces;


	public UnMix() {
		message = new LinkedList<>();
		messagePieces = new ArrayList<>();
	}

	/***************************************************************************************************************
	 * Takes string arguments as actual parameters that being two things: 1. the filename, 2. Double quoted encoded
	 * message (The first line in the filename.file) and sends it to unMixture
	 * @param args
	 *********************************************************************************************************A****/
	public static void main(String[] args) {
	    UnMix v = new UnMix();

		//Linked list initialization
		String userMessage = args[1];
		for(int i = 0; i < userMessage.length(); i++) {
			v.message.add(userMessage.charAt(i));
		}
		System.out.println(v.message.toString());
		//Passing to unMixture
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

			boolean removeFlag = false;
			boolean insertFlag = false;
			boolean reverseFlag = false;
			boolean cutFlag = false;
			switch (charInput) {
				case 'b':
					//In the case of the b enigma we remove the string at the starting the insert index minus str.length
					System.out.println("Command detected: b\n\tRemoving char sequence...\n");
					removeFlag = true;
					break;
				case 'r':
					if(scan.hasNextInt()) {
						System.out.println("Command detected: r\n\tRemoving char sequence...\n");
						insertFlag = true;
					} else {
						//reverse replace() algorithm
						//fixme OPTIONAL FOR LEETSPEAK TRANSLATION make replace method
						reverseFlag = true;
					}
					break;
				case 'x':
					System.out.println("Command detected: x\n\tRemoving char sequence...\n");
					cutFlag = true;
					break;
				case 'd':
					// reinsert chars at the saved index's
					System.out.println("d detected!");
					//fixme OPTIONAL FOR LEETSPEAK implement for loop and index saving in replace for mix.java
					insertFlag = true;
					break;

			}
			if(insertFlag || cutFlag) {
				String commandLine = scan.nextLine();
				Scanner commandScan = new Scanner(commandLine);
				String token = "";
				int index = 0;
				int stop = 0;
				int clipNum = 0;
				if(insertFlag) {
					//normal insertion protocol
					index = commandScan.nextInt();
					stop = commandScan.nextInt();

					token = commandScan.nextLine();
					//removes single quotes
					token = token.substring(2, token.length() - 1);
					if(token.length() <= 1) {
						message.add(index, token.charAt(0));
					} else {
						//Takes token and makes it a series of char's reversed so it can pop into the message LinkedList nice and easily
						for(int i = 0; i < token.length(); i++) {
							message.add(index, token.charAt(token.length() - 1 - i));
						}
					}



				} else {
					//cut insertion protocol
					index = commandScan.nextInt();
					stop = commandScan.nextInt();
					clipNum = commandScan.nextInt();
					token = commandScan.nextLine();

					//removes single quotes
					token = token.substring(2, token.length() - 1);
					for(int i = 0; i < token.length(); i++) {
						message.add(token.charAt(i));

					}

					//Removes the "mirror paste string" that appears at the end of message
					for(int i = 0; i < token.length(); i++) {
						message.remove(message.size() - 1);
					}

					for(int i = 0; i < token.length(); i++) {
						message.add(index, token.charAt(token.length() - 1 - i));
					}
				}


				//End of insert method
			}else if(removeFlag) {
				String commandLine = scan.nextLine();
				String strAdded = commandLine.substring(2, commandLine.length());
				int strLength = 0;
				while(strAdded.charAt(strLength) != '\'') {
					strLength++;
				}
				//plus 2's are used to ignore single quotes and spaces
				String num = commandLine.substring(2 + strLength + 2, commandLine.length());
				//This scanner is responsible for turning the number in the string into an int
				Scanner parseInt = new Scanner(num);
				int indexPlaced = parseInt.nextInt();

				for(int i = 0; i < strLength; i++) {
					message.remove(indexPlaced);
				}
				//End of remove method
			}else if(reverseFlag) {
				//fixme emulate countChar in mix to get indexes of the chars needing to be switched
				//End of reverse method
			}else if(cutFlag) {
				//Similar to insert but accustomed to cut commands
				//End of cut method
			}
			// put undo commands here
			} catch (Exception e) {
			System.out.println("Error in command!  Problem!!!! in undo commands");
			System.exit(0);
		}
		finally {
			scan.close();
		}
		System.out.println("Decrypting file: " + message.toString());
		return message.toString();
	}

	/**
	 * Mimics Mix.java's insertbefore method for any commands that remove sequences of chars or a char
	 * @param token
	 * @param placeholder
	 */
	private void insert(String token, int placeholder) {
		//Takes token and makes it a series of char's reversed so it can pop into the message LinkedList nice and easily

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
		//passed to UnMixUsingFile
		String original = UnMixUsingFile (filename, userMessage);

		System.out.println ("The Original message was: " + original);
	}

	/**
	 * Reads the first line and stores it as userMessage which is then passed into processCommand to
	 * @param filename
	 * @param userMessage
	 * @return
	 */
	public String UnMixUsingFile(String filename, String userMessage) {
		Scanner scanner = null;
		String str;
		try {
			scanner = new Scanner(new File(filename));
			str = scanner.nextLine();
			userMessage = str;

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
