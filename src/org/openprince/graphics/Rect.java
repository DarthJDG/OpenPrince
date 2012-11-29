package org.openprince.graphics;

public class Rect {
	public int x1;
	public int y1;
	public int x2;
	public int y2;
	
	public Rect(int x1, int y1, int x2, int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public Rect() {
		this(0, 0, 0, 0);
	}
	
	public Rect(int x, int y) {
		this(x, y, x, y);
	}
	
	public void setWidth(int width) {
		x2 = x1 + width;
	}
	
	public void setHeight(int height) {
		y2 = y1 + height;
	}
	
	public int getWidth() {
		return x2 - x1;
	}
	
	public int getHeight() {
		return y2 - y1;
	}
}
