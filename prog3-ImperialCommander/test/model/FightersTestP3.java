package model;

/*
 * esta clase tiene los mismos tests que FightersPreTest.java 
 */


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Test;

import model.fighters.AWing;
import model.fighters.TIEBomber;
import model.fighters.XWing;

public class FightersTestP3 {

	Ship rebelShip, imperialShip;
	Board board;
	
	@Before
	public void setUp() throws Exception {
		rebelShip = new Ship("Tydirium", Side.REBEL);
		imperialShip = new Ship("Lanzadera T-4a", Side.IMPERIAL);
		board = new Board(5);
	}

	@Test
	public void testCreateFighter() {
		Fighter rebel = FighterFactory.createFighter("AWing", rebelShip);
		assertTrue(rebel instanceof Fighter);
		assertTrue(rebel instanceof AWing);
		
		Fighter imperial = FighterFactory.createFighter("TIEBomber", imperialShip);
		assertTrue(imperial instanceof Fighter);
		assertTrue(imperial instanceof TIEBomber);	
	}
	
	@Test
	public void testCopy() {
		Fighter fx = FighterFactory.createFighter("XWing", rebelShip);
		Fighter fy = FighterFactory.createFighter("YWing", rebelShip);
		assertEquals("copy YWing",fy,fy.copy());
		XWing fxx = (XWing)fx;
		XWing fxcopy = (XWing) fx.copy();
		assertEquals("copy XWing as XWing",fxx,fxcopy);
	}
	
	@Test
	public void testTIEInterceptor() {
		Fighter ti = FighterFactory.createFighter("TIEInterceptor", imperialShip);
		
		assertEquals("velocity",145,ti.getVelocity());
		assertEquals("attack",85,ti.getAttack());
		assertEquals("shield",60,ti.getShield());
		assertEquals("symbol",'i',ti.getSymbol());
		
		Fighter fx = FighterFactory.createFighter("XWing", rebelShip);
		Fighter fy = FighterFactory.createFighter("YWing", rebelShip);
		Fighter fa = FighterFactory.createFighter("AWing", rebelShip);
		
		assertEquals("getDamage(50,XWing)",14,ti.getDamage(50, fx));
		assertEquals("getDamage(50,YWing)",28,ti.getDamage(50, fy));
		assertEquals("getDamage(50,XWing)",7,ti.getDamage(50, fa));
		
		// añade métodos con tests similares para los demás tipos de cazas
	}
}
