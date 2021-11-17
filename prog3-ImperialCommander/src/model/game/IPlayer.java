package model.game;

/**
 * @author Javier Rodríguez Pérez - 24435270R
 */
public interface IPlayer {
	
	void setBoard(GameBoard gb);
	
	GameShip getGameShip();
	
	void initFighters();

	boolean isFleetDestroyed();
	
	String showShip();
	
	void purgeFleet();
	
	boolean nextPlay();
	
}
