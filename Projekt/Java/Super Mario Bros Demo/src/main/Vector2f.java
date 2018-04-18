package main;

public class Vector2f {
	public float x;
	public float y;
	public Vector2f(float x, float y) {
		this.x = x;
		this.y = y;
	}
	
	public double distance(Vector2f position){
		return Math.abs(Math.sqrt(Math.pow(x-position.x, 2) + Math.pow(y-position.y, 2)));
	}
}
