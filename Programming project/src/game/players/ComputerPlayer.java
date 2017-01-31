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
    public ComputerPlayer(Color color, Strategy strategy) {
        super("Computer Player", color);
        this.strategy = strategy;
        view = new GameTUIView();
    }

    //@ requires color != null;
    //@ ensures getStrategy() instanceOf NaiveStrategy;
    public ComputerPlayer(Color color) {
        super("Computer Player", color);
        this.strategy = new NaiveStrategy();
        view = new GameTUIView();
    }

    public Strategy getStrategy() {
        return strategy;
    }

    //@ requires strategy != null;
    //@ ensures getStrateg.equals(strategy);
    public void setStrategy(Strategy strategy) {
        this.strategy = strategy;
    }

    @Override
    //@ requires board != null;
    //@ ensures \result >= 0;
    public int[] determineMove(Board board) {
        int[] choice = strategy.determineMove(board, super.getColor());
        String prompt = "> " + getName() + " (" + getColor().toString() + ")"
                + ", chooses " + choice + ".";
        view.print(prompt);
        return choice;
    }
}