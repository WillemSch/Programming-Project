package network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author Willem Schooltink
 * @Version 1.0.0
 * Starts the server to which clients can connect.
 */
public class Server {

    private static final String USAGE = "usage: <port>";
    private static List<ClientHandeler> clients;

    /**
     * The main function which initiates the Server and starts the acceptor in a new thread.
     * @param args
     */
    public static void main(String[] args){
        args = new String[] {"4040"};

        if (args.length != 1) {
            System.out.println(USAGE);
            System.exit(0);
        }

        int port = Integer.valueOf(args[0]);
        clients = new ArrayList<>();
        Thread acceptor = new Thread(() -> { acceptClient(port);});
        acceptor.start();
    }

    /**
     * Waits for requests of clients connecting, accepts them and creates a new <code>ClientHandeler</code> for the client
     * @param port an <code>int</code> which represents the port being used.
     */
    private static void acceptClient(int port){
        try {
            ServerSocket server = new ServerSocket(port);
            while (true) {
                Socket clientSocket = server.accept();
                ClientHandeler newClient = new ClientHandeler(clientSocket);
                newClient.start();
                clients.add(newClient);

                System.out.println("New client added.");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Sends a given string to all connected clients.
     * @param message A <code>String</code> with the message which is to be send.
     */
    private static void broadCast(String message){
        for (ClientHandeler c : clients){
            c.send(message);
        }
    }
}
