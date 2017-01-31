package game.tests;

import game.Board;
import game.Color;
import game.players.strategies.SmartStrategy;
import game.players.strategies.Strategy;
import org.junit.Before;
import org.junit.Test;

/**
 * @author Willem
 * @version 1.0.0
 * Tests the SmartStrategy to see if it makes logical moves.
 */
public class SmartStrategyTest {

    private Board board;
    private Strategy strategy;
    private Color color;

    /**
     * Sets up all class variables
     */
    @Before
    public void setUp(){
        board = new Board(4,4,4);
        strategy = new SmartStrategy();
        color = Color.BLUE;
    }

    /**
     * Tests the smartStrategy in 3 cases: can make a winning move, can block someone, can make a winning move and
     * can block someone.
     */
    @Test
    public void testSmartMove(){
        //Make winning move
        board.setField(0,0,color);
        board.setField(0,0,color);
        board.setField(0,0,color);
        board.setField(strategy.determineMove(board, color), color);
        assert (board.getField(new int[]{0,0,3}).equals(color));

        //Block someone
        board.reset();
        board.setField(0,0,color.other());
        board.setField(0,0,color.other());
        board.setField(0,0,color.other());
        board.setField(strategy.determineMove(board, color), color);
        assert (board.getField(new int[]{0,0,3}).equals(color));

        //If it can choose between the blocking and winning it should choose winning
        board.reset();
        board.setField(0,0,color);
        board.setField(0,0,color);
        board.setField(0,0,color);
        board.setField(0,1,color.other());
        board.setField(0,1,color.other());
        board.setField(0,1,color.other());
        board.setField(strategy.determineMove(board, color), color);
        assert (board.getField(new int[]{0,0,3}).equals(color));
    }
}
