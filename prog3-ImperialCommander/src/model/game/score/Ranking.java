package model.game.score;

import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

public class Ranking<ScoreType extends Score<?>> {

	/**
	 * The scores
	 */
	private final SortedSet<ScoreType> scores = new TreeSet<>();

	/**
	 * Constructor
	 */
	public Ranking() {
	}

	/**
	 * @param score score to add
	 */
	public void addScore(ScoreType score) {
		scores.add(Objects.requireNonNull(score));
	}

	/**
	 * @return the winner score
	 */
	public ScoreType getWinner() {
		return scores.first();
	}

	/**
	 * @return the ranking
	 */
	public SortedSet<ScoreType> getSortedRanking() {
		return scores;
	}

}
