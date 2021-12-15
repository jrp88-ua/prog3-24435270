package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class CoordinateTestP2 {
	Coordinate c1, c2;
	final String kNeigborhood1 []= {"[2,4]","[2,5]","[2,6]","[3,4]","[3,6]",
			"[4,4]","[4,5]","[4,6]"}; //Coordenadas ordenadas
	final String kNeigborhood2 []= {"[-1,-1]","[-1,0]","[-1,1]","[0,-1]",
			"[0,1]","[1,-1]","[1,0]","[1,1]"}; //Coordenadas ordenadas
			
	
	@Before
	public void setUp() throws Exception {
		c1 = new Coordinate(3, 5);
		c2 = new Coordinate(0,0);
	}

	
	
	/* Comprueba compareTo para dos coordenadas con distintos valores para la X e Y. */
	@Test
	public final void testCompareTo1() {
		assertTrue (c1.compareTo(c2)>0);
		assertTrue (c2.compareTo(c1)<0);
	}
	
	/* Comprueba compareTo para dos coordenadas con mismo valor para la X 
	 	y distinto valor para la Y*/
	@Test
	public final void testCompareTo2() {
		c2 = new Coordinate(3,-1);
		assertTrue (c1.compareTo(c2)>0);
		assertTrue (c2.compareTo(c1)<0);
	}
	
	/* Comprueba compareTo para dos coordenadas con distinto valor para la X 
 	y mismo valor para la Y*/
	@Test
	public final void testCompareTo3() {
		c2 = new Coordinate(1,5);
		assertTrue (c1.compareTo(c2)>0);
		assertTrue (c2.compareTo(c1)<0);
	}
	
	/* Comprueba compareTo para dos coordenadas con distinto valor para la X 
 	y mismo valor para la Y*/
	@Test
	public final void testCompareTo4() {
		c1 = new Coordinate(-1,0);
		assertTrue (c1.compareTo(c2)<0);
		assertTrue (c2.compareTo(c1)>0);
	}

	/* Comprueba compareTo para dos coordenadas con mismo valor para la X 
 	y mismo valor para la Y*/
	@Test
	public final void testCompareTo5() {
		c2 = new Coordinate(3,5);
		assertTrue (c1.compareTo(c2)==0);
		assertTrue (c2.compareTo(c1)==0);
	}
	
	/* Comprueba los vecinos a la coordenada [3,5] y que las Coordenadas
	 * están correctamente ordenadas. */
	@Test
	public final void testNeighborhood1() {
		Set<Coordinate> lcoord = c1.getNeighborhood();
		assertEquals ("Mismo número de coordenadas", kNeigborhood1.length, lcoord.size());
		int i=0;
		for (Coordinate c : lcoord) {
			assertEquals (kNeigborhood1[i], c.toString());
			i++;
		}
	}

	/* Comprueba los vecinos a la coordenada [0,0] y que las Coordenadas
	 * están correctamente ordenadas. */
	@Test
	public final void testNeighborhood2() {
		Set<Coordinate> lcoord = c2.getNeighborhood();
		assertEquals ("Mismo número de coordenadas", kNeigborhood2.length, lcoord.size());
		int i=0;
		for (Coordinate c : lcoord) {
			assertEquals (kNeigborhood2[i], c.toString());
			i++;
		}
	}

}
