package model.fighters;

import model.Fighter;
import model.Ship;

/**
 * @author Javier Rodríguez Pérez - 24435270R
 */
public class XWing extends Fighter {

	/**
	 * @param mother The mother ship
	 */
	public XWing(Ship mother) {
		super(mother);
		addVelocity(10);
		addAttack(20);
	}

	/**
	 * @param f The fighter to copy
	 */
	private XWing(XWing f) {
		super(f);
	}

	@Override
	public XWing copy() {
		return new XWing(this);
	}

	@Override
	public char getSymbol() {
		return 'X';
	}

}
