package entities;

import main.BoxCollider;
import main.Game;
import main.SpriteFactory;
import main.SpriteFactory.SpriteType;

public class Goomba extends Entity {

	public Goomba(Game game) {
		super(game);
		this.sprite = SpriteFactory.getInstance().getSprite(SpriteType.ENEMY_GOOMBA);
		velocity.x = -0.5f;
	}
	
	@Override
	public void update() {
		if(position.x - game.player.position.x > 140) return;
		//if(position.distance(game.player.position) > 140) return;
		super.update();
	}
	
	@Override
	public void onHorizontalCollision(BoxCollider collider) {
		velocity.x *= -1;
	}
	
	@Override
	public void onCollision(Entity e) {
		super.onCollision(e);
		if(e instanceof Player && !e.getInvincible()){
			Game.pause(60);
			onHorizontalCollision(null);
			e.invincible();
		}
	}
}
