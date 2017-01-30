package game;

/**
 * @author willem
 * @version 1.0.0
 * Makes a random possible move.
 */
public class NaiveStrategy implements Strategy {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public int[] determineMove(Board board, Color color) {
        int[] coordinates;
        do {
            int x = (int) (Math.random() * board.getWidth());
            int y = (int) (Math.random() * board.getLength());
            int z = board.getHeightOfField(x, y);
            coordinates = new int[]{x,y,z};
        } while (board.isField(coordinates) && board.isEmptyField(coordinates));
        return coordinates;
    }
}
