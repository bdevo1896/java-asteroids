import java.awt.Color;
import java.awt.Graphics;

/**
 * An instance of this class will draw a yellow circle that will be used as a 
 * projectile. The 'age' variable will be used to keep track of how long the 
 * bullet will stay on screen.
 * @author Bryce DeVaughn
 *
 */
public class Bullet extends Sprite {
	
	private static final int MAX_AGE = 10;
	private int age;
	private Color color;
	
	public Bullet(int left, int right, int top, int bottom) {
		super(left, right, top, bottom);
		age = MAX_AGE;
		size = 5;
		color = Color.yellow;
	}

	@Override
	void drawOn(Graphics gfx) {
		gfx.setColor(color);
		gfx.fillOval((int)x-(size/2), (int)y-(size/2), size, size);
	}
	
	public int getAge() {
		return age;
	}

	/**
	 * This method will check to see if the age value has become negative, therefore, too old.
	 * @return
	 */
	public boolean isTooOld(){
		boolean isOld = false;
		if(age < 0){
			isOld = true;
		}
		return isOld;
	}
	
	public void move(){
		super.move();
		age--;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	

}
