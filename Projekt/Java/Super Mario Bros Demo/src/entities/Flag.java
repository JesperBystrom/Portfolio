package entities;

import main.Game;
import main.SpriteFactory;
import main.SpriteFactory.SpriteType;

public class Flag extends Entity {

	public Flag(Game game) {
		super(game);
		sprite = SpriteFactory.getInstance().getSprite(SpriteType.FLAG);
		toggleGravity();
	}
	
	@Override
	public void update() {
		super.update();
		if(game.getLevel().getWin() && position.y <= game.player.position.y)
			position.y++;
	}

}
