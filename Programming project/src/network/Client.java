package network;

import game.*;
import network.Interfaces.Connect4Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @Author Willem Schooltink
 * @Version 1.0.0
 * Starts the the client for the game. Handles all communication between server and player;
 */
public class Client extends Thread implements Connect4Client {

    //All possible capabilities have a value of 2 to the power n. Example: local scoreboard = 1, dynamic field size = 2,...
    private static final int CAPABILITIES = 0;
    private static final String USAGE = "Arguments: <name> <address> <port>";
    private String name;
    private boolean ai;
    private int id;
    private int opponentId;
    private int turnOfId;
    private long thinkingTime;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private Player player;
    private Color opponentColor;
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
        this.player = player;
        this.opponentColor = Color.RED;
        this.player.setClient(this);

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
                        player = new ComputerPlayer(Color.BLUE, new SmartStrategy());
                        client = new Client(server, "Smart computer", 1, true, player);
                        break;
                    case "-N":
                        player = new ComputerPlayer(Color.BLUE, new NaiveStrategy());
                        client = new Client(server, "Naive computer", 1, true, player);
                        break;
                    default:
                        player = new HumanPlayer(args[1], Color.BLUE);
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
            cmdHello("me", false, 0);

            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(" ");
                if (words.length >= 2) {
                    switch (words[0]) {
                        case "WELCOME":
                            welcomeHandler(line);
                            break;
                        case "GAME":
                            gameHandler(line);
                            break;
                        case "GAME_END":
                            gameEndHandler(line);
                            break;
                        case "MOVE_SUCCESS":
                            moveSuccessHandler(line);
                            break;
                        case "LEFT":
                            playerLeftHandler(line);
                            break;
                        case "ILLEGAL":
                            reportIllegalHandler(line);
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
     * Translates and handles the "WELCOME" command.
     * @param line a <code>String</code> with the entire command including its arguments
     */
    private void welcomeHandler(String line){
        String[] words = line.split(" ");
        if (words.length == 4 && words[1].matches("\\d+") && words[2].matches("\\d+")
                && words[3].matches("\\d+")) {
            id = Integer.parseInt(words[1]);
            thinkingTime = Long.parseLong(words[2]);
            int serverCapabilities = Integer.parseInt(words[3]);
            cmdRequest();
        } else {
            System.out.println("Something went terribly wrong...");
        }
    }

    /**
     * Translates and handles the "GAME" command.
     * @param line a <code>String</code> with the entire command including its arguments
     */
    private void gameHandler(String line){
        String[] words = line.split(" ");
        if ((words.length == 8) && words[2].matches("\\d+") && words[3].matches("\\d+")
                && words[4].matches("\\d+") && words[5].matches("\\d+")
                && words[6].matches("\\d+") && words[7].matches("\\d+")){
            opponentId = Integer.parseInt(words[2]);
            int[] fieldsize = new int[3];
            fieldsize[0] = Integer.parseInt(words[3]);
            fieldsize[1] = Integer.parseInt(words[4]);
            fieldsize[2] = Integer.parseInt(words[5]);
            int turnOfId = Integer.parseInt(words[6]);
            int winLenght = Integer.parseInt(words[7]);
            board = new Board(fieldsize[0], fieldsize[1], fieldsize[2]);
            System.out.println("Game started:");
            System.out.println(board.toString());
            if(turnOfId == id) {
                player.makeMove(board);
            }
            System.out.println("Wait for other player...");
        } else {
            System.out.println("Something went terribly wrong...");
        }
    }

    /**
     * Translates and handles the "GAME_END" command.
     * @param line a <code>String</code> with the entire command including its arguments
     */
    private void gameEndHandler(String line){
        String[] words = line.split(" ");
        if (words.length == 2 && words[1].equals(String.valueOf(id))){
            System.out.println("You won");
        } else if (words.length == 2 && words[1].equals(String.valueOf(opponentId))){
            System.out.println("Your opponent won");
            //"-1" is send if the game ends in a draw
        } else if (words.length == 2 && words[1].equals("-1")){
            System.out.println("Draw");
        } else {
            System.out.println("Something went terribly wrong...");
        }
        board.reset();
        cmdRequest();
    }

    /**
     * Translates and handles the "MOVE_SUCCESS" command.
     * @param line a <code>String</code> with the entire command including its arguments
     */
    private void moveSuccessHandler(String line){
        String[] words = line.split(" ");
        if ((words.length == 5) && words[1].matches("\\d+") && words[2].matches("\\d+")
                && words[3].matches("\\d+") && words[4].matches("\\d+")){
            int xCoordinate = Integer.parseInt(words[1]);
            int yCoordinate = Integer.parseInt(words[2]);
            int nextPlayerId = Integer.parseInt(words[4]);
            if (nextPlayerId == id){
                board.setField(xCoordinate, yCoordinate, opponentColor);
                System.out.println("Opponent made move: " + xCoordinate + " - " + yCoordinate);
                System.out.println(board.toString());
                player.makeMove(board);
                System.out.println("Wait for other player...");
            }
        } else {
            System.out.println("Something went terribly wrong");
        }
    }

    /**
     * Translates and handles the "MOVE_SUCCESS" command.
     * @param line a <code>String</code> with the entire command including its arguments
     */
    private void playerLeftHandler(String line){
        String[] words = line.split(" ");
        if (words.length >= 3 && words[1].matches("\\d+")){
            String reason = "";
            for (int i = 2; i < words.length - 1; i++){
                reason += " ";
                reason += words[i];
            }
            System.out.println("Player " + words[1] + " left, Reason:" + reason);
            board.reset();
        } else {
            System.out.println("Something went terribly wrong...");
        }
    }

    /**
     * Translates and handles the "REPORT_ILLEGAL" command. (This should never happen!)
     * @param line a <code>String</code> with the entire command including its arguments
     */
    private void reportIllegalHandler(String line){
        System.out.println("A command has been flagged Illegal by the server:");
        System.out.println(line);
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
    public void cmdHello(String username, boolean isAI, int clientCapabilities) {
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

    /**
     * Sends a Request command to the server saying it's ready for a game.
     */
    @Override
    public void cmdRequest(){
        String command = "REQUEST";
        writer.println(command);
        writer.flush();
    }
}