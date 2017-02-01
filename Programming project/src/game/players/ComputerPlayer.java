package game.players;

import game.Board;
import game.Color;
import game.players.strategies.NaiveStrategy;
import game.players.strategies.Strategy;
import game.ui.GameTUIView;

/**
 * @author willem
 * @version 1.0.0
 * A computer player for a connect-4 game.
 */
public class ComputerPlayer extends Player {
    private Strategy strategy;
    private GameTUIView view;

    //@ requires color != null;
    //@ requires strategy != null;
    //@ ensures getStrategy() instanceOf NaiveStrategy;
    /**
     * Constructs a computerplayer with a given strategy.
     * @param color the Color of this player.
     * @param strategy the strategy of this player.
     */
    public ComputerPlayer(Color color, Strategy strategy) {
        super("Computer Player", color);
        this.strategy = strategy;
        view = new GameTUIView();
    }

    //@ requires color != null;
    //@ ensures getStrategy() instanceOf NaiveStrategy;
    /**
     * Constructs a Computer player with a NaiveStrategy.
     * @param color the Color of the this player.
     */
    public ComputerPlayer(Color color) {
        super("Computer Player", color);
        this.strategy = new NaiveStrategy();
        view = new GameTUIView();
    }

    /**
     * Returns the Strategy of this player.
     * @return a <code>Strategy</code> of this player.
     */
    public Strategy getStrategy() {
        return strategy;
    }

    //@ requires strategy != null;
    //@ ensures getStrateg.equals(strategy);
    /**
     * Sets the Strategy of this player.
     * @param strategy the Strategy that will be given to this player.
     */
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    //@ requires board != null;
    //@ ensures \result >= 0;
    /**
     * Determines a move for this player on a given board.
     * @return an <code>int[]</code> with the coordinates of the move.
     */
    public int[] determineMove(Board board) {
        int[] choice = strategy.determineMove(board, super.getColor());
        String prompt = "> " + getName() + " (" + getColor().toString() + ")"
                + ", chooses " + choice + ".";
        view.print(prompt);
        return choice;
    }
}