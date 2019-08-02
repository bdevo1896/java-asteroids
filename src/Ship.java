import java.awt.Color;
import java.awt.Polygon;

/**
 * An instance of this class will be used by the player. It can shoot and move.
 * @author Bryce DeVaughn
 *
 */
public class Ship extends PolygonSprite {
	
	private static final double ACCELERATION = 0.9;
	private static final double BRAKE_SPEED = .1;
	private static final double BULLET_SPEED = 20;
	private int speed;

	public Ship(int left, int right, int top, int bottom) {
		super(left, right, top, bottom);
		size = 10;
		//setMyColor(new Color(56,27,197));
		setMyColor(Color.red);
		//Uncomment next line for the basic regular triangle.
		//this.setMyPolygon(createBody(3,false,size));
		
		//This next line creates a special quadrilateral.
		this.createBody();
		speed = 1;
	}
	
	/**
	 * Creates a bullet at the players location to shoot.
	 * @return
	 */
	public Bullet shoot(){
		Bullet s = new Bullet(minx,maxx,miny,maxy);
		s.setVelocity(this.dx+BULLET_SPEED*Math.cos(angle), this.dy+BULLET_SPEED*Math.sin(angle));
		s.setLocation((int) Math.round(this.x), (int) Math.round(this.y));
		return s;
	}
	
	/**
	 * This method pushes the ship in the direction it's pointing.
	 */
	public void thrust(){
		this.setVelocity(speed*(dx+ACCELERATION*Math.cos(angle)),speed*(dy+ACCELERATION*Math.sin(angle)));
	}
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	/**
	 * This method will allow the player to brake, and slow themselves.
	 */
	public void brake(){
		this.setVelocity(dx + BRAKE_SPEED*Math.cos(-angle),dy + BRAKE_SPEED * Math.sin(-angle));
			
	}
	
	public void move(){
		super.move();
		
		if((int)x >= maxx){
			this.setLocation(minx,(int)y);
		}else if((int)x <= minx){
			this.setLocation(maxx, (int)y);
		}else if((int)y >= maxy){
			this.setLocation((int)x, miny);

		}else if((int)y <= miny){
			this.setLocation((int)x, maxy);

		}
	}
	
	public void createBody(){
		Polygon poly = new Polygon();
		double baseAngle = angle;
		double turnFact = ((360/3) * Math.PI)/180;
		int radius;
		radius = size;
		int nx,ny;
		nx = (int)(x + (radius*Math.cos(baseAngle)));
		ny = (int)(y + (radius*Math.sin(baseAngle)));
	    poly.addPoint(nx,ny);
		baseAngle+=turnFact;
		
		nx = 0;
		ny = 0;
		
		nx = (int)(x + (radius*Math.cos(baseAngle)));
		ny = (int)(y + (radius*Math.sin(baseAngle)));
	    poly.addPoint(nx,ny);
		baseAngle+=turnFact;
		
		nx = 0;
		ny = 0;
		
		poly.addPoint((int)x,(int)y);
		
		nx = (int)(x + (radius*Math.cos(baseAngle)));
		ny = (int)(y + (radius*Math.sin(baseAngle)));
	    poly.addPoint(nx,ny);
	    
	    setMyPolygon(poly);
	}

}
