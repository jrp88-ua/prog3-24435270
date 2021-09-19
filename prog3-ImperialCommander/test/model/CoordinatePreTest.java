package model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

/* Para realizar los test se sugiere usar métodos de la librería de junit como:
 * assertEquals, assertNotEquals, assertSame; assertNotSame, assertTrue; assertFalse
 */
public class CoordinatePreTest {
	
    List vcoordinates = new ArrayList<int[]>();
	int[] vcoor = { 0, 0, -70, -2, 20 }; // Para crear Coordinates
	final int DIM = vcoor.length;
	List<Coordinate> lcoor;

	/*
	 * El método setUp inicializa atributos de la clase que serán usados después en
	 * los tests. Se ejecuta siempre al principio, antes de cada test
	 */
	@Before
	public void setUp() throws Exception {
		lcoor = new ArrayList<Coordinate>();
		// Se crean las Coordinates (0,0),(0,-70), (-70,-2),(-2,20);
		for (int i = 0; i < DIM - 1; i++) {
			lcoor.add(new Coordinate(vcoor[i], vcoor[i + 1]));
		}

	}

	/*
	 * Se comprueba que cuando dos Coordinate son iguales, el resultado del hash ha
	 * de ser el mismo. Si los Coordinate son distintos el hashCode puede ser igual
	 * o no.
	 */
	@Test
	public void testHashCode() {
		Coordinate c1 = lcoor.get(2);
		Coordinate c2 = new Coordinate(c1);

		assertEquals(c1, c2);
		assertEquals(c1.hashCode(), c2.hashCode());
	}

	/*
	 * Se comprueba que el Constructor funciona bien. Para ello se analiza que las
	 * las 'x' y la 'y' de cada Coordinate creada en el setUp() son las correctas.
	 */
	@Test
	public void testCoordinateConstructor() {
		int j = 0;
		for (int i = 0; i < DIM - 1; i++) {
			assertEquals("x", vcoor[i], lcoor.get(j).getX());
			assertEquals("y", vcoor[i + 1], lcoor.get(j).getY());
			j++;
		}
	}

	/*
	 * Se comprueba que los métodos getX() y getY() de Coordinate funcionan
	 * correctamente.
	 */
	@Test
	public void testGetter() {
		Coordinate c = lcoor.get(2);
		assertEquals("x==-70", -70, c.getX());
		assertEquals("y==-2", -2, c.getY());

		Coordinate c1 = lcoor.get(0);
		assertEquals("x==", 0, c1.getX());
		assertEquals("y==", 0, c1.getY());
	}

	@Test
	public void testAdd() {
		Coordinate c1 = lcoor.get(2); // (-70,-2)
		Coordinate c2 = lcoor.get(3); // (-2, 20)

		Coordinate cadd = c1.add(c2);
		// comprobamos que se suma bien
		assertEquals("x==-72", -72, cadd.getX());
		assertEquals("y==18", 18, cadd.getY());

		// comprobamos que la coordenada que devuelve no es la misma sobre la que se
		// invoca el método
		assertNotSame(c1, cadd);

		// comprobamos que la coordenada sobre la que se invoca el método no ha cambiado
		assertEquals("x==-70", -70, c1.getX());
		assertEquals("y==-2", -2, c1.getY());

		// haz pruebas similares con el otro método .add(x,y)
		cadd = c1.add(1, 1);
		assertEquals("x==-69", -69, cadd.getX());
		assertEquals("y==-1", -1, cadd.getY());
	}

	/*
	 * Se suman las Coordinate creadas en el setUp() y comprueba, conforme se van
	 * sumando, que los valores de sus componentes van tomando los valores correctos
	 * y que el Coordinate que devuelve no es el mismo que el Coordinate que invoca
	 * al método.
	 */
	@Test // {0,0,-70,-2,20}
	public void testAddAll() {
		Coordinate caux1 = lcoor.get(0);
		Coordinate caux2;

		int sumx = caux1.getX();
		int sumy = caux1.getY();
		for (int i = 0; i < DIM - 2; i++) {
			caux2 = caux1;
			caux1 = caux1.add(lcoor.get(i + 1));
			sumx += (vcoor[i + 1]);
			sumy += (vcoor[i + 2]);
			/*
			 * Usa aquí los métodos de junit adecuados para comprobar: - que sumx y sumy son
			 * iguales a los componentes '0' y '1' respectivamente de caux1. - que el
			 * Coordinate que devuelve 'add' no es el mismo que el Coordinate que invocó al
			 * método.
			 */
			assertEquals(sumx, caux1.getX());
			assertEquals(sumy, caux1.getY());
		}
	}

	/*
	 * Se comprueba, para el método toString(), que las Coordinate creadas en el
	 * setUp() tienen el formato correcto.
	 */
	@Test
	public void testToString() {
		assertEquals("[0,0]", lcoor.get(0).toString());
		assertEquals("[0,-70]", lcoor.get(1).toString());
		assertEquals("[-70,-2]", lcoor.get(2).toString());
		assertEquals("[-2,20]", lcoor.get(3).toString());
	}

	/*
	 * Se toma una Coordinate y se comprueba todas las posibles condiciones bajo las
	 * cuales, nuestra función equals() devuelve true o false
	 */
	@Test
	public void testEqualsObject() {
		Object obj = new String("(0, 0)");
		Coordinate c = lcoor.get(0);
		// equals() devuelve falso cuando le paso null
		assertFalse(c.equals(null));
		// equals() devuelve falso cuando le paso un objeto que no es de tipo Coordinate
		assertFalse(c.equals(obj));

		/*
		 * Sigue comprobando lo siguiente: 1. equals() devuelve false cuando el valor de
		 * 'x' o 'y' es distinto 2. equals() devuelve true cuando se compara un objeto
		 * Coordinate consigo mismo 3. equals() devuelve true cuando comparo dos objetos
		 * Coordinate distintos y los valores de sus componentes respectivos son
		 * iguales.
		 */
		Coordinate c1 = new Coordinate(0, 0);
		Coordinate c2 = new Coordinate(0, 0);
		assertFalse(c1.equals(c2.add(1, 0)));
		assertTrue(c1.equals(c1));
		assertTrue(c1.equals(c1));
	}
}
