package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class MyFighterTestP2 {

	@Test
	public void testGetDamage() {
		Fighter f = new Fighter("A", null);
		int n = 1;
		assertEquals(((n * Fighter.DEF_ATTACK) / 300), f.getDamage(n, null));
	}
	
	@Test
	public void testFight() {
		Fighter a = new Fighter(null, null);
		Fighter b = new Fighter(null, null);
		a.addAttack(500000);
		a.addShield(500000);
		a.addVelocity(500000);
		assertEquals(1, a.fight(b));
		assertTrue(b.isDestroyed());
	}

}
