package model.fighters;

import model.Fighter;
import model.Ship;
/**
 * new Fighter subclass
 * @author paco
 *
 */
public class TIEAdvancedFighter extends Fighter {

	/**
	 * constructor
	 * @param mother motherShip
	 */
	public TIEAdvancedFighter(Ship mother) {
		super(mother);
		addVelocity(10);
		addShield(-50);
		addAttack(5);
	}

	/**
	 * copy constructor
	 * @param other fighter
	 */
	private TIEAdvancedFighter(TIEAdvancedFighter other) {
        super(other);	
	}

	@Override
	public Fighter copy() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public char getSymbol() {
		// TODO Auto-generated method stub
		return 0;
	}

}
