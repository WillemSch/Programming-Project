package game;

import game.players.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Board {

	private Map<int[], Color> fields;

	private int length;
	private int width;
	private int height;
	private int winLength;

	private Player[] players;

	private int[] lastMove = {};

	private Color lastColor;

	/**
	 * Constructs a board with the given width, length and height.
	 * @param width an <code>int</code> with the wanted width.
	 * @param length an <code>int</code> with the wanted length.
	 * @param height an <code>int</code> with the wanted height.
	 */
	public Board(int width, int length, int height) {
		this.length = length;
		this.width = width;
		if (height == -1) {
			this.height = Integer.MAX_VALUE;
		} else {
			this.height = height;
		}
		this.height = height;
		this.winLength = 4;
		this.fields = new HashMap<>();
	}

    /**
     * Constructs a board with the given width, length and height.
     * @param width an <code>int</code> with the wanted width.
     * @param length an <code>int</code> with the wanted length.
     * @param height an <code>int</code> with the wanted height.
     * @param players an <code>Player[]</code> with the players of the game.
     */
	public Board(int width, int length, int height, Player[] players) {
		this.length = length;
		this.width = width;
		if (height == -1) {
			this.height = Integer.MAX_VALUE;
		} else {
			this.height = height;
		}
		this.height = height;
		this.winLength = 4;
		this.players = players;
		this.fields = new HashMap<>();
	}

    /**
     * Constructs a board with the given width, length and height.
     * @param width an <code>int</code> with the wanted width.
     * @param length an <code>int</code> with the wanted length.
     * @param height an <code>int</code> with the wanted height.
     * @param winLength an <code>int</code> with the wanted win length.
     * @param players an <code>Player[]</code> with the players of the game.
     */
	public Board(int width, int length, int height, int winLength, Player[] players) {
		this.length = length;
		this.width = width;
		if (height == -1) {
			this.height = Integer.MAX_VALUE;
		} else {
			this.height = height;
		}
		this.winLength = winLength;
		this.players = players;
		this.fields = new HashMap<>();
	}

    /**
     * Checks if a given field is used.
     * @param coordinates an <code>int[]</code> with the coordinates of the field.
     * @return true if the field is empty, false if not.
     */
	public boolean isUsedField(int[] coordinates){
	    for (int[] i : fields.keySet()){
            if(Arrays.equals(i, coordinates)){
                return true;
            }
        }
        return false;
    }

    /**
     * Gets the length of this Board.
     * @return an <code>int</code> representing the length of this Board.
     */
	public int getLength() {
		return length;
	}

    /**
     * Gets the width of this Board.
     * @return an <code>int</code> representing the width of this Board.
     */
	public int getWidth() {
		return width;
	}

    /**
     * Gets the height of this Board.
     * @return an <code>int</code> representing the height of this Board.
     */
	public int getHeight() {
		return height;
	}

    /**
     * Gets the win length of this Board.
     * @return an <code>int</code> representing the win length of this Board.
     */
	public int getWinLength() {
		return winLength;
	}

    /**
     * gets the players of this game in an array.
     * @return an <code>Player[]</code> with the current players.
     */
	public Player[] getPlayers() {
		return players;
	}

    /**
     * Makes a deep copy of this Board.
     * @return a <code>Board</code> that is exactly the same as this one.
     */
	public Board deepCopy() {
		Board copy = new Board(getWidth(),getLength(), getHeight(),getWinLength(),players);
		for (int[] coord: fields.keySet()){
		    copy.setField(coord, fields.get(coord));
        }
		return copy;
	}

    /**
     * Checks if a given field exists.
     * @param coordinates an <code>int[]</code> with the coordinates of the field.
     * @return true if the field exists, false if not.
     */
	public boolean isField(int[] coordinates) {
		int x = coordinates[0];
		int y = coordinates[1];
		int z = coordinates[2];
		return (x >= 0 && x < width) && (y >= 0 && y < length) && (z >= 0 && z < height);
	}

    /**
     * Returns the color of a given field.
     * @param coordinates an <code>int[]</code> with the coordinates of the field.
     * @return the Color of the field.
     */
	public Color getField(int[] coordinates) {
		for (int[] i : fields.keySet()){
			if(Arrays.equals(i, coordinates)){
				return fields.get(i);
			}
		}
		Color color = Color.NONE;
		return color;
	}

    /**
     * Checks if a given field is empty.
     * @param coordinates an <code>int[]</code> with the coordinates of the field.
     * @return true if the field is empty, false if not.
     */
	public boolean isEmptyField(int[] coordinates) {
        for (int[] i : fields.keySet()){
            if(Arrays.equals(i, coordinates)){
                return false;
            }
        }
        return true;
	}

    /**
     * Checks if the board is full.
     * @return true if all fields are used, false if not.
     */
	public boolean isFull() {
		return fields.size() == length * width * height;
	}

    /**
     * Checks if the game is over, either draw or has a winner.
     * @return true if this game is over, false if not.
     */
	public boolean gameOver() {
		return (this.isFull() || this.hasWinner());
	}

    /**
     * Checks if the last move was a winning move.
     * @param color The Color it is going to check for.
     * @return true if the move was a winning move, false if not.
     */
	public boolean isWinningMove(Color color) {
		if (lastMove.length == 0 || lastColor != color) {
			return false;
		} else {
			int x = lastMove[0];
			int y = lastMove[1];
			int z = lastMove[2];

			int lineCount = 1;

			// Test line over the x axes
			for (int i = 1; x + i < width; i++) {
				int[] square = { x + i, y, z };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; x - i >= 0; i++) {
				int[] square = { x - i, y, z };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			if (lineCount >= winLength) {
				return true;
			} else {
				lineCount = 1;
			}

			// Test line over the y axes
			for (int i = 1; y + i < length; i++) {
				int[] square = { x, y + i, z };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; y - i >= 0; i++) {
				int[] square = { x, y - i, z };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			if (lineCount >= winLength) {
				return true;
			} else {
				lineCount = 1;
			}

			// Test line over the z axes
			for (int i = 1; z + i < height; i++) {
				int[] square = { x, y, z + i };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; z - i >= 0; i++) {
				int[] square = { x, y, z - i };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			if (lineCount >= winLength) {
				return true;
			} else {
				lineCount = 1;
			}

			// Test DiagonalDown on z plane
			for (int i = 1; x + i < width && y - i >= 0; i++) {
				int[] square = { x + i, y - i, z };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; x - i >= 0 && y + i < length; i++) {
				int[] square = { x - i, y + i, z };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			if (lineCount >= winLength) {
				return true;
			} else {
				lineCount = 1;
			}

			// Test DiagonalUp on z plane
			for (int i = 1; x + i < width && y + i < length; i++) {
				int[] square = { x + i, y + i, z };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; x - i >= 0 && y - i >= 0; i++) {
				int[] square = { x - i, y - i, z };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			if (lineCount >= winLength) {
				return true;
			} else {
				lineCount = 1;
			}

			// Test DiagonalDown on y plane
			for (int i = 1; x + i < width && z >= 0; i++) {
				int[] square = { x + i, y, z - i };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; x - i >= 0 && z + i < height; i++) {
				int[] square = { x - i, y, z + i };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			if (lineCount >= winLength) {
				return true;
			} else {
				lineCount = 1;
			}

			// Test DiagonalUp on y plane
			for (int i = 1; x + i < width && z + i < height; i++) {
				int[] square = { x + i, y, z + i };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; x - i >= 0 && z - i >= 0; i++) {
				int[] square = { x - i, y, z - i };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			if (lineCount >= winLength) {
				return true;
			} else {
				lineCount = 1;
			}

			// Test DiagonalDown on x plane
			for (int i = 1; y + i < length && z >= 0; i++) {
				int[] square = { x, y + i, z + i };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; y - i >= 0 && z + i < height; i++) {
				int[] square = { x, y - i, z - i };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			if (lineCount >= winLength) {
				return true;
			} else {
				lineCount = 1;
			}

			// Test DiagonalUp on x plane
			for (int i = 1; y + i < length && z - i >= 0; i++) {
				int[] square = { x, y + i, z - i };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; y - i >= 0 && z + i < height; i++) {
				int[] square = { x, y - i, z + i };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			if (lineCount >= winLength) {
				return true;
			} else {
				lineCount = 1;
			}

			// Test DiagonalDown on y x plane
			for (int i = 1; x + i < width && y - i >= 0 && z >= 0; i++) {
				int[] square = { x + i, y - i, z - i };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; x - i >= 0 && y + i < length && z + i < height; i++) {
				int[] square = { x - i, y + i, z + i };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			if (lineCount >= winLength) {
				return true;
			} else {
				lineCount = 1;
			}

			// Test DiagonalUp on y x plane
			for (int i = 1; x + i < width && y - i >= 0 && z + i < height; i++) {
				int[] square = { x + i, y - i, z + i };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; x - i >= 0 && y + i < length && z - i >= 0; i++) {
				int[] square = { x - i, y + i, z - i };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			if (lineCount >= winLength) {
				return true;
			} else {
				lineCount = 1;
			}

			// Test DiagonalDown on x y plane
			for (int i = 1; x + i < width && y + i < length && z >= 0; i++) {
				int[] square = { x + i, y + i, z - i };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; x - i >= 0 && y - i >= 0 && z + i < height; i++) {
				int[] square = { x - i, y - i, z + i };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			if (lineCount >= winLength) {
				return true;
			} else {
				lineCount = 1;
			}

			// Test DiagonalUp on x y plane
			for (int i = 1; x + i < width && y + i < length && z + i < height; i++) {
				int[] square = { x + i, y + i, z + i };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; x - i < width && y + i < length && z - i >= 0; i++) {
				int[] square = { x + i, y + i, z - i };
				if (isUsedField(square) && getField(square).equals(color)) {
					lineCount++;
				} else {
					break;
				}
			}

			if (lineCount >= winLength) {
				return true;
			} else {
				return false;
			}
		}
	}

    /**
     * Checks if the game haas a winner.
     * @return true if there is a winner, fallse if not.
     */
	public boolean hasWinner() {
	    Color[] colors = {Color.BLUE,Color.RED};
        for (Color color : colors) {
            if (isWinner(color)) {
                return isWinner(color);
            }
        }
		return false;
	}

    /**
     * Checks if a given color is the winner.
     * @param color the Color that needs to be checked.
     * @return true if the given Color is a winner, false if not.
     */
	public boolean isWinner(Color color) {
		return isWinningMove(color);
	}

    /**
     * Returns an overview of the board, seen from the top.
     * @return a <code>String</code> that represents the board.
     */
	public String toStringTop() {
		String result = "";
		String result_0 = "  x >\n       ";
		for (int i = 0; i < width; i++) {
			result_0 += "    " + i + "     ";
		}
		for (int i = 0; i < length; i++) {
			String result_1 = "      +---------+";
			String result_2 = "    " + i + " |";
			for (int j = 1; j < width; j++) {
				result_1 += "---------+";
			}
			for (int j = 0; j < width; j++) {
				int z;
				if (getHeightOfField(j,i) == -1) {
					z = height - 1;
				} else {
					z = getHeightOfField(j, i) - 1;
				}
				int[] coordinates = { j, i, z};
				if (!isEmptyField(coordinates)) {
					if (getField(coordinates).equals(Color.BLUE)) {
						result_2 += " " + z + ": " + getField(coordinates).toString() + " |";
					} else if (getField(coordinates).equals(Color.RED)) {
						result_2 += " " + z + ": " + getField(coordinates).toString() + "  |";
					} else {
						result_2 += "  UNKOWN |";
					}
				} else {
					result_2 += "         |";
				}
			}
			result += "\n" + result_1 + "\n" + result_2;
		}
		String result_3 = "  ^   +---------+";
		for (int i = 1; i < width; i++) {
			result_3 += "---------+";
		}
		result_0 += result + "\n" + result_3 + "\n" + "  y";
		return result_0;
	}

    /**
     * Returns an overview of a slice of the board.
     * @param z an <code>int</code> saying which height the player wants to view.
     * @return a <code>String</code> that represents the board.
     */
	public String toStringLevel(int z) {
		String result = "";
		String result_0 = "  x >\n       ";
		for (int i = 0; i < width; i++) {
			result_0 += "    " + i + "     ";
		}
		for (int i = 0; i < length; i++) {
			String result_1 = "      +---------+";
			String result_2 = "    " + i + " |";
			for (int j = 1; j < width; j++) {
				result_1 += "---------+";
			}
			for (int j = 0; j < width; j++) {
				int[] coordinates = { j, i, z};
				if (!isEmptyField(coordinates)) {
					if (getField(coordinates).equals(Color.BLUE)) {
						result_2 += " " + z + ": " + getField(coordinates).toString() + " |";
					} else if (getField(coordinates).equals(Color.RED)) {
						result_2 += " " + z + ": " + getField(coordinates).toString() + "  |";
					} else {
						result_2 += "  UNKOWN |";
					}
				} else {
					result_2 += "         |";
				}
			}
			result += "\n" + result_1 + "\n" + result_2;
		}
		String result_3 = "  ^   +---------+";
		for (int i = 1; i < width; i++) {
			result_3 += "---------+";
		}
		result_0 += result + "\n" + result_3 + "\n" + "  y";
		return result_0;
	}

    /**
     * Returns an overview of the board. Only use with small heights.
     * @return a <code>String</code> that represents the board.
     */
	public String toString(){
        String result = "";
        String result_0 = "  x >\n       ";
        for (int i = 0; i < width; i++) {
            result_0 += "    " + i + "     ";
        }
        for (int i = 0; i < length; i++) {
            String result_1 = "      +---------+";
            String result_2 = "    " + i + " |";
            for (int j = 1; j < width; j++) {
                result_1 += "---------+";
            }
            for(int z = 0; z < height; z++) {
                for (int j = 0; j < width; j++) {
                    int[] coordinates = {j, i, z};
                    if (!isEmptyField(coordinates)) {
                        if (getField(coordinates).equals(Color.BLUE)) {
                            result_2 += " " + z + ": " + getField(coordinates).toString() + " |";
                        } else if (getField(coordinates).equals(Color.RED)) {
                            result_2 += " " + z + ": " + getField(coordinates).toString() + "  |";
                        } else {
                            result_2 += "  UNKOWN |";
                        }
                    } else {
                        result_2 += "         |";
                    }
                }
                if(z != height-1){
                    result_2 += "\n";
                    result_2 += "      |";
                }

            }
            result += "\n" + result_1 + "\n" + result_2;
        }
        String result_3 = "  ^   +---------+";
        for (int i = 1; i < width; i++) {
            result_3 += "---------+";
        }
        result_0 += result + "\n" + result_3 + "\n" + "  y";
        return result_0;
    }

    /**
     * Resets this Board, and all its values.
     */
	public void reset() {
		this.fields = new HashMap<>();
	}

    /**
     * Sets the given field to the given color.
     * @param coordinates an <code>int[]</code> with the coordinates of the field.
     * @param color the <code>Color</code> of the player making the move.
     * @return true if the move was successful, false if not.
     */
	public boolean setField(int[] coordinates, Color color) {
		lastMove = coordinates;
		fields.put(coordinates, color);
		lastColor = color;
		return isUsedField(coordinates);
	}

    /**
     * Sets the given field to the given color.
     * @param x an <code>int</code> with the x-coordinate of the field.
     * @param y an <code>int</code> with the x-coordinate of the field.
     * @param color the <code>Color</code> of the player making the move.
     * @return true if the move was successful, false if not.
     */
	public boolean setField(int x, int y, Color color) {
		int z = getHeightOfField(x, y);
		int[] coordinates = { x, y, z };
		lastMove = coordinates;
		fields.put(coordinates, color);
		lastColor = color;
		return isUsedField(coordinates);
	}

    /**
     * Returns the corresponding z value of given x-y coordinates.
     * @param x an <code>int</code> with the x-coordinate of the field.
     * @param y an <code>int</code> with the y-coordinate of the field.
     * @return an <code>int</code> with the z-coordinate of the field, -1 if not valid.
     */
	public int getHeightOfField(int x, int y) {
		for (int i = 0; i < height; i++) {
			int[] coordinates = { x, y, i };
			if (isEmptyField(coordinates)) {
				return i;
			}
		}
		return -1;
	}
}
