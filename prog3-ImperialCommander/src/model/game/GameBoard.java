package model.game;

import model.Board;
import model.Coordinate;
import model.Side;
import model.exceptions.InvalidSizeException;

/**
 * Class that represents a game board
 * 
 * @author Javier Rodríguez Pérez - 24435270R
 */
public class GameBoard extends Board {

	/**
	 * {@inheritDoc}
	 */
	public GameBoard(int size) throws InvalidSizeException {
		super(size);
	}

	/**
	 * @param side The side to count fighters
	 * @return The amount of Fighters in the board for the given side
	 */
	public int numFighters(Side side) {
		return (int) board.values().stream().filter((f) -> f.getSide() == side).count();
	}

	/**
	 * @return A string representation of the board
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("  ");
		for (int i = 0; i < getSize(); i++)
			sb.append(i);
		sb.append("\n  ");
		for (int i = 0; i < getSize(); i++)
			sb.append("-");

		sb.append("\n");

		for (int i = 0; i < getSize(); i++) {
			sb.append(i);
			sb.append("|");
			for (int j = 0; j < getSize(); j++) {
				Coordinate c = new Coordinate(j, i);
				sb.append(board.containsKey(c) ? board.get(c).getSymbol() : " ");
			}
			sb.append("\n");
		}

		return sb.toString();
	}

}
