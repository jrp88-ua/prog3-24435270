package model;

import java.util.Arrays;

/**
 * Enumeration that represents the possible sides of a ship
 * 
 * @author Javier Rodríguez Pérez - 24435270R
 */
public enum Side {
	/**
	 * Imperial side
	 */
	IMPERIAL("TIEFighter", "TIEBomber", "TIEInterceptor"),
	/**
	 * Rebel side
	 */
	REBEL("XWing", "YWing", "AWing");

	/**
	 * The possible fighter types for the side
	 */
	private final String[] types;

	/**
	 * @param types The possible fighter types for the side
	 */
	private Side(String... types) {
		this.types = types;
	}

	/**
	 * @return The possible fighter types for the side
	 */
	public String[] getTypes() {
		return Arrays.copyOf(types, types.length);
	}
	
}
