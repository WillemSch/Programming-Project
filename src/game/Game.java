package connectFour;

public class Game {

	private int numberOfPlayers;

	private Board board;

	private Player[] players;

	private int current;

	public Game(Player[] players) {
		numberOfPlayers = players.length;
		board = new Board(board.getLength(), board.getWidth(), board.getHeigth(), board.getWinLength(), players);
		this.players = new Player[numberOfPlayers];
		for (int i = 0; i < numberOfPlayers; i++) {
			this.players[i] = players[i];
		}
		current = 0;
	}

	public void start() {
		reset();
		play();

	}

	private void reset() {
		current = 0;
		board.reset();
	}

	private void play() {
		Player currPlayer = players[current];
		while (!board.gameOver()) {
			update();
			board.setField(currPlayer.determineMove(board)[0], currPlayer.determineMove(board)[1],
					currPlayer.determineMove(board)[2], currPlayer.getMark());
			current = (current + 1) % numberOfPlayers;
			currPlayer = players[current];
		}
		printResult();
	}

	private void update() {
		System.out.println("\ncurrent game situation: \n\n" + board.toString() + "\n");
	}

	private void printResult() {
		if (board.hasWinner()) {
			Player winner = board.isWinner(players[0].getMark()) ? players[0] : players[1];
			System.out.println("Speler " + winner.getName() + " (" + winner.getMark().toString() + ") has won!");
		} else {
			System.out.println("Draw. There is no winner!");
		}
	}
}
