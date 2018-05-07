package entities;

import main.BoxCollider;
import main.Game;
import main.Vector2f;

public class Mob extends Entity {
	
	public Mob(Game game) {
		super(game);
		velocity.x = -0.5f;
	}
	
	@Override
	public void update() {
		if(position.x - game.player.position.x > 160) return;
		super.update();
	}
	
	@Override
	public void onHorizontalCollision(BoxCollider collider) {
		if(getEntityState() != EntityState.DYING)
			velocity.x *= -1;
	}
}
