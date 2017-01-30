package game;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jesper Simon
 * @version 1.0.0
 * A <code>Strategy</code> for a smart computer player.
 */
public class SmartStrategy implements Strategy {

    private static final String NAME = "SMART COMPUTER PLAYER";

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


        System.out.println(color.other());

        //checks if the other player can make a winning move
        if ((coordinate = winningMove(color.other(), board)) != null){
            return coordinate;
        }

        return randomMove(board);
    }

    /**
     * Finds a random valid move on the board.
     * @param board The board of the current game.
     * @return an int[] with the coordinates of the move.
     */
    private int[] randomMove(Board board) {
        int[] coordinates = {0,0,0};
        List<Integer> xIndices = new ArrayList<>();
        List<Integer> yIndices = new ArrayList<>();

        for (int i = 0; i < board.getLength(); i++){
            xIndices.add(i);
        }

        for (int i = 0; i < board.getWidth(); i++){
            yIndices.add(i);
        }

        for (int i = 0; i < board.getWidth() * board.getLength(); i++){
            int randomIndex = (int) (Math.random() * xIndices.size());
            int x = xIndices.get(randomIndex);
            xIndices.remove(randomIndex);

            randomIndex = (int) (Math.random() * yIndices.size());
            int y = yIndices.get(randomIndex);
            yIndices.remove(randomIndex);

            int z = board.getHeightOfField(x, y);
            coordinates = new int[]{x,y,z};

            if (board.isField(coordinates) && board.isEmptyField(coordinates)){
                break;
            }
        }
        return coordinates;
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
