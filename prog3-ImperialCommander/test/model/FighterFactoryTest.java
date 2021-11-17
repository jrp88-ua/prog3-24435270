package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.fighters.*;

public class FighterFactoryTest {
	Fighter fighter;
	Ship ship;
	@Before
	public void setUp() throws Exception {
		ship = new Ship("Tydirium", Side.REBEL);
	}

	@Test
	public void testCreateFighter() {
		fighter=FighterFactory.createFighter("AWing", ship);
		assertTrue(fighter instanceof AWing);
		fighter=FighterFactory.createFighter("XWing", ship);
		assertTrue(fighter instanceof XWing);
		fighter=FighterFactory.createFighter("YWing", ship);
		assertTrue(fighter instanceof YWing);
		fighter=FighterFactory.createFighter("TIEFighter", ship);
		assertTrue(fighter instanceof TIEFighter);
		fighter=FighterFactory.createFighter("TIEBomber", ship);
		assertTrue(fighter instanceof TIEBomber);
		fighter=FighterFactory.createFighter("TIEInterceptor", ship);
		assertTrue(fighter instanceof TIEInterceptor);
	}
	
	@Test
	public void testCreateFighterWrong() {
		assertNull(FighterFactory.createFighter("Awing", ship));
		
		assertNull(FighterFactory.createFighter("xWing", ship));
		assertNull(FighterFactory.createFighter("ywing", ship));
		assertNull(FighterFactory.createFighter("tieFighter", ship));
		assertNull(FighterFactory.createFighter("TIEbomber", ship));
		assertNull(FighterFactory.createFighter("TIEIntercepto", ship));
		assertNull(FighterFactory.createFighter("ZWing", ship));
	}
	

}
