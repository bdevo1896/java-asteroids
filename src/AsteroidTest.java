import static org.junit.Assert.*;

import java.awt.Color;
import java.util.ArrayList;

import org.junit.Test;


public class AsteroidTest {

	@Test
	public void testGeneration() {
		Asteroid a  = new Asteroid(0,800,0,800);
		assertTrue(a.getMyPolygon()!=null);
		assertTrue(a.getMyColor() == Color.gray);
		assertTrue(a.getSize() == 20);
	}
	
	@Test
	public void testSplit(){
		Asteroid a = new Asteroid(0,800,0,800);
		ArrayList<Asteroid> asteroids = a.split();
		
		assertTrue(asteroids.size() == 2); //the split function should return a list of 2 elements.
		
		for(int i = 0; i < asteroids.size(); i++){
			assertTrue(asteroids.get(i).getDx() != 0.0 && asteroids.get(i).getDy() != 0.0); //Test to see that both asteroids created have velocities.
		}
	}

}
