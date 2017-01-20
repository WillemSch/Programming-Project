package Network;

import Network.Interfaces.Connect4Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by willem on 18-1-17.
 */
public class Client extends Thread implements Connect4Client{

    private Socket socket;
    private String name;
    private int id;
    private PrintWriter writer;
    private BufferedReader reader;
    private boolean ai;
    private static final String USAGE = "Arguments: <name> <port>";

    public Client(Socket socket, String name, int id, boolean ai){
        this.socket = socket;
        this.name = name;
        this.id = id;
        this.ai = ai;

        try {
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error: communicating to server;");
        }
    }

    public static void main(String[] args){
        args = new String[] {"name", "4040"};

        if(args.length != 2){
            System.out.println(USAGE);
        } else {
            Socket server;

            try{
                server = new Socket("localhost", 4040);
                Client client = new Client(server,"randy",1,false);
                System.out.println("Sleep");
                try{
                    sleep(1000);
                } catch (InterruptedException e){
                    e.printStackTrace();
                }
                System.out.println("Awake");

                client.start();
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void run(){
        try {
            String line;
            cmdHello("me", 0, false);
            cmdMove(1,2);
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void cmdHello(String username, int clientCapabilities, boolean isAI) {
        String command = "HELLO " + username + " " + isAI + " " + clientCapabilities;
        writer.println(command);
        writer.flush();
    }

    @Override
    public void cmdMove(int x, int y) {
        String command = "MOVE " + x + " " + y;
        writer.println(command);
        writer.flush();
    }
}
