package network;

import game.*;
import network.Interfaces.Connect4Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

//TODO: DESIGN PROJECT

/**
 * @Author Willem Schooltink
 * @Version 1.0.0
 * Starts the the client for the game. Handles all communication between server and player;
 */
public class Client extends Thread implements Connect4Client {

    private static final int CAPABILITIES = 0;
    private static final String USAGE = "Arguments: <name> <address> <port>";
    private String name;
    private boolean ai;
    private int id;
    private int opponentId;
    private int serverCapabilities;
    private int turnOfId;
    private int winLenght;
    private int[] fieldsize; //[0] = x-axis, [1] = y-axis, [2] = z-axis
    private long thinkingTime;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private Player player;
    private Board board;

    /**
     * The constructor of the client.
     * @param socket The <code>Socket</code> through which the communication is send and recieved.
     * @param name A <code>String</code> with the username of the player.
     * @param id An <code>int</code> with the value of the id of the player. This id is unique on the server.
     * @param ai A <code>Boolean</code> which says whether the player is an ai or not.
     * @param player A <code>Player</code> belonging to the player of this client.
     */
    public Client(Socket socket, String name, int id, boolean ai, Player player) {
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

    /**
     * The void which is started when running the application, it checks for the arguments given and if thoes are
     * correct connects to a server. After connecting it instantiates a <code>Player</code>; <code>HumanPlayer</code>
     * if a normal name is given, <code>ComputerPlayer</code> with <code>SmartStrategy</code> if the name \"-S\"
     * is given or <code>ComputerPlayer</code> with <code>NaiveStrategy</code> if the name \"-N\" is given.
     * @param args A <code>String[]</code> which is used to determine the type of player and server. Arguments: <name> <address> <port>
     */
    public static void main(String[] args) {
        args = new String[]{"name", "localhost", "4040"};

        if (args.length != 3) {
            System.out.println(USAGE);
        } else {
            Socket server;

            try {
                server = new Socket(args[1], Integer.parseInt(args[2]));
                Client client;
                Player player;
                switch (args[0]){
                    case "-S":
                        //TODO: Fix marks
                        player = new ComputerPlayer(Mark.OO, new SmartStrategy());
                        client = new Client(server, "Smart computer", 1, true, player);
                        break;
                    case "-N":
                        //TODO: Fix marks
                        player = new ComputerPlayer(Mark.OO, new NaiveStrategy());
                        client = new Client(server, "Naive computer", 1, true, player);
                        break;
                    default:
                        //TODO: Fix marks
                        player = new HumanPlayer(args[1], Mark.OO);
                        client = new Client(server, args[0], 1, false, player);
                        break;
                }
                client.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Starts the operations the Client must do: Sends a HELLO command to the server to be added to the player list.
     * After the HELLO it starts listening to the socket for input from the server. This input is evaluated and
     * the client takes action appropriate to the command received.
     */
    public void run() {
        try {
            String line;
            cmdHello("me", 0, false);
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(" ");
                if (words.length >= 2) {
                    switch (words[0]) {
                        case "WELCOME":
                            if (words.length == 4 && words[1].matches("\\d+") && words[2].matches("\\d+")
                                    && words[3].matches("\\d+")) {
                                id = Integer.parseInt(words[1]);
                                thinkingTime = Long.parseLong(words[2]);
                                serverCapabilities = Integer.parseInt(words[3]);
                            } else {
                                System.out.println("Something went terribly wrong...");
                            }
                            break;
                        case "GAME":
                            if ((words.length == 8) && words[2].matches("\\d+") && words[3].matches("\\d+")
                                    && words[4].matches("\\d+") && words[5].matches("\\d+")
                                    && words[6].matches("\\d+") && words[7].matches("\\d+")){
                                opponentId = Integer.parseInt(words[2]);
                                fieldsize[0] = Integer.parseInt(words[3]);
                                fieldsize[1] = Integer.parseInt(words[4]);
                                fieldsize[2] = Integer.parseInt(words[5]);
                                turnOfId = Integer.parseInt(words[6]);
                                winLenght = Integer.parseInt(words[7]);
                            } else {
                                System.out.println("Something went terribly wrong...");
                            }
                            break;
                        case "GAMEEND":
                            if (words.length == 2 && words[1].equals(String.valueOf(id))){
                                System.out.println("You won!");
                            } else if (words.length == 2 && words[1].equals(String.valueOf(opponentId))){
                                System.out.println("You lost...");
                            } else {
                                System.out.println("Something went terribly wrong...");
                            }
                            break;
                        case "MOVESUCCESS":
                            //TODO: check id, if from other player change Board and start determineMove()
                            break;
                        case "PLAYERLEFT":
                            //TODO: if player is yourself or opponent quit game state
                            break;
                        case "REPORTILLEGAL":
                            //TODO: if its the last move command of this player restart determinemove, and notify player
                            break;
                        default:
                            System.out.println(line);
                            break;
                    }
                } else {
                    System.out.println(line);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Sends a HELLO command to the server to be added to the player list.
     * @param username the desired username to be displayed on the scoreboard.
     * @param clientCapabilities an integer acting as a boolean flag vector. Every bit corresponds to a capability.
     * Setting a certain bit to <code>0</code> means the client is reporting that it does not have an implementation
     * for the associated capability.
     * @param isAI <code>true</code> indicates that the user that is registering is a computer player. <code>false</code> indicates a human player.
     */
    @Override
    public void cmdHello(String username, int clientCapabilities, boolean isAI) {
        String command = "HELLO " + username + " " + isAI + " " + clientCapabilities;
        writer.println(command);
        writer.flush();
    }

    /**
     * Sends a MOVE command to the server saying it made a move on the given coordinates.
     * @param x the x coordinate of the move
     * @param y the y coordinate of the move
     */
    @Override
    public void cmdMove(int x, int y) {
        String command = "MOVE " + x + " " + y;
        writer.println(command);
        writer.flush();
    }
}