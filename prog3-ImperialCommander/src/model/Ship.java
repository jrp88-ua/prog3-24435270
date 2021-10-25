package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Class that represents a ship
 * 
 * @author Javier Rodríguez Pérez - 24435270R
 *
 */
public class Ship {

	/**
	 * List of foghters
	 */
	private List<Fighter> fleet = new ArrayList<>();
	/**
	 * Name of the ship
	 */
	private String name;
	/**
	 * Side of the ship
	 */
	private Side side;
	/**
	 * wins of the ship
	 */
	private int wins;
	/**
	 * losses of the ship
	 */
	private int losses;

	/**
	 * @param name The name of the ship
	 * @param side The side of the ship
	 */
	public Ship(String name, Side side) {
		this.name = name;
		this.side = side;
		this.wins = 0;
		this.losses = 0;
	}

	/**
	 * @return A string representation of the current not destroyed fighters with
	 *         the format used by {@link #addFighters(String)}
	 */
	public String myFleet() {
		StringBuilder sb = new StringBuilder();
		Map<String, Integer> map = new LinkedHashMap<>();
		fleet.forEach((f) -> map.compute(f.getType(), (k, v) -> {
			if (f.isDestroyed())
				return v;
			return v == null ? 1 : v + 1;
		}));
		Iterator<Entry<String, Integer>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Integer> e = it.next();
			sb.append(e.getValue()).append('/').append(e.getKey());
			if (it.hasNext())
				sb.append(':');
		}
		return sb.toString();
	}

	/**
	 * @return A string representing the fleet of the ship
	 */
	public String showFleet() {
		StringBuilder b = new StringBuilder();
		for (int i = 0; i < fleet.size(); i++) {
			Fighter f = fleet.get(i);
			b.append(f);
			if (f.isDestroyed())
				b.append(" (X)");
			b.append('\n');
		}
		return b.toString();
	}

	/**
	 * Removes all destroyed fighters from the fleet
	 */
	public void purgeFleet() {
		Iterator<Fighter> it = fleet.iterator();
		while (it.hasNext()) {
			Fighter f = it.next();
			if (f.isDestroyed())
				it.remove();
		}
	}

	/**
	 * Gets the first not destroyed fighter that matches the given type. If no
	 * fighter matches the criteria returns null
	 * 
	 * @param type The type to search, empty or null to search for all types
	 * @return The first fighter that matches the criteria
	 */
	public Fighter getFirstAvailableFighter(String type) {
		if (type == null || type.isEmpty()) {
			for (Fighter fighter : fleet)
				if (!fighter.isDestroyed())
					return fighter;
		}
		for (Fighter f : fleet)
			if (!f.isDestroyed() && f.getType().equals(type))
				return f;
		return null;
	}

	/**
	 * Adds fighters to this ship. The fighters are represented as a string with the
	 * format
	 * {@code ammount_to_add/name_of_fighter:ammount_to_add/name_of_fighter:...}
	 * 
	 * @param fighters The fighters
	 */
	public void addFighters(String fighters) {
		String[] fs = fighters.split(":");
		for (String f : fs) {
			String[] sp = f.split("/");
			int amm = Integer.parseInt(sp[0]);
			for (int i = 0; i < amm; i++) {
				Fighter fighter = new Fighter(sp[1], this);
				fleet.add(fighter);
			}
		}
	}

	/**
	 * Updates the win/losses depending on if r is 1/-1
	 * 
	 * @param r amount to update. Only can be 1 or -1
	 */
	public void updateResults(int r) {
		if (r == 1) {
			addWins(1);
		} else if (r == -1) {
			addLosses(1);
		}
	}

	@Override
	public String toString() {
		return new StringBuilder("Ship [").append(getName()).append(' ').append(getWins()).append('/').append(getLosses())
				.append("] ").append(myFleet()).toString();
	}

	/**
	 * only for testing
	 * 
	 * @return the fleet
	 */
	List<Fighter> getFleetTest() {
		return fleet;
	}

	/**
	 * @return the fleet
	 */
	public List<Fighter> getFleet() {
		List<Fighter> l = new ArrayList<>(this.fleet.size());
		this.fleet.forEach((f) -> l.add(new Fighter(f)));
		return fleet;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the side
	 */
	public Side getSide() {
		return side;
	}

	/**
	 * @param side the side to set
	 */
	public void setSide(Side side) {
		this.side = side;
	}

	/**
	 * @return the wins
	 */
	public int getWins() {
		return wins;
	}

	/**
	 * @param wins the wins to set
	 */
	public void setWins(int wins) {
		this.wins = wins;
	}

	/**
	 * @param wins Number of wins to add
	 */
	public void addWins(int wins) {
		setWins(getWins() + wins);
	}

	/**
	 * @return the losses
	 */
	public int getLosses() {
		return losses;
	}

	/**
	 * @param losses the losses to set
	 */
	public void setLosses(int losses) {
		this.losses = losses;
	}

	/**
	 * @param losses Losses to add
	 */
	public void addLosses(int losses) {
		setLosses(getLosses() + losses);
	}

}
