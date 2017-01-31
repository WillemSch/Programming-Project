package game.players.strategies;

import game.Board;
import game.Color;

/**
 * @author Jesper Simon
 * @version 1.0.0
 * A <code>Strategy</code> for a smart computer player.
 */
public class SmartStrategy implements Strategy {

    private static final String NAME = "SMART COMPUTER PLAYER";

    private NaiveStrategy naiveStrategy;

    /**
     * Constructor of SmartStrategy, initiates a NaiveStrategy to get a random move.
     */
    public SmartStrategy(){
        super();
        naiveStrategy = new NaiveStrategy();
    }

    /**
     * Returns the name of this computer player.
     * @return a <code>String</code> with the name of this computer player.
     */
    @Override
    public String getName() {
        return NAME;
    }

    /**
     * Returns an int[] with the x,y,z coordinates of a random valid move.
     * @param board the current <code>Board</code>.
     * @param color the <code>Color</code> of this player.
     * @return An int[] with int[] with the x,y,z coordinates of a random valid move.
     */
    @Override
    public int[] determineMove(Board board, Color color) {
        int[] coordinate;

        //checks if it can make a winning move
        if ((coordinate = winningMove(color, board)) != null){
            return coordinate;
        }

        //checks if the other player can make a winning move
        if ((coordinate = winningMove(color.other(), board)) != null){
            return coordinate;
        }

        return randomMove(board, color);
    }

    /**
     * Finds a random valid move on the board.
     * @param board The board of the current game.
     * @return an int[] with the coordinates of the move.
     */
    private int[] randomMove(Board board, Color color) {
        return naiveStrategy.determineMove(board, color);
    }

    /**
     * Checks every possible move if it is a winning move for the given color
     * @param color The color that has to be checked.
     * @param board The board of the current game.
     * @return an int[] with the coordinates of the move, null if no move was found.
     */
    private int[] winningMove(Color color, Board board){
        int[] coordinates;
        for (int x = 0; x < board.getWidth(); x++){
            for (int y = 0; y < board.getLength(); y++) {
                Board copy = board.deepCopy();
                int z = copy.getHeightOfField(x, y);
                coordinates = new int[]{x, y, z};

                if (copy.isField(coordinates) && copy.isEmptyField(coordinates)) {
                    copy.setField(coordinates, color);
                    if (copy.isWinner(color)) {
                        return coordinates;
                    }
                }
            }
        }
        return null;
    }
}
