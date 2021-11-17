package model.fighters;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Fighter;
import model.FighterFactory;
import model.Ship;
import model.Side;

public class TIEInterceptorTest {
	
	Fighter fighter;
	Ship imperialShip, rebelShip;
	
	@Before
	public void setUp() throws Exception {
		
		rebelShip = new Ship("Tydirium", Side.REBEL);
		imperialShip = new Ship("Lanzadera T-4a", Side.IMPERIAL);
		fighter = FighterFactory.createFighter("TIEInterceptor",imperialShip);
	}

	/* Se comprueba que los valores iniciales de velocity, attack y shield son los
	 * correctos  
	 */		
	@Test 
	public void testValues( ) {
		assertEquals(145,fighter.getVelocity());
		assertEquals(85, fighter.getAttack());
		assertEquals(60, fighter.getShield());
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
		assertEquals('i',fighter.getSymbol());
	}
	

	/* Se crea un AWing, un XWing y un YWing. Se comprueba que el TIEInterceptor le proporciona
	 * un daño al AWing de 2, al XWing de 3 y al YWing de 3 con n=75 
	 */
	@Test
	public final void testGetDamage() {		
		Fighter enemy1 = FighterFactory.createFighter("AWing",rebelShip);
		Fighter enemy2 = FighterFactory.createFighter("XWing",rebelShip);
		Fighter enemy3 = FighterFactory.createFighter("YWing",rebelShip);
		assertEquals (10, fighter.getDamage(75, enemy1));
		assertEquals (21, fighter.getDamage(75, enemy2));
		assertEquals (42, fighter.getDamage(75, enemy3));		
	}
}
