package network;

import game.Board;
import game.Color;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author Willem Schooltink
 * @Version 1.0.0
 * The controller of a game on the server.
 */
public class GameServer extends Thread{
    private Map<ClientHandeler, Color> players;
    private Board board;
    private Server server;
    private boolean playerLeft;
    private int turnOfIndex;
    private static final Color[] COLORS = {Color.BLUE, Color.RED};

    /**
     * The constructor of the <code>GameServer</code>. Assigns marks to the players and makes a board.
     * @param players a <code>List</code> of <code>ClientHandeler</code>s of the players in the game
     */
    public GameServer(List<ClientHandeler> players, Server server){
        this.server = server;
        this.players = new LinkedHashMap<>();
        for (int i =0; i < players.size(); i++) {
            ClientHandeler c = players.get(i);
            this.players.put(c, COLORS[i]);
            c.setGameServer(this);
        }
        turnOfIndex = 0;
        this.board = new Board(4,4,4);
    }

    /**
     * Starts a game
     */
    public void run(){
        play();
    }

    /**
     * Starts and executes the whole game, in the end it sends the commands to let the clients know.
     */
    private void play(){
        List<ClientHandeler> listOfPlayers = new ArrayList<>(players.keySet());
        for (int i = 0; i < listOfPlayers.size(); i++){
            int otherIndex = (i + 1) % players.size();
            System.out.println(i);
            listOfPlayers.get(i).cmdGame(listOfPlayers.get(otherIndex).getName(),
                    listOfPlayers.get(otherIndex).getClientId(), board.getWidth(), board.getLength(), board.getHeigth(),
                    listOfPlayers.get(0).getClientId(), board.getWinLength());
        }
    }

    /**
     * Sends a message to all players of this game
     * @param message a <code>String</code> that contains the message which is to be send
     */
    private void broadcast (String message){
        for(ClientHandeler c : players.keySet()){
            c.send(message);
        }
    }

    /**
     * Changes the turnOfIndex into the next index, if it is at the last index it starts again at 0.
     */
    private void nextIndex() {
        turnOfIndex = (turnOfIndex + 1) % players.size();
    }

    public boolean move(int idOfPlayer, int x, int y, ClientHandeler client){
        List<ClientHandeler> listOfPlayers = new ArrayList<>(players.keySet());
        Color color = players.get(client);
        if(idOfPlayer == listOfPlayers.get(turnOfIndex).getClientId()){
            board.setField(x, y, color);
            nextIndex();
            System.out.println(x + "-" + y);
            checkWinner();
            for (ClientHandeler c : players.keySet()){
                c.cmdMoveSuccess(x, y, idOfPlayer, turnOfIndex);
            }
            return true;
        } else {
            return false;
        }
    }

    /**
     * A void that checks if there is a winner, and if so sends a GAMEEND message to the players.
     */
    private void checkWinner(){
        if(board.hasWinner()) {
            ClientHandeler winner = null;
            for (ClientHandeler c : players.keySet()) {
                if (board.isWinner(players.get(c))) {
                    winner = c;
                }
            }

            for (ClientHandeler c : players.keySet()) {
                //winner will always be initialized here so NPE will not happen
                c.cmdGameEnd(winner.getClientId());
            }
        }
    }

    /**
     * Can be called by a <code>ClientHandeler</code> to say the client left the game.
     * @param client A <code>ClientHandeler</code> of the client that left.
     * @param reason A <code>String</code> which contains the reason the player left.
     */
    public void leave(ClientHandeler client, String reason){
        for (ClientHandeler c : players.keySet()){
            c.cmdPlayerLeft(client.getClientId(), reason);
            System.out.println("Did this");
        }

        System.out.println("Did this");
    }
}
