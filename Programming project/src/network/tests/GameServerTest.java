package network.tests;

import network.server.ClientHandeler;
import network.server.GameServer;
import network.server.Server;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author willem
 * @version 1.0.0
 * Test the GameServer class with 77% method coverage, and 31% line coverage.
 */
public class GameServerTest {
    private Server server;
    private GameServer gameServer;

    /**
     * Sets up the server and GameServer.
     */
    @Before
    public void setUp(){
        server = new Server(4040);
        server.start();
        List<ClientHandeler> players = new ArrayList<>();
        gameServer = new GameServer(players, server);
    }

    /**
     * Tests the run() method in GameServer.
     */
    @Test
    public void testRun(){
        //Shouldn't throw any exceptions.
        gameServer.run();
    }

    /**
     * Tests the checkWinner() method in GameServer.
     */
    @Test
    public void testCheckWinner(){
        gameServer.run();
        //We cant add moves because we cannot initiate game with ClientHandelers that we can track.
        assert (!gameServer.checkWinner());
    }

    /**
     * Tests the broadCast() method in GameServer.
     */
    @Test
    public void testBroadCast(){
        //Shouldn't throw any exceptions.
        gameServer.broadcast("Sample broadcast 1");
    }

    /**
     * Tests the Leave() method in GameServer.
     */
    @Test
    public void testLeave(){
        //Shouldn't throw exceptions
        gameServer.leave(null, "");
    }
}
