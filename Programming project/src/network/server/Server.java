package network.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Willem Schooltink
 * @version 1.0.0
 * Starts the server to which clients can connect.
 */
public class Server extends Thread{

    private static final String USAGE = "usage: <port>";
    private List<ClientHandeler> clients;
    private List<ClientHandeler> waitingClients;
    private List<GameServer> games;
    private int port;

    /**
     * The Constructor
     * @param port The port number on which the server will run.
     */
    public Server (int port){
        clients = new ArrayList<>();
        waitingClients = new ArrayList<>();
        games = new ArrayList<>();
        this.port = port;
    }

    public void run(){
        acceptClient(port);
    }

    /**
     * The main function which initiates the server with the requested port.
     * @param args contains 1 value: the port on which the server will run.
     */
    public static void main(String[] args){
        /*The line below makes it possible to test without giving any input in main*/
        args = new String[] {"4040"};
        if (args.length != 1) {
            System.out.println(USAGE);
            System.exit(0);
        }
        int port = Integer.valueOf(args[0]);
        Server server = new Server(port);
        server.run();
    }

    /**
     * Waits for requests of clients connecting, accepts them and creates a new <code>ClientHandeler</code> for the client
     * @param port an <code>int</code> which represents the port being used.
     */
    private void acceptClient(int port){
        try {
            ServerSocket server = new ServerSocket(port);
            while (true) {
                Socket clientSocket = server.accept();
                ClientHandeler newClient = new ClientHandeler(clientSocket, this);
                newClient.start();
                clients.add(newClient);

                System.out.println("New client added.");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Adds a client to the waiting list.
     * @param client a <code>ClientHandeler</code> that is added to the waiting list.
     */
    public void addToWaiting (ClientHandeler client){
        waitingClients.add(client);
        startMatch();
    }

    /**
     * Removes a client from the waiting list.
     * @param client a <code>ClientHandeler</code> that should be removed from the waiting list.
     */
    public void removeFromWaiting (ClientHandeler client) {
        if(clients.contains(client)){
            clients.remove(client);
        }
    }

    /**
     * Checks whether there are more than 1 players waiting for a game and makes a game for them
     */
    private void startMatch(){
        if(waitingClients.size() > 1) {
            GameServer newGame = new GameServer(waitingClients.subList(0,2), this);
            games.add(newGame);
            waitingClients.remove(1);
            waitingClients.remove(0);
            System.out.println(newGame);
            newGame.start();
        }
    }

    /**
     * Sends a given string to all connected clients.
     * @param message A <code>String</code> with the message which is to be send.
     */
    public void broadCast(String message){
        for (ClientHandeler c : clients){
            c.send(message);
        }
    }

    /**
     * Removes a <code>GameServer</code> from games
     * @param game The <code>GameServer</code> that is to be removed.
     */
    public void removeGame(GameServer game){
        games.remove(game);
    }

    /**
     * Gets the list of all active clientHandelers.
     * @return a <code>List<ClientHandeler></code> with all active clienthandelers.
     */
    public List<ClientHandeler> getClients(){
        return clients;
    }

    /**
     * Gets the list of all active games.
     * @return a <code>List<GameServer></code> with all active GameServers.
     */
    public List<GameServer> getGames(){
        return games;
    }
}
