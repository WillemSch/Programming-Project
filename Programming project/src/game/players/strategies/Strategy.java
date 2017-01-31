package game.players.strategies;

import game.Board;
import game.Color;

/**
 * @author willem
 * An interface for the strategies.
 */
public interface Strategy {
    /**
     * Gives the name of this strategy.
     * @return a <code>String</code> with the name of this strategy.
     */
    public String getName();

    /**
     * Returns an int[] with coordinates for a move.
     * @param board the current <code>Board</code>.
     * @param color the <code>Color</code> of this player.
     * @return an int[] with the x,y,z coordinates of a move.
     */
    public int[] determineMove(Board board, Color color);
}