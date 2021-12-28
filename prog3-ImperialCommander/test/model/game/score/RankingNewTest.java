package model.game.score;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import model.FighterFactory;
import model.Side;
import model.game.GameShip;
import model.game.IPlayer;
import model.game.PlayerRandom;

public class RankingNewTest {

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

	/*
	 * Se crea para Imperial los 2 tipos de Scores y se añaden a los distintos Ranking. Se 
	 * comprueba que la salida de los ranking coincide con SRANKING1.
	 * Imperial consigue 10 victorias y destruye 4 XWing, 3 YWing y 3 AWing. 
	 * Realiza lo mismo ahora para Rebel, consiguiendo 9 victorias y destruyendo
	 * 8 TIEFighter, 8 TIEBomber y 7 TIEInterceptor.
	 * Comprueba que la salida  coincide con SRANKING2
	 * 
	 */
	@Test
	public void testAddScore() {
		//Iniciamos marcadores para Julia
		winsScore = new WinsScore(Side.IMPERIAL);
		destroyedScore = new DestroyedFightersScore(Side.IMPERIAL);
				
		//Los añadimos al ranking
		destroyedRanking.addScore(destroyedScore);
		winsRanking.addScore(winsScore);
		System.out.println(rankingsToString());
		compareLines(SRANKING1, rankingsToString());
				
		//Modificamos los marcadores winsScore y destroyedScore de IMPERIAL
		for (int i=0; i<10; i++) {
				winsScore.score(1);
				destroyedScore.score(FighterFactory.createFighter(kREBEL_FIGHTERS[i%3], rebelShip));
		}
		destroyedRanking.addScore(destroyedScore);
		winsRanking.addScore(winsScore);
		System.out.println(rankingsToString());
	
		//Iniciamos marcadores para Rebel
		winsScore = new WinsScore(Side.REBEL);
		destroyedScore = new DestroyedFightersScore(Side.REBEL);
		//Modificamos los marcadores winsScore y destroyedScore de REBEL
		
		for (int i=0; i<23; i++) {
				if (i<9) winsScore.score(1);
				destroyedScore.score(FighterFactory.createFighter(kIMPERIAL_FIGHTERS[i%3], rebelShip));
		}
		destroyedRanking.addScore(destroyedScore);
		winsRanking.addScore(winsScore);	
		System.out.println(rankingsToString());
		compareLines(SRANKING2, rankingsToString());
	}

	/*
	 * Se crea para Imperial se crean los 2 tipos de Scores y se añaden a los distintos Ranking. Se 
	 * comprueba que la salida de los ranking coincide con SRANKING1.
	 * Imperial consigue 10 victorias y destruye 4 XWing, 3 YWing y 3 AWing y 1 SuperFighter
	 * Se realiza lo mismo ahora para Rebel, consiguiendo 10 victorias y destruyendo
	 * 4 TIEFighter, 3 TIEBomber y 3 TIEInterceptor.
	 * Comprueba que los marcadores son iguales, pero en el Ranking gana IMPERIAL por el
	 * criterio del orden en Side (la salida  coincide con SRANKING3)
	 * 
	 */
	@Test
	public void testAddScoreSide() {
		//Iniciamos marcadores para Imperial
		winsScore = new WinsScore(Side.IMPERIAL);
		destroyedScore = new DestroyedFightersScore(Side.IMPERIAL);
				
		//Los añadimos al ranking
		destroyedRanking.addScore(destroyedScore);
		winsRanking.addScore(winsScore);
		compareLines(SRANKING1, rankingsToString());
				
		//Modificamos los marcadores winsScore y destroyedScore de IMPERIAL
		for (int i=0; i<10; i++) {
				winsScore.score(1);
				destroyedScore.score(FighterFactory.createFighter(kREBEL_FIGHTERS[i%3], rebelShip));
		}
		destroyedScore.score(FighterFactory.createFighter("TIEAdvancedFighter", rebelShip));
		destroyedRanking.addScore(destroyedScore);
		winsRanking.addScore(winsScore);
	
		//Iniciamos marcadores para Rebel
		winsScore = new WinsScore(Side.REBEL);
		destroyedScore = new DestroyedFightersScore(Side.REBEL);
		//Modificamos los marcadores winsScore y destroyedScore de REBEL
		
		for (int i=0; i<10; i++) {
				winsScore.score(1);
				destroyedScore.score(FighterFactory.createFighter(kIMPERIAL_FIGHTERS[i%3], rebelShip));
		}
		destroyedRanking.addScore(destroyedScore);
		winsRanking.addScore(winsScore);	
		compareLines(SRANKING3, rankingsToString());
		//System.out.println(rankingsToString());
	}
	
	@Test
	public void testGetWinner() {
		//Iniciamos marcadores para Imperial
		winsScore = new WinsScore(Side.IMPERIAL);
		destroyedScore = new DestroyedFightersScore(Side.IMPERIAL);
		for (int i=0; i<2000; i++) {
			winsScore.score(1);
			destroyedScore.score(FighterFactory.createFighter(kREBEL_FIGHTERS[i%3], rebelShip));
		}
		destroyedRanking.addScore(destroyedScore);
		winsRanking.addScore(winsScore);
		
		//Iniciamos marcadores para Rebel
		winsScore = new WinsScore(Side.REBEL);
		destroyedScore = new DestroyedFightersScore(Side.REBEL);
		for (int i=0; i<2000; i++) {
			if (i>100) winsScore.score(1);
			else winsScore.score(2);
			destroyedScore.score(FighterFactory.createFighter(kIMPERIAL_FIGHTERS[i%3], rebelShip));
		}
		destroyedRanking.addScore(destroyedScore);
		winsRanking.addScore(winsScore);
		compareLines("Player REBEL: 389970",(destroyedRanking.getWinner()).toString());
		compareLines("Player IMPERIAL: 2000",(winsRanking.getWinner()).toString());
	}

	@Test
	public void testGetSortedRanking() {
		testAddScore();
		compareLines("[Player REBEL: 4455, PlayerIMPERIAL: 1770]",(destroyedRanking.getSortedRanking()).toString());
		compareLines("[Player IMPERIAL: 10, Player REBEL: 9]",(winsRanking.getSortedRanking()).toString());
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
