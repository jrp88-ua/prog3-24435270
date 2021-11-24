package model.game;

import model.Ship;

/**
 * Represents a player
 * 
 * @author Javier Rodríguez Pérez - 24435270R
 */
public interface IPlayer {

	/**
	 * Assigns the board to the player
	 * 
	 * @param gb The board
	 */
	void setBoard(GameBoard gb);

	/**
	 * Test method
	 * 
	 * @return The ship of the player
	 */
	GameShip getGameShip();

	/**
	 * Adds fighters to the player ship
	 */
	void initFighters();

	/**
	 * @return The same as {@link GameShip#isFleetDestroyed()}
	 */
	boolean isFleetDestroyed();

	/**
	 * @return A representation of the ship and of the fleet separated by a line
	 */
	String showShip();

	/**
	 * Calls {@link Ship#purgeFleet()} on the player ship
	 */
	void purgeFleet();

	/**
	 * Does the next player play
	 * 
	 * @return True if the player keeps playing, false otherwise
	 */
	boolean nextPlay();

}
