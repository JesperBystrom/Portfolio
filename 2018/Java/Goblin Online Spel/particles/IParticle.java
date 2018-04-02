package particles;

import main.Game;
import main.Screen;

public interface IParticle {

	public void emit();
	public void update(Game game);
	public void render(Screen screen);
}
