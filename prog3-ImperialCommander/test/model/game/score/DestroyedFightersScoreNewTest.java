package model.game.score;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Fighter;
import model.FighterFactory;
import model.Ship;
import model.Side;
import model.game
.GameShip;public class DestroyedFightersScoreNewTest {
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
	
		
	/* Inicialmente se comprueba que el marcador de scRebel es igual a sí mismo
	 * y menor que el de scImperial (aun teniendo los mismos score)
	 * Se aplica sobre uno de ellos con score, un Fighter que incremente su puntuación.
	 * Se comprueba ahora que el que ha aumentado, si es el que invoca a compareTo da un
	 * valor negativo y si es el menor el que lo invoca, da un valor positivo.
	 */
	@Test
	public void testCompareTo() {
		assertTrue(scRebel.compareTo(scRebel)==0);
		assertTrue(scRebel.compareTo(scImperial)>0);
		scImperial.score(xWing);
		scRebel.score(tieFighter);
		assertTrue(scImperial.compareTo(scRebel)<0);
		scRebel.score(tieInterceptor);
		assertTrue(scRebel.compareTo(scImperial)<0);
		assertTrue(scImperial.compareTo(scRebel)>0);
		
	}

	/* Se pasan varios Fighters sucesivamente al DestroyedFighterScore scImperial mediante
	 * el método score.
	 * Se comprueba con getScore() que  los valores que se van obteniendo se van 
	 * acumulando sucesivamente. 
	 */
	@Test
	public void testScore() {
		assertEquals(0,scImperial.getScore(),0.01);
		scImperial.score(xWing);
		assertEquals(210,scImperial.getScore(),0.01);
		scImperial.score(aWing);
		assertEquals(435,scImperial.getScore(),0.01);
		scImperial.score(yWing);
		assertEquals(585,scImperial.getScore(),0.01);
		scImperial.score(null);
		assertEquals(585,scImperial.getScore(),0.01);
		scImperial.score(yWing);
		assertEquals(735,scImperial.getScore(),0.01);
		scImperial.score(aWing);
		assertEquals(960,scImperial.getScore(),0.01);
		scImperial.score(xWing);
		assertEquals(1170,scImperial.getScore(),0.01);
	}


	/* Se comprueba toString sobre el DestroyedFighterScore scRebel y se comprueba que
	 * inicialmente es: "Player REBEL: 0"
	 * Se aplica el método score sobre scRebel varias veces con distintos tipos de Fighter.
	 * Se va comprobando a la vez que la salida va cambiando de valor.
	 * Finalmente se prueba la salida aplicando un score sobre scImperial con un Fighter.
	 */
	@Test
	public void testToString() {
			
		compareScores ("Player REBEL: 0",scRebel.toString());
	
		scRebel.score(tieBomber);
		compareScores ("Player REBEL: 100",scRebel.toString());

		scRebel.score(tieFighter);
		compareScores ("Player REBEL: 295",scRebel.toString());

		scRebel.score(tieInterceptor);
		compareScores ("Player REBEL: 525",scRebel.toString());	
		
		scRebel.score(tieFighter);
		compareScores ("Player REBEL: 720",scRebel.toString());

		scRebel.score(tieInterceptor);
		compareScores ("Player REBEL: 950",scRebel.toString());

		scImperial.score(xWing);
		compareScores ("Player IMPERIAL: 210",scImperial.toString());	
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
