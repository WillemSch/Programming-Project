package network.tests;

import network.server.Server;
import org.junit.Before;
import org.junit.Test;

/**
 * @author willem
 * @version 1.0.0
 * Tests the server class, with 40% percent method coverage, and 22% line coverage. This doesn't seem like a lot
 * but a lot of the functions in server can simply not be tested with a Unit test.
 */
public class ServerTest {
    private Server server;

    /**
     * Sets up the server to be tested.
     */
    @Before
    public void setUp(){
        server = new Server(4040);
        server.start();
    }

    /**
     * Test the getters of server.
     */
    @Test
    public void testGetters(){
        assert (server.getClients().size() == 0);
        assert (server.getGames().size() == 0);
    }

    /**
     * Tests the broadCast() method in server, although nothing can be asserted it shouldn't throw exceptions.
     */
    @Test
    public void testBroadCast(){
        //We can't assert anything but no exceptions should be thrown.
        server.broadCast("Sample broadcsst");
    }
}
