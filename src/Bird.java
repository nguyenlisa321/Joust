//Lisa Nguyen (lyn5nw) + Jiawen Lin (jl9dc)

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Bird {

	// / imgs: default storage for the pictures of the bird
	private BufferedImage[] imgs;

	// TODO: add your own fields here
	private double x;
	private double y;
	private double dy;
	private double dx;
	private double gravity;
	private double wind;

	private CollisionBox hitbox;

	/**
	 * Creates a bird object with the given image set
	 * 
	 * @param basename
	 *            should be "birdg" or "birdr" (assuming you use the provided
	 *            images)
	 */
	public Bird(String basename, double x, double y) {
		// You may change this method if you wish, including adding
		// parameters if you want; however, the existing image code works as is.
		this.x = x;
		this.y = y;

		dy = 0;
		dx = 0;
		gravity = 0.4;
		wind = 0.1;
		setHitbox(new CollisionBox((int) x, (int) y, 25, 25));
		this.imgs = new BufferedImage[6];
		try {
			// 0-2: right-facing (folded, back, and forward wings)
			this.imgs[0] = ImageIO.read(new File(basename + ".png"));
			this.imgs[1] = ImageIO.read(new File(basename + "f.png"));
			this.imgs[2] = ImageIO.read(new File(basename + "b.png"));
			// 3-5: left-facing (folded, back, and forward wings)
			this.imgs[3] = Bird.makeFlipped(this.imgs[0]);
			this.imgs[4] = Bird.makeFlipped(this.imgs[1]);
			this.imgs[5] = Bird.makeFlipped(this.imgs[2]);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * A helper method for flipping in image left-to-right into a mirror image.
	 * There is no reason to change this method.
	 * 
	 * @param original
	 *            The image to flip
	 * @return A left-right mirrored copy of the original image
	 */
	private static BufferedImage makeFlipped(BufferedImage original) {
		AffineTransform af = AffineTransform.getScaleInstance(-1, 1);
		af.translate(-original.getWidth(), 0);
		BufferedImage ans = new BufferedImage(original.getWidth(),
				original.getHeight(), original.getType());
		Graphics2D g = (Graphics2D) ans.getGraphics();
		g.drawImage(original, af, null);
		return ans;
	}

	/**
	 * Draws this bird
	 * 
	 * @param g
	 *            the paintbrush to use for the drawing
	 */
	public void draw(Graphics g) {
		int i;
		if(this.dx<1 && this.dx>=0){
			i = 0;
			g.drawImage(this.imgs[i], (int) x - this.imgs[i].getWidth() / 2,
					(int) y - this.imgs[i].getHeight() / 2, null);
		}else if(this.dx>-1 && this.dx<=0){
			i = 0;	
			g.drawImage(this.imgs[i], (int) x - this.imgs[i].getWidth() / 2,
					(int) y - this.imgs[i].getHeight() / 2, null);
		}else if(this.dx>=1 && this.dy<0){
			i = 1;
			g.drawImage(this.imgs[i], (int) x - this.imgs[i].getWidth() / 2,
					(int) y - this.imgs[i].getHeight() / 2, null);
		}else if(this.dx>=1 && this.dy>0) {
			i = 0;
			g.drawImage(this.imgs[i], (int) x - this.imgs[i].getWidth() / 2,
					(int) y - this.imgs[i].getHeight() / 2, null);
		}else if(this.dx<=-1 && this.dy<0) {
			i = 2;
			g.drawImage(this.imgs[i], (int) x - this.imgs[i].getWidth() / 2,
					(int) y - this.imgs[i].getHeight() / 2, null);
		}else if(this.dx<=-1 &&this.dy>0 ){
			i = 0;
			g.drawImage(this.imgs[i], (int) x - this.imgs[i].getWidth() / 2,
					(int) y - this.imgs[i].getHeight() / 2, null);
		}
		double x = this.x; // where to center the picture
		double y = this.y;

		// TODO: find the right x, y, and i instead of the examples given here
	}

	public void draw2(Graphics g) {
		int i;
		if(this.dx<1 && this.dx>=0){
			i = 3;
			g.drawImage(this.imgs[i], (int) x - this.imgs[i].getWidth() / 2,
					(int) y - this.imgs[i].getHeight() / 2, null);
		}else if(this.dx>-1 && this.dx<=0){
			i = 3;	
			g.drawImage(this.imgs[i], (int) x - this.imgs[i].getWidth() / 2,
					(int) y - this.imgs[i].getHeight() / 2, null);
		}else if(this.dx>=1 && this.dy<0){
			i = 5;
			g.drawImage(this.imgs[i], (int) x - this.imgs[i].getWidth() / 2,
					(int) y - this.imgs[i].getHeight() / 2, null);
		}else if(this.dx>=1 && this.dy>0) {
			i = 3;
			g.drawImage(this.imgs[i], (int) x - this.imgs[i].getWidth() / 2,
					(int) y - this.imgs[i].getHeight() / 2, null);
		}else if(this.dx<=-1&&this.dy<0 ){
			i = 4;
			g.drawImage(this.imgs[i], (int) x - this.imgs[i].getWidth() / 2,
					(int) y - this.imgs[i].getHeight() / 2, null);
		}else if(this.dx<=-1 &&this.dy>0 ){
			i = 3;
			g.drawImage(this.imgs[i], (int) x - this.imgs[i].getWidth() / 2,
					(int) y - this.imgs[i].getHeight() / 2, null);
		}
		double x = this.x; // where to center the picture
		double y = this.y;

		// TODO: find the right x, y, and i instead of the examples given here

	}
	
	

	public void move() {
		dy = dy + gravity;
		if(dx>0) {
			dx = dx - wind;
		}else if (dx<0) {
			dx = dx + wind;
		}
		y = y + dy;
		x = x + dx;
		getHitbox().moveCenterTo((int) x, (int) y);
	}
	public void moveTo(int x, int y) {
		this.x = x;
		this.dx = 0;
		this.y = y;
		this.dy = 0;
		getHitbox().moveCenterTo((int) x, (int) y);
	}

	public void flapleft() {
		dy = -8;
		dx = -4;
		try {
		    Thread.sleep(40);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}

	}

	public void flapright() {
		dy = -8;
		dx = 4;
		try {
		    Thread.sleep(40);                 //1000 milliseconds is one second.
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}

	public void bounceIfOutsideOf(Rectangle r, double bounciness) {
		if (this.x < r.getMinX()) {
			this.x = r.getMinX();
			this.dx = Math.abs(dx * bounciness);
		}
		if (this.x > r.getMaxX()) {
			this.x = r.getMaxX();
			this.dx = -Math.abs(dx * bounciness);
		}

		if (this.y < r.getMinY()) {
			this.y = r.getMinY();
			this.dy = Math.abs(dy * bounciness);
		}
		if (this.y > r.getMaxY()) {
			this.y = r.getMaxY();
			this.dy = -Math.abs(dy * bounciness);
			this.dx = 0;
		}
	}

	public void bounceBird(CollisionBox b) {
		if(this.getHitbox().collidesWith(b)) {
			gravity = 0;
			this.dx = -this.dx;
			this.dy = -this.dy;
		}
		gravity = 0.4;
	}
	public void bounceObstacle(CollisionBox b) {
		if(this.getHitbox().collidesWith(b) && this.getHitbox().isHigherThan(b)) {
			gravity = 0;
			this.dx = -this.dx;
			this.dy = -this.dy;
			if (this.dy > -1){
				this.dy =-4;
			}
		}else if (this.getHitbox().collidesWith(b) && !this.getHitbox().isHigherThan(b)){
			gravity = 0;
			this.dx = -this.dx;
			this.dy = -this.dy;
			
		
		}
		gravity = 0.4;
	}
		
	
//				&& this.x<b.getX() && this.dx>0) {
//			this.x = b.getX();
//			this.dx = 0;
//		}
//		if(this.hitbox.collidesWith(b) && this.x>(b.getX()+b.getWidth()) && this.dx<0) {
//			this.x = b.getX()+b.getWidth();
//			this.dx = 0;
//		}
//		if(this.hitbox.collidesWith(b) && this.y<b.getY() && this.dy>0) {
//			this.y = b.getY();
//			this.dy = 0;
//		}
//		if(this.hitbox.collidesWith(b) && this.y>(b.getY()+b.getHeight()) && this.dy<0) {
//			this.y = b.getY()+b.getHeight();
//			this.dy = 0;
//		}


	public CollisionBox getHitbox() {
		return hitbox;
	}

	public void setHitbox(CollisionBox hitbox) {
		this.hitbox = hitbox;
	}
	
	public boolean onTop(Bird b) {
		if(this.hitbox.collidesWith(b.hitbox) && this.hitbox.getY()<b.hitbox.getY() && Math.abs(this.x-b.x)<20) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean isLeftOf(Bird b) {
		if((this.hitbox.getX() + (this.hitbox.getWidth())/2) < b.hitbox.getX() + (b.hitbox.getHeight())/2) {
			return true;
		}else {
			return false;
		}	
	}

}
