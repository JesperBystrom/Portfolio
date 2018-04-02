package entities;

import main.Main;
import main.NetworkServer;
import tools.Vector2;

public class Entity {
	protected Main main;
	public Vector2 position = new Vector2(1,1);
	public int speed = 1;
	public Vector2 velocity = Vector2.zero();
	public int direction;
	public int health = 3;
	public int flip = 0;
	public int entityId;
	public EntityType entityType = EntityType.HUMAN;
	
	public Entity(){
	}
	
	public Entity(Main main){
		this.main = main;
	}
	
	public void update(){
	}
	public void kill(){
		main.getMap().removeEntity(this);
	}
	public void hurt(int amount, int port) {
		health -= amount;
		if(health <= 0)
			kill();
	}
}
