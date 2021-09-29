package model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.junit.Test;

public class MyCoordinateTestP2 {

	@Test
	public void testCompareTo() {
		List<Coordinate> list = new ArrayList<>(9);
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++)
				list.add(new Coordinate(i, j));
		// 0 < 1 < 2 < 3 < 4
		for (int i = 0; i < list.size() - 1; i++)
			assertEquals(-1, list.get(i).compareTo(list.get(i + 1)));
		for (int i = list.size() - 1; i > 0; i--)
			assertEquals(1, list.get(i).compareTo(list.get(i - 1)));
		assertEquals(0, new Coordinate(0, 0).compareTo(new Coordinate(0, 0)));
	}

	@Test
	public void testGetNeighborhood() {
		int centerX = 4;
		int centerY = 4;
		Coordinate c = new Coordinate(centerX, centerY);
		Set<Coordinate> s = c.getNeighborhood();
		assertEquals("Ammount of neighbors invalid", 8, s.size());
		List<Coordinate> neig = new ArrayList<>(s.size());
		for (int i = -1; i <= 1; i++)
			for (int j = -1; j <= 1; j++)
				if (i != 0 || j != 0)
					neig.add(new Coordinate(centerX + i, centerY + j));
		assertTrue("The returned set does not have all the neighbors", s.containsAll(neig));
		neig.clear();
		neig.addAll(s);
		for (int i = 0; i < neig.size() - 1; i++)
			assertEquals("The neighbors are not ordered", -1, neig.get(i).compareTo(neig.get(i + 1)));
	}

}
