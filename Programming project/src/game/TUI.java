package game;

/**
 * @author Jesper & Willem
 * @version 1.0.0
 * Prints given string to the console, creating a tui.
 */
public class TUI {
    /**
     * Prints a given <code>String</code> to the console.
     * @param message
     */
    public void print(String message){
       System.out.println(message);
    }

    public void printBoard (Board board){
        print(board.toString());
    }
}
