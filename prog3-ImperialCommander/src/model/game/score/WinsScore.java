package model.game.score;

import model.Side;

/**
 * Represents the wins score
 * 
 * @author Javier Rodríguez Pérez - 24435270R
 */
public class WinsScore extends Score<Integer> {

	/**
	 * @param side the side
	 */
	public WinsScore(Side side) {
		super(side);
	}

	@Override
	public void score(Integer sc) {
		if (sc == 1)
			this.score++;
	}

}
