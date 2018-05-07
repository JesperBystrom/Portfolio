package main;

public class SpriteFactory {

	private static SpriteFactory instance;
	
	public static enum SpriteType {
		BLOCK, 
		MARIO_IDLE, MARIO_RUN_1, MARIO_RUN_2, MARIO_RUN_3, MARIO_TURN, MARIO_JUMP, MARIO_MIDDLE, MARIO_DIE, MARIO_WIN,
		TILE_GROUND_DEFAULT,
		TILE_QUESTIONMARK_DEFAULT, TILE_QUESTIONMARK_USED,
		TILE_BRICK_DEFAULT, TILE_BRICK_CASTLE,
		TILE_SQUARE_DEFAULT,
		TILE_PORTAL_TOP, TILE_PORTAL_PART,
		COIN_1, COIN_2, COIN_3, COIN_4,
		MUSHROOM,
		MARIO_BIG_IDLE, MARIO_BIG_RUN_1, MARIO_BIG_RUN_2, MARIO_BIG_RUN_3, MARIO_BIG_TURN, MARIO_BIG_JUMP,
		ENEMY_GOOMBA_1, ENEMY_GOOMBA_2, ENEMY_GOOMBA_SMOOSHED,
		TILE_FLAG_PART, TILE_FLAG_TOP, FLAG,
		CASTLE_TOP, CASTLE_DOOR_TOP
	}
	
	public Sprite getSprite(SpriteType type){
		SpriteSheet sheet = RenderHandler.getSpriteSheet();
		switch(type){
		case BLOCK:
			return sheet.getSprite(0, 0, 0xFF3118, 0xC66300, 0xFF945A, 8, 8);
		case MARIO_IDLE:
			return sheet.getSprite(16, 0, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 16);
		case MARIO_RUN_1:
			return sheet.getSprite(32, 0, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 16);
		case MARIO_RUN_2:
			return sheet.getSprite(48, 0, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 16);
		case MARIO_RUN_3:
			return sheet.getSprite(64, 0, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 16);
		case MARIO_TURN:
			return sheet.getSprite(80, 0, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 16);
		case MARIO_JUMP:
			return sheet.getSprite(96, 0, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 16);
		case MARIO_DIE:
			return sheet.getSprite(176, 0, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 16);
		case MARIO_WIN:
			return sheet.getSprite(192, 0, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 16, 8, 0);
		case MARIO_MIDDLE:
			return sheet.getSprite(0, 40, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 24, 0, -8);
		case MARIO_BIG_IDLE:
			return sheet.getSprite(16, 32, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 32, 0, -16);
		case MARIO_BIG_RUN_1:
			return sheet.getSprite(32, 32, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 32, 0, -16);
		case MARIO_BIG_RUN_2:
			return sheet.getSprite(48, 32, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 32, 0, -16);
		case MARIO_BIG_RUN_3:
			return sheet.getSprite(64, 32, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 32, 0, -16);
		case MARIO_BIG_TURN:
			return sheet.getSprite(80, 32, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 32, 0, -16);
		case MARIO_BIG_JUMP:
			return sheet.getSprite(96, 32, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 32, 0, -16);
		case ENEMY_GOOMBA_1:
			return sheet.getSprite(128, 0, CColor.GRAY, CColor.LIGHT_BROWN, CColor.SOFT_PINK, 16, 16, 0, 0);
		case ENEMY_GOOMBA_2:
			return sheet.getSprite(144, 0, CColor.GRAY, CColor.LIGHT_BROWN, CColor.SOFT_PINK, 16, 16, 0, 0);
		case ENEMY_GOOMBA_SMOOSHED:
			return sheet.getSprite(160, 0, CColor.GRAY, CColor.LIGHT_BROWN, CColor.SOFT_PINK, 16, 16, 0, 0);
		case TILE_GROUND_DEFAULT:
			return sheet.getSprite(16, 16, CColor.BROWN, CColor.BLACK, CColor.SOFT_PINK, 16, 16);
		case TILE_BRICK_DEFAULT:
			return sheet.getSprite(48, 16, CColor.BROWN, CColor.BLACK, CColor.SOFT_PINK, 16, 16);
		case TILE_BRICK_CASTLE:
			return sheet.getSprite(48, 16, CColor.BROWN, CColor.BLACK, CColor.BLACK, 16, 16);
		case TILE_SQUARE_DEFAULT:
			return sheet.getSprite(112, 0, CColor.BLACK, CColor.BROWN, CColor.SOFT_PINK, 16, 16);
		case TILE_QUESTIONMARK_DEFAULT:
			return sheet.getSprite(32, 16, CColor.YELLOW, CColor.BLACK, CColor.BROWN, 16, 16);
		case TILE_QUESTIONMARK_USED:
			return sheet.getSprite(80, 16, CColor.BLACK, CColor.BROWN, CColor.NONE, 16, 16);
		case TILE_PORTAL_TOP:
			return sheet.getSprite(0, 64, CColor.BLACK, CColor.GREEN, CColor.LIME, 32, 16);
		case TILE_PORTAL_PART:
			return sheet.getSprite(32, 64, CColor.BLACK, CColor.GREEN, CColor.LIME, 32, 16);
		case TILE_FLAG_TOP:
			return sheet.getSprite(112, 32, CColor.BLACK, CColor.GREEN, CColor.LIME, 8, 8);
		case TILE_FLAG_PART:
			return sheet.getSprite(120, 32, CColor.BLACK, CColor.GREEN, CColor.LIME, 8, 8);
		case COIN_1:
			return sheet.getSprite(96, 16, CColor.DARK_RED, CColor.WHITE, CColor.COIN_YELLOW, 16, 16);
		case COIN_2:
			return sheet.getSprite(112, 16, CColor.DARK_RED, CColor.WHITE, CColor.COIN_YELLOW, 16, 16);
		case COIN_3:
			return sheet.getSprite(128, 16, CColor.DARK_RED, CColor.WHITE, CColor.COIN_YELLOW, 16, 16);
		case COIN_4:
			return sheet.getSprite(144, 16, CColor.DARK_RED, CColor.WHITE, CColor.COIN_YELLOW, 16, 16);
		case MUSHROOM:
			return sheet.getSprite(160, 16, CColor.YELLOW, CColor.DARK_RED, CColor.WHITE, 16, 16);
		case FLAG:
			return sheet.getSprite(112, 40, CColor.NONE, CColor.GREEN, CColor.WHITE, 16, 16);
		case CASTLE_TOP:
			return sheet.getSprite(224, 0, CColor.BLACK, CColor.BROWN, CColor.BLACK, 16, 16);
		case CASTLE_DOOR_TOP:
			return sheet.getSprite(216, 0, CColor.BLACK, CColor.NONE, CColor.NONE, 8, 8);
			
		
		/*case BLOCK:
			return sheet.getSprite(0, 0, 0xFF3118, 0xC66300, 0xFF945A, 8, 8);
		case MARIO_IDLE:
			return sheet.getSprite(1, 0, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 16);
		case MARIO_RUN_1:
			return sheet.getSprite(2, 0, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 16);
		case MARIO_RUN_2:
			return sheet.getSprite(3, 0, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 16);
		case MARIO_RUN_3:
			return sheet.getSprite(4, 0, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 16);
		case MARIO_TURN:
			return sheet.getSprite(5, 0, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 16);
		case MARIO_JUMP:
			return sheet.getSprite(6, 0, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 16);
		case MARIO_MIDDLE:
			return sheet.getSprite(0, 40, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 24, 0, -8);
		case MARIO_BIG_IDLE:
			return sheet.getSprite(1, 32, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 32, 0, -16);
		case MARIO_BIG_RUN_1:
			return sheet.getSprite(2, 32, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 32, 0, -16);
		case MARIO_BIG_RUN_2:
			return sheet.getSprite(3, 32, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 32, 0, -16);
		case MARIO_BIG_RUN_3:
			return sheet.getSprite(4, 32, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 32, 0, -16);
		case MARIO_BIG_TURN:
			return sheet.getSprite(5, 32, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 32, 0, -16);
		case MARIO_BIG_JUMP:
			return sheet.getSprite(6, 32, CColor.MARIO_RED_CAP, CColor.LIGHT_BROWN, CColor.MARIO_FACE, 16, 32, 0, -16);
		case ENEMY_GOOMBA_1:
			return sheet.getSprite(8, 0, CColor.GRAY, CColor.LIGHT_BROWN, CColor.SOFT_PINK, 16, 16, 0, 0);
		case ENEMY_GOOMBA_2:
			return sheet.getSprite(9, 0, CColor.GRAY, CColor.LIGHT_BROWN, CColor.SOFT_PINK, 16, 16, 0, 0);
		case ENEMY_GOOMBA_DEAD:
			return sheet.getSprite(10, 0, CColor.GRAY, CColor.LIGHT_BROWN, CColor.SOFT_PINK, 16, 16, 0, 0);
		case TILE_GROUND_DEFAULT:
			return sheet.getSprite(1, 16, CColor.BROWN, CColor.BLACK, CColor.SOFT_PINK, 16, 16);
		case TILE_BRICK_DEFAULT:
			return sheet.getSprite(3, 16, CColor.BROWN, CColor.BLACK, CColor.SOFT_PINK, 16, 16);
		case TILE_SQUARE_DEFAULT:
			return sheet.getSprite(7, 0, CColor.BLACK, CColor.BROWN, CColor.SOFT_PINK, 16, 16);
		case TILE_QUESTIONMARK_DEFAULT:
			return sheet.getSprite(2, 16, CColor.YELLOW, CColor.BLACK, CColor.BROWN, 16, 16);
		case TILE_QUESTIONMARK_USED:
			return sheet.getSprite(5, 16, CColor.BLACK, CColor.BROWN, CColor.NONE, 16, 16);
		case TILE_PORTAL_TOP:
			return sheet.getSprite(0, 64, CColor.BLACK, CColor.GREEN, CColor.LIME, 32, 16);
		case TILE_PORTAL_PART:
			return sheet.getSprite(2, 64, CColor.BLACK, CColor.GREEN, CColor.LIME, 32, 16);
		case COIN_1:
			return sheet.getSprite(6, 16, CColor.DARK_RED, CColor.WHITE, CColor.COIN_YELLOW, 16, 16);
		case COIN_2:
			return sheet.getSprite(7, 16, CColor.DARK_RED, CColor.WHITE, CColor.COIN_YELLOW, 16, 16);
		case COIN_3:
			return sheet.getSprite(8, 16, CColor.DARK_RED, CColor.WHITE, CColor.COIN_YELLOW, 16, 16);
		case COIN_4:
			return sheet.getSprite(9, 16, CColor.DARK_RED, CColor.WHITE, CColor.COIN_YELLOW, 16, 16);
		case MUSHROOM:
			return sheet.getSprite(10, 16, CColor.YELLOW, CColor.DARK_RED, CColor.WHITE, 16, 16);*/
		}
		return null;
	}
	
	public static SpriteFactory getInstance(){
		if(instance == null)
			instance = new SpriteFactory();
		return instance;
	}
}
