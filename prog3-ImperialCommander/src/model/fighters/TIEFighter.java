package model.fighters;

import model.Fighter;
import model.Ship;

/**
 * A TIEFighter
 * 
 * @author Javier Rodríguez Pérez - 24435270R
 */
public class TIEFighter extends Fighter {

	/**
	 * @param mother The mother ship
	 */
	public TIEFighter(Ship mother) {
		super(mother);
		addVelocity(10);
		addAttack(5);
		addShield(-10);
	}

	/**
	 * @param f The fighter to copy
	 */
	public TIEFighter(TIEFighter f) {
		super(f);
	}

	/**
	 * @return A copy of this fighter
	 */
	@Override
	public TIEFighter copy() {
		return new TIEFighter(this);
	}

	/**
	 * @return The symbol of the fighter
	 */
	@Override
	public char getSymbol() {
		return 'f';
	}

}
