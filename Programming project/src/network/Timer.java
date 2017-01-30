package network;

/**
 * @author willem
 * @version 1.0.0
 * A timer to wait thr given time, if the time is up it tells the <code>GameServer</code> the client has left,
 * if a client makes a valid move this thread gets interrupted so it stops.
 */
public class Timer extends Thread {

    private long waitingTime;
    private ClientHandeler client;

    /**
     * Constructor of the timer. Gets the waiting time and <code>ClientHandeler</code> as input.
     * @param waitingTime a <code>long</code> with the ammount of milliseconds untill the player will be considered
     *                    as left.
     * @param client a <code>ClientHandeler</code> of the client which makes the move.
     */
    public Timer(long waitingTime, ClientHandeler client){
        this.waitingTime = waitingTime;
        this.client = client;
    }

    /**
     * The run() method of this Thread, it waits the amount of time given in milliseconds, and after that it kicks
     */
    public void run(){
        synchronized (this) {
            try {
                wait(waitingTime);
                //if the timer is not inerrupted in the time given it tells the client to leave the game.
                client.leave("Client took too long to respond.");
                System.out.println("Left");
            } catch (InterruptedException e) {
            }
        }
    }
}
