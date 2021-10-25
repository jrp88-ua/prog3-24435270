package model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * Class that represents a board
 * 
 * @author Javier Rodriguez Perez - 24435270R
 */
public class Board {

	/**
	 * Size of the board
	 */
	private int size;

	/**
	 * Board of the game
	 */
	private Map<Coordinate, Fighter> board;

	/**
	 * Creates a new board with the given size
	 * 
	 * @param size The size of the board
	 */
	public Board(int size) {
		this.size = size;
		board = new HashMap<>();
	}

	/**
	 * Makes the given fighter fight all enemies on the neighborhood
	 * 
	 * @param f The fighter
	 */
	public void patrol(Fighter f) {
		Objects.requireNonNull(f);
		if (!inBoard(f))
			return;
		Set<Coordinate> neigh = getNeighborhood(f.getPosition());
		for (Coordinate c : neigh) {
			fight(c, f, false);
			if (f.isDestroyed()) {
				removeFighter(f);
				return;
			}
		}
	}

	/**
	 * Makes the given fighter fight the enemy in the given coordinate, if there is
	 * an enemy, if not updates the location if not ocupied
	 * 
	 * @param c The coordinate
	 * @param f The fighter
	 * @return The result of the fight, 0 in any other case
	 */
	public int launch(Coordinate c, Fighter f) {
		Objects.requireNonNull(c);
		Objects.requireNonNull(f);
		return fight(c, f, true);
	}

	/**
	 * Makes the given fighter fight the enemy in the given coordinate, if there is
	 * an enemy
	 * 
	 * @param c              The coordinate
	 * @param f              The fighter
	 * @param updatePosition If true, poistions are updated
	 * @return The result of the fight, 0 in any other case
	 */
	private int fight(Coordinate c, Fighter f, boolean updatePosition) {
		Objects.requireNonNull(c);
		Objects.requireNonNull(f);
		if (!inside(c))
			return 0;
		Fighter enemy = getFighter0(c);
		if (enemy == null) {
			// no hay nadie
			if (updatePosition) {
				board.put(c, f);
				f.setPosition(c);
			}
			return 0;
		}
		if (enemy.getSide() == f.getSide()) {
			// esta ocupada por una caza del mismo bando
			// no se hace nada
			return 0;
		}
		int result = f.fight(enemy);
		if (result == 0)
			return 0;
		if (result == 1) {
			f.getMotherShip().updateResults(1);
			enemy.getMotherShip().updateResults(-1);
			removeFighter(enemy);
			if (updatePosition) {
				board.put(c, f);
				f.setPosition(c);
			}
		} else if (result == -1) {
			f.getMotherShip().updateResults(-1);
			enemy.getMotherShip().updateResults(1);
			removeFighter(f);
		}
		return result;
	}

	/**
	 * @param c The central coordinate
	 * @return An ordered set with the coordinates that surround the given
	 *         coordinate and are inside the board
	 */
	public Set<Coordinate> getNeighborhood(Coordinate c) {
		Objects.requireNonNull(c);
		Set<Coordinate> s = c.getNeighborhood();
		Iterator<Coordinate> it = s.iterator();
		while (it.hasNext()) {
			Coordinate n = it.next();
			if (!inside(n))
				it.remove();
		}
		return s;
	}

	/**
	 * @param c The coordinate
	 * @return True if the coordinate is inside the board
	 */
	public boolean inside(Coordinate c) {
		if (c == null)
			return false;
		return (0 <= c.getX() && c.getX() < size) && (0 <= c.getY() && c.getY() < size);
	}

	/**
	 * Removes the given fighter from the board if the board contains the given
	 * fighter
	 * 
	 * @param f The fighter to remove
	 * @return True if the fighter was removed, false otherwise
	 */
	public boolean removeFighter(Fighter f) {
		Objects.requireNonNull(f);
		if (inBoard(f)) {
			Fighter f2 = board.remove(f.getPosition());
			f2.setPosition(null);
			return true;
		}
		return false;
	}

	/**
	 * Checks if a fighter is in the board
	 * 
	 * @param f The fighter
	 * @return True if the fighter is in the board
	 */
	public boolean inBoard(Fighter f) {
		Objects.requireNonNull(f);
		if (f.getPosition() == null)
			return false;
		if (!board.containsKey(f.getPosition()))
			return false;
		return board.get(f.getPosition()).equals(f);
	}

	/**
	 * @param c The coordinate
	 * @return A copy of the fighter in the given coordinate, null if no fighter is
	 *         there
	 */
	public Fighter getFighter(Coordinate c) {
		Fighter f = getFighter0(c);
		if (f == null)
			return null;
		return new Fighter(f);
	}

	/**
	 * @param c The coordinate
	 * @return The fighter in the given coordinate, null if no fighter is there
	 */
	public Fighter getFighter0(Coordinate c) {
		Objects.requireNonNull(c);
		if (board.get(c) == null)
			return null;
		return board.get(c);
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Creates the hash code of this board
	 */
	@Override
	public int hashCode() {
		return getSize();
	}

	/**
	 * Compares this board with the given object
	 * 
	 * @param obj The other object to compare
	 * 
	 * @return True if {@code this == other} or both boards have the same size,
	 *         false otherwise
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof Board)) {
			return false;
		}
		Board other = (Board) obj;
		return getSize() == other.getSize();
	}

	/**
	 * A string representation of the board
	 */
	@Override
	public String toString() {
		return "Board [size=" + size + "]";
	}

}
