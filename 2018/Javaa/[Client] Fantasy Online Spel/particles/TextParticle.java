package particles;

import main.Font;
import main.Game;
import main.Screen;
import main.Sprite;
import tools.Vector2;

public class TextParticle extends ParticleSystem implements IParticle {
	
	String text = "";
	
	public TextParticle(Vector2 position, Sprite sprite, float lifeInSeconds) {
		super(position, sprite, lifeInSeconds);
	}
	public TextParticle(Vector2 position, float lifeInSeconds, String text) {
		super();
		this.life = lifeInSeconds;
		startLife = lifeInSeconds;
		this.position = position.duplicate();
		this.text = text;
	}
	
	@Override
	public void emit() {
		super.emit();
	}
	
	@Override
	public void update(Game game) {
		super.update(game);
		position.y--;
	}
	@Override
	public void render(Screen screen){
		if(isAlive())
			Font.render(text, position.x, position.y, screen, true);
	}
}
