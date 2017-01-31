package network.interfaces;

public interface Connect4Client {

	/**
	 * Command to register this client on the server. If the registering is successful a response in the shape of the WELCOME
	 * command can be expected from the server.
	 * 
	 * @param username the desired username to be displayed on the scoreboard.
	 * @param clientCapabilities an integer acting as a boolean flag vector. Every bit corresponds to a capability.
	 * Setting a certain bit to <code>0</code> means the client is reporting that it does not have an implementation
	 * for the associated capability.
	 * @param isAI <code>true</code> indicates that the user that is registering is a computer player. <code>false</code> indicates a human player.
	 */
    void cmdHello(String username, boolean isAI, int clientCapabilities);
	
	/** 
	 * The x and y coordinate of the desired move. The coordinate system is defined as follows: <br>
	 * <b>NB: See this javadoc in the actual editor for the correct layout in ASCII art</b><br>
	 * 
	 *  	  x -->			<br>
	 * 	O  0 1 2 3 4 5  	<br>
	 * y    _._._._._._		<br>
	 *   0 |_|_|_|_|_|_|	<br>
	 * | 1 |_|_|_|_|_|_|	<br>
	 * V 2 |_|_|_|_|_|_|	<br>
	 *   3 |_|_|_|_|_|_|
	 *   
	 * @param x the x coordinate of the move
	 * @param y the y coordinate of the move
	 */
    void cmdMove(int x, int y);

	/**
	 * Tells the server its ready for a game.
	 */
	void cmdRequest();
}
