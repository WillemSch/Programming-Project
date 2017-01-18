package Network.Interfaces;

public interface LobbyCapabilityServer {
	
	void cmdPlayerConnect(int newPlayerID, String newPlayerUsername);
	
	void cmdPlayerDisconnect(int discPlayerID);
	
	void cmdRequest(int challengerID);

}
