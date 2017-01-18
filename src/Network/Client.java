package Network;

import Network.Interfaces.ChatCapabilityClient;
import Network.Interfaces.Connect4Client;
import Network.Interfaces.LobbyCapabilityClient;
import Network.Interfaces.ScoreboardCapabilityClient;

/**
 * Created by willem on 18-1-17.
 */
public class Client implements Connect4Client, ChatCapabilityClient, LobbyCapabilityClient, ScoreboardCapabilityClient{
    @Override
    public void cmdChat(String msg) {

    }

    @Override
    public void cmdHello(String username, int clientCapabilities, boolean isAI) {

    }

    @Override
    public void cmdInvite(int desiredOpponentID) {

    }

    @Override
    public void cmdMove(int x, int y) {

    }

    @Override
    public void getScoreboard() {

    }
}
