package model.fighters;

import model.Fighter;
import model.Ship;

/**
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

	@Override
	public int getDamage(int n, Fighter enemy) {
		int d = super.getDamage(n, enemy);
		if (enemy != null) {
			if ("TIEBomber".equals(enemy.getType()))
				d *= 2;
		}
		return d;
	}

	@Override
	public AWing copy() {
		return new AWing(this);
	}

	@Override
	public char getSymbol() {
		return 'A';
	}

}
