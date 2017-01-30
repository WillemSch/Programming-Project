package game;

import java.util.Scanner;

/**
 * Class for maintaining a human player in Connect Four.
 * 
 * @author Jesper Simon
 * @version $Revision: 2.0 $
 */
public class HumanPlayer extends Player {

	// -- Constructors -----------------------------------------------

	/*
	 * @ requires name != null; requires mark == Color.RED || mark == Color.BLUE;
	 * ensures this.getName() == name; ensures this.getColor() == mark;
	 */
	/**
	 * Creates a new human player object.
	 * 
	 */
	public HumanPlayer(String name, Color color) {
		super(name, color);
	}

	// -- Commands ---------------------------------------------------

	/*
	 * @ requires board != null; ensures board.isField(\result) &&
	 * board.isEmptyField(\result);
	 * 
	 */
	/**
	 * Asks the user to input the field where to place the next mark. This is
	 * done using the standard input/output. \
	 * 
	 * @param board
	 *            the game board
	 * @return the player's chosen field
	 */
	private int x;
	private int y;
	private int z;

	public int[] determineMove(Board board) {
		String prompt = "> " + getName() + " (" + getColor().toString() + ")" + ", what is your choice? ";
		int[] choice = readInt(prompt, board);
		boolean valid = board.isField(choice) && board.isEmptyField(choice);
		while (!valid) {
			System.out.println("ERROR: field " + choice[0] + "," + choice[1] + " is no valid choice.");
			choice = readInt(prompt, board);
			valid = board.isField(choice) && board.isEmptyField(choice);
		}
		return choice;
	}

	/**
	 * Writes a prompt to standard out and tries to read an int value from
	 * standard in. This is repeated until an int value is entered.
	 * 
	 * @param prompt
	 *            the question to prompt the user
	 * @return the first int value which is entered by the user
	 */
	private int[] readInt(String prompt, Board board) {
		boolean intRead = false;
		@SuppressWarnings("resource")
		Scanner line = new Scanner(System.in);
		do {
			System.out.print(prompt);
			try (Scanner scannerLine = new Scanner(line.nextLine());) {
				if (scannerLine.hasNext()) {
					String in_1 = scannerLine.next();
					if (scannerLine.hasNext()) {
						String in_2 = scannerLine.next();
						if (in_1.matches("\\d+") && in_2.matches("\\d+")) {
							x = Integer.parseInt(in_1);
							y = Integer.parseInt(in_2);
							z = board.getHeightOfField(x, y);
						}
					}
				}
			}
			int[] coordinates = { x, y, z };
			if (board.isField(coordinates)) {
				intRead = true;
			} else {
				System.out.println("error" + z);
			}
		} while (!intRead);
		int[] coordinates = { x, y, z };
		return coordinates;
	}
}
