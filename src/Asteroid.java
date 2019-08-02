import java.awt.Color;
import java.util.ArrayList;
import java.util.Random;

/**
 * An instance of this class will create a asteroid like object. It will drift and rotate
 * ; also acts as the target for the player to shoot.
 * @author Bryce DeVaughn
 *
 */
public class Asteroid extends PolygonSprite {
		
	public Asteroid(int left, int right, int top, int bottom) {
		super(left, right, top, bottom);
		setMyColor(Color.gray);
		size = 20;
		setBody(this);
	}
	
	/**
	 * Moves the asteroid. Overrides the original 'move' and rotates the asteroid. 
	 */
	@Override
	public void move(){
		super.move();
		rotate(.01);
		if(isOutOfBounds()){
			rebound();
		}
	}
	
	/**
	 * This method will create two smaller asteroids based off the current one.
	 * @return
	 */
	public ArrayList<Asteroid> split(){
		ArrayList<Asteroid> rtnList = new ArrayList<Asteroid>();
		//Creates both asteroids and adds them to the list.
		for(int i = 0; i < 2; i++){
			Asteroid a = new Asteroid(minx,maxx,miny,maxy);
			a.setSize(size-5);
			setBody(a);
			a.setLocation((int)x,(int)y);
			
			//This group of statements randomly assign a velocity to the newly made asteroid.
			if(i == 0){
				a.setVelocity(dx+Math.cos(Math.PI/2),dy+Math.sin(Math.PI/2));
				}else{
				a.setVelocity(dy-Math.cos(Math.PI/2),dy-Math.sin(Math.PI/2));
				}
			rtnList.add(a);
		}
		
		return rtnList;
	}
	
	/**
	 * This method sets the body of the parameter asteroid (a), by calling the 
	 * 'setMyPolygon' method and 'createBody' methods with a random number inputed.
	 * @param a
	 */
	public void setBody(Asteroid a){
		Random rand = new Random();
		int numOfPoints = 5+rand.nextInt(6);
		a.setMyPolygon(createBody(numOfPoints,true,a.size));
	}
	

}
