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

	public Board(int width, int length, int heigth) {
		this.length = length;
		this.width = width;
		if (heigth == -1) {
			this.height = Integer.MAX_VALUE;
		} else {
			this.height = heigth;
		}
		this.height = heigth;
		this.winLength = 4;
		this.fields = new HashMap<>();
	}

	public Board(int width, int length, int heigth, Player[] players) {
		this.length = length;
		this.width = width;
		if (heigth == -1) {
			this.height = Integer.MAX_VALUE;
		} else {
			this.height = heigth;
		}
		this.height = heigth;
		this.winLength = 4;
		this.players = players;
		this.fields = new HashMap<>();
	}

	public Board(int width, int length, int heigth, int winLength, Player[] players) {
		this.length = length;
		this.width = width;
		if (heigth == -1) {
			this.height = Integer.MAX_VALUE;
		} else {
			this.height = heigth;
		}
		this.winLength = winLength;
		this.players = players;
		this.fields = new HashMap<>();
	}

	public boolean isUsedField(int[] coordinates){
	    for (int[] i : fields.keySet()){
            if(Arrays.equals(i, coordinates)){
                return true;
            }
        }
        return false;
    }

	public int getLength() {
		return length;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getWinLength() {
		return winLength;
	}

	public Player[] getPlayers() {
		return players;
	}

	public Board deepCopy() {
		Board copy = new Board(getWidth(),getLength(), getHeight(),getWinLength(),players);
		for (int[] coord: fields.keySet()){
		    copy.setField(coord, fields.get(coord));
        }
		return copy;
	}

	public boolean isField(int[] coordinates) {
		int x = coordinates[0];
		int y = coordinates[1];
		int z = coordinates[2];
		return (x >= 0 && x < width) && (y >= 0 && y < length) && (z >= 0 && z < height);
	}

	public Color getField(int[] coordinates) {
		for (int[] i : fields.keySet()){
			if(Arrays.equals(i, coordinates)){
				return fields.get(i);
			}
		}
		Color color = Color.NONE;
		return color;
	}

	public boolean isEmptyField(int[] coordinates) {
        for (int[] i : fields.keySet()){
            if(Arrays.equals(i, coordinates)){
                return false;
            }
        }
        return true;
	}

	public boolean isFull() {
		return fields.size() == length * width * height;
	}

	public boolean gameOver() {
		return (this.isFull() || this.hasWinner());
	}

	public boolean isWinningMove(Color m) {
		if (lastMove.length == 0) {
			return false;
		} else {
			int x = lastMove[0];
			int y = lastMove[1];
			int z = lastMove[2];

			int lineCount = 1;

			// Test line over the x axes
			for (int i = 1; x + i < width; i++) {
				int[] square = { x + i, y, z };
				if (isUsedField(square) && getField(square).equals(m)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; x - i >= 0; i++) {
				int[] square = { x - i, y, z };
				if (isUsedField(square) && getField(square).equals(m)) {
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
				if (isUsedField(square) && getField(square).equals(m)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; y - i >= 0; i++) {
				int[] square = { x, y - i, z };
				if (isUsedField(square) && getField(square).equals(m)) {
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
				if (isUsedField(square) && getField(square).equals(m)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; z - i >= 0; i++) {
				int[] square = { x, y, z - i };
				if (isUsedField(square) && getField(square).equals(m)) {
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
				if (isUsedField(square) && getField(square).equals(m)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; x - i >= 0 && y + i < length; i++) {
				int[] square = { x - i, y + i, z };
				if (isUsedField(square) && getField(square).equals(m)) {
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
				if (isUsedField(square) && getField(square).equals(m)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; x - i >= 0 && y - i >= 0; i++) {
				int[] square = { x - i, y - i, z };
				if (isUsedField(square) && getField(square).equals(m)) {
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
				if (isUsedField(square) && getField(square).equals(m)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; x - i >= 0 && z + i < height; i++) {
				int[] square = { x - i, y, z + i };
				if (isUsedField(square) && getField(square).equals(m)) {
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
				if (isUsedField(square) && getField(square).equals(m)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; x - i >= 0 && z - i >= 0; i++) {
				int[] square = { x - i, y, z - i };
				if (isUsedField(square) && getField(square).equals(m)) {
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
				if (isUsedField(square) && getField(square).equals(m)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; y - i >= 0 && z + i < height; i++) {
				int[] square = { x, y - i, z - i };
				if (isUsedField(square) && getField(square).equals(m)) {
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
				if (isUsedField(square) && getField(square).equals(m)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; y - i >= 0 && z + i < height; i++) {
				int[] square = { x, y - i, z + i };
				if (isUsedField(square) && getField(square).equals(m)) {
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
				if (isUsedField(square) && getField(square).equals(m)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; x - i >= 0 && y + i < length && z + i < height; i++) {
				int[] square = { x - i, y + i, z + i };
				if (isUsedField(square) && getField(square).equals(m)) {
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
				if (isUsedField(square) && getField(square).equals(m)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; x - i >= 0 && y + i < length && z - i >= 0; i++) {
				int[] square = { x - i, y + i, z - i };
				if (isUsedField(square) && getField(square).equals(m)) {
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
				if (isUsedField(square) && getField(square).equals(m)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; x - i >= 0 && y - i >= 0 && z + i < height; i++) {
				int[] square = { x - i, y - i, z + i };
				if (isUsedField(square) && getField(square).equals(m)) {
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
				if (isUsedField(square) && getField(square).equals(m)) {
					lineCount++;
				} else {
					break;
				}
			}

			for (int i = 1; x - i < width && y + i < length && z - i >= 0; i++) {
				int[] square = { x + i, y + i, z - i };
				if (isUsedField(square) && getField(square).equals(m)) {
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

	public boolean hasWinner() {
	    Color[] colors = {Color.BLUE,Color.RED};
        for (Color color : colors) {
            if (isWinner(color)) {
                return isWinner(color);
            }
        }
		return false;
	}

	public boolean isWinner(Color m) {
		return isWinningMove(m);
	}

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

	public void reset() {
		this.fields = new HashMap<>();
	}

	public boolean setField(int[] coordinates, Color m) {
		lastMove = coordinates;
		fields.put(coordinates, m);
		return isUsedField(coordinates);
	}

	public boolean setField(int x, int y, Color m) {
		int z = getHeightOfField(x, y);
		int[] coordinates = { x, y, z };
		lastMove = coordinates;
		fields.put(coordinates, m);
		return isUsedField(coordinates);
	}

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
