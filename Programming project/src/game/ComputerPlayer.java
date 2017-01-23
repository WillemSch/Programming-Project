package game;

/**
 * Created by willem on 20-12-16.
 */
public class ComputerPlayer extends Player {
    private Strategy strategy;

    //@ requires mark != null;
    //@ requires strategy != null;
    //@ ensures getStrategy() instanceOf NaiveStrategy;
    public ComputerPlayer(Mark mark, Strategy strategy) {
        super("Computer Player", mark);
        this.strategy = strategy;
    }

    //@ requires mark != null;
    //@ ensures getStrategy() instanceOf NaiveStrategy;
    public ComputerPlayer(Mark mark) {
        super("Computer Player", mark);
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
        Integer[] choice = strategy.determineMove(board, super.getMark());
        String prompt = "> " + getName() + " (" + getMark().toString() + ")"
                + ", chooses " + choice + ".";
        System.out.println(prompt);
        return choice;
    }
}