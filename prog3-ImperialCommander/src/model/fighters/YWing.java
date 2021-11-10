package model.fighters;

import model.Fighter;
import model.Ship;

/**
 * A YWing
 * 
 * @author Javier Rodríguez Pérez - 24435270R
 */
public class YWing extends Fighter {

	/**
	 * @param mother The mother ship
	 */
	public YWing(Ship mother) {
		super(mother);
		addVelocity(-20);
		addAttack(-10);
		addShield(30);
	}

	/**
	 * @param f Fighter to copy
	 */
	private YWing(YWing f) {
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
			if ("TIEFighter".equals(enemy.getType()) || "TIEInterceptor".equals(enemy.getType()))
				d /= 3;
			if ("TIEBomber".equals(enemy.getType()))
				d /= 2;
		}
		return d;
	}

	/**
	 * @return A copy of this fighter
	 */
	@Override
	public YWing copy() {
		return new YWing(this);
	}

	/**
	 * @return The symbol of the fighter
	 */
	@Override
	public char getSymbol() {
		return 'Y';
	}

}
