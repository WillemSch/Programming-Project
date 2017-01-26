package network;

import game.Board;
import game.Mark;

import java.util.List;
import java.util.Map;

/**
 * @Author Willem Schooltink
 * @Version 1.0.0
 * The controller of a game on the server.
 */
public class GameServer {
    private Map<ClientHandeler, Mark> players;
    private Board board;
    private int turnOfIndex;
    //TODO: Fix this shit with the marks
    private static final Mark[] MARKS = {Mark.OO, Mark.XX};

    /**
     * The constructor of the <code>GameServer</code> with a dynamic winlenght and board size. Assigns marks to
     * the players and makes a board of the given size.
     * @param players a <code>List</code> of <code>ClientHandeler</code>s of the players in the game
     * @param boardSize an <code>int[]</code> containing the width, length and height of the board respectively
     * @param winlength an <code>int</code> which represents the lenght of a row needed to win the game.
     */
    public GameServer(List<ClientHandeler> players, int[] boardSize, int winlength){
        for (int i =0; i < players.size(); i++) {
            ClientHandeler c = players.get(i);
            //TODO: MARKS --> COLORS
            this.players.put(c, MARKS[i]);
            //TODO: Fix last parameter
            this.board = new Board(boardSize[0], boardSize[1], boardSize[2], winlength, board.getPlayers());
        }
    }

    /**
     * The constructor of the <code>GameServer</code>. Assigns marks to the players and makes a board.
     * @param players a <code>List</code> of <code>ClientHandeler</code>s of the players in the game
     */
    public GameServer(List<ClientHandeler> players){
        for (int i =0; i < players.size(); i++) {
            ClientHandeler c = players.get(i);
            //TODO: MARKS --> COLORS
            this.players.put(c, MARKS[i]);
            //TODO: Fix last parameter
            this.board = new Board(4,4,4, board.getPlayers());
        }
    }

    /**
     * Makes a move on the board with the given coordinates and the given mark.
     * @param x an <code>int</code> with the x coordinate of the move.
     * @param y an <code>int</code> with the y coordinate of the move.
     * @param player a <code>ClientHandeler</code> of the player making a move.
     */
    public void makeMove(int x, int y, ClientHandeler player){
        Mark mark = players.get(player);
        if (board.setField(x, y, mark)){
            for (ClientHandeler c: players.keySet()){
                //TODO: fix nextID
                c.cmdMoveSuccess(x, y, player.getClientId(), player.getClientId());
                if (board.isWinner(mark)){
                    c.cmdGameEnd(player.getClientId());
                }
            }
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
}
