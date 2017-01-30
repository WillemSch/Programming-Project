package network;

import game.Board;
import game.Color;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Willem Schooltink
 * @version 1.0.0
 * The controller of a game on the server.
 */
public class GameServer extends Thread{
    private List<ClientHandeler> players;
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
        this.players = new ArrayList<>();
        for (int i =0; i < players.size(); i++) {
            ClientHandeler c = players.get(i);
            this.players.add(c);

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
        for (int i = 0; i < players.size(); i++){
            int otherIndex = (i + 1) % players.size();
            System.out.println(i);
            players.get(i).cmdGame(players.get(otherIndex).getName(),
                    players.get(otherIndex).getClientId(), board.getWidth(), board.getLength(), board.getHeigth(),
                    players.get(turnOfIndex).getClientId(), board.getWinLength());
        }
    }

    /**
     * Sends a message to all players of this game
     * @param message a <code>String</code> that contains the message which is to be send
     */
    private void broadcast (String message){
        for(ClientHandeler c : players){
            c.send(message);
        }
    }

    /**
     * Changes the turnOfIndex into the next index, if it is at the last index it starts again at 0.
     */
    private void nextIndex() {
        turnOfIndex = (turnOfIndex + 1) % players.size();
    }

    /**
     *
     * @param idOfPlayer
     * @param x
     * @param y
     * @param client
     * @return
     */
    public boolean move(int idOfPlayer, int x, int y, ClientHandeler client){
        Color color = COLORS[turnOfIndex];
        if(idOfPlayer == players.get(turnOfIndex).getClientId()){
            board.setField(x, y, color);
            nextIndex();
            System.out.println(x + "-" + y);
            checkWinner();
            int nextPlayerId = players.get(turnOfIndex).getClientId();
            for (ClientHandeler c : players){
                c.cmdMoveSuccess(x, y, idOfPlayer, nextPlayerId);
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
            for (int i = 0; i < players.size(); i++) {
                if (board.isWinner(COLORS[i])) {
                    winner = players.get(turnOfIndex);
                }
            }

            for (ClientHandeler c : players) {
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
        for (ClientHandeler c : players){
            c.cmdPlayerLeft(client.getClientId(), reason);
        }
    }
}
