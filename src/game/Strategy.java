package game;

/**
 * Created by willem on 20-12-16.
 */
public interface Strategy {
    public String getName();

    public int determineMove(Board board, Mark mark);
}