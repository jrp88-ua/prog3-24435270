package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/**
 * clase para generar nÃºmeros aleatorios
 * @author paco
 *
 */
public class RandomNumber {

	/**
	 * generador de nÃºmeros aleatorios
	 */
	private static Random generator = new Random(1L);
	
	/**
	 * lista de nÃºmeros generados (para debug)
	 */
	private static List<Integer> list = new ArrayList<Integer>();
	
	/**
	 * genera un nÃºmero aleatorio entre 0 y max-1
	 * @param max indica el mÃ¡ximo valor (no incluido)
	 * @return nÃºmero aleatorio entre 0 y max-1
	 */
	public static int newRandomNumber(int max) {
		int r = generator.nextInt(max);
		list.add(r);
		return r;
	}
	
	/**
	 * getter (debug)
	 * @return lista de nÃºmeros generados
	 */
	public static List<Integer> getRandomNumberList() {
		return list;
	}
	
	/**
	 * reinicializa el generador y la lista, para las pruebas unitarias
	 */
	public static void resetRandomCounter() {
        list.clear();
        generator = new Random(1L);
    } 
}