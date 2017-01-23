package game;

import java.util.HashMap;
import java.util.Map;

public class Board {

	private Map<Integer[], Mark> fields;

	private int length;
	private int width;
	private int heigth;
	private int winLength;
	private Player[] players;

	public Board(int width, int length, int heigth, Player[] players) {
		this.length = length;
		this.width = width;
		if (heigth == -1) {
			this.heigth = Integer.MAX_VALUE;
		} else {
			this.heigth = heigth;
		}
		this.heigth = heigth;
		this.fields = new HashMap<Integer[], Mark>();
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
		this.fields = new HashMap<Integer[], Mark>();
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

	public Board deepCopy() {
		Board copy = this;
		return copy;
	}

	public boolean isField(int x, int y, int z) {
		return (x > 0 && x < width) && (y > 0 && y < length) && (z > 0 && z < heigth);
	}

	public Mark getField(int x, int y, int z) {
		Integer[] coordinates = { x, y, z };
		return fields.get(coordinates);
	}

	public boolean isEmptyField(int x, int y, int z) {
		Integer[] coordinates = { x, y, z };
		return fields.containsKey(coordinates);
	}

	public boolean isFull() {
		return fields.size() == length * width * heigth;
	}

	public boolean gameOver() {
		return (this.isFull() || this.hasWinner());
	}

	public boolean hasRow(Mark m) {
		return false;
	}

	public boolean hasColumn(Mark m) {

	}

	public boolean hasDiagonal(Mark m) {

	}

	public boolean hasWinner() {
		for (Player player : players) {
			Mark m = player.getMark();
			if (isWinner(m)) {
				return isWinner(m);
			} else {
				return false;
			}
		}
		return false;
	}

	public boolean isWinner(Mark m) {
		return (this.hasRow(m) || this.hasColumn(m) || this.hasDiagonal(m));
	}

	public String toString() {

	}

	public void reset() {
		this.fields = new HashMap<Integer[], Mark>();
	}

	public void setField(int x, int y, int z, Mark m) {
		Integer[] coordinates = { x, y, z };
		fields.put(coordinates, m);
	}
}