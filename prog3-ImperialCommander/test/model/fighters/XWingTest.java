package model.fighters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Fighter;
import model.FighterFactory;
import model.Ship;
import model.Side;

public class XWingTest {
	
	Fighter fighter;
	Ship rebelShip, imperialShip;
	
	@Before
	public void setUp() throws Exception {
		imperialShip = new Ship("Lanzadera T-4a", Side.IMPERIAL);
		rebelShip = new Ship("Tydirium", Side.REBEL);
		fighter = FighterFactory.createFighter("XWing",rebelShip);
	}

	/* Se comprueba que los valores iniciales de velocity, attack y shield son los
	 * correctos  
	 */	
	@Test 
	public void testValues( ) {
		assertEquals(110,fighter.getVelocity());
		assertEquals(100, fighter.getAttack());
		assertEquals(80, fighter.getShield());
	}
	
	/* Se comprueba que copy() hace una copia correcta */
	@Test
	public void testCopy() {
		Fighter aux = fighter.copy();
		assertEquals(fighter, aux);
		assertEquals(fighter.getVelocity(), aux.getVelocity());
		assertEquals(fighter.getAttack(), aux.getAttack());
		assertEquals(fighter.getShield(), aux.getShield());
		assertEquals(fighter.getMotherShip(), aux.getMotherShip());
		assertEquals(fighter.getSymbol(), aux.getSymbol());
		assertEquals(fighter.getPosition(), aux.getPosition());
		assertNotSame(fighter, aux);;
	}

	/* Se comprueba que getSymbol devuelve el símbolo correcto */
	@Test
	public void testGetSymbol() {
		assertEquals('X',fighter.getSymbol());
	}

	/* Se crea un TIEFighter, un TIEBomber y un TIEInterceptor. Se comprueba que el XWing le proporciona
	 * un daño al TIEFighter de 16, al TIEBomber de 16 y al TIEInterceptor de 16 con n=50 
	 */
	@Test
	public final void testGetDamage() {
		
		Fighter enemy1 = FighterFactory.createFighter("TIEFighter",imperialShip);
		Fighter enemy2 = FighterFactory.createFighter("TIEBomber",imperialShip);
		Fighter enemy3 = FighterFactory.createFighter("TIEInterceptor",imperialShip);
		assertEquals (16, fighter.getDamage(50, enemy1));
		assertEquals (16, fighter.getDamage(50, enemy2));
		assertEquals (16, fighter.getDamage(50, enemy3));
		
	}

}
