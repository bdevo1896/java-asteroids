import java.awt.Graphics;

/**
 * This will be the base class to be modified by other classes. This class will enable objects ,that inherit this, to move and be
 * drawn and interact with other objects.
 * @author Bryce DeVaughn
 *
 */
public abstract class Sprite {
	
	protected int minx,miny,maxx,maxy,size;
	protected double x,y,dx,dy;
	private int speed;

	public Sprite(int left,int right, int top, int bottom) {
		minx = left;
		miny = top;
		maxx = right;
		maxy = bottom;
		speed = 1;
	}
	
	/**
	 * Checks if two objects are colliding.
	 * @param other
	 * @return
	 */
	public boolean isCollided(Sprite other){
		boolean collision = false;
		//This statement creates a line between the two centers of the objects and then compares this to the sum of the radii.
		if(Math.sqrt(Math.pow((this.x-other.x),2.0) + Math.pow((this.y-other.y),2.0)) < size+other.size){
			collision = true;
		}
		
		return collision;
		
	}
	
	/**
	 * Sets the location of the object.
	 * @param nx
	 * @param ny
	 */
	public void setLocation(int nx, int ny){
		x = nx;
		y = ny;
	}
	
	/**
	 * Sets the speed of the velocity on the x and y axis.
	 * @param dx
	 * @param dy
	 */
	public void setVelocity(double dx, double dy){
		this.dx = dx;
		this.dy = dy;
	}
	
	/**
	 * Sets the size(radius) of the object.
	 * @param ns
	 */
	public void setSize(int ns){
		size = ns;
	}
	
	/**
	 * Will be used in other classes.
	 * @param gfx
	 */
	abstract void drawOn(Graphics gfx);
	
	/**
	 * Moves the object by applying the velocity to it.
	 */
	public void move(){
		x += speed*dx;
		y += speed*dy;
	}
	
	/**
	 * This method tests if the object is out of bounds, then returns if it is true or not.
	 * @return
	 */
	public boolean isOutOfBounds(){
		boolean rtn = false;
		if(x < minx || x > maxx || y < miny || y > maxy)
			rtn = true;
		return rtn;
	}
	
	/**
	 * This method takes tests if this object is out of bounds. Then if
	 * 'true' it will flip the velocity and make the object bounce back 
	 * into the area.
	 */
	public void rebound(){
		double extra = 0.0;
		if(isOutOfBounds()){
			//Tests if it has passed the right side of the screen.
			if(x > maxx){
				extra = x - maxx;
				x = maxx - extra;
				dx*=-1;
			}
			//Tests if it has passed the left side of the screen.
			if(x < minx){
				extra = x + minx;
				x = minx - extra;
				dx*=-1;
			}
			//Tests if it has passed the bottom side of the screen.
			if(y > maxy){
				extra = y - maxy;
				y = maxy - extra;
				dy*=-1;
			}
			//Tests if it has passed the top side of the screen.
			if(y < miny){
				extra = y - miny;
				y = miny - extra;
				dy*=-1;
			}
		}
		
		
	}

	public int getMinx() {
		return minx;
	}

	public int getMiny() {
		return miny;
	}

	public int getMaxx() {
		return maxx;
	}

	public int getMaxy() {
		return maxy;
	}

	public int getSize() {
		return size;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getDx() {
		return dx;
	}

	public double getDy() {
		return dy;
	}
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

}
