package Network;

import Network.Interfaces.ChatCapabilityServer;
import Network.Interfaces.Connect4Server;
import Network.Interfaces.LobbyCapabilityServer;
import Network.Interfaces.ScoreboardCapabilityServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;

/**
 * Created by willem on 18-1-17.
 */
public class Server implements Connect4Server, ChatCapabilityServer, LobbyCapabilityServer, ScoreboardCapabilityServer {

    private static final String USAGE = "usage: <port>";
    private static List<ClientHandeler> clients;

    public static void main(String[] args){
        args = new String[] {"4040"};
        if (args.length != 1) {
            System.out.println(USAGE);
            System.exit(0);
        }
        int port = Integer.valueOf(args[1]);
    }

    private void acceptClient(int port){
        try {
            ServerSocket server = new ServerSocket(port);
            while (true) {
                Socket clientSocket = server.accept();
                clients.add(new ClientHandeler(clientSocket));
                System.out.println("Something");
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void broadCast(String message){
        for (ClientHandeler c : clients){
            //TODO: send message client c
        }
    }

    @Override
    public void cmdMoveSuccess(int moveX, int moveY, int actorID, int playerWhoHasNextTurnID) {

    }

    @Override
    public void cmdSendScoreboardEntry(int id, String name, int score) {

    }

    @Override
    public void cmdEndOfScoreboardTransmission() {

    }

    @Override
    public void cmdReportIllegal(String theIllegalCommandWithParameters) {

    }

    @Override
    public void cmdGameEnd(int winnerID) {

    }

    @Override
    public void cmdBroadcastMessage(String msg) {

    }

    @Override
    public void cmdGame(String nameOtherPlayer, int otherPlayerID, int playFieldX, int playFieldY, int playFieldZ, int playerWhoHasNextTurnID, int sequenceLengthOfWin) {

    }

    @Override
    public void cmdPlayerConnect(int newPlayerID, String newPlayerUsername) {

    }

    @Override
    public void cmdPlayerDisconnect(int discPlayerID) {

    }

    @Override
    public void cmdPlayerLeft(int leftPlayerID, String reason) {

    }

    @Override
    public void cmdRequest(int challengerID) {

    }

    @Override
    public void cmdWelcome(int assignedUserID, long allowedThinkTime, int capabilities) {

    }
}
