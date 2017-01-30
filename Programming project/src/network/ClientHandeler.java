package network;

import network.Interfaces.Connect4Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * @Author Willem Schooltink
 * @Version 1.0.0
 * Is made for every Client on the server, it manages the communication of this client.
 */
public class ClientHandeler extends Thread implements Connect4Server{

    //----------------LOCAL-VARIABLES---------------------------
    private int id;
    private String name;
    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private GameServer game;
    private Server server;
    private boolean turnOfThisClient;
    private boolean hasMove;
    private long thinkingTime;
    private int[] move;
    private Thread timer;

    //---------------STATIC-Variables---------------------------
    private static int nextId = 0;
    private static final long humanThinkingTime = 120000;
    private static final long aiThinkingTime = 2000;

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

    /**
     * The constructor of the ClientHandeler
     * @param socket A <code>Socket</code> to communicate with the client.
     */
    public ClientHandeler (Socket socket, Server server){
        hasMove = false;
        turnOfThisClient = false;
        nextId++;
        this.server = server;
        this.id = nextId;
        this.socket = socket;

        try{
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Sends the given string over the <code>Socket</code>.
     * @param message A <code>String</code> with the message to be send.
     */
    public void send(String message){
        writer.println(message);
        writer.flush();
    }

    /**
     * The run void of this <code>Thread</code>, it starts handleInput();
     */
    public void run(){
        synchronized (this) {
            handleInput();
        }
    }

    /**
     * Listens to the input from the client, translates the commands and takes appropiate action.
     */
    private void handleInput(){
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split(" ");
                switch (words[0]){
                    case HELLO:
                        handleHello(line);
                        break;
                    case MOVE:
                        handleMove(line);
                        break;
                    default:
                        cmdReportIllegal(line);
                }
            }

            if (game != null){
                leave("Client " + id + " disconnected.");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Is called when the first word of the input is "MOVE", checks if the command is legal and takes action accordingly.
     * @param line A <code>String</code> with the command
     */
    private void handleMove(String line){
        String[] words = line.split(" ");
        if(turnOfThisClient && words.length == 3 && words[1].matches("\\d+") && words[2].matches("\\d+")) {
            int x = Integer.parseInt(words[1]);
            int y = Integer.parseInt(words[2]);
            move = new int[] {x,y};
            if(timer.isAlive() && !sendMove(move)){
                timer.interrupt();
                cmdReportIllegal(line);
            }
        } else {
            cmdReportIllegal(line);
        }
    }

    /**
     * Is called when the first word of the input is "HELLO", checks if the command is legal and sends back a WELCOME.
     * @param line A <code>String</code> with the command
     */
    private void handleHello(String line){
        String[] words = line.split(" ");
        if(words.length == 4 && words[2].matches("\\d+") && (words[3].equals("true")
                || words[3].equals("false"))){
            name = words[1];
            boolean ai = words[3].equals("true");
            System.out.println(ai);
            if(ai){
                cmdWelcome(nextId, aiThinkingTime, 0);
                thinkingTime = aiThinkingTime;
            } else{
                cmdWelcome(nextId, humanThinkingTime, 0);
                thinkingTime = humanThinkingTime;
            }
            System.out.println(server);
            server.addToWaiting(this);
        } else {
            cmdReportIllegal(line);
        }
    }

    /**
     * Send a MOVESUCCESS command to the player, so the player knows a move is made.
     * @param moveX the x coordinate where a chip should be placed on top
     * @param moveY the y coordinate where a chip should be placed on top
     * @param actorID the ID of the player who made the move
     * @param playerWhoHasNextTurnID the ID
     */
    @Override
    public void cmdMoveSuccess(int moveX, int moveY, int actorID, int playerWhoHasNextTurnID) {
        String message = String.format("%1$s %2$d %3$d %4$d %5$d", MOVESUCCESS, moveX, moveY, actorID, playerWhoHasNextTurnID);
        send(message);
        if (playerWhoHasNextTurnID == id){
            turnOfThisClient = true;
            timer = new Timer(thinkingTime, this);
            timer.start();
        } else {
            turnOfThisClient = false;
        }
    }

    /**
     * Send an ILLEGAL command to the client letting it know that the command was illegal.
     * @param theIllegalCommandWithParameters The received illegal command along with its parameters
     */
    @Override
    public void cmdReportIllegal(String theIllegalCommandWithParameters) {
        String message = String.format("%1$s %2$s", REPORTILLEGAL, theIllegalCommandWithParameters);
        send(message);
    }

    /**
     * Send a GAMEEND command to the client letting it know the game ended.
     * @param winnerID the ID of the player who has won. <code>-1</code> means it's a draw
     */
    @Override
    public void cmdGameEnd(int winnerID) {
        String message = String.format("%1$s %2$d", GAMEEND, winnerID);
        send(message);
    }

    /**
     * Send a GAME command to the cient to let it know the client is in a game.
     * @param nameOtherPlayer username chosen by the opponent
     * @param otherPlayerID id assigned to the opponent
     * @param playFieldX the x dimension of the playing field
     * @param playFieldY the y dimension of the playing field
     * @param playFieldZ the z dimension of the playing field, in other words the height
     * @param playerWhoHasNextTurnID the ID of the player who has the first turn and who should send his move to the server
     * @param sequenceLengthOfWin the number of chips that make up a winning sequence
     */
    @Override
    public void cmdGame(String nameOtherPlayer, int otherPlayerID, int playFieldX, int playFieldY,
                        int playFieldZ, int playerWhoHasNextTurnID, int sequenceLengthOfWin) {
        String message = String.format("%1$s %2$s %3$d %4$d %5$d %6$d %7$d %8$d", GAME, nameOtherPlayer,
                otherPlayerID, playFieldX, playFieldY, playFieldZ, playerWhoHasNextTurnID, sequenceLengthOfWin);
        send(message);
        if(playerWhoHasNextTurnID == id) {
            timer = new Timer(thinkingTime, this);
            timer.start();
            System.out.println("started" + timer.isAlive());
        }
    }

    /**
     * Send a PLAYERLEFT command to let the client know a player left the game.
     * @param leftPlayerID the ID of the player who has left
     * @param reason a message generated by the server stating the reason why the player left (e.g. kicked)
     */
    @Override
    public void cmdPlayerLeft(int leftPlayerID, String reason) {
        String message = String.format("%1$s %2$d %3$s", PLAYERLEFT,leftPlayerID, reason);
        send(message);
    }

    /**
     * Send a WELCOME command to let the player know the HELLO command was received.
     * @param assignedUserID this is the unique identifier the server assigned to the client who receives this WELCOME command.
     * @param allowedThinkTime the time the client has to send a valid response to the server in milliseconds.
     * If the client does not respond in time the server will decide what to do next (this depends on the implementing server).
     * Note that the allowed think time can be dependent on whether the client is playing with an AI or as a human, which was reported in
     * the preceding HELLO command.
     * @param capabilities represents a vector with flags for the capabilities that are implemented in the server. See also the specification
     */
    @Override
    public void cmdWelcome(int assignedUserID, long allowedThinkTime, int capabilities) {
        String message = String.format("%1$s %2$d %3$d %4$d", WELCOME, assignedUserID, allowedThinkTime,
                capabilities);
        send(message);
    }

    /**
     * Returns the id of this client
     * @return an <code>int</code> with the id of this client
     */
    public int getClientId(){
        return id;
    }

    /**
     * Sets the <code>GameServer</code> field game to the given <code>GameServer</code>.
     * @param gameServer The <code>GameServer</code> in which the client is currently active
     */
    public void setGameServer(GameServer gameServer){
        game = gameServer;
    }

    /**
     * Sets the local <code>GameServer</code> field back to null.
     */
    public void removeFromGame(){
        game = null;
    }

    /**
     * Sends the given move to the <code>GameServer</code> if the move was successfull it returns true, if it failed
     * it returns false.
     * @param move An <code>int[]</code> which contains the x and y coordinate of the move.
     * @return A <code>boolean</code> which is true if all went well, and false if something went wrong.
     */
    private boolean sendMove(int[] move){
        return game.move(id, move[0], move[1], this);
    }

    /**
     * Tells the <code>GameServer</code> that the client has left, and gives a reason.
     * @param reason a <code>String</code> with the reason why the client left.
     */
    public void leave (String reason){
        game.leave(this, reason);
    }
}
