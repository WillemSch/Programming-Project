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

    //TODO: Fix this or Remove this!!!!
    /**
     * The constructor of the <code>GameServer</code> with a dynamic winlenght and board size. Assigns marks to
     * the players and makes a board of the given size.
     * @param players a <code>List</code> of <code>ClientHandeler</code>s of the players in the game
     * @param boardSize an <code>int[]</code> containing the width, length and height of the board respectively
     * @param winlength an <code>int</code> which represents the lenght of a row needed to win the game.
     */
    public GameServer(List<ClientHandeler> players, int[] boardSize, int winlength, Server server){
        playerLeft = false;
        this.server = server;
        this.players = new LinkedHashMap<ClientHandeler, Color>();
        for (int i =0; i < players.size(); i++) {
            ClientHandeler c = players.get(i);
            this.players.put(c, COLORS[i]);
        }
        this.turnOfIndex = 0;
        //TODO: Fix last parameter
        this.board = new Board(boardSize[0], boardSize[1], boardSize[2], winlength, board.getPlayers());
    }

    /**
     * The constructor of the <code>GameServer</code>. Assigns marks to the players and makes a board.
     * @param players a <code>List</code> of <code>ClientHandeler</code>s of the players in the game
     */
    public GameServer(List<ClientHandeler> players, Server server){
        this.server = server;
        this.players = new LinkedHashMap<ClientHandeler, Color>();
        for (int i =0; i < players.size(); i++) {
            ClientHandeler c = players.get(i);
            this.players.put(c, COLORS[i]);
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
            System.out.println(board.toString());
        }

        //TODO: Check if move is valid.
        ClientHandeler player = new ArrayList<>(players.keySet()).get(turnOfIndex);
        while (!board.gameOver() && !playerLeft) {
            synchronized (player) {
                Color color = players.get(player);
                player.isTurnOfThisClient(true);
                int[] coordinates = player.makeMove();
                if (/*isValid*/ true) {
                    player.isTurnOfThisClient(false);
                    board.setField(coordinates[0], coordinates[1], color);
                    nextIndex();
                    player = new ArrayList<>(players.keySet()).get(turnOfIndex);
                } else {
                    //report illegal
                }
            }
        }

        //-1 means draw
        int winnerID = -1;

        //Checks for a winner and if one is found winnerID get his/her/its id as value;
        for (ClientHandeler c: players.keySet()){
            Color color = players.get(c);
            if (board.isWinner(color)){
                winnerID = c.getClientId();
            }
        }

        for (ClientHandeler c: players.keySet()){
            c.cmdGameEnd(winnerID);
        }

        server.removeGame(this);
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
}
