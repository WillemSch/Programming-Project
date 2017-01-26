package network;

import game.Board;
import game.Mark;

import java.util.List;

/**
 * Created by willem on 26-1-17.
 */
public class GameServer {
    private List<ClientHandeler> players;
    private Board board;

    public GameServer(List<ClientHandeler> players, int[] boardSize, int winlength){

    }

    public GameServer(List<ClientHandeler> players){
        this.players = players;
    }

    public void makeMove(int x, int y, Mark mark){
        if (board.setField(x, y, mark)){

        }
    }
}
