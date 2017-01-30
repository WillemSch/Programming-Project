package game;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
/**
 * Created by willem on 30-1-17.
 */
public class BoardTest {
    private Board board;
    private Color color1;
    private Color color2;

    @Before
    public void setUp(){
        board = new Board(4, 4, 4);
        color1 = Color.BLUE;
        color2 = Color.RED;
    }

    @Test
    public void testSetField(){
        int[] coordinates = {0,0,0};
        assertTrue(board.setField(coordinates, color1));
        assertEquals(board.getField(coordinates), color1);

        int[] coordinates2 = {0,1,0};
        assertTrue(board.setField(0,1, color1));
        assertEquals(board.getField(coordinates2), color1);
    }

    @Test
    public void testGetHeightOfField(){
        int x = 0;
        int y = 0;

        assertEquals(board.getHeightOfField(x,y), 0);
        assertTrue(board.setField(0,0,color1));
        assertEquals(board.getHeightOfField(x,y), 1);
    }

    @Test
    public void testIsField(){
        int[] isRealField = {0,0,0};
        int[] isNotAField = {0,0,-1};
        int[] isNotAField2 = {0,0,5};

        assertTrue(board.isField(isRealField));
        assertFalse(board.isField(isNotAField));
        assertFalse(board.isField(isNotAField2));
    }

    @Test
    public void testIsEmptyField(){
        int[] coordinates = {0,0,0};

        assertTrue(board.isEmptyField(coordinates));

        assertTrue(board.setField(coordinates, color1));
        assertFalse(board.isEmptyField(coordinates));
    }

    @Test
    public void testBoardReset(){
        int[] coordinates = {0,0,0};
        assertTrue(board.setField(coordinates, color1));

        board.reset();
        assertTrue(board.isEmptyField(coordinates));
    }

    @Test
    public void testWinner(){
        int x = 0;
        int y = 0;

        assertFalse(board.hasWinner());

        board.setField(x,y,color1);
        board.setField(x,y,color1);
        board.setField(x,y,color1);
        board.setField(x,y,color1);

        assertTrue(board.hasWinner());
        assertTrue(board.isWinner(color1));
        assertFalse(board.isWinner(color2));
    }
}
