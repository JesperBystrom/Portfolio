package main;

import entities.Entity;
import networking.NetEntityPlayer;
import tools.Vector2;

/*
 * @author Jesper Byström
 * Datum: 2018-02-10
 * Class: 
 * 
 */

public class BoxCollider {
	
	public Entity entity;
	public Vector2 position;
	public int width;
	public int height;
	
	public BoxCollider(Entity e, Vector2 pos, int w, int h){
		this.position = pos;
		this.width = w;
		this.height = h;
	}
	
	public boolean intersect(BoxCollider other){
		//if(Game.DEBUG) return false;
		
		//System.out.println("Colliding with: " + other + " | " + other.entity + " | at: " + other.position.x + " | " + other.position.y);
		
		float xMe = this.position.x;
		float yMe = this.position.y;
		
		float xOther = other.position.x;
		float yOther = other.position.y;
		
		int wMe = width/2;
		int hMe = height/2;
		
		int wOther = other.width/2;
		int hOther = other.height/2;
		
		if(xMe-wMe > xOther+wOther || xOther-wOther > xMe+wMe)
			return false;
		if(yMe-hMe > yOther+hOther || yOther-hOther > yMe+hMe)
			return false;
		return true;
		
		//return (xMe < xOther + wOther && xMe + wMe > xOther && yMe < yOther + hOther && hMe + yMe > yOther);
		
		/*int xx = Math.abs(position.x - other.position.x);
		int yy = Math.abs(position.y - other.position.y);
		
		if(this.position.x > other.position.x - other.width &&
			this.position.x < other.position.x + other.width &&
			this.position.y > other.position.y - other.height &&
			this.position.y < other.position.y + other.height)
				return new BoxCollider(new Vector2(1,1), this.width, this.height);	
		return null;*/
	}
}
