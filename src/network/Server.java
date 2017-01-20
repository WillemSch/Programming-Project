package network;

import network.Interfaces.Connect4Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by willem on 18-1-17.
 */
public class Server implements Connect4Server {

    private static final String USAGE = "usage: <port>";
    private static List<ClientHandeler> clients;

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

    private static void acceptClient(int port){
        try {
            ServerSocket server = new ServerSocket(port);
            while (true) {
                Socket clientSocket = server.accept();
                ClientHandeler newClient = new ClientHandeler(clientSocket);
                newClient.start();
                clients.add(newClient);

                System.out.println("New client added.");
                broadCast("Hello");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void broadCast(String message){
        for (ClientHandeler c : clients){
            c.send(message);
        }
    }

    @Override
    public void cmdMoveSuccess(int moveX, int moveY, int actorID, int playerWhoHasNextTurnID) {

    }

    @Override
    public void cmdReportIllegal(String theIllegalCommandWithParameters) {

    }

    @Override
    public void cmdGameEnd(int winnerID) {

    }

    @Override
    public void cmdGame(String nameOtherPlayer, int otherPlayerID, int playFieldX, int playFieldY, int playFieldZ, int playerWhoHasNextTurnID, int sequenceLengthOfWin) {

    }

    @Override
    public void cmdPlayerLeft(int leftPlayerID, String reason) {

    }

    @Override
    public void cmdWelcome(int assignedUserID, long allowedThinkTime, int capabilities) {

    }
}
