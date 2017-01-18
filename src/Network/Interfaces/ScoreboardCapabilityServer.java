package connect4;

public interface ScoreboardCapabilityServer {

	public void cmdSendScoreboardEntry(int id, String name, int score);
	
	public void cmdEndOfScoreboardTransmission();
}
