package network.interfaces;

public interface LobbyCapabilityServer {
	
	void cmdPlayerConnect(int newPlayerID, String newPlayerUsername);
	
	void cmdPlayerDisconnect(int discPlayerID);
	
	void cmdRequest(int challengerID);

}
