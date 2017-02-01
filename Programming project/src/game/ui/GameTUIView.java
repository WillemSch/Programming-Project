package game.ui;

import game.Board;
import game.Color;
import game.Game;
import game.players.ComputerPlayer;
import game.players.HumanPlayer;
import game.players.Player;
import game.players.strategies.SmartStrategy;
import network.client.Client;

import java.util.Scanner;

/**
 * @author Jesper Simon.
 * @version 1.0.0
 * Prints the TUI for the player, can start a local game, and an online game.
 */
public class GameTUIView {
    private SmartStrategy smartStrategy;
    private Scanner scanner;
    private int length;
    private int width;
    private int height;
    private int winLength;
    private String name;

    private Player player1;
    private Player player2;

    private Board board;

    /**
     * Constructor of GameTUIView sets up a Scanner and some needed variables.
     */
    public GameTUIView() {
        smartStrategy = new SmartStrategy();
        scanner = new Scanner(System.in);
        length = 4;
        width = 4;
        height = 4;
        winLength = 4;
    }

    /**
     * Asks the player for his/her name and starts the TUI.
     */
    public void setup() {
        print("Wecome to Connect Four, what is your name?");
        if (scanner.hasNextLine()) {
            if (scanner.hasNext()) {
                name = scanner.next();
                start();
            }
        }
    }

    /**
     * Starts the TUI nd listens for inputs.
     */
    public void start() {
        print("\nWelcome to Connect Four, this is the main menu, choose a command number from below. \n"
                + "1. Local_Game \n" + "2. Online_Game \n" + "3. Game_Settings \n" + "4. Exit");
        try {
            while (scanner != null && scanner.hasNextLine()) {
                String word;
                if (scanner.hasNext()) {
                    word = scanner.next();
                    if (word.toUpperCase().equals("LOCAL_GAME")) {
                        localGame();
                    } else if (word.toUpperCase().equals("ONLINE_GAME")) {
                        onlineGame();
                        return;
                    } else if (word.toUpperCase().equals("GAME_SETTINGS")) {
                        gameSettings();
                    } else if (word.toUpperCase().equals("EXIT")) {
                        break;
                    }
                }
            }
        } catch (IllegalStateException e){

        }
        print("The Connect Four menu will now terminate.");
        scanner.close();

    }

    /**
     * Starts an client with the given ip & port.
     */
    public void onlineGame() {
        print("Please specify an ip-address and port below:");
        while (true){
            if(scanner.hasNext()){
                String address = scanner.next();
                if(scanner.hasNext()){
                    String port = scanner.next();
                    String[] args = new String[]{name, address, port};
                    Client.main(args);
                    return;
                }
            }
        }
    }

    /**
     * Starts a local game, asks if the player wants a Human or AI opponent.
     */
    public void localGame() {
        if (player2 == null) {
            player1 = new HumanPlayer(name, Color.RED);
            print(player1.getName() + ", want to play against the AI or another human player? (AI/HUMAN)");
            while (scanner.hasNextLine()) {
                String word;
                if (scanner.hasNext()) {
                    word = scanner.next();
                    if (word.toUpperCase().equals("AI")) {
                        player2 = new ComputerPlayer(Color.BLUE, smartStrategy);
                        Player[] players = { player1, player2 };
                        board = new Board(length, width, height, winLength, players);
                        Game game = new Game(board);
                        game.startGame();
                    } else if (word.toUpperCase().equals("HUMAN")) {
                        print("What is the name of the second player?");
                        if (scanner.hasNextLine()) {
                            if (scanner.hasNext()) {
                                word = scanner.next();
                                player2 = new HumanPlayer(word, Color.BLUE);
                            }
                        }
                        Player[] players = { player1, player2 };
                        board = new Board(length, width, height, winLength, players);
                        Game game = new Game(board);
                        game.startGame();
                    }
                }
            }
        } else {
            Player[] players = { player1, player2 };
            board = new Board(length, width, height, winLength, players);
            Game game = new Game(board);
            game.startGame();
        }
    }

    /**
     * Opens the game settings menu in the tui and listens for input.
     */
    public void gameSettings() {
        print(
                "\nWelcome to the Game Settings menu, here you can change the game settings for a local game.\n"
                        + "1. Set_Board \n" + "2. Set_Players \n" + "3. Set_Winning_Conditions \n" + "4. Back to menu");
        while (scanner.hasNextLine()) {
            String word;
            if (scanner.hasNext()) {
                word = scanner.next();
                if (word.toUpperCase().equals("SET_BOARD")) {
                    setBoard();
                } else if (word.toUpperCase().equals("SET_PLAYERS")) {
                    setPlayers();
                } else if (word.toUpperCase().equals("SET_WINNING_CONDITIONS")) {
                    setWinningConditions();
                } else if (word.toUpperCase().equals("BACK_TO_MENU")) {
                    back();
                }
            }
        }
    }

    /**
     * Sets up a board with the given dimension, via the TUI.
     */
    public void setBoard() {
        print("What are the width, length and height of the board? (write in the from 'x,y,z')");
        while (scanner.hasNextLine()) {
            String word;
            if (scanner.hasNext()) {
                word = scanner.next();
                String parts[] = word.split(",");
                if (parts[0].matches("\\d+") && parts[1].matches("\\d+") && parts[2].matches("\\d+")) {
                    length = Integer.parseInt(parts[0]);
                    width = Integer.parseInt(parts[1]);
                    height = Integer.parseInt(parts[2]);
                    print("Board width, length, height set to: " + width + ", " + length + ", " + height + ".");
                    gameSettings();
                } else {
                    print("Board not set.");
                    gameSettings();
                }
            }
        }
    }

    /**
     * Sets up the players with command gotten through the TUI.
     */
    public void setPlayers() {
        print("\nWhich player do you want to set? (Player1 / Player2)");
        while (scanner.hasNextLine()) {
            String word;
            if (scanner.hasNext()) {
                word = scanner.next();
                if (word.toUpperCase().equals("PLAYER1")) {
                    setPlayer(1, Color.RED);
                } else if (word.toUpperCase().equals("PLAYER2")) {
                    setPlayer(2, Color.BLUE);
                } else {
                    print("This is not a player.");
                }
            }
        }
    }

    /**
     * Sets up a player with the given color, number indicates which player is set up.
     * @param number an int which says which player needs to be set up.
     * @param color the Color of the player.
     */
    public void setPlayer(int number, Color color) {
        Player player = null;
        while (scanner.hasNextLine()) {
            print("Is Player " + number + " an AI or a Human player? (AI / HUMAN");
            String word;
            if (scanner.hasNext()) {
                word = scanner.next();
                if (word.toUpperCase().equals("AI")) {
                    player = new ComputerPlayer(color, smartStrategy);
                    print("Player " + number + " set to AI.");
                    gameSettings();
                } else if (word.toUpperCase().equals("HUMAN")) {
                    print("What is the name of this player?");
                    while (scanner.hasNextLine()) {
                        if (scanner.hasNext()) {
                            word = scanner.next();
                            player = new HumanPlayer(word, color);
                            print("Player " + number + " set to " + player.getName());
                            gameSettings();
                        }
                    }
                } else {
                    print("This is not an option.");
                    gameSettings();
                }
            }
        }
        if (number == 1) {
            player1 = player;
        } else {
            player2 = player;
        }
    }

    /**
     * Asks for winning conditions and applies them.
     */
    public void setWinningConditions() {
        print("What is the winning length of a line?");
        while (scanner.hasNextLine()) {
            String word;
            if (scanner.hasNext()) {
                word = scanner.next();
                if (word.matches("\\d+")) {
                    winLength = Integer.parseInt(word);
                    print("Winning length set to: " + winLength + ".");
                    gameSettings();
                } else {
                    print("Winning length not set.");
                    gameSettings();
                }
            }
        }
    }

    /**
     * goes back to the main part of the TUI.
     */
    public void back() {
        start();
    }

    public void switchLevel(int level, Player player) {
        print("\ncurrent game situation: (Level " + level + ") \n\n" + board.toStringLevel(level) + "\n");
        player.determineMove(board);

    }

    /**
     * Updates the game situation.
     */
    public void update() {
        print("\ncurrent game situation: \n\n" + board.toStringTop() + "\n");
    }

    /**
     * Prints a message to the console.s
     * @param message a <code>String</code> with the message you wan to print.
     */
    public void print(String message) {
        System.out.println(message);
    }

    /**
     * The main void of the TUI is tarts an TUI.
     * @param args
     */
    public static void main(String[] args) {
        GameTUIView game = new GameTUIView();
        game.setup();
    }

}
