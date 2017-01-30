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

    /**
     * Tests all getters of the <code>Board</code> class.
     */
    @Test
    public void testQueries(){
        assert (board.getLength() == 4);
        assert (board.getHeigth() == 4);
        assert (board.getWidth() == 4);
        assert (board.getWinLength() == 4);
    }

    /**
     * Tests the isFull() method of the board.
     */
    @Test
    public void testIsFull(){
        assert (!board.isFull());

        for (int x = 0; x < 4; x++){
            for (int y = 0; y < 4; y++){
                for (int z = 0; z < 4; z++){
                    board.setField(x,y, color1);
                }
            }
        }
        assert (board.isFull());
    }

    /**
     * Tests the GameOver() method of the board.
     */
    @Test
    public void testGameOver(){
        assert (!board.gameOver());

        for (int x = 0; x < 4; x++){
            for (int y = 0; y < 4; y++){
                for (int z = 0; z < 4; z++){
                    board.setField(x,y, color1);
                }
            }
        }
        assert (board.gameOver());

        board.reset();

        board.setField(0,0,color1);
        board.setField(0,0,color1);
        board.setField(0,0,color1);
        board.setField(0,0,color1);
        assert (board.gameOver());
    }

    /**
     * Tests the deepCopy() method in <code>Board</code>
     */
    @Test
    public void testDeepCopy(){
        board.setField(0,0,color1);
        Board copy = board.deepCopy();

        assert (copy.getField(new int[]{0,0,0}).equals(color1));
        copy.setField(0,1, color1);
        assert (copy.getField(new int[]{0,1,0}).equals(color1));
        assert (!board.getField(new int[]{0,1,0}).equals(color1));
    }
}
