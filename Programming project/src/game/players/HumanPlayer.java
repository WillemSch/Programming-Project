package game.players;

import game.Board;
import game.Color;
import game.players.strategies.SmartStrategy;
import game.players.strategies.Strategy;
import game.ui.GameTUIView;

import java.util.Scanner;
/**
 * Class for maintaining a human player in Connect Four.
 *
 * @author Jesper Simon
 * @version $Revision: 2.0 $
 */
public class HumanPlayer extends Player {

	private GameTUIView gameView;
    private Strategy strategy;

	/*
	 * @ requires name != null; requires color == Color.RED || color ==
	 * Color.BLUE; ensures this.getName() == name; ensures this.getColor() ==
	 * color;
	 */
	/**
	 * Creates a new human player object.
	 */
	public HumanPlayer(String name, Color color) {
		super(name, color);
		gameView = new GameTUIView();
		strategy = new SmartStrategy();
	}

	// -- Commands ---------------------------------------------------

	/*
	 * @ requires board != null; ensures board.isField(\result) &&
	 * board.isEmptyField(\result);
	 *
	 */
	/**
	 * Asks the user to input the field where to place the next color. This is
	 * done using the standard input/output. \
	 *
	 * @param board
	 *            the game board
	 * @return the player's chosen field
	 */

	public int[] determineMove(Board board) {
		String prompt = "> " + getName() + " (" + getColor().toString() + ")"
				+ ", what is your choice (write it in the form 'x y')?\n> ";
		int[] choice = readInt(prompt, board);
		boolean valid = board.isField(choice) && board.isEmptyField(choice);
		while (!valid) {
			gameView.print("ERROR: chosen field is not a valid choice.");
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
		int x = -1;
		int y = -1;
		int z = -1;
		boolean gotHint = false;
		@SuppressWarnings("resource")
		Scanner line = new Scanner(System.in);
		do {
			gameView.print(prompt);
			if (line.hasNext()) {
                try (Scanner scannerLine = new Scanner(line.nextLine())) {
                    if (scannerLine.hasNext()) {
                        String in_1 = scannerLine.next();
                        if (in_1.toUpperCase().equals("SWITCH")) {
                            int level = scannerLine.nextInt();
                            gameView.switchLevel(level, this);
                        } else if (in_1.toUpperCase().equals("EXIT")) {
                            gameView.print("Ending the game.");
                            gameView.start();
                        } else if (in_1.toUpperCase().equals("HINT")) {
                            int[] coordinate = strategy.determineMove(board, getColor());
                            gameView.print("Try this move: " + coordinate[0] + "-" + coordinate[1]);
                            gotHint = true;
                        } else if (scannerLine.hasNext()) {
                            String in_2 = scannerLine.next();
                            if (in_1.matches("\\d+") && in_2.matches("\\d+") /*&& !scannerLine.hasNext()*/) {
                                x = Integer.parseInt(in_1);
                                y = Integer.parseInt(in_2);
                                z = board.getHeightOfField(x, y);
                                System.out.println("DEBUG");
                            }
                        }
                    }
                }
            }
			int[] coordinates = { x, y, z };
			if (board.isField(coordinates)) {
				intRead = true;
			} else if(!gotHint){
				gameView.print("ERROR: Invalid move, try again.");
			} else {
                gotHint = true;
            }
		} while (!intRead);
		int[] coordinates = { x, y, z };
		return coordinates;
	}
}
