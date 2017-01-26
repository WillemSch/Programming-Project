package game;

/**
 * Created by willem on 20-12-16.
 */
public class ComputerPlayer extends Player {
    private Strategy strategy;

    //@ requires color != null;
    //@ requires strategy != null;
    //@ ensures getStrategy() instanceOf NaiveStrategy;
    public ComputerPlayer(Color color, Strategy strategy) {
        super("Computer Player", color);
        this.strategy = strategy;
    }

    //@ requires color != null;
    //@ ensures getStrategy() instanceOf NaiveStrategy;
    public ComputerPlayer(Color color) {
        super("Computer Player", color);
        this.strategy = new NaiveStrategy();
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
    public Integer[] determineMove(Board board) {
        Integer[] choice = strategy.determineMove(board, super.getColor());
        String prompt = "> " + getName() + " (" + getColor().toString() + ")"
                + ", chooses " + choice + ".";
        System.out.println(prompt);
        return choice;
    }
}