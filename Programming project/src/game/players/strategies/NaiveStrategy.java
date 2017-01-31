package game.players.strategies;

import game.Board;
import game.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jesper Simon
 * @version 1.0.0
 * A <code>Strategy</code> that makes a random possible move.
 */
public class NaiveStrategy implements Strategy {

    private static final String NAME = "NAIVE COMPUTER PLAYER";

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
}
