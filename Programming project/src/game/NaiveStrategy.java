package game;

/**
 * Created by willem on 23-1-17.
 */
public class NaiveStrategy implements Strategy {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Integer[] determineMove(Board board, Mark mark) {
    	Integer[] coordinates = {0,0,0};
        return coordinates;
    }
}
