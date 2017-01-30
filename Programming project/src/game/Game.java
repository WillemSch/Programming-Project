package game;

import network.Client;

public class Game extends Thread{

	private int numberOfPlayers;

	private Board board;

	private Client client;

	private Player[] players;

	private boolean isBusy;

	private int current;

	public Game(Board board, Client client) {
		this.client = client;
		this.board = board;
		this.players = board.getPlayers();
		numberOfPlayers = players.length;
		current = 0;
	}

	public Game(Board board, Player[] players, boolean isClientsTurn) {
		this.board = board;
		this.players = players;
		if (isClientsTurn){
			current = 0;
		} else {
			current = 1;
		}
		numberOfPlayers = players.length;
	}

	public void run(){
		startGame();
	}

	public void startGame() {
		play();
		reset();
	}

	private void reset() {
		current = 0;
		board.reset();
	}

	private void play() {
		isBusy = true;
		Player currPlayer = players[current];
		while (isBusy) {
			update();
			synchronized (currPlayer) {
				currPlayer.makeMove(board);
				current = (current + 1) % numberOfPlayers;
				currPlayer = players[current];
			}
		}
	}

	private void update() {
		System.out.println("\ncurrent game situation: \n\n" + board.toString() + "\n");
	}

	private void printResult() {
		if (board.hasWinner()) {
			Player winner = board.isWinner(players[0].getColor()) ? players[0] : players[1];
			System.out.println("Player " + winner.getName() + " (" + winner.getColor().toString() + ") has won!");
		} else {
			System.out.println("Draw. There is no winner!");
		}
	}

    /**
     * Forces the game to crown one person as winner, in case ssomeone gets kicked or disconnected
     * @param player a <code>Player</code> that will be crowned winner.
     */
	public void forceWinner(Player player){
		isBusy = false;
        System.out.println("Player " + player.getName() + " (" + player.getColor().toString() + ") has won!");
    }

    public void forceDraw(){
		isBusy = false;
		System.out.println("It's a draw, we'll get 'em next time.");
	}
}
