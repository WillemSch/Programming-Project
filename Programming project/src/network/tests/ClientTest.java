package network.tests;

import game.Color;
import game.players.HumanPlayer;
import game.players.Player;
import network.client.Client;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.net.Socket;

/**
 * @author willem
 * @version 1.0.0
 * Tests the ClientHandeler class. !IMPORTANT! To run the ClientText class there must be a
 * server active on this machine. This test covers 93% of the methods in client and 60% of the lines.
 */
public class ClientTest {
    private Socket socket;
    private Client client;
    private Player player;

    /**
     *
     */
    @Before
    public void setUp() {
        try {
            socket = new Socket("localhost", 4040);
        } catch (IOException e){
            e.printStackTrace();
        }

        player =  new HumanPlayer("name", Color.BLUE);

        client = new Client(socket,"name", 0, false, player);
        client.start();
    }

    /**
     * Tests all commands, although we can't assert anything it should throw any exceptions.
     */
    @Test
    public void testCommands(){
        client.cmdHello("", false, 0);
        client.cmdMove(0,0);
        client.cmdRequest();
    }

    /**
     * Tests all command handlers, although we can't assert anything it should throw any exceptions.
     */
    @Test
    public void testHandlers(){
        String line = "RANDOM COMMAND 1";
        client.gameEndHandler(line);
        client.gameHandler(line);
        client.moveSuccessHandler(line);
        client.playerLeftHandler(line);
        client.welcomeHandler(line);
    }

    /**
     * Tests the getters of client.
     */
    @Test
    public void testGetters(){
        assert (!client.getIsAi());
        //We can't be sure which id the client will get, but is should be over 0.
        assert (client.getClientId() >= 0);
        assert (client.getPlayer().equals(player));
    }
}
