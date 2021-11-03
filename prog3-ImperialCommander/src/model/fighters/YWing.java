package model.fighters;

import model.Fighter;
import model.Ship;

/**
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

	@Override
	public YWing copy() {
		return new YWing(this);
	}

	@Override
	public char getSymbol() {
		return 'Y';
	}

}
