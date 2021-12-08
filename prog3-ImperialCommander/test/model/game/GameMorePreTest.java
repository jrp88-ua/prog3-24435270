package model.game;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

import model.Fighter;
import model.RandomNumber;
import model.Side;

public class GameMorePreTest {
	PlayerFile plfRebel, plfImperial;
	Reader stringReader;
	Game game;
	ByteArrayOutputStream baos;
	PrintStream ps, old;
	
	@Before
	public void setUp() throws Exception {
		 
        RandomNumber.resetRandomCounter();
        Fighter.resetNextId();
	}

	/* Game con dos PlayerRandom con 11 fighters cada uno
	 * gana REBEL porque IMPERIAL hace 'exit'
	 */
	@Test
	public void testPlayRandom11() {
		
		PlayerRandom plimperial = new PlayerRandom(Side.IMPERIAL,11);
		PlayerRandom plrebel = new PlayerRandom(Side.REBEL,11);

		game = new Game(plimperial,plrebel);
	
		standardIO2Stream();
		Side winner = game.play();
		
		String sout = Stream2StandardIO(); //Cambia salida standard a un Stream
		assertEquals(Side.REBEL, winner);
		String solution = readSolutionFromFile("files/testPlayRandom11.out"); //Cambia salida de Stream a la consola
		compareLines(solution, sout, false);
	}
	
	/* Misma partida que la anterior, pero con PlayerFile
	 */
	@Test
	public void testPlayFile11() {
		
		String inputImp = "4/TIEFighter:6/TIEBomber:8/TIEInterceptor\nlaunch 8 8 11\nlaunch 2 2 6\nlaunch 0 9 17\nlaunch 5 4 3\nlaunch 9 0 5\nlaunch 7 2 4\nlaunch 1 6 1\npatrol 5\nlaunch 7 2 18\nlaunch 8 5 18\npatrol 11\nlaunch 4 8 10\nlaunch 3 7 8\nlaunch 3 0 12\nexit\n";
		stringReader = new StringReader(inputImp);
		BufferedReader br = new BufferedReader(stringReader);
		plfImperial = new PlayerFile(Side.IMPERIAL, br);
		
		String inputReb = "8/XWing:9/YWing:2/AWing\nlaunch 7 3 29\nimprove 21 96\nlaunch 8 3 37\npatrol 29\nlaunch 5 0 36\npatrol 29\nlaunch 6 8 19\nlaunch 6 5 24\nlaunch 7 8 23\nlaunch 5 9 35\nlaunch 1 6 21\nlaunch 7 7 34\nlaunch 5 0 31\nlaunch 6 1 30\nexit\n";
		stringReader = new StringReader(inputReb);
		br = new BufferedReader(stringReader);	
		plfRebel = new PlayerFile(Side.REBEL, br);	
		
        game = new Game(plfImperial,plfRebel);
		standardIO2Stream();
		Side winner = game.play();
		
		String sout = Stream2StandardIO(); //Cambia salida standard a un Stream
		assertEquals(Side.REBEL, winner);
		String solution = readSolutionFromFile("files/testPlayFile11.out"); //Cambia salida de Stream a la consola
		// System.out.println(sout); // to test
		compareLines(solution, sout, false);
	}


	/***************************
	 * METODOS DE APOYO
	 ***************************/
	/* Compara dos String línea a línea.
	 * Parámetros: la cadena esperada, la cadena resultado y true si queremos que considere los espacios 
	 * interiores de la cadena o false en caso contrario. 
	 */
		private void  compareLines(String expected, String result, boolean spaces) {
			String exp[]=expected.split("\n");
			String res[]=result.split("\n");
			boolean iguales = true;
			if (exp.length!=res.length) 
				fail("Cadena esperada de tamaño ("+exp.length+") distinto a la resultante ("+res.length+")");
			for (int i=0; i<exp.length && iguales; i++) {
				if (! exp[i].contains("ERROR:")) {
					if (spaces)
						assertEquals("linea "+i, exp[i].trim(),res[i].trim());
					else
						assertEquals("linea "+i, exp[i].replace(" ",""), res[i].replace(" ","")); 
				} 
				else if (! res[i].contains("ERROR:"))
						fail("Error: el resultado esperado debía contener en la línea "+i+" la cadena 'ERROR:'");
			}
		}
	
	
	//Redirección de la salida estandard de la consola a un Stream	
	private  void standardIO2Stream(){
		baos = new ByteArrayOutputStream();
		ps = new PrintStream(baos);
		// IMPORTANTE: Guarda el antiguo System.out!
		old = System.out;
		// Cambiamos en Java la salida estandar al nuevo stream
		System.setOut(ps);   
	}
		
	//Volver a restaurar la salida standard a la consola devolviendo lo recogido en el stream
	private String Stream2StandardIO(){
		System.out.flush();
		System.setOut(old); //Reestablecemos la salida standard
		return (baos.toString());
	}
		
	
	//Lee la solución de un fichero y la devuelve en un String	
	private String readSolutionFromFile(String file) {
			Scanner sc=null;
			try {
					sc = new Scanner(new File(file));
			} catch (FileNotFoundException e) {
					System.out.println("File "+file+" not found");
					fail("Fichero "+file+" no encontrado");
			}
			StringBuilder sb = new StringBuilder();
			while (sc.hasNext()) 
				sb.append(sc.nextLine()+"\n");			
			sc.close();
			return (sb.toString());
		}
	
}
