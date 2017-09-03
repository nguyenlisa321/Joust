//Lisa Nguyen (lyn5nw) + Jiawen Lin (jl9dc)

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.Timer;


public class JoustScreen extends KeyAdapter implements ActionListener {

	/**
	 * A simple method to make the game runnable. You should not modify 
	 * this main method: it should print out a list of extras you added
	 * and then say "new JoustScreen();" -- nothing more than that.
	 */
	public static void main(String[] args) {
		// add a list of all extras you did, such as
		// System.out.println("Moving obstacles");
		// System.out.println("Birds leave trails of glowing faerie dust");
		// System.out.println("Press left-right-left-left-down to open a game of Mahjong");
		new JoustScreen();
	}

	
	// DO NOT CHANGE the next four fields (the window and timer)
	private JFrame window;         // the window itself
	private BufferedImage content; // the current game graphics
	private Graphics2D paintbrush; // for drawing things in the window
	private Timer gameTimer;       // for keeping track of time passing
	// DO NOT CHANGE the previous four fields (the window and timer)
	
	
	// TODO: add your own fields here
	private Bird bird1;
	private Bird bird2;
	private Scoreboard score;
	private Rectangle bounds; 
	private CollisionBox wall1;
	private CollisionBox wall2;
	private int moving;
	private CollisionBox movingobstacle;
	private BufferedImage bomb;
	private CollisionBox bombbox;

	
	public JoustScreen() {
		// DO NOT CHANGE the window, content, and paintbrush lines below
		this.window = new JFrame("Joust Clone");
		this.content = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
		this.paintbrush = (Graphics2D)this.content.getGraphics();
		this.window.setContentPane(new JLabel(new ImageIcon(this.content)));
		this.window.pack();
		this.window.setVisible(true);
		this.window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.window.addKeyListener(this);
		// DO NOT CHANGE the window, content, and paintbrush lines above

		
		// TODO: add anything else you might need (e.g., a couple of Bird objects, some walls)
		this.bird1 = new Bird("birdr",175,200);
		this.bird2 = new Bird("birdg",650,200);
		this.score = new Scoreboard();
		this.bounds = new Rectangle(24,24, 750, 550);
		this.wall1 = new CollisionBox(0,300,150,15);
		this.wall2 = new CollisionBox(350,150,250,15);

		this.moving = 0;
		
		try {
			this.bomb = ImageIO.read(new File("bomb.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.bombbox = new CollisionBox(0,100,40,40);


		// DO NOT CHANGE the next two lines nor add lines after them
		this.gameTimer = new Timer(20, this); // tick at 1000/20 fps
		this.gameTimer.start();               // and start ticking now
		// DO NOT CHANGE the previous two lines nor add lines after them
	}
		

	/**
	 * This method gets called each time a player presses a key.
	 * You can find out what key the pressed by comparing event.getKeyCode() with KeyEvent.VK_...
	 */
	public void keyPressed(KeyEvent event) {
		
		// TODO: handle the keys you want to use to run your game

		if (event.getKeyCode() == KeyEvent.VK_A) { // example
			bird1.flapleft();
		}
		if (event.getKeyCode() == KeyEvent.VK_S) { // example
			bird1.flapright();
		}	
		if (event.getKeyCode() == KeyEvent.VK_K) { // example
			bird2.flapleft();
		}		
		if (event.getKeyCode() == KeyEvent.VK_L) { // example
			bird2.flapright();
		}
		
	}

	/**
	 * Java will call this every time the gameTimer ticks (50 times a second).
	 * If you want to stop the game, invoke this.gameTimer.stop() in this method.
	 */
	public void actionPerformed(ActionEvent event) {
		// DO NOT CHANGE the next four lines, and add nothing above them
		if (! this.window.isValid()) { // the "close window" button
			this.gameTimer.stop();     // should stop the timer
			return;                    // and stop doing anything else
		}                              
		// DO NOT CHANGE the previous four lines
		
		
		
		// TODO: add every-frame logic in here (gravity, momentum, collisions, etc)
		bird1.move();
		bird2.move();
		bird1.bounceIfOutsideOf(bounds, .3);
		bird2.bounceIfOutsideOf(bounds, .3);
		bird1.bounceObstacle(wall1);
		bird1.bounceObstacle(wall2);
		bird2.bounceObstacle(wall1);
		bird2.bounceObstacle(wall2);
		

		if(bird1.onTop(bird2)){
			score.incrementScore1();	
			bird2.moveTo(650,200);
		}else {
			bird1.bounceBird(bird2.getHitbox());
		}
		if(bird2.onTop(bird1)){
			score.incrementScore2();
			bird1.moveTo(175,200);
		}else {
			bird2.bounceBird(bird1.getHitbox());
		}
		if(moving==-100 || moving<100 && moving>=0){
			if(moving==-100) {
				moving = 0;
			}
			this.paintbrush.fill(new Rectangle(400+moving,320,100,15));
			this.movingobstacle = new CollisionBox(400+moving,320,100,15);
			bird1.bounceObstacle(movingobstacle);
			bird2.bounceObstacle(movingobstacle);
			moving++;
		}else if(moving==100 || moving<0 && moving>-100){
			if(moving==100) {
				moving = 0;
			}
			this.paintbrush.fill(new Rectangle(400+100+moving,320,100,15));
			this.movingobstacle = new CollisionBox(400+100+moving,320,100,15);
			bird1.bounceObstacle(movingobstacle);
			bird2.bounceObstacle(movingobstacle);
			moving--;
		}
		
		if(bird2.getHitbox().collidesWith(bombbox)){
			score.incrementScore1();	
			bird2.moveTo(650,200);
		}
		if(bird1.getHitbox().collidesWith(bombbox)){
			score.incrementScore2();	
			bird1.moveTo(175,200);
		}
	
		
		//System.out.println(bird1.getHitbox().getY() + "," + bird2.getHitbox().getY());

		
		
		// DO NOT CHANGE the next line; it must be last in this method
		this.refreshScreen(); // redraws the screen after things move
		// DO NOT CHANGE the above line; it must be last in this method
	}

	/**
	 * Re-draws the screen. You should erase the old image and draw a 
	 * new one, but you should not change anything in this method
	 * (use actionPerformed instead if you need something to change).
	 */
	private void refreshScreen() {
		this.paintbrush.setColor(new Color(150, 210, 255)); // pale blue
		this.paintbrush.fillRect(0, 0, this.content.getWidth(), this.content.getHeight()); // erases the previous frame

		
		// TODO: replace the following example code with code that does 
		// the right thing (i.e., draw the birds, walls, and score)
		
		// example bird drawing; replace with something sensible instead of making a new bird every frame
		if(bird1.isLeftOf(bird2)) {
		bird1.draw(this.paintbrush);
		bird2.draw2(this.paintbrush);
		}
		else if(!bird1.isLeftOf(bird2)){
			bird1.draw2(this.paintbrush);
			bird2.draw(this.paintbrush);
		}
	
					

		// example wall drawing; replace with something sensible instead of making a new wall every frame
		this.paintbrush.setColor(Color.BLACK);
		this.paintbrush.fill(new Rectangle(0,300,150,15));
		this.paintbrush.fill(new Rectangle(350,150,250,15));
		this.paintbrush.fill(movingobstacle.getRectangle());
		// example text drawing, for scores and/or other messages
		Font f = new Font(Font.SANS_SERIF, Font.BOLD, 20);
		this.paintbrush.setFont(f);
		this.paintbrush.setColor(new Color(127,0,0)); // dark red
		this.paintbrush.drawString(""+this.score.getScore1(), 30, 30);
		this.paintbrush.setColor(new Color(0,127,0)); // dark green
		this.paintbrush.drawString(""+this.score.getScore2(), 760, 30);
		f = new Font(Font.SANS_SERIF, Font.BOLD, 60);
		if(score.getScore1()>=5 || score.getScore2()>=5) {
			this.paintbrush.setFont(f);
			this.paintbrush.setColor(new Color(0,30,127));
			this.paintbrush.drawString("Gameover", 245, 120);
			if(score.getScore1()>score.getScore2()){
				f = new Font(Font.SANS_SERIF, Font.BOLD, 20);
				this.paintbrush.setFont(f);
				this.paintbrush.setColor(new Color(127,0,0));
				this.paintbrush.drawString("Red Bird Wins!", 320, 300);
			}else if(score.getScore2()>score.getScore1()){
				f = new Font(Font.SANS_SERIF, Font.BOLD, 20);
				this.paintbrush.setFont(f);
				this.paintbrush.setColor(new Color(0,127,0));
				this.paintbrush.drawString("Green Bird Wins!", 320, 300);
			}
		}
		this.paintbrush.setFont(f);
		this.paintbrush.setColor(Color.BLUE);
		

		this.paintbrush.drawImage(bomb, 0, 100, window);
		//this.paintbrush.drawString(msg, 400-(int)r.getWidth()/2, 300);
		
		
		
		// DO NOT CHANGE the next line; it must be last in this method
		this.window.repaint();  // displays the frame to the screen
		// DO NOT CHANGE the above line; it must be last in this method
	}
	



}
