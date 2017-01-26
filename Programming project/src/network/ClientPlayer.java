package network;

import game.Board;
import game.Color;
import game.Player;

/**
 * Created by willem on 26-1-17.
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
    public Integer[] determineMove(Board board) {
        while(!hasNewMove){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return move;
    }

    public void giveMove(int x, int y, int z){
        move = new Integer[] {x, y, z};
        hasNewMove = true;
        notifyAll();
    }
}
