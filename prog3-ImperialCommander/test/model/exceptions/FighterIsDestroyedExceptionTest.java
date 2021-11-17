package model.exceptions;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Board;
import model.Coordinate;
import model.Fighter;
import model.FighterFactory;
import model.Ship;
import model.Side;

public class FighterIsDestroyedExceptionTest {

	Fighter rebel, imperial;
	Ship rebelShip, imperialShip;
	Board board;
	Coordinate c, c1, c2;
	
	@Before
	public void setUp() throws Exception {
		rebelShip = new Ship("Tydirium", Side.REBEL);
		imperialShip = new Ship("Lanzadera T-4a", Side.IMPERIAL);
		rebel = FighterFactory.createFighter("AWing", rebelShip);
		imperial = FighterFactory.createFighter("TIEBomber", imperialShip);
		board = new Board(10);
	}

	/* Se destruye un caza enemigo. Se comprueba que Fighter.fight lanza la excepción FighterIsDestroyedException.
	 */
	@Test
	public void FighterIsDestroyedExceptionFight1Test() throws FighterIsDestroyedException {
		
		imperial.addShield(-300);
		
		try {
			rebel.fight(imperial);
			fail ("ERROR: Se debió lanzar la excepción FighterIsDestroyedException");
		} catch (FighterIsDestroyedException e) {
			assertTrue(e.getMessage().startsWith("ERROR:"));	
		}
		
	}
	
	/* Se destruye previamente el caza que invoca a fight. Se comprueba que se lanza ña excepción 
	 * FighterIsDestroyedException.
	 */
	@Test
	public void FighterIsDestroyedExceptionFight2Test() throws FighterIsDestroyedException {
	
		rebel.addShield(-300);
		try {
			rebel.fight(imperial);	
			fail ("ERROR: Se debió lanzar la excepción FighterIsDestroyedException");
		}  catch (FighterIsDestroyedException e) {
			assertTrue(e.getMessage().startsWith("ERROR:"));	
		}
	}

	/* Se pone en una posición vacía del tablero un caza intacto. Se intenta poner en esa misma posición
	 * otro caza del bando contrario que sí está destruído. Debe lanzar excepción RuntimeException 
	 * (y no FighterIsDestroyedException) en este segundo Board.launch ya que el segundo caza que se 
	 * intenta poner está destruído. 
	 */
	@Test
	public void FighterIsDestroyedExceptionLaunch1Test()  {
		c = new Coordinate(4,5);
		try {
			board.launch(c, imperial);
		} catch (Exception e) {
			fail ("ERROR: No se debió originar una "+e.getClass().getSimpleName());
		} 
		rebel.addShield(-300);
		try {
			board.launch(c, rebel);
			fail ("ERROR: Se debió originar una RuntimeException.");
		} catch (RuntimeException e1) {
		} catch (Exception e2) {
			fail ("ERROR: No se debió originar una "+e2.getClass().getSimpleName());
		}	
	}
	
	/* Se pone en una posición vacía del tablero un caza ya destruído. Se intenta poner en esa misma posición
	 * otro caza del bando contrario que no está destruído. Se ebe lanzar excepción RuntimeException 
	 * (y no FighterIsDestroyedException) en este segundo Board.launch ya que el primer caza del tablero está
	 * destruído. 
	 */
	@Test
	public void FighterIsDestroyedExceptionLaunch2Test() throws OutOfBoundsException, FighterAlreadyInBoardException  {
		c = new Coordinate(4,5);
		imperial.addShield(-300);
		try {
			board.launch(c, imperial);
		} catch (Exception e) {
			fail ("ERROR: No se debió originar una "+e.getClass().getSimpleName());
		} 
		try {
			board.launch(c, rebel);
			fail ("ERROR: Se debió originar una RuntimeException.");
		} catch (RuntimeException e1) {
		} catch (Exception e2) {
			fail ("ERROR: No se debió originar una "+e2.getClass().getSimpleName());
		}	
	}
	
	/* Se pone en una posición vacía del tablero un caza no destruído. Se pone en una  posición 
	 * vecina y vacía otro caza del bando contrario que sí está destruído. Se pone a patrullar 
	 * al primer caza. Debe lanzar excepción RuntimeException (y no FighterIsDestroyedException)
	 * 
	 */
	@Test
	public void FighterIsDestroyedExceptionPatrol1Test()  {
		c1 = new Coordinate(4,5);
		c2 = new Coordinate(3,5);
		imperial.addShield(-300);
		try {
			board.launch(c1, rebel);
			board.launch(c2, imperial);
		} catch (Exception e) {
			fail ("ERROR: No se debió originar una "+e.getClass().getSimpleName());
		} 
		
		try {
			board.patrol(rebel);
			fail ("ERROR: Se debió originar una RuntimeException.");
		} catch (RuntimeException e1) {
			
		} catch (Exception e2) {
			fail ("ERROR: No se debió originar una "+e2.getClass().getSimpleName());
		}	
	}
	
	/* Se pone en una posición vacía del tablero un caza ya destruído. Se pone en una  posición 
	 * vecina y vacía otro caza del bando contrario que no está destruído. Se pone a patrullar 
	 * al primer caza. Debe lanzar excepción RuntimeException (y no FighterIsDestroyedException)
	 * 
	 */
	@Test
	public void FighterIsDestroyedExceptionPatrol2Test()  {
		c1 = new Coordinate(4,5);
		c2 = new Coordinate(3,5);
		rebel.addShield(-300);
		try {
			board.launch(c1, rebel);
			board.launch(c2, imperial);
		} catch (Exception e) {
			fail ("ERROR: No se debió originar una "+e.getClass().getSimpleName());
		} 
		try {
			board.patrol(rebel);
			fail ("ERROR: Se debió originar una RuntimeException.");
		} catch (RuntimeException e1) {
		} catch (Exception e2) {
			fail ("ERROR: No se debió originar una "+e2.getClass().getSimpleName());
		}	
	}

}
