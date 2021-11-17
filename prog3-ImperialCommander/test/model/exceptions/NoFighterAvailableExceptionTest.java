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

public class NoFighterAvailableExceptionTest {
	
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
		
	}
	
	/* Se intenta buscar un caza en una nave vacía. Se comprueba que se lanza la excepción
	 * NoFighterAvailableException */
	@Test
	public void getFirstAvailableFighterInEmptyShipTest() {
		try {
			imperialShip.getFirstAvailableFighter("TIEBomber");
			fail("ERROR: Debió lanzar la excepción NoFighterAvailableException");
		} catch (NoFighterAvailableException e) {	
			assertTrue(e.getMessage().startsWith("ERROR:"));
		}
	}
	
	/* Se intenta buscar un caza que no está en una nave con figthers. Se comprueba que 
	 * se lanza la excepción NoFighterAvailableException */
	@Test
	public void getFirstAvailableFighterNoExistInNotEmptyShipTest() {
		rebelShip.addFighters("1/XWing:3/AWing:3/YWing:1/TIEBomber:45/TIEInterceptor");
		try {
			rebelShip.getFirstAvailableFighter("TIEFighter");
			fail("ERROR: Debió lanzar la excepción NoFighterAvailableException");
		} catch (NoFighterAvailableException e) {	
			assertTrue(e.getMessage().startsWith("ERROR:"));
		}
	}
	
	/* Se intenta buscar un caza con un type erroneo en una nave con figthers. Se comprueba que 
	 * se lanza la excepción NoFighterAvailableException */
	@Test
	public void getFirstAvailableFighterUnknownFighterTest() {
		rebelShip.addFighters("1/XWing:3/AWing:3/YWing:1/TIEBomber:45/TIEInterceptor:3/UWing");
		try {
			rebelShip.getFirstAvailableFighter("UWing");
			fail("ERROR: Debió lanzar la excepción NoFighterAvailableException");
		} catch (NoFighterAvailableException e) {	
			assertTrue(e.getMessage().startsWith("ERROR:"));
		}
	}
	
		
	/* Se intenta buscar un caza que está en una nave con figthers. Se comprueba que 
	 * NO se lanza la excepción NoFighterAvailableException. Este mismo caza se destruye.
	 * Se vuelve a buscar y se comprueba que ahora SÍ lanza la excepción NoFighterAvailableException */
	@Test
	public void getFirstAvailableFighterExistDestroyedInNotEmptyShipTest1() {
		rebelShip.addFighters("1/XWing:3/AWing:3/YWing:1/TIEBomber:45/TIEInterceptor");
		Fighter f=null;
		try {
			f = rebelShip.getFirstAvailableFighter("TIEBomber");
		} catch (NoFighterAvailableException e) {	
			fail("ERROR: NO debió lanzar la excepción NoFighterAvailableException");
		}
		f.addShield(-300);
		try {
			rebelShip.getFirstAvailableFighter("TIEBomber");
			fail("ERROR: Debió lanzar la excepción NoFighterAvailableException");
		} catch (NoFighterAvailableException e) {	
			assertTrue(e.getMessage().startsWith("ERROR:"));
		}	
	}
	
	/* Se destruyen todos los cazas de una nave. Se intenta buscar el primer caza no destruído.
	 * Se comprueba que se lanza la excepción NoFighterAvailableException
	 */
	@Test
	public void getFirstAvailableFighterExistDestroyedInNotEmptyShipTest2() {
		rebelShip.addFighters("1/XWing:3/AWing:3/YWing:1/TIEBomber:45/TIEInterceptor");
		for (Fighter f : rebelShip.getFleetTest()) {
			f.addShield(-300);
		}
		
		try {
			rebelShip.getFirstAvailableFighter("");
			fail("ERROR: Debió lanzar la excepción NoFighterAvailableException");
		} catch (NoFighterAvailableException e) {	
			assertTrue(e.getMessage().startsWith("ERROR:"));
		}
	}

}
