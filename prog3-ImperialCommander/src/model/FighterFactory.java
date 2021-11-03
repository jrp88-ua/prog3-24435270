package model;

import model.fighters.AWing;
import model.fighters.TIEBomber;
import model.fighters.TIEFighter;
import model.fighters.TIEInterceptor;
import model.fighters.XWing;
import model.fighters.YWing;

public class FighterFactory {

	private FighterFactory() {
	}

	/**
	 * Creates a fighter of the given type for the given mother ship
	 * 
	 * @param type   The type of the fighter
	 * @param mother The mother ship of the fighter
	 * @return The fighter, null if an invalid type is given
	 */
	public static Fighter createFighter(String type, Ship mother) {
		switch (type) {
		case "AWing":
			return new AWing(mother);
		case "XWing":
			return new XWing(mother);
		case "YWing":
			return new YWing(mother);
		case "TIEBomber":
			return new TIEBomber(mother);
		case "TIEFighter":
			return new TIEFighter(mother);
		case "TIEInterceptor":
			return new TIEInterceptor(mother);
		default:
			return null;
		}
	}

}
