package game;

/**
 * Created by willem on 23-1-17.
 */
public class ComputerPlayer implements Player {
    public ComputerPlayer (String name, Board board, String color, Strategy strategy){

    }

    @Override
    public int[] determineMove(){
        return new int[0];
    }
}
