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
import main.Timer;
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
	protected EntityState state = EntityState.IDLING;
	protected Timer timer = new Timer(6);
	protected Timer invincibleTimer = new Timer(15);
	protected boolean affectedByGravity = true;
	
	public Entity(Game game){
		this.game = game;
		game.addTimer(timer);
		game.addTimer(invincibleTimer);
	}
	
	public void update(){
		if(Game.paused()){
			return;
		}
		if(velocity.y < 10 && affectedByGravity)
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
			onInvincible();
			if((game.getTicks() & 2) == 0) return;
		}
		if(Game.DEBUG)
			collider.render(renderHandler);
		
		switch(state){
		case IDLING:
			onIdling();
			break;
		case MOVING:
			onMoving();
			break;
		case JUMPING:
			onJumping();
			break;
		case TURNING:
			onTurning();
			break;
		case GROWING:
			onGrowing();
			break;
		case DYING:
			onDying();
		case WIN:
			onWin();
			break;
		}
		
		renderHandler.render(new Vector2f(position.x,position.y), sprite, flip);
	}

	public void idle(){
		if((getEntityState() != EntityState.GROWING && onGround) && getEntityState() != EntityState.DYING)
			setEntityState(EntityState.IDLING);
	}
	
	public void move(byte direction){
		if(getEntityState() == EntityState.IDLING)
			setEntityState(EntityState.MOVING);
	}
	
	public void hurt() {
		invincible = true;
		invincibleTimer.start();
	}
	
	public void push(Direction direction, float force) {
		switch(direction){
		case UP:
			velocity.y = -force;
			break;
		case DOWN:
			velocity.y = force;
			break;
		case LEFT:
			velocity.x = -force;
			break;
		case RIGHT:
			velocity.x = force;
			break;
		}
	}

	public void kill(){
		timer.stop();
		game.getLevel().removeEntity(this);
	}
	
	public void setPosition(Vector2f position){
		this.position = position;
		this.collider.position = this.position;
	}
	
	public boolean onCollision(Entity e){
		if(e.getSolid() == false || !this.collider.getSolid()) return false;
		return true;
	}
	
	public void onHorizontalCollision(BoxCollider collider){
		if(!this.collider.getSolid()) return;
		while(!this.collider.intersect(collider, Math.signum(velocity.x), 0)){
			position.x += Math.signum(velocity.x);
		}
		velocity.x = 0;
	}
	
	public void onVerticalCollison(BoxCollider collider){
		if(!this.collider.getSolid()) return;
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

	public boolean getInvincible() {
		return invincible;
	}
	
	protected void toggleGravity(){
		affectedByGravity ^= true;
	}
	
	protected void setEntityState(EntityState state){
		if(!this.state.getLocked())
			this.state = state;
	}
	
	protected EntityState getEntityState(){
		return state;
	}
	
	protected void onGrowing() {
		
	}

	protected void onTurning() {
		
	}

	protected void onJumping() {
		
	}

	protected void onMoving() {
		
	}

	protected void onIdling() {
		
	}
	
	protected void onDying(){
		if(timer.getFinished())
			kill();
	}
	
	protected void onInvincible() {
		if(invincibleTimer.getFinished()){
			invincible = false;
			invincibleTimer.stop();
		}
	}
	
	protected void onWin() {
	}
}
