package tiles;

import animation.Animation;
import main.CColor;
import main.RenderHandler;
import main.Sprite;
import main.Vector2f;

public class TileAnimation extends TileSolid {
	
	private int frame = 0;
	private float timer = 0;
	private int[] colors = new int[]{
		CColor.YELLOW,
		CColor.BROWN,
		CColor.DARK_BROWN
	};
	public TileAnimation(Sprite sprite) {
		super(sprite);
	}
	
	@Override
	public void render(RenderHandler renderHandler, Vector2f position) {
		super.render(renderHandler, position);
		if(!canBump()) return;
		timer += 0.5f;
		if(timer >= 0){
			sprite.color[0] = colors[0];
		}
		if(timer >= 150){
			sprite.color[0] = colors[1];
		}
		if(timer >= 200){
			sprite.color[0] = colors[2];
		}
		if(timer >= 250)
			timer = 0;
	}

}
