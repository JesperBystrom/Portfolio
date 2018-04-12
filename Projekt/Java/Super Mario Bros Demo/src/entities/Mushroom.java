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
		velocity.x = 1;
	}
	
	@Override
	public void update() {
		if(timer > 30){
			super.update();
			velocity.x = 1;
		}
		timer++;
	}
	
	@Override
	public void render(RenderHandler renderHandler) {
		if(timer > 30)
			super.render(renderHandler);
	}
	
	@Override
	public void onVerticalCollison(BoxCollider collider) {
		position.y -= 0.25f;
		super.onVerticalCollison(collider);
	}
	
	@Override
	public void onHorizontalCollision(BoxCollider collider) {
		// TODO Auto-generated method stub
		super.onHorizontalCollision(collider);
	}
	
	@Override
	public void onCollision(Entity e) {
		if(e instanceof Player){
			//make player grow
			Player p = (Player)e;
			p.grow();
			game.getLevel().removeEntity(this);
		}
	}
	
	@Override
	public boolean getSolid() {
		return false;
	}

}
