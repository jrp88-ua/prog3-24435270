package model.fighters;

import model.Fighter;
import model.Ship;

/**
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

	@Override
	public TIEInterceptor copy() {
		return new TIEInterceptor(this);
	}

	@Override
	public char getSymbol() {
		return 'i';
	}

}
