package game;

public class ConnectFour {
	public static void main(String[] args) {
		Player p1 = new HumanPlayer(args[0], Mark.XX);
		Player p2 = new HumanPlayer(args[1], Mark.OO);
		Player[] players = {p1,p2};
		Board board = new Board(1,1,1,5,players);
		Game game = new Game(board);
		game.start();
	}
}
