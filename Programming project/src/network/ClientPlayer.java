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

    public ClientPlayer(Board board, Color color){
        //TODO: make constructor
        super("name", Color.BLUE);
        new Thread(() -> {determineMove(board);});
    }

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
