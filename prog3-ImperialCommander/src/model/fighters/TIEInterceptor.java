package model.fighters;

import model.Fighter;
import model.Ship;

/**
 * A TIEInterceptor
 * 
 * @author Javier Rodríguez Pérez - 24435270R
 */
public class TIEInterceptor extends Fighter {

	/**
	 * @param mother The mother ship
	 */
	public TIEInterceptor(Ship mother) {
		super(mother);
		addVelocity(45);
		addAttack(5);
		addShield(-20);
	}

	/**
	 * @param f The fighter to copy
	 */
	private TIEInterceptor(TIEInterceptor f) {
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
			if ("YWing".equals(enemy.getType()))
				d *= 2;
			if ("AWing".equals(enemy.getType()))
				d /= 2;
		}
		return d;
	}

	/**
	 * @return A copy of this fighter
	 */
	@Override
	public TIEInterceptor copy() {
		return new TIEInterceptor(this);
	}

	/**
	 * @return The symbol of the fighter
	 */
	@Override
	public char getSymbol() {
		return 'i';
	}

}
