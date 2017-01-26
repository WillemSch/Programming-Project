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
	 * @ requires name != null; requires mark == Mark.XX || mark == Mark.OO;
	 * ensures this.getName() == name; ensures this.getMark() == mark;
	 */
	/**
	 * Creates a new human player object.
	 * 
	 */
	public HumanPlayer(String name, Mark mark) {
		super(name, mark);
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

	/*
	 * public Integer[] determineMove(Board board) { boolean validmove = false;
	 * while (!validmove) { boolean validinput = false; while (!validinput) {
	 * 
	 * System.out.printf("> %s (%s), what is your move (in the form x,y)",
	 * getName(), getMark().toString()); Scanner input = new Scanner(System.in);
	 * if (input.hasNextLine() && input.nextLine().contains(",")) { String[] in
	 * = input.nextLine().split(","); if (in[0].matches("\\d+") &&
	 * in[1].matches("\\d+")) { x = Integer.parseInt(in[0]); y =
	 * Integer.parseInt(in[1]); z = 0; if (board.isField(x, y, z)) { validinput
	 * = true; } else { input.close();
	 * System.out.println("Error: invalid input. Try again."); } } else {
	 * input.close(); System.out.println("Error: invalid input. Try again."); }
	 * } else { input.close();
	 * System.out.println("Error: invalid input. Try again."); } input.close();
	 * } while (!board.isEmptyField(x, y, z) && (z < board.getHeigth())) { z++;
	 * } if (z == board.getHeigth()) {
	 * System.out.printf("Error: field %d,%d is no valid choice. Try again.", x,
	 * y); } else { validmove = true; } } Integer[] coordinates = { x, y, z };
	 * return coordinates; }
	 */

	public Integer[] determineMove(Board board) {
		String prompt = "> " + getName() + " (" + getMark().toString() + ")" + ", what is your choice? ";
		Integer[] choice = readInt(prompt, board);
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
	private Integer[] readInt(String prompt, Board board) {
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
							z = 0;
						}
					}
				}
			}
			Integer[] coordinates = { x, y, z };
			while (!board.isEmptyField(coordinates) && (z < board.getHeigth())) {
				z++;
			}
			if (z == board.getHeigth()) {
				System.out.printf("Error: field %d,%d is no valid choice. \nTry again.", x, y);
			} else {
				intRead = true;
			}
		} while (!intRead);
		Integer[] coordinates = { x, y, z };
		return coordinates;
	}
}
