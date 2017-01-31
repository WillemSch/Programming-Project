package network.interfaces;

public interface ScoreboardCapabilityServer {

	void cmdSendScoreboardEntry(int id, String name, int score);
	
	void cmdEndOfScoreboardTransmission();
}
