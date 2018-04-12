package main;

public class SpriteFactory {

	private static SpriteFactory instance;
	
	public static enum SpriteType {
		BLOCK, 
		MARIO_IDLE, MARIO_RUN_1, MARIO_RUN_2, MARIO_RUN_3, MARIO_TURN, MARIO_JUMP, MARIO_MIDDLE,
		TILE_GROUND_DEFAULT,
		TILE_QUESTIONMARK_DEFAULT, TILE_QUESTIONMARK_USED,
		TILE_BRICK_DEFAULT,
		COIN_1, COIN_2, COIN_3, COIN_4,
		MUSHROOM,
		MARIO_BIG_IDLE, MARIO_BIG_RUN_1, MARIO_BIG_RUN_2, MARIO_BIG_RUN_3, MARIO_BIG_TURN, MARIO_BIG_JUMP
	}
	
	public Sprite getSprite(SpriteType type){
		SpriteSheet sheet = RenderHandler.getSpriteSheet();
		switch(type){
		case BLOCK:
			return sheet.getSprite(0, 0, 0xFF3118, 0xC66300, 0xFF945A, 8, 8);
		case MARIO_IDLE:
			return sheet.getSprite(16, 0, CColor.MARIO_RED_CAP, CColor.MARIO_BROWN_SHIRT, CColor.MARIO_FACE, 16, 16);
		case MARIO_RUN_1:
			return sheet.getSprite(32, 0, CColor.MARIO_RED_CAP, CColor.MARIO_BROWN_SHIRT, CColor.MARIO_FACE, 16, 16);
		case MARIO_RUN_2:
			return sheet.getSprite(48, 0, CColor.MARIO_RED_CAP, CColor.MARIO_BROWN_SHIRT, CColor.MARIO_FACE, 16, 16);
		case MARIO_RUN_3:
			return sheet.getSprite(64, 0, CColor.MARIO_RED_CAP, CColor.MARIO_BROWN_SHIRT, CColor.MARIO_FACE, 16, 16);
		case MARIO_TURN:
			return sheet.getSprite(80, 0, CColor.MARIO_RED_CAP, CColor.MARIO_BROWN_SHIRT, CColor.MARIO_FACE, 16, 16);
		case MARIO_JUMP:
			return sheet.getSprite(96, 0, CColor.MARIO_RED_CAP, CColor.MARIO_BROWN_SHIRT, CColor.MARIO_FACE, 16, 16);
		case MARIO_MIDDLE:
			return sheet.getSprite(0, 40, CColor.MARIO_RED_CAP, CColor.MARIO_BROWN_SHIRT, CColor.MARIO_FACE, 16, 24, 2, -8);
		case MARIO_BIG_IDLE:
			return sheet.getSprite(16, 32, CColor.MARIO_RED_CAP, CColor.MARIO_BROWN_SHIRT, CColor.MARIO_FACE, 16, 32, 2, -16);
		case MARIO_BIG_RUN_1:
			return sheet.getSprite(32, 32, CColor.MARIO_RED_CAP, CColor.MARIO_BROWN_SHIRT, CColor.MARIO_FACE, 16, 32, 0, -16);
		case MARIO_BIG_RUN_2:
			return sheet.getSprite(48, 32, CColor.MARIO_RED_CAP, CColor.MARIO_BROWN_SHIRT, CColor.MARIO_FACE, 16, 32, 0, -16);
		case MARIO_BIG_RUN_3:
			return sheet.getSprite(64, 32, CColor.MARIO_RED_CAP, CColor.MARIO_BROWN_SHIRT, CColor.MARIO_FACE, 16, 32, 0, -16);
		case MARIO_BIG_TURN:
			return sheet.getSprite(80, 32, CColor.MARIO_RED_CAP, CColor.MARIO_BROWN_SHIRT, CColor.MARIO_FACE, 16, 32, 0, -16);
		case MARIO_BIG_JUMP:
			return sheet.getSprite(96, 32, CColor.MARIO_RED_CAP, CColor.MARIO_BROWN_SHIRT, CColor.MARIO_FACE, 16, 32, 0, -16);
		case TILE_GROUND_DEFAULT:
			return sheet.getSprite(16, 16, CColor.BROWN, CColor.BLACK, CColor.SOFT_PINK, 16, 16);
		case TILE_QUESTIONMARK_DEFAULT:
			return sheet.getSprite(32, 16, CColor.YELLOW, CColor.BLACK, CColor.BROWN, 16, 16);
		case TILE_BRICK_DEFAULT:
			return sheet.getSprite(48, 16, CColor.BROWN, CColor.BLACK, CColor.SOFT_PINK, 16, 16);
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
		case TILE_QUESTIONMARK_USED:
			return sheet.getSprite(80, 16, CColor.BLACK, CColor.BROWN, CColor.NONE, 16, 16);
		}
		return null;
	}
	
	public static SpriteFactory getInstance(){
		if(instance == null)
			instance = new SpriteFactory();
		return instance;
	}
}
