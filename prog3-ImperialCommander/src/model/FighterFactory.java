package model;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * A class to create fighters
 * 
 * @author Javier Rodríguez Pérez - 24435270R
 */
public class FighterFactory {

	/**
	 * Don't instanciate this class >:(
	 */
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
		Objects.requireNonNull(type);
		Objects.requireNonNull(mother);
		try {
			@SuppressWarnings("unchecked")
			Class<? extends Fighter> clazz = (Class<? extends Fighter>) Class.forName("model.fighters." + type);
			Constructor<? extends Fighter> constructor = clazz.getConstructor(Ship.class);
			return constructor.newInstance(mother);
		} catch (ClassCastException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | ClassNotFoundException | NoSuchMethodException | SecurityException e) {
			return null;
		}
	}

}
