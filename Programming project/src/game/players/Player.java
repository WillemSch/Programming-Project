package game.players;

import game.Board;
import game.Color;
import network.client.Client;

/**
 * Abstract class for keeping a player in the Connect Four game. 
 * 
 * @author Jesper Simon
 * @version $Revision: 1.0 $
 */
public abstract class Player {

    // -- Instance variables -----------------------------------------

    private String name;
    private Color color;
    private Client client;

    // -- Constructors -----------------------------------------------

    /*@
       requires name != null;
       requires color == Color.RED || color== Color.BLUE;
       ensures this.getName() == name;
       ensures this.getColor() == color;
     */
    /**
     * Creates a new Player object.
     * 
     */
    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    // -- Queries ----------------------------------------------------

    /**
     * Returns the name of the player.
     */
    /*@ pure */ public String getName() {
        return name;
    }

    /**
     * Returns the color of the player.
     */
    /*@ pure */ public Color getColor() {
        return color;
    }

    /*@
       requires board != null & !board.isFull();
       ensures board.isField(\result) & board.isEmptyField(\result);

     */
    /**
     * Determines the field for the next move.
     * 
     * @param board
     *            the current game board
     * @return the player's choice
     */
    public abstract int[] determineMove(Board board);

    // -- Commands ---------------------------------------------------

    /*@
       requires board != null & !board.isFull();
     */
    /**
     * Makes a move on the board. <br>
     * 
     * @param board
     *            the current board
     */
    public void makeMove(Board board) {
        int[] move = determineMove(board);
        if(client != null) {
            client.cmdMove(move[0], move[1]);
        }
        board.setField(move, getColor());
    }

    public void setClient(Client client){
        this.client = client;
    }

}

