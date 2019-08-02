import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;


public class DisplayPanel extends JPanel {
	

	private static final long serialVersionUID = 1L;
	private Ship player;
	private	ArrayList<Asteroid> asteroids;
	private ArrayList<Bullet> bullets;
	private ArrayList<Comet> comets;
	private int shipCounter;
	private static final int MAX = 50;
	private Timer timer;
	private KeyHelper kH;
	private TimerHelper tH;
	private Image myImage;
	private boolean gameOver;
	private int score;
	private boolean playerCanDie = true;
	//private AudioClip shootSound,thrusterSound,explodeSound,music;

	public DisplayPanel(){
		
		setPreferredSize(new Dimension(800,800));
		this.setBounds(new Rectangle(0,0,800,800));
		player = new Ship(0,getWidth(),0,getHeight());
		player.setLocation(getWidth()/2,getHeight()/2);
		asteroids = new ArrayList<Asteroid>();
		createAsteroids();
		bullets = new ArrayList<Bullet>();
		comets = new ArrayList<Comet>();
		shipCounter = 3;
		kH = new KeyHelper();
		tH = new TimerHelper();
		addKeyListener(kH);
		timer = new Timer(30,tH);
		timer.start();
		setFocusable(true);
		gameOver = false;
		score = 0;
	}
	
	/**
	 * This method will create a typical JLabel object.
	 */
	public JLabel createLabel(String label){
		JLabel rtnL = new JLabel(label);
		rtnL.setFont(new Font("Arial",Font.BOLD,12));
		return rtnL;
	}
	
	/**
	 * This method will create a typical JButton
	 */
	public JButton createButton(String label){
		JButton rtnB = new JButton(label);
		rtnB.setHorizontalAlignment(SwingConstants.RIGHT);
//		rtnB.setMaximumSize(new Dimension(200,20));
//		rtnB.setMinimumSize(new Dimension(200,20));
//		rtnB.setPreferredSize(new Dimension(200,20));
		return rtnB;
	}
	
	/**
	 * This method will set all game settings.
	 */
	public void updateGameSettings(boolean canDie,int newSpeed,String asterNumT){
		playerCanDie = canDie;
		player.setSpeed(newSpeed);
		for(Asteroid a: asteroids){
			a.setSpeed(newSpeed);
		}
		if(asterNumT.trim().length()==0)return;
		int asterNum = Integer.parseInt(asterNumT);
		createAsteroidsSpecified(asterNum);
		this.requestFocus();
	}
	
	/**
	 * This method creates and places the asteroids around the screen.
	 */
	public void createAsteroidsSpecified(int addMax){
		Random rand = new Random();
		//This 'int' provides a barrier around player spawn with a radius that is 'playerSpawnBounds'.
		int playerSpawnBounds = 100;
		//This 'int' keeps the asteroids in the bounds of the window.
		int wallBounds = 20;
		for(int i = 0; i < addMax; i++){
			Asteroid a = new Asteroid(0,getWidth(),0,getHeight());
			int dir = rand.nextInt(4);
			switch(dir){
			case 0:
				//Right side spawning.
				a.setLocation(((getWidth()/2)+playerSpawnBounds+rand.nextInt((getWidth()/2)-playerSpawnBounds))-wallBounds,rand.nextInt(getHeight()));
				break;
			case 1:
				//Left side spawning.
				a.setLocation(((getWidth()/2)-playerSpawnBounds-rand.nextInt((getWidth()/2)-playerSpawnBounds))+wallBounds,rand.nextInt(getHeight()));
				break;
			case 2:
				//Down side spawning.
				a.setLocation(rand.nextInt(getWidth()),((getHeight()/2)+playerSpawnBounds+rand.nextInt((getHeight()/2)-playerSpawnBounds))-wallBounds);
				break;
			case 3:
				//Top side spawning.
				a.setLocation(rand.nextInt(getWidth()),((getHeight()/2)-playerSpawnBounds-rand.nextInt((getHeight()/2)-playerSpawnBounds))+wallBounds);
				break;
			}

			//This 'if' st. block will set the velocity of the new Asteroid to a randomized velocity.
			if(rand.nextInt(2) == 1){
				a.setVelocity(rand.nextDouble()/2,rand.nextDouble()/2);
			}else{
				a.setVelocity(rand.nextDouble()*-1/2,rand.nextDouble()*-1/2);
			}
			asteroids.add(a);
		}


	}

	/**
	 * This will paint all objects.
	 */
	public void paintComponent(Graphics g){
		
		super.paintComponent(g);
		
		Graphics gfx;
		//Creates an image to allow for double buffering.
		myImage = createImage(getWidth(),getHeight());
		gfx = myImage.getGraphics();

		//This 'if' statement will check to see if the game is over or not.
		if(!gameOver){

			//Draws black background behind everything.
			gfx.setColor(new Color(40,40,40));
			gfx.fillRect(0, 0, getWidth(), getHeight());

			//This loops draws every asteroid.
			for(int i = 0; i < asteroids.size(); i++){
				asteroids.get(i).drawOn(gfx);
			}

			//This loop draws all bullets currently visible.
			for(int i = 0; i < bullets.size(); i++){
				bullets.get(i).drawOn(gfx);
			}
			
			if(playerCanDie == false)player.setMyColor(Color.blue);
			//Draws the player.
			player.drawOn(gfx);

			for(Comet c: comets){
				c.drawOn(gfx);
			}

			//Draws score.
			gfx.setColor(Color.white);
			gfx.drawString("Score: "+score, 0,20 );
			gfx.drawString("Lives Remaining: "+shipCounter, 0,50 );
		} else {

			gfx.setColor(Color.black);
			gfx.fillRect(0,0, getWidth(), getHeight());

			gfx.setColor(Color.white);
			//Tells you game is over.
			gfx.drawString("Game Over", getWidth()/2, getHeight()/2);
			//This line draws the score.
			gfx.drawString("Score: "+score, getWidth()/2, getHeight()/2+20);

			//Depending on how the game ends, a win or a lose statement will be drawn.
			if(asteroids.size() == 0){
				gfx.drawString("You Destroyed Them All!", getWidth()/2, getHeight()/2+40);
			}else{
				gfx.drawString("Dang, you lost, get em' next time!", getWidth()/2, getHeight()/2+40);
			}
			timer.stop();
			//music.stop();
		}

		g.drawImage(myImage,0,0,this);
	}

	/**
	 * This is called to allow for double buffering.
	 */
	public void update(Graphics gfx){
		paintComponent(gfx);
	}

	/**
	 * This method creates and places the asteroids around the screen.
	 */
	public void createAsteroids(){
		Random rand = new Random();
		//This 'int' provides a barrier around player spawn with a radius that is 'playerSpawnBounds'.
		int playerSpawnBounds = 100;
		//This 'int' keeps the asteroids in the bounds of the window.
		int wallBounds = 20;
		for(int i = 0; i < MAX; i++){
			Asteroid a = new Asteroid(0,getWidth(),0,getHeight());
			int dir = rand.nextInt(4);
			switch(dir){
			case 0:
				//Right side spawning.
				a.setLocation(((getWidth()/2)+playerSpawnBounds+rand.nextInt((getWidth()/2)-playerSpawnBounds))-wallBounds,rand.nextInt(getHeight()));
				break;
			case 1:
				//Left side spawning.
				a.setLocation(((getWidth()/2)-playerSpawnBounds-rand.nextInt((getWidth()/2)-playerSpawnBounds))+wallBounds,rand.nextInt(getHeight()));
				break;
			case 2:
				//Down side spawning.
				a.setLocation(rand.nextInt(getWidth()),((getHeight()/2)+playerSpawnBounds+rand.nextInt((getHeight()/2)-playerSpawnBounds))-wallBounds);
				break;
			case 3:
				//Top side spawning.
				a.setLocation(rand.nextInt(getWidth()),((getHeight()/2)-playerSpawnBounds-rand.nextInt((getHeight()/2)-playerSpawnBounds))+wallBounds);
				break;
			}

			//This 'if' st. block will set the velocity of the new Asteroid to a randomized velocity.
			if(rand.nextInt(2) == 1){
				a.setVelocity(rand.nextDouble()/2,rand.nextDouble()/2);
			}else{
				a.setVelocity(rand.nextDouble()*-1/2,rand.nextDouble()*-1/2);
			}
			asteroids.add(a);
		}


	}

	public void createRandComet(){
		Random rand = new Random();
		Comet c = new Comet(0,getWidth(),0,getHeight());
		comets.add(c);
		int randLocChoice = rand.nextInt(8);
		switch(randLocChoice){
		case 0:
			c.setLocation(0,0);
			c.setVelocity(5,5);
			break;
		case 1:
			c.setLocation(getWidth()/2,0);
			c.setVelocity(0,5);
			break;
		case 2:
			c.setLocation(getWidth(),0);
			c.setVelocity(-5,5);
			break;
		case 3:
			c.setLocation(getWidth(),getHeight()/2);
			c.setVelocity(-5,0);
			break;
		case 4:
			c.setLocation(getWidth(),getHeight());
			c.setVelocity(-5,-5);
			break;
		case 5:
			c.setLocation(getWidth()/2,getHeight());
			c.setVelocity(0,-5);
			break;
		case 6:
			c.setLocation(0,getHeight());
			c.setVelocity(5,-5);
			break;
		case 7:
			c.setLocation(0,getHeight()/2);
			c.setVelocity(5,0);
			break;
		}
	}

	/**
	 * This class will allow the use of the keyboard to move the player and shoot bullets.
	 * @author Bryce DeVaughn
	 *
	 */
	private class KeyHelper implements KeyListener{

		private static final int TURNSPEED = 15;

		public KeyHelper(){
		}

		@Override
		public void keyPressed(KeyEvent arg0) {

			//Pressing the up arrow or the W key will thrust the ship in a direction.
			if(arg0.getKeyCode() == KeyEvent.VK_UP || arg0.getKeyCode() == KeyEvent.VK_W){
				player.thrust();
				//thrusterSound.play();
			}

			//Pressing the left key or A key will rotate the ship to the counter-clockwise.
			if(arg0.getKeyCode() == KeyEvent.VK_LEFT || arg0.getKeyCode() == KeyEvent.VK_A){
				player.rotate(-TURNSPEED*Math.PI/180);
			}

			//Pressing the right key or D key will rotate the ship to the clockwise.
			if(arg0.getKeyCode() == KeyEvent.VK_RIGHT || arg0.getKeyCode() == KeyEvent.VK_D){
				player.rotate(TURNSPEED*Math.PI/180);
			}

			//			//Pressing the down key or S key will slow the ship to a stop (But will reverse when stopped).
			//			if(arg0.getKeyCode() == KeyEvent.VK_DOWN || arg0.getKeyCode() == KeyEvent.VK_S){
			//				player.brake();
			//			}

			//Pressing the space bar will shoot a bullet.
			if(arg0.getKeyCode() == KeyEvent.VK_SPACE || arg0.getKeyCode() == KeyEvent.VK_ENTER){
				//Creates the bullet and adds it to the list of bullets.
				bullets.add(player.shoot());
				//shootSound.play();

			}

			//Pressing the space bar will shoot a bullet.
			if(arg0.getKeyCode() == KeyEvent.VK_Z){
				asteroids.clear();
			}

		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			//Releasing the up key or the W key will stop the player's ship.
			//			if(arg0.getKeyCode() == KeyEvent.VK_UP || arg0.getKeyCode() == KeyEvent.VK_W){
			//				player.setVelocity(0,0);
			//				}

		}

		@Override
		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * This class will be used to perform actions that are dependent on the timer. It will move all sprites
	 * , perform various colliding checkings and will remove items when needed.
	 * @author Bryce DeVaughn
	 *
	 */
	private class TimerHelper implements ActionListener{

		private int spawner = 0;
		
		@Override
		public void actionPerformed(ActionEvent arg0) {

			if(spawner == 150){
				createRandComet();
				spawner = 0;
			}

			ArrayList<Sprite> remB = new ArrayList<Sprite>(); // declare empty list for removing

			for (Bullet b : bullets) { // iterate over each bullet
				if (b.isTooOld()) remB.add(b); // add old bullets to remove list
			}
			bullets.removeAll(remB);

			//This loop allows all the asteroids to move and checks for collisions between the player and asteroids.
			for(int i = 0; i < asteroids.size(); i++){
				asteroids.get(i).move();
				collisionCheck(player,asteroids.get(i),remB);
			}

			//This loop allows all the bullets to move and checks for collisions between those and asteroids.
			for(int i = 0; i < bullets.size(); i++){
				bullets.get(i).move();
				for(int k = 0; k < asteroids.size(); k++){
					collisionCheck(bullets.get(i),asteroids.get(k),remB);
				}
			}
				
			/**
			 * Moves the comets.
			 */
			for(Comet c: comets){
				c.move();
				collisionCheck(player,c,remB);
				if(c.isTooOld())
					remB.add(c);
			}

			//Allows for player movement.
			player.move();

			bullets.removeAll(remB);
			asteroids.removeAll(remB);
			comets.removeAll(remB);

			//A check to make sure that if all asteroids are destoryed, the game will end.
			if(asteroids.size()==0)
				gameOver = true;

			spawner++;
			repaint();
		}
		
		/**
		 * This funciton gives temporary immunity to the player when it dies and respawns.
		 */
		public void playerImmunity(){
			Runnable r = new Runnable(){
				public void run(){
					
					playerCanDie = false;
					player.setMyColor(Color.green); //Makes the player green to indicate the immunity is active.
					
					/*
					 * This will create the immunity delay of 3 seconds.
					 */
					try {Thread.sleep(3000);}
					catch (InterruptedException e) {
						e.printStackTrace();
					}
					playerCanDie = true;
					player.setMyColor(Color.red); //Resets the color of the player to show immunity isn't active anymore.
				}
			};
			
			Thread thr = new Thread(r);
			thr.start();
			
		}
		
		/**
		 * This method will check for all collision situations that occur in the game.
		 * @param a
		 * @param b
		 * @param remB
		 */
		public void collisionCheck(Sprite a, Sprite b, ArrayList<Sprite> remB){
			if(a.isCollided(b) && playerCanDie){
				//Checks for player and asteroid collisions
				if(a == player && b instanceof Asteroid){
					shipCounter--;
					playerImmunity();
					player.setLocation(getWidth()/2,getHeight()/2);
					player.setVelocity(0,0);
					player.setAngle(0);
					//explodeSound.play();

					//Checks to see if all the lives have been lost.
					if(shipCounter < 1){
						gameOver = true;
						remB.add(player);
						remB.addAll(asteroids);
					}

					//Transfers the asteroid as a 'Sprite' to an 'Asteroid' type.
					Asteroid trashRoid = (Asteroid) b;
					asteroids.addAll(trashRoid.split());
					remB.add(trashRoid);

				}
				//Checks for bullet and asteroid collisions.
				if(a instanceof Bullet && b instanceof Asteroid){
					//Transfers the asteroid as a 'Sprite' to an 'Asteroid' type.
					Asteroid aObj = (Asteroid) b;
					if(aObj.size >= 20){
						asteroids.addAll(aObj.split());
					}
					//Puts the bullet and asteroid into the 'remB' list to be removed later on.
					remB.add(a);
					remB.add(b);
					score+=50;
					//explodeSound.play();
				}
				
				/*
				 * This is the check between the player and Comet objects.
				 */
				if(a == player && b instanceof Comet && playerCanDie){
					shipCounter--;
					playerImmunity();
					player.setLocation(getWidth()/2,getHeight()/2);
					player.setVelocity(0,0);
					player.setAngle(0);
					//explodeSound.play();

					//Checks to see if all the lives have been lost.
					if(shipCounter < 1){
						gameOver = true;
						remB.add(player);
						remB.addAll(asteroids);
					} 
				}
			}
		}

	}
}
