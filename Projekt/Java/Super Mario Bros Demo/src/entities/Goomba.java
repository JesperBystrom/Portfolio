package entities;

import animation.Animation;
import etc.Tools;
import main.BoxCollider;
import main.Game;
import main.RenderHandler;
import main.Sprite;
import main.SpriteFactory;
import main.SpriteFactory.SpriteType;

public class Goomba extends Mob {

	private Animation animation = new Animation(
			SpriteFactory.getInstance().getSprite(SpriteType.ENEMY_GOOMBA_2),
			SpriteFactory.getInstance().getSprite(SpriteType.ENEMY_GOOMBA_1)
	);
	
	private byte deathReason;
	private static final byte REASON_SMOOSHED = 0x01;
	private static final byte REASON_PUSHED = 0x02;
	
	
	public Goomba(Game game) {
		super(game);
		this.sprite = SpriteFactory.getInstance().getSprite(SpriteType.ENEMY_GOOMBA_1);
		game.addAnimation(animation);
		this.collider.setSolid(true);
	}
	
	@Override
	public void update() {
		if(getEntityState() != EntityState.DYING || deathReason == REASON_PUSHED)
			super.update();
		if(velocity.x != 0) move((byte)0x00);
	}
	
	@Override
	public void render(RenderHandler renderHandler) {
		super.render(renderHandler);
	}
	
	@Override
	public boolean onCollision(Entity e) {
		if(!super.onCollision(e)) return false;
		if(e instanceof Player && !e.getInvincible()){
			if(e.position.y+8 < position.y){
				e.push(Direction.UP, 3);
				die(REASON_SMOOSHED);
				return true;
			}
			Game.pause(60);
			onHorizontalCollision(null);
			e.hurt();
		}
		return false;
	}
	
	public void die(byte reason){
		this.deathReason = reason;
		setEntityState(EntityState.DYING);
		timer.start();
	}
	
	@Override
	public void push(Direction direction, float force) {
		//super.push(direction, force);
		this.collider.setSolid(false);
		super.push((Direction)Tools.choose(Direction.LEFT, Direction.RIGHT), 1);
		super.push(Direction.UP, 4);
		die(REASON_PUSHED);
		System.out.println("push");
	}

	
	@Override
	protected void onMoving() {
		sprite = animation.getCurrentFrame();
	}

	@Override
	protected void onIdling() {
		sprite = SpriteFactory.getInstance().getSprite(SpriteType.ENEMY_GOOMBA_1);
	}
	
	@Override
	protected void onDying() {
		super.onDying();
		if(deathReason == REASON_SMOOSHED)
			sprite = SpriteFactory.getInstance().getSprite(SpriteType.ENEMY_GOOMBA_SMOOSHED);
		else
			flip = Sprite.FLIP_Y;
		
	}
}
