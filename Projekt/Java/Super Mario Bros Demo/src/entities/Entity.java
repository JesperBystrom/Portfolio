package entities;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import animation.Animation;
import main.BoxCollider;
import main.Game;
import main.Game.State;
import main.RenderHandler;
import main.Sprite;
import main.SpriteFactory;
import main.SpriteFactory.SpriteType;
import main.Vector2f;

public class Entity {

	public Vector2f position = new Vector2f(0,0);
	public Vector2f velocity = new Vector2f(0,0);
	public BoxCollider collider = new BoxCollider(position, 16, 16, 0, 0);
	public Sprite sprite;
	public Game game;
	protected boolean onGround = false;
	protected Animation animation;
	protected byte flip;
	protected float speed = 0;
	protected boolean turn = false;
	protected boolean jump = false;
	protected boolean invincible = false;
	protected int dir = 1;//-1 or 1;
	protected float jumpspeed = 0;
	private float holdtime = 0;
	protected float maxSpeed = 0;
	private float yOld;
	
	public Entity(Game game){
		this.game = game;
	}
	
	public void update(){
		if(Game.paused()){
			return;
		}
		if(velocity.y < 10)
			velocity.y += Game.GRAVITY;
		
		List<BoxCollider> colliders = game.getLevel().getColliders(collider, 4);
		colliders.forEach(i -> {
			if(collider.intersect(i, velocity.x, 0)){
				onHorizontalCollision(i);
			}
		});
		position.x += velocity.x;
		
		colliders.forEach(i -> {
			if(collider.intersect(i, 0, velocity.y)){
				onVerticalCollison(i);
			} 
		});
		//System.out.println("vel: " + velocity.y);
		position.y += velocity.y;
		
		List<Entity> entities = game.getLevel().getEntityCollisions(collider);
		entities.forEach(i -> {
			if(i != this){
				onCollision(i);
				//onHorizontalCollision(i.collider);
				//onVerticalCollison(i.collider);
			}
		});
	}
	
	public void render(RenderHandler renderHandler){
		if(invincible){
			if((game.getTicks() & 2) == 0) return;
			
		}
		if(Game.DEBUG)
			collider.render(renderHandler);
		renderHandler.render(new Vector2f(position.x,position.y), sprite, flip);
	}
	
	public void move(byte direction){
	}
	
	public void setPosition(Vector2f position){
		this.position = position;
		this.collider.position = position;
	}
	
	public void onCollision(Entity e){
		if(e.getSolid() == false) return;
	}
	
	public void onHorizontalCollision(BoxCollider collider){
		while(!this.collider.intersect(collider, Math.signum(velocity.x), 0)){
			position.x += Math.signum(velocity.x);
		}
		velocity.x = 0;
	}
	
	public void onVerticalCollison(BoxCollider collider){
		while(!this.collider.intersect(collider, 0, Math.signum(velocity.y))){
			position.y += Math.signum(velocity.y);
		}
		velocity.y = 0;
	}
	
	public boolean getOnGround(){
		return onGround;
	}
	
	public boolean getSolid(){
		return !getInvincible();
	}

	public void invincible() {
		invincible = true;
	}

	public boolean getInvincible() {
		return invincible;
	}
}
