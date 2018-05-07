package entities;

import main.Game;
import main.RenderHandler;
import main.Sprite;
import main.SpriteFactory;
import main.SpriteFactory.SpriteType;
import main.Vector2f;

public class FlagPole extends Entity {
	
	
	private int xo = 4;
	private Flag flag;
	private Sprite[] sprites = {
		SpriteFactory.getInstance().getSprite(SpriteType.TILE_FLAG_TOP),
		SpriteFactory.getInstance().getSprite(SpriteType.TILE_FLAG_PART),
		SpriteFactory.getInstance().getSprite(SpriteType.TILE_FLAG_PART),
		SpriteFactory.getInstance().getSprite(SpriteType.TILE_FLAG_PART),
		SpriteFactory.getInstance().getSprite(SpriteType.TILE_FLAG_PART),
		SpriteFactory.getInstance().getSprite(SpriteType.TILE_FLAG_PART),
		SpriteFactory.getInstance().getSprite(SpriteType.TILE_FLAG_PART),
		SpriteFactory.getInstance().getSprite(SpriteType.TILE_FLAG_PART),
		SpriteFactory.getInstance().getSprite(SpriteType.TILE_FLAG_PART),
		SpriteFactory.getInstance().getSprite(SpriteType.TILE_FLAG_PART),
		SpriteFactory.getInstance().getSprite(SpriteType.TILE_FLAG_PART),
		SpriteFactory.getInstance().getSprite(SpriteType.TILE_FLAG_PART),
		SpriteFactory.getInstance().getSprite(SpriteType.TILE_FLAG_PART),
		SpriteFactory.getInstance().getSprite(SpriteType.TILE_FLAG_PART),
		SpriteFactory.getInstance().getSprite(SpriteType.TILE_FLAG_PART),
		SpriteFactory.getInstance().getSprite(SpriteType.TILE_FLAG_PART),
		SpriteFactory.getInstance().getSprite(SpriteType.TILE_FLAG_PART),
		SpriteFactory.getInstance().getSprite(SpriteType.TILE_FLAG_PART),
		SpriteFactory.getInstance().getSprite(SpriteType.TILE_FLAG_PART),
		SpriteFactory.getInstance().getSprite(SpriteType.TILE_SQUARE_DEFAULT)
	};
	
	public FlagPole(Game game) {
		super(game);
		toggleGravity();
		System.out.println(game.getLevel());
		collider.height = 128;
		collider.width = 2;
		collider.xo = 7;
	}
	
	@Override
	public void update() {
		super.update();
	}
	
	@Override
	public void render(RenderHandler renderHandler) {
		renderHandler.render(new Vector2f(position.x+xo, position.y), sprites[0], Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+xo, (int)position.y + (1<<3)), sprites[1], Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+xo, (int)position.y + (2<<3)), sprites[2], Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+xo, (int)position.y + (3<<3)), sprites[3], Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+xo, (int)position.y + (4<<3)), sprites[4], Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+xo, (int)position.y + (5<<3)), sprites[5], Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+xo, (int)position.y + (6<<3)), sprites[6], Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+xo, (int)position.y + (7<<3)), sprites[7], Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+xo, (int)position.y + (8<<3)), sprites[8], Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+xo, (int)position.y + (9<<3)), sprites[9], Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+xo, (int)position.y + (10<<3)), sprites[10], Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+xo, (int)position.y + (11<<3)), sprites[11], Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+xo, (int)position.y + (12<<3)), sprites[12], Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+xo, (int)position.y + (13<<3)), sprites[13], Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+xo, (int)position.y + (14<<3)), sprites[14], Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+xo, (int)position.y + (15<<3)), sprites[15], Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+xo, (int)position.y + (16<<3)), sprites[16], Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+xo, (int)position.y + (17<<3)), sprites[17], Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+xo, position.y + (18<<3)), sprites[18], Sprite.FLIP_NONE);
	}
	
	@Override
	public boolean onCollision(Entity e) {
		if(e instanceof Player){
			game.getLevel().win();
		}
		return super.onCollision(e);
	}
	
	public Sprite[] getSprites() {
		return sprites;
	}
	
	public Flag getFlag(){
		return flag;
	}
	
	public void setFlag(Flag flag){
		this.flag = flag;
	}
}
