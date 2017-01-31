package game;

import game.players.Player;
import game.ui.GameTUIView;

public class Game extends Thread{

	private int numberOfPlayers;

	private GameTUIView view;

	private Board board;
	
	private Player[] players;

	private boolean isBusy;

	private int current;

	public Game(Board board) {
		this.board = board;
		this.players = board.getPlayers();
		numberOfPlayers = players.length;
		current = 0;
		view = new GameTUIView();
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
            currPlayer.makeMove(board);
            if (board.isWinningMove(currPlayer.getColor()) || board.isFull()){
                printResult();
                break;
            }
            current = (current + 1) % numberOfPlayers;
            currPlayer = players[current];
        }
		printResult();
	}

	private void update() {
		view.print("\ncurrent game situation: \n\n" + board.toString() + "\n");
	}

	private void printResult() {
		if (board.hasWinner()) {
			Player winner = board.isWinningMove(players[0].getColor()) ? players[0] : players[1];
			view.print("Player " + winner.getName() + " (" + winner.getColor().toString() + ") has won!");
		} else {
			view.print("Draw. There is no winner!");
		}
	}
}
