package network;

import game.Board;
import game.Mark;

import java.util.List;
import java.util.Map;

/**
 * Created by willem on 26-1-17.
 */
public class GameServer {
    private Map<ClientHandeler, Mark> players;
    private Board board;
    private int turnOfIndex;

    public GameServer(List<ClientHandeler> players, int[] boardSize, int winlength){

    }

    public GameServer(List<ClientHandeler> players){
        for (ClientHandeler c : players) {
            //TODO: Give each player a mark
            this.players.put(c, Mark.OO);
        }
    }

    public void makeMove(int x, int y, ClientHandeler player){
        Mark mark = players.get(player);
        if (board.setField(x, y, mark)){
            for (ClientHandeler c: players.keySet()){
                c.cmdMoveSuccess(x, y, player.getClientId(), player.getClientId());
                if (board.isWinner(mark)){
                    c.cmdGameEnd(player.getClientId());
                }
            }
        }
    }

    private void broadcast (String message){
        for(ClientHandeler c : players.keySet()){
            c.send(message);
        }
    }
}
