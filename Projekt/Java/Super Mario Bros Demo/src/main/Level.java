package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import entities.Castle;
import entities.Entity;
import entities.Flag;
import entities.FlagPole;
import entities.Goomba;
import etc.Tools;
import main.SpriteFactory.SpriteType;
import particles.ParticleSystem;
import tiles.Tile;
import tiles.TileSolid;
import tiles.TileUseable;

public class Level {

	public Tile[] tiles;
	public Game game;
	private int tileWidth;
	private int tileHeight;
	private List<Entity> entities = new ArrayList<Entity>();
	private List<ParticleSystem> particles = new ArrayList<ParticleSystem>();
	private BufferedImage world;
	private int[] pixels;
	private FlagPole flagPole;
	private boolean won = false;
	
	public Level(Game game, int tileWidth, int tileHeight){
		this.game = game;
		tiles = new Tile[tileWidth*tileHeight];
		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		System.out.println(tileHeight);
		for(int x=0;x<tileWidth;x++){
			for(int y=0;y<tileHeight;y++){
				//setTile(x, y, Tile.AIR);
			}
			//setTile(x, tileHeight-1, Tile.BLOCK);
			//setTile(x, tileHeight-2, Tile.BLOCK);
		}
		//setTile(26, tileHeight-6, Tile.BLOCK_QUESTION);
		//setTile(4, tileHeight-6, Tile.BLOCK_QUESTION);
		
		try {
			world = ImageIO.read(new File("res/world.png"));
			pixels = Tools.imageToPixels(world);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for(int y=0;y<tileHeight;y++){
			for(int x=0;x<tileWidth;x++){
				switch(pixels[x+y*tileWidth]){
				case 0xFF9C4A00:
					setTile(x, y, new TileSolid(SpriteFactory.getInstance().getSprite(SpriteType.TILE_GROUND_DEFAULT)));
					break;
				case 0xFFFFA542:
					setTile(x, y, new TileUseable(SpriteFactory.getInstance().getSprite(SpriteType.TILE_QUESTIONMARK_DEFAULT), Tile.Reward.COIN));
					break;
				case 0xFFFFD800:
					setTile(x, y, new TileUseable(SpriteFactory.getInstance().getSprite(SpriteType.TILE_QUESTIONMARK_DEFAULT), Tile.Reward.MUSHROOM));
					break;
				case 0xFF7F7F7F:
					setTile(x, y, new TileSolid(SpriteFactory.getInstance().getSprite(SpriteType.TILE_BRICK_DEFAULT)));
					break;
				case 0xFFE75A10:
					setTile(x, y, new TileSolid(SpriteFactory.getInstance().getSprite(SpriteType.TILE_SQUARE_DEFAULT)));
					break;
				case 0xFF8CD600:
					setTile(x, y, new TileSolid(SpriteFactory.getInstance().getSprite(SpriteType.TILE_PORTAL_PART)));
					break;
				case 0xFF5A8700:
					setTile(x, y, new TileSolid(SpriteFactory.getInstance().getSprite(SpriteType.TILE_PORTAL_TOP)));
					break;
				case 0xFF732D08:
					Goomba g = new Goomba(game);
					g.setPosition(new Vector2f(x<<4,y<<4));
					addEntity(g);
					break;
				case 0xFFA4BC00:
					if(flagPole == null){
						flagPole = new FlagPole(game);
						flagPole.setPosition(new Vector2f(x<<4, y<<4));
						Flag flag = new Flag(game);
						flag.setPosition(new Vector2f(flagPole.position.x-8, flagPole.position.y+8));
						addEntity(flagPole);
						addEntity(flag);
						flagPole.setFlag(flag);
					}
					break;	
				case 0xFFFF0000:
					game.player.position.x = x<<4;
					game.player.position.y = y<<4;
					break;
				case 0xFF7F0000:
					Castle castle = new Castle(game);
					castle.setPosition(new Vector2f(x<<4, y<<4));
					addEntity(castle);
					break;
				}
			}
		}
	}
	
	public void update() {
		for(int x=0;x<tileWidth;x++){
			for(int y=0;y<tileHeight;y++){
				Tile t = getTile(x,y);
				if(t != null)
					t.update();
			}
		}
		
		for(int i=0;i<entities.size();i++){
			entities.get(i).update();
		}
		
		for(int i=0;i<particles.size();i++){
			particles.get(i).update(this);
		}
	}
	
	public void render(RenderHandler renderHandler){
		for(int i=0;i<entities.size();i++){
			entities.get(i).render(renderHandler);
		}
		
		for(int x=0;x<tileWidth;x++){
			for(int y=0;y<tileHeight;y++){
				Tile t = getTile(x,y);
				if(t != null)
					t.render(renderHandler, new Vector2f(x*16, y*16));
			}
		}
		
		for(int i=0;i<particles.size();i++){
			particles.get(i).render(renderHandler);
		}
	}
	
	public Vector2f getNearestTilePosition(int xt, int yt){
		if(getTile(xt,yt) instanceof TileUseable) return new Vector2f(xt<<4,yt<<4);
		if(getTile(xt,yt+1) instanceof TileUseable) return new Vector2f(xt<<4,(yt+1)<<4);
		if(getTile(xt,yt-1) instanceof TileUseable) return new Vector2f(xt<<4,(yt-1)<<4);
		if(getTile(xt+1,yt) instanceof TileUseable) return new Vector2f((xt+1)<<4,yt<<4);
		if(getTile(xt-1,yt) instanceof TileUseable) return new Vector2f((xt-1)<<4,yt<<4);
		if(getTile(xt+1,yt+1) instanceof TileUseable) return new Vector2f((xt+1)<<4,(yt+1)<<4);
		if(getTile(xt-1,yt-1) instanceof TileUseable) return new Vector2f((xt-1)<<4,(yt-1)<<4);
		if(getTile(xt+1,yt-1) instanceof TileUseable) return new Vector2f((xt+1)<<4,(yt-1)<<4);
		if(getTile(xt-1,yt+1) instanceof TileUseable) return new Vector2f((xt-1)<<4,(yt+1)<<4);
		return null;
	}
	
	public Vector2f getTileCoordinates(int xt, int yt){
		Vector2f vec = new Vector2f(xt<<4,yt<<4);
		if(getTile(xt,yt) == null){
			vec = getNearestTilePosition(xt,yt);
		}
		return vec;
	}
	
	public List<BoxCollider> getColliders(BoxCollider collider, int radius){
		Vector2 tPos = Tile.toTileCoordinates(collider.position);
		ArrayList<BoxCollider> colliders = new ArrayList<BoxCollider>();
		
		for(int xt=tPos.x-radius;xt<tPos.x+radius;xt++){
			for(int yt=tPos.y-radius;yt<tPos.y+radius;yt++){
				if(!getInBounds(xt, 0, tileWidth, yt, 0, tileHeight)) continue;
				Tile t = getTile(xt,yt);
				if(t != null){
					if(t.getSolid())
						colliders.add(new BoxCollider(new Vector2f(xt<<4, yt<<4), t.getWidth(), t.getHeight()));
				}
			}
		}
		return colliders;
	}
	
	public List<Entity> getEntityCollisions(BoxCollider collider){
		ArrayList<Entity> colliders = new ArrayList<Entity>();
		for (int i = 0; i < entities.size(); i++) {
			if(entities.get(i).collider.intersect(collider, 0, 0)){
				colliders.add(entities.get(i));
			}
		}
		return colliders;
	}
	
	public Vector2f getRealTileCoordinates(int xt, int yt){
		Tile t = getTile(xt,yt);
		if(t != null){
			return new Vector2f(xt*8, yt*8);
		}
		return new Vector2f(0,0);
	}
	
	public static boolean getInBounds(float x, float x0, float x1, float y, float y0, float y1){
		return(x >= x0 && x < x1 && y >= y0 && y < y1);
	}
	
	public void setTile(int xt, int yt, Tile tile){
		tiles[xt+yt*tileWidth] = tile;
	}
	
	public Tile getTile(int xt, int yt){
		return tiles[xt+yt*tileWidth];
	}
	
	public void addParticle(ParticleSystem ps) {
		particles.add(ps);
	}

	public void removeParticle(ParticleSystem ps) {
		particles.remove(ps);
	}

	public void addEntity(Entity e){
		entities.add(e);
	}
	
	public void removeEntity(Entity e) {
		entities.remove(e);
	}
	
	public void win(){
		won = true;
	}

	public boolean getWin() {
		return won;
	}
	
	public FlagPole getFlagPole(){
		return flagPole;
	}

	public float getHeight() {
		return tileHeight << 4;
	}
}
