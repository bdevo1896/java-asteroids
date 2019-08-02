import static org.junit.Assert.*;
import org.junit.Test;


public class BulletsTest {

	@Test
	public void testGeneration() {
		
		Bullet b = new Bullet(0,800,0,800);
		
		assertEquals(0, b.getMinx());
		assertEquals(800, b.getMaxx());
		assertEquals(0, b.getMiny());
		assertEquals(800, b.getMaxy());
		assertEquals(10,b.getAge());
	}
	
	@Test
	public void testMove(){
		Bullet b = new Bullet(0,800,0,800);
		
		b.move();
		
		//The age should decrease by one. This way we can tell that the move function works. So 9 should be returned.
		assertEquals(9, b.getAge());
	}
	
	@Test
	public void testIsTooOldTrue(){
		Bullet b = new Bullet(0,800,0,800);
		
		/*
		 * Now the 'move' function will be called 11 times to decrease the age by 11 therefore making it too old.
		 */
		for(int i = 0; i < 11; i++)
			b.move();
		
		boolean testOne = b.isTooOld();
		
		assertEquals(true, testOne);
		
	}
	
	@Test
	public void testIsTooOldFalse(){
		Bullet b = new Bullet(0,800,0,800);
		
		boolean testOne = b.isTooOld();
		
		assertEquals(false, testOne);
	}
	
	@Test
	public void testCollisionTrue(){
		
		Bullet a = new Bullet(0,800,0,800);
		a.setSize(50);
		a.setLocation(50, 50);
		
		Bullet b = new Bullet(0,800,0,800);
		b.setSize(50);
		a.setLocation(70, 70);
		
		assertTrue(a.isCollided(b));
	}
	
	@Test
	public void testCollisionFalse(){
		
		Bullet a = new Bullet(0,800,0,800);
		a.setSize(5);
		a.setLocation(25, 25);
		
		Bullet b = new Bullet(0,800,0,800);
		b.setSize(5);
		a.setLocation(70, 70);
		
		assertFalse(a.isCollided(b));
	}

}
