import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.geom.AffineTransform;
import java.util.Random;

/**
 * This is a class that will be used to make the physical bodies of other objects
 * based off of geometry.
 * @author Bryce DeVaughn
 *
 */
public abstract class PolygonSprite extends Sprite {

	protected Polygon myPolygon;
	protected static AffineTransform IDENTITY;
	protected Color myColor;
	protected double angle;

	public PolygonSprite(int left, int right, int top, int bottom) {
		super(left, right, top, bottom);
		angle = 0.0;
		IDENTITY = new AffineTransform();
	}

	@Override
	void drawOn(Graphics gfx) {

		Graphics2D gfx2 = (Graphics2D)gfx;

		gfx2.setColor(myColor);
		gfx2.setTransform(IDENTITY);
		gfx2.translate(x,y);
		gfx2.rotate(angle);
		gfx2.fillPolygon(myPolygon);

		//Resets the transform for later use.
		gfx2.setTransform(IDENTITY);

	}

	public void move(){
		super.move();
	}

	/**
	 * This method will use the 'angle' parameter to rotate the sprite.
	 * @param angle
	 */
	public void rotate(double deltaAngle){
		angle += deltaAngle;
	}

	public Polygon getMyPolygon() {
		return myPolygon;
	}

	public void setMyPolygon(Polygon myPolygon) {
		this.myPolygon = myPolygon;
	}

	public static AffineTransform getIDENTITY() {
		return IDENTITY;
	}

	public static void setIDENTITY(AffineTransform iDENTITY) {
		IDENTITY = iDENTITY;
	}

	public Color getMyColor() {
		return myColor;
	}

	public void setMyColor(Color myColor) {
		this.myColor = myColor;
	}

	public double getAngle() {
		return angle;
	}

	public void setAngle(double angle) {
		this.angle = angle;
	}

	/**
	 * Creates a polygon that will represent the object's body. The 'irregular' boolean will be used
	 * when decided to create a regular or irregular polygon.
	 * @return
	 */
	public Polygon createBody(int numOfPoints,boolean irregular,int nsize){
		Random rand = new Random();
		Polygon poly = new Polygon();
		double baseAngle = angle;
		double turnFact = ((360/numOfPoints) * Math.PI)/180;
		int radius;
		radius = nsize;

		for(int i = 0; i < numOfPoints; i++){
			int x = 0;
			int y = 0;
			if(irregular){
				int randNum = rand.nextInt(11);
				/*
				 * Next two lines calculate two points on the x and y axis using 'baseangle' for the angle.
				 */
				x = (int)(x + ((radius - randNum)*Math.cos(baseAngle)));
				y = (int)(y + ((radius-randNum)*Math.sin(baseAngle)));
				/*
				 * Adds the new point to the polygon.
				 */
				poly.addPoint(x,y);
				/*
				 * Rotates to the next point.
				 */
				baseAngle+=turnFact;
			}
			else{
				x = (int)(x + (radius*Math.cos(baseAngle)));
				y = (int)(y + (radius*Math.sin(baseAngle)));
				poly.addPoint(x,y);
				baseAngle+=turnFact;
			}
		}

		return poly;

	}


}
