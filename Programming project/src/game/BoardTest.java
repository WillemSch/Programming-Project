package game;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author willem
 * @version 1.0.0
 * Tests the functionality of the <code>Board</code> class, with 100% coverage.
 */
public class BoardTest {
    private Board board;
    private Color color1;
    private Color color2;

    /**
     * Setup the board and global variables.
     */
    @BeforeEach
    public void setUp(){
        board = new Board(4, 4, 4);
        color1 = Color.BLUE;
        color2 = Color.RED;
    }

    /**
     * Test both setField() methods in <code>Board</code>.
     */
    @Test
    public void testSetField(){
        int[] coordinates = {0,0,0};
        assert (board.setField(coordinates, color1));
        assert (board.getField(coordinates).equals(color1));

        int[] coordinates2 = {0,1,0};
        assert (board.setField(0,1, color1));
        assert (board.getField(coordinates2).equals(color1));
    }

    /**
     * Test the getHeightOfField() method in <code>Board</code>.
     */
    @Test
    public void testGetHeightOfField(){
        int x = 0;
        int y = 0;

        assert (board.getHeightOfField(x,y) == 0);

        board.setField(0,0,color1);
        assert (board.getHeightOfField(x,y) == 1);
    }

    /**
     * Test the isField() method in <code>Board</code>.
     */
    @Test
    public void testIsField(){
        int[] isRealField = {0,0,0};
        int[] isNotAField = {0,0,-1};
        int[] isNotAField2 = {0,0,5};

        assert (board.isField(isRealField));
        assert (!board.isField(isNotAField));
        assert (!board.isField(isNotAField2));
    }

    /**
     * Test the isEmptyField() method in <code>Board</code>.
     */
    @Test
    public void testIsEmptyField(){
        int[] coordinates = {0,0,0};

        assert (board.isEmptyField(coordinates));

        board.setField(coordinates, color1);
        assert (!board.isEmptyField(coordinates));
    }

    /**
     * Test the reset() method in <code>Board</code>.
     */
    @Test
    public void testBoardReset(){
        int[] coordinates = {0,0,0};
        board.setField(coordinates, color1);

        board.reset();
        assert (board.isEmptyField(coordinates));
    }

    /**
     * Test the hasWinner() and isWinner(Color) method in <code>Board</code>.
     */
    @Test
    public void testWinner(){
        int x = 0;
        int y = 0;

        assert (!board.hasWinner());

        board.setField(0,0,color1);
        board.setField(1,1,color1);
        board.setField(2,2,color1);
        board.setField(3,3,color1);

        assert (board.hasWinner());
        assert (board.isWinner(color1));
        assert (!board.isWinner(color2));
    }
}
