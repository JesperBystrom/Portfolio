package entities;

import java.awt.event.KeyEvent;
import java.util.List;

import animation.Animation;
import main.BoxCollider;
import main.Game;
import main.RenderHandler;
import main.Sprite;
import main.SpriteFactory;
import main.SpriteFactory.SpriteType;
import main.Timer;
import main.Vector2f;
import tiles.Tile;

enum GrowState {
	SMALL, BIG
}

public class Player extends Entity {

	//private float growTimer = -1;
	private float holdTime = 0;
	private float baseAcceleration = 0.0882f;
	private float acceleration = baseAcceleration;
	private GrowState growState = GrowState.SMALL;
	
	private Animation bigMarioAnimation;
	private Animation smallMarioAnimation;
	private Sprite[] growPattern = {
			SpriteFactory.getInstance().getSprite(SpriteType.MARIO_BIG_IDLE),
			SpriteFactory.getInstance().getSprite(SpriteType.MARIO_MIDDLE),
			SpriteFactory.getInstance().getSprite(SpriteType.MARIO_IDLE),
			SpriteFactory.getInstance().getSprite(SpriteType.MARIO_MIDDLE),
			SpriteFactory.getInstance().getSprite(SpriteType.MARIO_IDLE),
			SpriteFactory.getInstance().getSprite(SpriteType.MARIO_MIDDLE),
			SpriteFactory.getInstance().getSprite(SpriteType.MARIO_BIG_IDLE)
			
	};
	public Sprite idleSprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_IDLE);
	public Sprite jumpSprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_JUMP);
	public Sprite turnSprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_TURN);
	private Timer growTimer = new Timer(growPattern.length-1);
	
	public Player(Game game) {
		super(game);
		jumpspeed = 4.3f;
		maxSpeed = 1.5f;
		setPosition(new Vector2f(128,0));
		
		smallMarioAnimation = new Animation(
				SpriteFactory.getInstance().getSprite(SpriteType.MARIO_RUN_3),
				SpriteFactory.getInstance().getSprite(SpriteType.MARIO_RUN_2),
				SpriteFactory.getInstance().getSprite(SpriteType.MARIO_RUN_1)
		);
		bigMarioAnimation = new Animation(
				SpriteFactory.getInstance().getSprite(SpriteType.MARIO_BIG_RUN_3),
				SpriteFactory.getInstance().getSprite(SpriteType.MARIO_BIG_RUN_2),
				SpriteFactory.getInstance().getSprite(SpriteType.MARIO_BIG_RUN_1)
		);
		animation = smallMarioAnimation;
		game.addAnimation(animation);
		game.addAnimation(smallMarioAnimation);
		game.addAnimation(bigMarioAnimation);
		growTimer.setSpeed(7);
		game.addTimer(growTimer);
		
		sprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_IDLE);
	}
	
	@Override
	public void update() {
		
		switch(growState){
		case BIG:
			animation = bigMarioAnimation;
			idleSprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_BIG_IDLE);
			jumpSprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_BIG_JUMP);
			turnSprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_BIG_TURN);
			collider.height = 32;
			collider.yo = -16;
			collider.width = 18;
			collider.xo = -2;
			break;
		case SMALL:
			collider.width = 12;
			collider.height = 12;
			collider.xo = 2;
			collider.yo = 3;

			animation = smallMarioAnimation;
			idleSprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_IDLE);
			jumpSprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_JUMP);
			turnSprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_TURN);
			break;
		}
		
		if(!game.getKeyManager().getAnyArrowKey()){
			if(velocity.x > 0)
				velocity.x -= 0.375f; //deaccelerate
			else if(velocity.x < 0)
				velocity.x += 0.375f; //deaccelerate
			
			if(velocity.x < 0.375f && velocity.x > -0.375f)
				velocity.x = 0;
		}
		
		super.update();
		
		if(game.getLevel().getWin()){
			velocity.x = 0;
			velocity.y = -Game.GRAVITY;
			setEntityState(EntityState.WIN);
			if(game.getLevel().getFlagPole().getFlag().position.y < game.getLevel().getHeight()-64){
				if(position.y <= game.getLevel().getFlagPole().getFlag().position.y)
					position.y++;
			} else {
				velocity.x = 1;
				velocity.y = Game.GRAVITY*5;
				collider.xo = sprite.xOffset+8;
			}
			return;
		}
		
		//Idle
		if(velocity.x == 0 || onGround)
			idle();
		
		//Move
		if(game.getKeyManager().getKey(KeyEvent.VK_LEFT)){
			move((byte)-1);
		}
		
		if(game.getKeyManager().getKey(KeyEvent.VK_RIGHT)){
			move((byte)1);
		}
		
		//Sprint
		if(game.getKeyManager().getKey(KeyEvent.VK_C)){
			maxSpeed = 2.5f;
		} else {
			maxSpeed = 1.5f;
		}
		
		//Hold space for higher jumps
		if(holdTime >= 0)
			holdTime++;
		if(holdTime > 2 && holdTime < 24 && game.getKeyManager().getKey(KeyEvent.VK_SPACE) && !onGround){
			velocity.y -= (0.2f + Math.abs(velocity.x/20));
		} else if(!game.getKeyManager().getKey(KeyEvent.VK_SPACE) || onGround)
			holdTime = 0;
		
		//Grow
		if(growTimer.getFinished()){
			if(getGrowState() == GrowState.SMALL)
				setGrowState(GrowState.BIG);
			else
				setGrowState(GrowState.SMALL);
			growTimer.stop();
			setEntityState(EntityState.IDLING);
		}
		
		//Jump
		if(game.getKeyManager().getKey(KeyEvent.VK_SPACE) && onGround){
			jump();
		}
		
		if(position.x < game.getWall()){
			position.x = game.getWall();
		}
		
		animation.setSpeed(Math.abs(velocity.x));
		 
		//super.update();
	}
	
	public GrowState getGrowState() {
		return growState;
	}
	
	@Override
	public void onHorizontalCollision(BoxCollider collider) {
		if(!game.getLevel().getWin())
			super.onHorizontalCollision(collider);
	}
	
	@Override
	public void onVerticalCollison(BoxCollider collider) {
		super.onVerticalCollison(collider);
		if(position.y < collider.position.y){
			onGround = true;
		} else {
			//if bounce on it, for question block etc
			Tile t = game.getLevel().getTile((int)collider.position.x>>4, (int)collider.position.y>>4);
			List<Entity> entities = game.getLevel().getEntityCollisions(new BoxCollider(new Vector2f((int)collider.position.x, (int)collider.position.y-16), 16, 16));
			if(t != null)
				if(t.canBump() && this.collider.getSolid()){
					t.bump(this);
					entities.forEach(e -> {
						e.push(Direction.UP, 3);
					});
				}
		}
		holdTime = 24;
	}

	public void grow() {
		setEntityState(EntityState.GROWING);
		growTimer.start();
		Game.pause(60);
	}
	
	@Override
	public void idle(){
		super.idle();
	}
	
	public void turn() {
		if(getEntityState() == EntityState.MOVING && getEntityState() != EntityState.DYING)
		setEntityState(EntityState.TURNING);
		sprite.xOffset *= -dir;
		
		sprite = turnSprite;
		if(Math.round(velocity.x) == 0){
			animation.setCurrentFrame(0);
			return;
		}
	}
	
	public void jump() {
		if(getEntityState() != EntityState.DYING)
			setEntityState(EntityState.JUMPING);
		acceleration = baseAcceleration / 2;
		velocity.y = -jumpspeed;
		onGround = false;
	}
	
	@Override
	public void move(byte direction) {
		super.move(direction);
		
		if(velocity.x < maxSpeed && velocity.x > -maxSpeed){
			velocity.x += acceleration * direction;
		}
		
		if(velocity.x > maxSpeed)
			velocity.x -= 0.375f;
		else if(velocity.x < -maxSpeed)
			velocity.x += 0.375f;
		
		if(onGround){
			flip = (byte) -direction;
			if(direction == 1){
				if(velocity.x < -0.5f)
					turn();
			}
			
			if(direction == -1){
				if(velocity.x > 0.5f)
					turn();
			}
		}
	}
	
	@Override
	public void hurt() {
		if(getGrowState() == GrowState.BIG){
			shrink();
		} else {
			kill();
		}
		super.hurt();
	}
	
	public void shrink(){
		growTimer.start();
		setEntityState(EntityState.GROWING);
	}
	
	public boolean isSmall() {
		return growState == GrowState.SMALL;
	}
	
	public boolean isBig(){
		return growState == GrowState.BIG;
	}
	
	public void setGrowState(GrowState state){
		this.growState = state;
	}

	@Override
	protected void onTurning() {
		sprite = turnSprite;
	}

	@Override
	protected void onJumping() {
		sprite = jumpSprite;
	}

	@Override
	protected void onMoving() {
		sprite = animation.getCurrentFrame();
	}

	@Override
	protected void onIdling() {
		sprite = idleSprite;
	}
	
	@Override
	protected void onGrowing() {
		if(growTimer.getTime() >= 0)
			sprite = growPattern[(int)growTimer.getTime()];
	}
	
	@Override
	protected void onDying() {
		sprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_DIE);
	}
	
	@Override
	protected void onWin() {
		if(velocity.x == 0){
			sprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_WIN);
			if(position.y > game.getLevel().getHeight()-64){
				flip = Sprite.FLIP_X;
			}
		} else {
			flip = Sprite.FLIP_NONE;
			sprite = animation.getCurrentFrame();
			animation.setSpeed(1);
		}
	}
	
	@Override
	public void kill() {
		setEntityState(EntityState.DYING);
		velocity.y = -8;
		collider.setSolid(false);
	}
}
