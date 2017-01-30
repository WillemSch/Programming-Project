package game;

import org.junit.jupiter.api.Test;

/**
 * Created by willem on 30-1-17.
 */
public class BoardTest {
    private Board board;
    private Color color1;
    private Color color2;

    @Test
    public void testSetField(){
        board = new Board(4, 4, 4);
        color1 = Color.BLUE;
        color2 = Color.RED;

        Integer[] coordinates = {0,0,0};
        board.setField(coordinates, color1);

        assert(board.getField(coordinates).equals(color1));
    }
}
