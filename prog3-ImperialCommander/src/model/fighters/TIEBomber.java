package model.fighters;

import model.Fighter;
import model.Ship;

/**
 * A TIEBomber
 * 
 * @author Javier Rodríguez Pérez - 24435270R
 */
public class TIEBomber extends Fighter {

	/**
	 * @param mother The mother ship
	 */
	public TIEBomber(Ship mother) {
		super(mother);
		addVelocity(-30);
		addAttack(-50);
		addShield(35);
	}

	/**
	 * @param f The fighter to copy
	 */
	private TIEBomber(TIEBomber f) {
		super(f);
	}

	/**
	 * Computes the damage to deal to the given enemy
	 * 
	 * @param n     A random number
	 * @param enemy The enemy
	 * @return The damage to deal
	 */
	@Override
	public int getDamage(int n, Fighter enemy) {
		int d = super.getDamage(n, enemy);
		if (enemy != null) {
			if ("XWing".equals(enemy.getType()) || "YWing".equals(enemy.getType()))
				d /= 2;
			if ("AWing".equals(enemy.getType()))
				d /= 3;
		}
		return d;
	}

	/**
	 * @return A copy of this fighter
	 */
	@Override
	public TIEBomber copy() {
		return new TIEBomber(this);
	}

	/**
	 * @return The symbol of the fighter
	 */
	@Override
	public char getSymbol() {
		return 'b';
	}

}
