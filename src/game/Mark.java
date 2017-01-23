package game;

/**
 * Represents a mark in the Connect Four game. There three possible values:
 * Mark.XX, Mark.OO and Mark.EMPTY.
 * 
 * @author Jesper SIMON
 * @version $Revision: 1.0 $
 */
public enum Mark {
    
    EMPTY, XX, OO;

    /*@
       ensures this == Mark.XX ==> \result == Mark.OO;
       ensures this == Mark.OO ==> \result == Mark.XX;
       ensures this == Mark.EMPTY ==> \result == Mark.EMPTY;
     */
    /**
     * Returns the other mark.
     * 
     * @return the other mark is this mark is not EMPTY or EMPTY
     */
    public Mark other() {
        if (this == XX) {
            return OO;
        } else if (this == OO) {
            return XX;
        } else {
            return EMPTY;
        }
    }
}
