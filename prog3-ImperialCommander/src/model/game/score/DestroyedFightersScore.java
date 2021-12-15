package model.game.score;

import model.Fighter;
import model.Side;

/**
 * Represents the destroyed fighters score
 * 
 * @author Javier Rodríguez Pérez - 24435270R
 */
public class DestroyedFightersScore extends Score<Fighter> {

	/**
	 * @param side the side
	 */
	public DestroyedFightersScore(Side side) {
		super(side);
	}

	@Override
	public void score(Fighter sc) {
		if (sc != null)
			this.score += sc.getValue();
	}

}
