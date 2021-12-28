package model.game.score;

import java.util.Objects;

import model.Side;

/**
 * Represents a score
 * 
 * @param <T> the score type
 * @author Javier Rodríguez Pérez - 24435270R
 */
public abstract class Score<T> implements Comparable<Score<T>> {

	/**
	 * The score
	 */
	protected int score = 0;
	/**
	 * The side
	 */
	private Side side;

	/**
	 * @param side The side
	 */
	public Score(Side side) {
		Objects.requireNonNull(side);
		this.side = side;
	}

	/**
	 * Sets the score
	 * 
	 * @param sc The score
	 */
	public abstract void score(T sc);

	@Override
	public int compareTo(Score<T> other) {
		int c = Integer.compare(this.getScore(), other.getScore()) * -1;
		if (c == 0) {
			return side.compareTo(other.side);
		}
		return c;
	}

	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}

	@Override
	public String toString() {
		return "Player " + side.name() + ": " + getScore();
	}

}
