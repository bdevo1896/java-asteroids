import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Random;

/**
 * An instance of this class will create a comet. The 'tailColor' variable represents the the color of 
 * the trail behind the white part of the comet. the 'r','g',and 'b' variables are to control what color
 * the trail will be. 'TAIL_AGE' indicates how long the trail will be behind the comet. The 'tail' 
 * ArrayList will hold bullet instances with no velocity that will represent the trail.
 * @author Bryce DeVaughn
 *
 */
public class Comet extends PolygonSprite {

	private Color tailColor;
	private int r,g,b;
	private static final int TAIL_AGE = 20;
	private ArrayList<Bullet> tail;
	private int age;
	private static final int MAX_AGE = 200;
	private int cooldown;
	private static final int COOLDOWN_TIME = 1;
	
	public Comet(int left, int right, int top, int bottom) {
		super(left, right, top, bottom);

		tail = new ArrayList<Bullet>();

		Random rand = new Random(); 
		r = 100+rand.nextInt(100);
		g = 100+rand.nextInt(100);
		b = 100+rand.nextInt(100);

		tailColor = new Color(r,g,b,50);

		setSize(50);
		setMyColor(Color.white);
		
		age = MAX_AGE;
		
		cooldown = COOLDOWN_TIME;
	}

	@Override
	void drawOn(Graphics gfx) {

		/*
		 * These next two lines are creating a tail color for the comet.
		 */
		if(tail.size()>0){
			for (Bullet b: tail){
				b.drawOn(gfx);
			}
		}
		
		/*
		 * These next two lines will create the head of the comet.
		 */
		gfx.setColor(Color.white);
		gfx.fillOval((int)x-(size/2), (int)y-(size/2), size, size);

	}

	@Override
	public void move(){
		super.move();
		
		age--;
		
		if(cooldown == 0){
			createTail();
			cooldown = COOLDOWN_TIME;
		}
		
		if(tail.size()>0){
			/*
			 * This loop will make sure the trail of Bullet objects age and later on get removed.
			 */
			for(int i = 0; i < tail.size(); i++){
				tail.get(i).setAge(tail.get(i).getAge()-1);
				if(tail.get(i).isTooOld()){
					tail.remove(tail.get(i));
				}
			}
		}
		
		cooldown--;
	}
	
	/**
	 * This method will check to see if the 'age' goes negative, therefore, too old.
	 * @return
	 */
	public boolean isTooOld(){
		boolean isOld = false;
		if(age < 0){
			isOld = true;
		}
		return isOld;
	}
	
	/**
	 * This function will create a Bullet object and adds it to the 'tail' list.
	 */
	public void createTail(){
		Bullet b = new Bullet(minx,maxx,miny,maxy);
		b.setSize(getSize());
		b.setLocation((int)Math.round(this.getX()),(int)Math.round(this.getY()));
		b.setAge(TAIL_AGE);
		b.setColor(tailColor);
		tail.add(b);
	}

	public ArrayList<Bullet> getTail() {
		return tail;
	}



}
