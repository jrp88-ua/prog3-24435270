package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import model.fighters.AWing;
import model.fighters.TIEBomber;
import model.fighters.XWing;

public class FightersPreTest {

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

		assertNull(FighterFactory.createFighter("No existe xd", imperialShip));
	}

	@Test
	public void testCopy() {
		Fighter xW = FighterFactory.createFighter("XWing", rebelShip);
		assertNotNull("FighterFactory.createFighter returned null", xW);
		String[] types = { "YWing", "AWing", "TIEBomber", "TIEFighter", "TIEInterceptor" };
		for (String t : types) {
			Fighter fighter = FighterFactory.createFighter(t, rebelShip);
			assertNotNull("FighterFactory.createFighter returned null for " + t, fighter);
			assertEquals("copy " + types, fighter, fighter.copy());
			assertNotEquals(xW, fighter);
		}
	}

	@Test
	public void testTIEInterceptor() {
		Fighter ti = FighterFactory.createFighter("TIEInterceptor", imperialShip);

		assertEquals("velocity", 145, ti.getVelocity());
		assertEquals("attack", 85, ti.getAttack());
		assertEquals("shield", 60, ti.getShield());
		assertEquals("symbol", 'i', ti.getSymbol());

		Fighter fx = FighterFactory.createFighter("XWing", rebelShip);
		Fighter fy = FighterFactory.createFighter("YWing", rebelShip);
		Fighter fa = FighterFactory.createFighter("AWing", rebelShip);

		assertEquals("getDamage(50,XWing)", 14, ti.getDamage(50, fx));
		assertEquals("getDamage(50,YWing)", 28, ti.getDamage(50, fy));
		assertEquals("getDamage(50,XWing)", 7, ti.getDamage(50, fa));
	}

	@Test
	public void testTIEFighter() {
		Fighter ti = FighterFactory.createFighter("TIEFighter", imperialShip);

		assertEquals("velocity", Fighter.DEF_VELOCITY + 10, ti.getVelocity());
		assertEquals("attack", Fighter.DEF_ATTACK + 5, ti.getAttack());
		assertEquals("shield", Fighter.DEF_SHIELD - 10, ti.getShield());
		assertEquals("symbol", 'f', ti.getSymbol());
	}
}
