package model.fighters;

import model.Fighter;
import model.Ship;

/**
 * An AWing
 * 
 * @author Javier Rodríguez Pérez - 24435270R
 */
public class AWing extends Fighter {

	/**
	 * @param mother The mother ship
	 */
	public AWing(Ship mother) {
		super(mother);
		addVelocity(40);
		addAttack(5);
		addShield(-50);
	}

	/**
	 * @param f The fighter to copy
	 */
	private AWing(AWing f) {
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
			if ("TIEBomber".equals(enemy.getType()))
				d *= 2;
		}
		return d;
	}

	/**
	 * @return A copy of this fighter
	 */
	@Override
	public AWing copy() {
		return new AWing(this);
	}

	/**
	 * @return The symbol of the fighter
	 */
	@Override
	public char getSymbol() {
		return 'A';
	}

}
