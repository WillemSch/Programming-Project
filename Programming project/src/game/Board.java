package game;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Board {

	private Map<int[], Color> fields;

	private int length;
	private int width;
	private int heigth;
	private int winLength;

	private Player[] players;

	private int[] lastMove = {};

	public Board(int width, int length, int heigth) {
		this.length = length;
		this.width = width;
		if (heigth == -1) {
			this.heigth = Integer.MAX_VALUE;
		} else {
			this.heigth = heigth;
		}
		this.heigth = heigth;
		this.winLength = 4;
		this.fields = new HashMap<>();
	}

	public Board(int width, int length, int heigth, Player[] players) {
		this.length = length;
		this.width = width;
		if (heigth == -1) {
			this.heigth = Integer.MAX_VALUE;
		} else {
			this.heigth = heigth;
		}
		this.heigth = heigth;
		this.winLength = 4;
		this.players = players;
		this.fields = new HashMap<>();
	}

	public Board(int width, int length, int heigth, int winLength, Player[] players) {
		this.length = length;
		this.width = width;
		if (heigth == -1) {
			this.heigth = Integer.MAX_VALUE;
		} else {
			this.heigth = heigth;
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

	public int getHeigth() {
		return heigth;
	}

	public int getWinLength() {
		return winLength;
	}

	public Player[] getPlayers() {
		return players;
	}

	public Board deepCopy() {
		Board copy = this;
		return copy;
	}

	public boolean isField(int[] coordinates) {
		int x = coordinates[0];
		int y = coordinates[1];
		int z = coordinates[2];
		return (x >= 0 && x < width) && (y >= 0 && y < length) && (z >= 0 && z < heigth);
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
		return fields.size() == length * width * heigth;
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
			for (int i = 1; z + i < heigth; i++) {
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

			for (int i = 1; x - i >= 0 && z + i < heigth; i++) {
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
			for (int i = 1; x + i < width && z + i < heigth; i++) {
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

			for (int i = 1; y - i >= 0 && z + i < heigth; i++) {
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

			for (int i = 1; y - i >= 0 && z + i < heigth; i++) {
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

			for (int i = 1; x - i >= 0 && y + i < length && z + i < heigth; i++) {
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
			for (int i = 1; x + i < width && y - i >= 0 && z + i < heigth; i++) {
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

			for (int i = 1; x - i >= 0 && y - i >= 0 && z + i < heigth; i++) {
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
			for (int i = 1; x + i < width && y + i < length && z + i < heigth; i++) {
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

	public String toString() {
		String result = "";
		String result_2;
		for (int i = 0; i < length; i++) {
			String result_1 = "+-------+";
			for (int j = 1; j < width; j++) {
				result_1 += "-------+";
			}
			Integer[] coordinates = {0,i,0};
			if (fields.containsKey(coordinates)) {
				if (fields.get(coordinates).equals(players[1].getColor())) {
					result_2 = "|  BLUE |";
				} else if (fields.get(coordinates).equals(players[0].getColor())) {
					result_2 = "|  RED  |";
				} else {
					result_2 = "|       |";
				}
			} else {
				result_2 = "|       |";
			}
			for (int j = 1; j < width; j++) {
				Integer[] coordinates_2 = {j,i,0};
				if (fields.containsKey(coordinates_2)) {
					result_2 = "|       |";
				} else {
					result_2 = "|       |";
				}
			}
			result += result_1 + "\n" + result_2 + "\n";
		}
		String result_3 = "+-------+";
		for (int i = 1; i < width; i++) {
			result_3 += "-------+";
		}
		result += result_3;
		return result;
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
		for (int i = 0; i < heigth; i++) {
			int[] coordinates = { x, y, i };
			if (isEmptyField(coordinates)) {
				return i;
			}
		}
		return -1;
	}
}
