package model.game.score;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.FighterFactory;
import model.Side;
import model.game.GameShip;
import model.game.IPlayer;
import model.game.PlayerRandom;

public class RankingTest {

	Ranking<WinsScore> winsRanking;
	Ranking<DestroyedFightersScore> destroyedRanking;
	WinsScore winsScore;
	DestroyedFightersScore destroyedScore;
	//IPlayer playerJulia, playerRaul;
	IPlayer imperialPlayer, rebelPlayer;
	//IPlayer playerLaura, playerSimon;
	GameShip rebelShip, imperialShip;
	final String kIMPERIAL_FIGHTERS[]= {"XWing","YWing","AWing"};
	final String kREBEL_FIGHTERS[]= {"TIEFighter", "TIEBomber", "TIEInterceptor"};
	final String SRANKING1 = "DestroyedFighters ranking =  | Player IMPERIAL: 0 | \n" + 
			"Wins ranking =  | Player IMPERIAL: 0 | ";
	final String SRANKING2 = "DestroyedFighters ranking =  | Player REBEL: 4455 | Player IMPERIAL: 1770 | \n" + 
			"Wins ranking =  | Player IMPERIAL: 10 | Player REBEL: 9 |";
	final String SRANKING3 = "DestroyedFighters ranking =  | Player IMPERIAL: 1965 | Player REBEL: 1965 | \n" + 
			"Wins ranking =  | Player IMPERIAL: 10 | Player REBEL: 10 |";
	
	@Before
	public void setUp() throws Exception {
		destroyedRanking = new Ranking<>();
		winsRanking = new Ranking<WinsScore>();
		imperialPlayer= new PlayerRandom(Side.IMPERIAL, 20);
		rebelPlayer = new PlayerRandom(Side.REBEL, 20);
		rebelShip = new GameShip("Rebel Ship", Side.REBEL);
		imperialShip = new GameShip("Imperial Ship", Side.IMPERIAL);
	}

	/* Se comprueba que el constructor crea los set y que están vacíos */
	@Test
	public void testRanking() {
		assertNotNull(destroyedRanking.getSortedRanking());
		assertNotNull(winsRanking.getSortedRanking());
		assertTrue(destroyedRanking.getSortedRanking().isEmpty());
		assertTrue(winsRanking.getSortedRanking().isEmpty());
	}
	
	@Test
	public void testGetWinnerEmpty() {
		try {
			destroyedRanking.getWinner();
			fail("ERROR: Debía haber lanzado RuntimeException por Ranking vacío");
		} catch (RuntimeException e) {} //ok
	}
	
	
	
	/*************************
	 * FUNCIONES AUXILIARES
	 *************************/
	
	/* Construye la salida como String, con los distintos Ranking */
	private String rankingsToString () {
		StringBuilder ps = new StringBuilder();
		ps.append("DestroyedFighters ranking = "+destroyedRanking.toString()+"\n");
		ps.append("Wins ranking = "+ winsRanking.toString()+"\n");
		return ps.toString();
	}
	
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
