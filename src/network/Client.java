package network;

import network.Interfaces.Connect4Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @Author Willem Schooltink
 * @Version 1.0.0
 * Starts the client, establishes a connection with a server and handles communication.
 */
public class Client extends Thread implements Connect4Client {

    private static final int CAPABILITIES = 0;
    private static final String USAGE = "Arguments: <name> <port>";
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

    public Client(Socket socket, String name, int id, boolean ai) {
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

    public static void main(String[] args) {
        args = new String[]{"name", "4040"};

        if (args.length != 2) {
            System.out.println(USAGE);
        } else {
            Socket server;

            try {
                server = new Socket("localhost", 4040);
                Client client = new Client(server, "randy", 1, false);
                System.out.println("Sleep");
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Awake");

                client.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void run() {
        try {
            String line;
            cmdHello("me", 0, false);
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(" ");
                if (words.length < 2) {
                    switch (words[0]) {
                        case "WELCOME":
                            //TODO: get the id and capabilities of server
                            if (words.length == 4 && words[1].contains("//d+") && words[2].contains("//d+")
                                    && words[3].contains("//d+")) {
                                id = Integer.parseInt(words[1]);
                                thinkingTime = Long.parseLong(words[2]);
                                serverCapabilities = Integer.parseInt(words[3]);
                            }
                            break;
                        case "GAME":
                            //TODO: start gamestate of the player
                            if (words.length == 8 && words[2].contains("//d+") && words[3].contains("//d+")
                                    && words[4].contains("//d+") && words[5].contains("//d+")
                                    && words[6].contains("//d+") && words[7].contains("//d+")){
                                opponentId = Integer.parseInt(words[2]);
                                fieldsize[0] = Integer.parseInt(words[3]);
                                fieldsize[1] = Integer.parseInt(words[4]);
                                fieldsize[2] = Integer.parseInt(words[5]);
                                turnOfId = Integer.parseInt(words[6]);
                                winLenght = Integer.parseInt(words[7]);
                            }
                            break;
                        case "GAMEEND":
                            //TODO: end gamestate of the player
                            break;
                        case "MOVESUCCESS":
                            //TODO: check id, if from other player change Board and tart determineMove()
                            break;
                        case "PLAYERLEFT":
                            //TODO: if player is yourself or opponent quit game state
                            break;
                        case "REPORTILLEGAL":
                            //TODO: if its the last move command of this player restart determinemove, and notify player
                            break;
                        default:
                            //TODO: print the line to the players console
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
