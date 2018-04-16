package tiles;

import entities.Entity;
import main.RenderHandler;
import main.Sprite;
import main.SpriteFactory;
import main.SpriteFactory.SpriteType;
import main.Vector2;
import main.Vector2f;

public class Tile {
	public Sprite sprite;
	private float yVel = 0;
	private Tile type;
	
	public static enum Reward {
		COIN(0x01), MUSHROOM(0x02), STAR(0x03);
		private byte id;
		Reward(int id){
			this.id = (byte)id;
		}
		public byte id() {
			return id;
		}
	}
	
	public Tile(Sprite sprite){
		this.sprite = sprite;
	}
	
	public Tile(Tile tile) {
		Sprite s = new Sprite(tile.sprite.pixels, tile.sprite.color[0], tile.sprite.color[1], tile.sprite.color[2],tile. sprite.width, tile.sprite.height);
		if(tile instanceof TileSolid)
			type = new TileSolid(s);
		if(tile instanceof TileAnimation)
			type = new TileAnimation(s);
		
	}
	
	public Tile getType(){
		return type;
	}

	public Sprite getSprite(){
		return sprite;
	}

	public void update(){
		//Make the block bounce when jumped from below
		if(yVel != 0 || sprite.yOffset != 0){
			sprite.yOffset += yVel;
			yVel += 0.2f;
		}
		if(sprite.yOffset == 0){
			sprite.yOffset = 0;
			yVel = 0;
		}
	}
	
	public void render(RenderHandler renderHandler, Vector2f position) {
		renderHandler.render(position, sprite, Sprite.FLIP_NONE);
	}
	
	public static Vector2 toTileCoordinates(Vector2f pos){
		return new Vector2((int)pos.x >> 4, (int)pos.y >> 4);
	}
	
	public static Vector2f toRealCoordinates(int xt, int yt){
		return new Vector2f(xt << 4, yt << 4);
	}
	
	public boolean getSolid(){
		return false;
	}
	
	public int getWidth(){
		return sprite.width;
	}
	
	public int getHeight(){
		return sprite.height;
	}

	public void setSprite(Sprite sprite) {
		this.sprite = new Sprite(sprite.pixels, sprite.color[0], sprite.color[1], sprite.color[2], sprite.width, sprite.height);
	}

	public void bump(Entity source) {
		yVel = -2;
	}
	
	public boolean canBump(){
		return sprite.yOffset == 0;
	}
}
