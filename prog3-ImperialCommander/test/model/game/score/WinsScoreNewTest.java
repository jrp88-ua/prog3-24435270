package model.game.score;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.Fighter;
import model.Side;

public class WinsScoreNewTest {

	Score<Integer> scRebel, scImperial;
	@Before
	public void setUp() throws Exception {
		scImperial = new WinsScore(Side.IMPERIAL);
		scRebel = new WinsScore(Side.REBEL);
	}

	
	/* Se pasan varios valores al WinsScore scImperial mediante
	 * el método score.
	 * Se comprueba con getScore() que  los valores que se van obteniendo se van 
	 * acumulando sucesivamente. 
	 */
	@Test
	public void testScore() {
		assertEquals(0,scImperial.getScore());
		scImperial.score(10);
		assertEquals(0,scImperial.getScore());
		scImperial.score(0);
		for (int i=0; i<300; i++) {
			scImperial.score(1);
			assertEquals(i+1,scImperial.getScore());
		}	
		//scImperial.score(null);
		scImperial.score(-1);
		assertEquals(300,scImperial.getScore());
	}
	
	/* Se comprueba toString sobre el WinsScore scRebel y se comprueba que
	 * inicialmente es: "Player REBEL: 0"
	 * Se aplica el método score sobre scRebel varias veces con distintos tipos valores
	 * Integer.
	 * Comprueba a la vez que la salida va cambiando de valor.
	 */
	@Test
	public void testToString() {
			
		compareLines ("Player REBEL: 0",scRebel.toString());
		
		scRebel.score(0);
		compareLines ("Player REBEL: 0",scRebel.toString());

		for (int i=0; i<350; i++) {
			scRebel.score(1);
			compareLines ("Player REBEL: "+(i+1),scRebel.toString());
		}
		scRebel.score(20);
		compareLines ("Player REBEL: 350", scRebel.toString());
	}
	
	/*************************
	 * FUNCIONES AUXILIARES
	 *************************/
		
		
	/* Compara dos String línea a línea.
	 * Parámetros: la cadena esperada, la cadena resultado.
	 */
	private void  compareLines(String expected, String result) {
		expected=expected.replaceAll("\n+","\n");
		result=result.replaceAll("\n+","\n");
		String exp[]=expected.split("\n");
		String res[]=result.split("\n");
		boolean iguales = true;
		if (exp.length!=res.length) 
			fail("Cadena esperada de tamaño ("+exp.length+") distinto a la resultante ("+res.length+")");
		for (int i=0; i<exp.length && iguales; i++) {
			if (! exp[i].contains("ERROR:")) {
				res[i]=res[i].trim();
				if (res[i].length()>1 && (res[i].charAt(1)=='|')) //Es un Board
						assertEquals("linea "+i, exp[i].trim(),res[i]);
				else
					assertEquals("linea "+i, exp[i].replaceAll(" ",""), res[i].replaceAll(" ","")); 
			} 
			else if (! res[i].contains("ERROR:"))
					fail("Error: el resultado esperado debía contener en la línea "+i+" la cadena 'ERROR:'");
		}
	}
}
