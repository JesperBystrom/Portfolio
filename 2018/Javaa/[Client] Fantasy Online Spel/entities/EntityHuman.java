package entities;

import main.Game;
import main.Screen;
import world.Map;

/*
 * @author Jesper Byström
 * Datum: 2018-02-18
 * Class: 
 * 
 */

public class EntityHuman extends EntityMob {

	public EntityHuman(Game game) {
		super(game);
		initAnimations(game.getScreen().getSpriteSheet().getSprites(16, 32, 48));
	}
	
	public EntityHuman(Game game, int x, int y){
		super(game);
		position.x = x;
		position.y = y;
		initAnimations(game.getScreen().getSpriteSheet().getSprites(16, 32, 48));
	}
	
	@Override
	public void update() {
		super.update();
	}

	@Override
	public void render(Screen screen) {
		super.render(screen);
	}
}
