package entities;

import main.BoxCollider;
import main.Game;
import main.RenderHandler;
import main.SpriteFactory;
import main.SpriteFactory.SpriteType;
import main.Vector2f;

public class Mushroom extends Entity {

	private int timer = 0;
	public Mushroom(Game game, float target) {
		super(game);
		sprite = SpriteFactory.getInstance().getSprite(SpriteType.MUSHROOM);
		velocity.x = 0;
	}
	
	@Override
	public void update() {
		if(getVisible()){
			super.update();
		}
		if(timer > 100)
			if(velocity.x == 0) velocity.x = 1;
		timer++;
	}
	
	@Override
	public void render(RenderHandler renderHandler) {
		if(getVisible())
			super.render(renderHandler);
	}
	
	@Override
	public void onHorizontalCollision(BoxCollider collider) {
		//super.onHorizontalCollision(collider);
		if(timer > 100)
			velocity.x *= -1;
	}
	
	@Override
	public void onVerticalCollison(BoxCollider collider) {
		super.onVerticalCollison(collider);
		position.y -= 0.25f;
	}
	
	@Override
	public boolean onCollision(Entity e) {
		if(e instanceof Player){
			//make player grow
			Player p = (Player)e;
			if(p.isSmall()){
				p.grow();
			}
			game.getLevel().removeEntity(this);
		}
		return true;
	}
	
	private boolean getVisible(){
		return timer > 30;
	}
}
