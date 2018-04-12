package entities;

import java.awt.event.KeyEvent;

import animation.Animation;
import main.BoxCollider;
import main.Game;
import main.RenderHandler;
import main.Sprite;
import main.SpriteFactory;
import main.Vector2f;
import main.Game.State;
import main.SpriteFactory.SpriteType;
import tiles.Tile;

enum PlayerState {
	SMALL, BIG
}

public class Player extends Mob {

	private float growTimer = 0;
	private PlayerState state = PlayerState.SMALL;
	private Animation bigMarioAnimation;
	private Animation smallMarioAnimation;
	public Sprite idleSprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_IDLE);
	public Sprite jumpSprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_JUMP);
	public Sprite turnSprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_TURN);
	
	public Player(Game game) {
		super(game);
		jumpspeed = 4.6f;
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
		
		sprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_IDLE);
	}

	@Override
	public void update() {
		
		switch(state){
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
			animation = smallMarioAnimation;
			idleSprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_IDLE);
			jumpSprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_JUMP);
			turnSprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_TURN);
			break;
		}
		
		if(growTimer > 0){
			growTimer++;
			//sprite = idleSprite;
			velocity.x = 0;
			velocity.y = 0;
			jump = false;
			turn = false;
		}
		
		if(Game.state == State.PAUSED) return;
		if(!game.getKeyManager().getAnyArrowKey()){
			if(velocity.x > 0)
				velocity.x -= 0.375f;
			else if(velocity.x < 0)
				velocity.x += 0.375f;
			
			if(velocity.x < 0.375f && velocity.x > -0.375f)
				velocity.x = 0;
		}
		if(game.getKeyManager().getKey(KeyEvent.VK_LEFT)){
			if(turn)
				turn = false;
			if(onGround){
				flip = Sprite.FLIP_X;
				if(velocity.x > 0.5f){
					turn = true;
				}
				dir = -1;
			} else {
				turn = false;
			}
			
			if(velocity.x > -maxSpeed){
				if(onGround){
					velocity.x -= 0.0882f;
				} else {
					velocity.x -= 0.0441f;
				}
			}
		}
		if(game.getKeyManager().getKey(KeyEvent.VK_RIGHT)){
			if(turn)
				turn = false;
			if(onGround){
				flip = -1;
				if(velocity.x < -0.5f){
					turn = true;
				}
				dir = 1;
			} else {
				turn = false;
			}
			
			if(velocity.x < maxSpeed){
				if(onGround){
					velocity.x += 0.0882f;
				} else {
					velocity.x += 0.0441f;
				}
			}
		} 
		if(game.getKeyManager().getKey(KeyEvent.VK_C)){
			maxSpeed = 2.5f;
		} else {
			maxSpeed = 1.5f;
			if(velocity.x > maxSpeed)
				velocity.x -= 0.375f;
			else if(velocity.x < -maxSpeed)
				velocity.x += 0.375f;
		}
		
		animation.setSpeed(Math.abs(velocity.x));
		if(position.x < game.getWall()){
			position.x = game.getWall();
		}
		
		super.update();
	}
	
	@Override
	public void render(RenderHandler renderHandler) {
		if(velocity.x == 0)
			this.sprite = idleSprite;
		else
			this.sprite = animation.getCurrentFrame();//SpriteFactory.getInstance().getSprite(SpriteType.MARIO_IDLE);
		
		if(jump){
			sprite = jumpSprite;
			if(onGround)
				jump = false;
		}
		if(turn){
			sprite = turnSprite;
			if(Math.round(velocity.x) == 0){
				turn = false;
				animation.setCurrentFrame(0);
			}
		}
		
		if(growTimer > 0){
			if(growTimer > 10){
				sprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_MIDDLE);
				sprite.xOffset = 2 * -dir;
			}
			if(growTimer > 30){
				sprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_IDLE);
			}
			if(growTimer > 50){
				sprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_MIDDLE);
				sprite.xOffset = 2 * -dir;
			}
			if(growTimer > 60){
				sprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_IDLE);
			}
			if(growTimer > 70){
				sprite = SpriteFactory.getInstance().getSprite(SpriteType.MARIO_BIG_IDLE);
				sprite.xOffset = 2 * -dir;
			}
			if(growTimer > 73){
				Game.state = State.GAME;
				growTimer = 0;
				state = PlayerState.BIG;
			}	
		}
		super.render(renderHandler);
	}
	
	@Override
	public void onVerticalCollison(BoxCollider collider) {
		super.onVerticalCollison(collider);
		if(position.y < collider.position.y){
			onGround = true;
		} else {
			//if bounce on it, for question block etc
			Tile t = game.getLevel().getTile((int)collider.position.x>>4, (int)collider.position.y>>4);
			if(t != null)
				t.bump(this);
		}
	}

	public void grow() {
		Game.state = State.PAUSED;
		growTimer++;
		sprite = idleSprite;
	}
}
