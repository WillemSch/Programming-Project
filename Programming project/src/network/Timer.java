package network;

/**
 * Created by willem on 29-1-17.
 */
public class Timer extends Thread {

    private long waitingTime;
    private ClientHandeler client;

    public Timer(long waitingTime, ClientHandeler client){
        this.waitingTime = waitingTime;
        this.client = client;
    }

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
