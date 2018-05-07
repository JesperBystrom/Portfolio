package entities;

import main.Game;
import main.RenderHandler;
import main.Sprite;
import main.SpriteFactory;
import main.SpriteFactory.SpriteType;
import main.Vector2f;

public class Castle extends Entity {
	
	public Castle(Game game) {
		super(game);
		toggleGravity();
	}
	
	@Override
	public void render(RenderHandler renderHandler) {
		
		//Layer two
		
		//Row 1
		renderHandler.render(new Vector2f(position.x+16,position.y-38), SpriteFactory.getInstance().getSprite(SpriteType.TILE_BRICK_CASTLE), Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+32,position.y-38), SpriteFactory.getInstance().getSprite(SpriteType.TILE_BRICK_CASTLE), Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+48,position.y-38), SpriteFactory.getInstance().getSprite(SpriteType.TILE_BRICK_CASTLE), Sprite.FLIP_NONE);
		
		//Row 2
		renderHandler.render(new Vector2f(position.x+16,position.y-53), SpriteFactory.getInstance().getSprite(SpriteType.TILE_BRICK_CASTLE), Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+32,position.y-53), SpriteFactory.getInstance().getSprite(SpriteType.TILE_BRICK_CASTLE), Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+48,position.y-53), SpriteFactory.getInstance().getSprite(SpriteType.TILE_BRICK_CASTLE), Sprite.FLIP_NONE);
		
		
		//Top
		renderHandler.render(new Vector2f(position.x+16,position.y-68), SpriteFactory.getInstance().getSprite(SpriteType.CASTLE_TOP), Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+32,position.y-68), SpriteFactory.getInstance().getSprite(SpriteType.CASTLE_TOP), Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+48,position.y-68), SpriteFactory.getInstance().getSprite(SpriteType.CASTLE_TOP), Sprite.FLIP_NONE);
		
		//Windows
		renderHandler.renderColor(new Vector2f(position.x,position.y), 26, -52, 8, 16, 0x040404);
		renderHandler.renderColor(new Vector2f(position.x,position.y), 46, -52, 8, 16, 0x040404);
		
		//Layer one
		
		//Top
		renderHandler.render(new Vector2f(position.x,position.y-30), SpriteFactory.getInstance().getSprite(SpriteType.CASTLE_TOP), Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+16,position.y-30), SpriteFactory.getInstance().getSprite(SpriteType.CASTLE_TOP), Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+32,position.y-30), SpriteFactory.getInstance().getSprite(SpriteType.CASTLE_TOP), Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+48,position.y-30), SpriteFactory.getInstance().getSprite(SpriteType.CASTLE_TOP), Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+64,position.y-30), SpriteFactory.getInstance().getSprite(SpriteType.CASTLE_TOP), Sprite.FLIP_NONE);
		
		//Row 1
		renderHandler.render(new Vector2f(position.x,position.y), SpriteFactory.getInstance().getSprite(SpriteType.TILE_BRICK_CASTLE), Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+16,position.y), SpriteFactory.getInstance().getSprite(SpriteType.TILE_BRICK_CASTLE), Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+48,position.y), SpriteFactory.getInstance().getSprite(SpriteType.TILE_BRICK_CASTLE), Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+64,position.y), SpriteFactory.getInstance().getSprite(SpriteType.TILE_BRICK_CASTLE), Sprite.FLIP_NONE);
		
		//Row 2
		renderHandler.render(new Vector2f(position.x,position.y-15), SpriteFactory.getInstance().getSprite(SpriteType.TILE_BRICK_CASTLE), Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+16,position.y-15), SpriteFactory.getInstance().getSprite(SpriteType.TILE_BRICK_CASTLE), Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+48,position.y-15), SpriteFactory.getInstance().getSprite(SpriteType.TILE_BRICK_CASTLE), Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+64,position.y-15), SpriteFactory.getInstance().getSprite(SpriteType.TILE_BRICK_CASTLE), Sprite.FLIP_NONE);
		
		//Doors
		renderHandler.render(new Vector2f(position.x+32,position.y-15), SpriteFactory.getInstance().getSprite(SpriteType.TILE_BRICK_CASTLE), Sprite.FLIP_NONE);
		renderHandler.renderColor(new Vector2f(position.x,position.y), 32, -10, 16, 26, 0x040404);
		renderHandler.render(new Vector2f(position.x+32,position.y-18), SpriteFactory.getInstance().getSprite(SpriteType.CASTLE_DOOR_TOP), Sprite.FLIP_NONE);
		renderHandler.render(new Vector2f(position.x+42,position.y-18), SpriteFactory.getInstance().getSprite(SpriteType.CASTLE_DOOR_TOP), Sprite.FLIP_X);
	}

}
