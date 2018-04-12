package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import entities.Entity;
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
					setTile(x, y, new TileUseable(SpriteFactory.getInstance().getSprite(SpriteType.TILE_QUESTIONMARK_DEFAULT), Tile.Reward.MUSHROOM));
					break;
				case 0xFF7F7F7F:
					setTile(x, y, new TileSolid(SpriteFactory.getInstance().getSprite(SpriteType.TILE_BRICK_DEFAULT)));
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
		for(int xx=xt-2;xx<xt+2;xx++){
			for(int yy=yt-2;yy<yt+2;yy++){
				if(!getInBounds(xt, 0, tileWidth, yt, 0, tileHeight)) continue;
				if(getTile(xx,yy) instanceof TileUseable){
					return new Vector2f(xx<<4,yy<<4);
				}
			}
		}
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
//		for (int i = 0; i < entities.size(); i++) {
//			if(entities.get(i).getSolid() && !collider.equals(entities.get(i).collider))
//				colliders.add(entities.get(i).collider);
//		}
		for(int xt=tPos.x-radius;xt<tPos.x+radius;xt++){
			for(int yt=tPos.y-radius;yt<tPos.y+radius;yt++){
				if(!getInBounds(xt, 0, tileWidth, yt, 0, tileHeight)) continue;
				Tile t = getTile(xt,yt);
				if(t != null){
					if(t.getSolid())
						colliders.add(new BoxCollider(xt*16, yt*16, 16, 16));
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
}
