package network;

import game.Board;
import game.Color;
import game.Player;

/**
 * @author Willem Schooltink
 * @version 1.0.0
 */
public class ClientPlayer extends Player{
    private boolean hasNewMove;
    private Integer[] move;

    /**
     * Constructor of <code>ClientPlayer</code>. Starts a new thread for DetermineMove() because it has to wait until
     * a move is given to it.
     * @param board The <code>Board</code> of the game being played.
     * @param color the <code>Color</code> of this <code>Player</code> of the <code>Game</code>.
     */
    public ClientPlayer(Board board, Color color){
        super("name", color);
        hasNewMove = false;
        new Thread(() -> {determineMove(board);});
    }

    /**
     * Waits for a new Move from the Client and returns that, this method is called in Game.
     * @param board The current <code>Board</code> of the active game.
     * @return an <code>Integer[]</code> containing the x and y value of the move.
     */
    public synchronized Integer[] determineMove(Board board) {
        while(!hasNewMove){
        }
        //Because the z-value isn't communicated over the network we have to calculate it ourselves
        int z = board.getHeightOfField(move[0], move[1]);
        move[2] = z;
        hasNewMove = false;
        return move;
    }

    /**
     * To be called by the Client and say which move has been made.
     * @param x An <code>int</code> with the x-coordinate of the move.
     * @param y An <code>int</code> with the y-coordinate of the move.
     */
    public synchronized void giveMove(int x, int y){
        move = new Integer[] {x, y, 0};
        hasNewMove = true;
    }
}
