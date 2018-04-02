package tiles;

import main.Screen;
import main.Sprite;
import tools.Vector2;

/*
 * @author Jesper Byström
 * Datum: 2018-02-10
 * Class: 
 * 
 */

public abstract class Tile {
	
	public static final byte LAYER_BACKGROUND = 0x0001;
	public static final byte LAYER_FOREGROUND = 0x0002;
	
	public static final Tile AIR = new TileBasic(new Sprite(255), LAYER_BACKGROUND);
	public static final Tile BASIC = new TileBasic(new Sprite(255), LAYER_BACKGROUND);
	public static final Tile WALL = new TileSolid(new Sprite(6), LAYER_FOREGROUND);
	public static final Tile ROOF = new TileSolid(new Sprite(5), LAYER_FOREGROUND);
	public static final Tile TREE = new TileSolid(new Sprite(2), LAYER_FOREGROUND);
	public static final Tile GRASS = new TileBasic(new Sprite(6), LAYER_BACKGROUND);
	
	public Sprite sprite;
	public byte layer;
	
	public Tile(Sprite sprite, byte layer){
		this.sprite = sprite;
		this.layer = layer;
	}
	public static Vector2 toWorldPosition(int x, int y){
		x *= Screen.SPRITE_SIZE;
		y *= Screen.SPRITE_SIZE;
		return new Vector2(x,y);
	}
	public static Vector2 toTilePosition(int x, int y){
		x /= Screen.SPRITE_SIZE;
		y /= Screen.SPRITE_SIZE;
		return new Vector2(x,y);
	}
	public abstract boolean isSolid();
}
