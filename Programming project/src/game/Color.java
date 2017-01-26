package game;

/**
 * Represents a mark in the Connect Four game. There three possible values:
 * Color.RED, Color.BLUE and Color.NONE.
 * 
 * @author Jesper SIMON
 * @version $Revision: 1.0 $
 */
public enum Color {

    NONE, RED, BLUE;

    /*@
       ensures this == Color.RED ==> \result == Color.BLUE;
       ensures this == Color.BLUE ==> \result == Color.RED;
       ensures this == Color.NONE ==> \result == Color.NONE;
     */
    /**
     * Returns the other mark.
     * 
     * @return the other mark is this mark is not NONE or NONE
     */
    public Color other() {
        if (this == RED) {
            return BLUE;
        } else if (this == BLUE) {
            return RED;
        } else {
            return NONE;
        }
    }
}
