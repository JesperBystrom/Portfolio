package particles;

import animations.Animation;
import animations.AnimationClip;
import entities.Entity;
import main.Game;
import main.Screen;
import main.Sprite;
import tools.Vector2;

public class SwordParticle extends ParticleSystem implements IParticle {

	private int direction;
	private int distance;
	
	public SwordParticle(Game game, Vector2 position, float lifeInSeconds, int clipLength, boolean killOnAnimationFinish, int direction, int distance, Sprite[] frames){
		super(game,position,lifeInSeconds,clipLength,killOnAnimationFinish,frames);
		this.direction = direction;
		this.distance = distance;
	}
	public SwordParticle(Vector2 position, Sprite sprite, float lifeInSeconds, int direction, int distance){
		super(position,sprite,lifeInSeconds);
		this.direction = direction;
		this.distance = distance;
	}
	
	@Override
	public void emit() {
		super.emit();
	}

	@Override
	public void update(Game game) {
		super.update(game);
	}

	@Override
	public void render(Screen screen) {
		switch(direction){
		case Entity.LEFT:
			screen.render(position.x - distance, position.y, sprite.tile, null, Entity.LEFT, 0);
			break;
		case Entity.RIGHT:
			screen.render(position.x + distance, position.y, sprite.tile, null, Entity.RIGHT, 0);
			break;
		case Entity.UP:
			screen.render(position.x, position.y + (Screen.SPRITE_SIZE-distance), sprite.tile, null, -1, 270);
			break;
		case Entity.DOWN:
			screen.render(position.x + Screen.SPRITE_SIZE, position.y + distance, sprite.tile, null, -1, 90);
			break;
		}
	}
}
