package entities;

import java.util.Random;

import main.Game;
import main.Screen;
import tools.Tools;
import tools.Vector2;
import world.Map;

/*
 * @author Jesper Byström
 * Datum: 2018-02-10
 * Class: 
 * 
 */

public class EntityMob extends EntityLiving {
	
	private EntityState state;
	private EntityLiving chasing;
	
	public boolean move;
	public int xTowards;
	public int yTowards;
	
	public EntityMob(Game game) {
		super(game);
		state = EntityState.IDLE;
	}
	
	@Override
	public void update() {
		
		super.update();
		
		speed = game.getTicks() & 1;
		if(move){
			move();
		}
	}
	
	public void chase(EntityLiving e){
		moveTowards(e.position.x, e.position.y);
	}
	
	public void hurt(EntityLiving source, int damage, Vector2 dir) {
		super.hurt(damage, dir);
		setState(EntityState.CHASING);
		chasing = source;
	}
	
	public void setState(EntityState state){
		this.state = state;
	}
	public EntityState getState(){
		return state;
	}
	
	public void move(){
		float xDelta = position.x - xTowards;
		float yDelta = position.y - yTowards;
		
		int xVel = 0, yVel = 0;
		
		if(xDelta < 0)
			xVel++;
		if(xDelta > 0)
			xVel--;
		if(yDelta < 0)
			yVel++;
		if(yDelta > 0)
			yVel--;
		
		velocity.x = xVel * speed;
		velocity.y = yVel * speed;
		//if(game.getTicks() % 2 == 0)
		//super.move(velocity.x, velocity.y);
	}
	
	public void moveTowards(int x, int y) {
		move = true;
		xTowards = x;
		yTowards = y;
	}
}
