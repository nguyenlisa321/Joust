//Lisa Nguyen (lyn5nw) + Jiawen Lin (jl9dc)

import java.awt.Rectangle;


public class CollisionBox {

	private Rectangle rect;
	
	public CollisionBox(Rectangle rect) {
		this.rect = rect;
	}
	public CollisionBox(int x, int y, int width, int height) { //first two numbers are coordinate to upper left hand corner
		this.rect = new Rectangle(x, y, width, height);
	}
	public Rectangle getRectangle() {	
		return this.rect;
	}
	public boolean collidesWith(CollisionBox other) {
		if(this.rect.intersects(other.rect)) {
			return true;
			
		}else{
			return false;
		}
	}
	public double getX() {
		return this.rect.x;
	}
	public double getY() {
		return this.rect.y;
	}
	public double getWidth() {
		return this.rect.width;
	}
	public double getHeight() {
		return this.rect.height;
	}
	public void moveTo(int x, int y) {
		this.rect.x = x;
		this.rect.y = y;
	}
	public void moveCenterTo(int x, int y) {
		int midwidth = this.rect.width / 2;
		int midheight = this.rect.height / 2;
		this.rect.x = x - midwidth;
		this.rect.y = y - midheight;
	}
	public boolean isHigherThan (CollisionBox other){
		if(this.rect.getCenterY() < other.rect.getCenterY())
		return true;
		else return false;
	}

	public boolean isLeftOf(CollisionBox other){
		if(this.rect.getCenterX() < other.rect.getCenterX())
		return true;
		else
			return false;
		
	}
	public int verticalDifference(CollisionBox other) {
		double diff1 = Math.abs(this.rect.getMinY() - other.rect.getMaxY());
		double diff2 = Math.abs(other.rect.getMinY() - this.rect.getMaxY());
		if (diff1>diff2) {
			return (int) diff2;
		}else {
			return (int) diff1;
		}	
	}
	public int horizontalDifference(CollisionBox other) {
		double diff1 = Math.abs(this.rect.getMinX() - other.rect.getMaxX());
		double diff2 = Math.abs(other.rect.getMinX() - this.rect.getMaxX());
		if (diff1>diff2) {
			return (int) diff2;
		}else {
			return (int) diff1;
		}	
	}
	public boolean isSmallerOverlapVertical(CollisionBox other) {
		if(this.verticalDifference(other) < this.horizontalDifference(other)) {
			return true;
		}else {
			return false;
		}
	}
//	public String toString(){
//		return "(" + this.rect.x +","+ this.rect.y +","+ this.rect.width +","+ this.rect.height +")";
//	}
	
	
	

}
