package connect4;

public interface LobbyCapabilityServer {
	
	public void cmdPlayerConnect(int newPlayerID, String newPlayerUsername);
	
	public void cmdPlayerDisconnect(int discPlayerID);
	
	public void cmdRequest(int challengerID);

}
