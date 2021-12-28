package model.game.score;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * Class that represents a ranking
 * 
 * @param <ScoreType> The score type
 * @author Javier Rodríguez Pérez - 24435270R
 */
public class Ranking<ScoreType extends Score<?>> {

	/**
	 * The scores
	 */
	private final List<ScoreType> scores = new ArrayList<>();

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
		SortedSet<ScoreType> score = getSortedRanking();
		if (score.size() == 0)
			throw new RuntimeException("ERROR: ranking vacio");
		return score.first();
	}

	/**
	 * @return the ranking
	 */
	public SortedSet<ScoreType> getSortedRanking() {
		return new TreeSet<>(scores);
	}

	@Override
	public String toString() {
		SortedSet<ScoreType> set = getSortedRanking();
		if (set.size() == 0)
			return "";
		String delimitier = " | ";
		StringBuilder str = new StringBuilder(delimitier);
		set.forEach((e) -> str.append(e.toString()).append(delimitier));
		return str.toString();
	}

}
