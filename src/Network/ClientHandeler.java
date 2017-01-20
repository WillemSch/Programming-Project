package Network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by willem on 18-1-17.
 */
public class ClientHandeler extends Thread{

    private static int nextId = 0;
    private int id;
    private String name;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;

    //------------------POSSIBLE-COMMANDS-----------------------
    private static final String HELP = "HELP";
    private static final String HELLO = "HELLO";
    private static final String MOVE = "MOVE";
    private static final String WELCOME = "WELCOME";
    private static final String GAME = "GAME";
    private static final String MOVESUCCESS = "MOVESUCCESS";
    private static final String GAMEEND = "GAMEEND";
    private static final String REPORTILLEGAL = "REPORTILLEGAL";
    private static final String PLAYERLEFT = "PLAYERLEFT";

    public ClientHandeler (Socket socket){
        nextId++;
        this.id = nextId;
        this.socket = socket;

        try{
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void send(String message){
        writer.write(message);
        writer.flush();
    }

    public void run(){
        handleInput();
    }

    public void Send(String message){
        writer.write(message);
        writer.flush();
    }

    private void handleInput(){
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(name + " " + line);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
