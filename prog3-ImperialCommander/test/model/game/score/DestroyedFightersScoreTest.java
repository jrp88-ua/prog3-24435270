package model.game.score;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Fighter;
import model.FighterFactory;
import model.Ship;
import model.Side;
import model.game
.GameShip;public class DestroyedFightersScoreTest {
    Fighter tieBomber, tieInterceptor, tieFighter, xWing, yWing, aWing;
    GameShip imperialShip, rebelShip;
    Score<Fighter> scRebel, scImperial;
	@Before
	public void setUp() throws Exception {
		imperialShip = new GameShip("ImperialShip",Side.IMPERIAL);
		rebelShip = new GameShip("RebelShip",Side.REBEL);
		tieBomber = FighterFactory.createFighter("TIEBomber", imperialShip);
		tieInterceptor = FighterFactory.createFighter("TIEInterceptor", imperialShip);
		tieFighter = FighterFactory.createFighter("TIEFighter", imperialShip);
		xWing = FighterFactory.createFighter("XWing", rebelShip);
		yWing = FighterFactory.createFighter("YWing", rebelShip);
		aWing = FighterFactory.createFighter("AWing", rebelShip);
		scImperial = new DestroyedFightersScore(Side.IMPERIAL);
		scRebel = new DestroyedFightersScore(Side.REBEL);
		
	}

	/* Test del constructor DestroyedFighterScore. Se comprueba que es
	 * un tipo de Score y que se inicia a 0
	 */
	@Test
	public void testDestroyedFightersScore() {
		assertTrue(scImperial instanceof Score);
		assertEquals(0, scImperial.getScore());
	}

    /* Se comprueba que los valores null en el método score no incrementan
     * los marcadores.
     */
	@Test
	public void testScoreNullFighter() {
		scImperial = new DestroyedFightersScore(Side.IMPERIAL);
		tieBomber=tieInterceptor=tieFighter=null;
		scImperial.score(tieBomber);
		assertEquals(0,scImperial.getScore());
		scImperial.score(tieInterceptor);
		assertEquals(0,scImperial.getScore());
		scImperial.score(tieFighter);
		assertEquals(0,scImperial.getScore());
	}
	
	/*************************
	 * FUNCIONES AUXILIARES
	 *************************/
		
	/* Para las salidas Score.toString() compara los valores impresos
	 * de los Scores hasta una precisión de 0.01
	 * 
	 */
	private void compareScores(String expected, String result ) {
		String ex[]= expected.split(":");
		String re[]= result.split(":");
		if (ex.length!=re.length) fail("Lineas distintas");
		if (ex.length==2) {
			ex[0]=ex[0].replaceAll(" ","").trim();
			re[0]=(re[0]).replaceAll(" ", "").trim();
			if ((ex[0]).equals(re[0])) {
				double ed = Double.parseDouble(ex[1]);
				double rd = Double.parseDouble(re[1]);
		
				assertEquals(ex[0],ed,rd,0.01);
			}
			else fail("Nombres jugadores distintos: esperado=<"+ex[0]+"> obtenido=<"+re[0]+">");
		}
		else
			assertEquals(expected.replaceAll(" ","").trim(), result.replaceAll(" ","").trim());	
				
	}	
}
