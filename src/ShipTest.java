import static org.junit.Assert.*;

import java.awt.Color;

import org.junit.Test;


public class ShipTest {

	@Test
	public void testGeneration() {
		Ship s = new Ship(0,800,0,800);
		assertEquals(true,s.getMyPolygon()!=null);
		assertEquals(true,s.getMyColor() == Color.red);
		assertEquals(true,s.getAngle() == 0.0);
		assertEquals(true,s.getSize()==10);
	}
	
	@Test
	public void testRotate(){
		Ship s = new Ship(0,800,0,800);
		s.rotate(50*Math.PI/180);
		
		assertTrue(s.getAngle()!=0.0);
	}
	
	@Test
	public void testShoot(){
		Ship s = new Ship(0,800,0,800);
		Bullet b = s.shoot();
		assertTrue(b!=null);
	}
	
	@Test
	public void testThrust(){
		Ship s = new Ship(0,800,0,800);
		s.thrust();
		
		assertTrue(s.getDx()!=0.0); // since the ship starts at an angle of 0 radians, on DX should change.
	}

}
