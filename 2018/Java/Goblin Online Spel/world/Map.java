package world;

import java.util.ArrayList;
import java.util.Random;

import entities.Entity;
import entities.EntityMob;
import entities.EntityPlayer;
import main.Game;
import main.Screen;
import main.Sprite;
import networking.NetEntityPlayer;
import particles.ParticleSystem;
import tiles.Tile;
import tools.Vector2;

/*
 * @author Jesper Byström
 * Datum: 2018-02-10
 * Class: 
 * 
 */

public class Map {
	
	public ArrayList<Entity> entities = new ArrayList<Entity>();
	public ArrayList<Marker> markers = new ArrayList<Marker>();
	public ArrayList<ParticleSystem> particles = new ArrayList<ParticleSystem>();
	public int width;
	public int height;
	
	public int realWidth;
	public int realHeight;
	
	private Tile[] background;
	private Tile[] foreground;
	private Game game;
	public NetEntityPlayer player;
	int tileCount;
	
	public Map(int width, int height, Game game){
		this.width = width;
		this.height = height;
		
		this.background = new Tile[(width*height)];
		this.foreground = new Tile[(width*height)];
		this.game = game;
		//player = new NetEntityPlayer(game.getKeyboardInput(), game, this, game.network, game.network.username);
		//add(player);
	}
	
	public void update(){
		for(int i=0;i<entities.size();i++){
			Entity top = entities.get(entities.size()-1);
			if(top != null && entities.get(i).position.distance(top.position) < 32 && entities.get(i) != top){
				if(entities.get(i).position.y > top.position.y){
					changeIndexPositions(entities.get(i), top);
				}
			}
			Entity e = entities.get(i);
			e.update();
		}
		for(int i=0;i<particles.size();i++){
			particles.get(i).update(game);
		}
	}
	public void render(Screen screen){
		renderTiles(screen);
		renderEntities(screen);
		renderParticles(screen);
		renderMarkers(screen);
	}
	
	public void renderEntities(Screen screen){
		for(int i=0;i<entities.size();i++){
			Entity e = entities.get(i);
			//System.out.println(player);
			if(player == null) continue;
			boolean bounds = inBounds(e.position.x, e.position.y, player.position.x - Game.WIDTH, player.position.y - Game.HEIGHT, player.position.x + Game.WIDTH, player.position.y + Game.HEIGHT);
			if(bounds)
				e.render(screen);
			
//			if(Game.DEBUG)
//				screen.renderCollider(e.collider, 0x00FF0000, true);
		}
	}
	public void renderTiles(Screen screen){
		Vector2 tiledRange = Tile.toTilePosition(screen.width + 16, screen.height + 32);
		Vector2 tiledPosition = Tile.toTilePosition((int)screen.scroll.x, (int)screen.scroll.y);
		
		int x0 = (tiledPosition.x) - tiledRange.x;
		int y0 = (tiledPosition.y) - tiledRange.y;
		int x1 = (tiledPosition.x) + tiledRange.x;
		int y1 = (tiledPosition.y) + tiledRange.y;
		
		for(int y=y0;y<y1;y++){
			for(int x=x0;x<x1;x++){
				Tile t = getBackgroundTile(x,y);
				Tile f = getForegroundTile(x,y);
				Vector2 tPos = Tile.toWorldPosition(x, y);
				if(t != null){
					screen.render(tPos.x, tPos.y, t.sprite.tile, null, 0, 0);
				}
				if(f != null){
					screen.render(tPos.x, tPos.y, f.sprite.tile, null, 0, 0);
				}
			}
		}
	}
	public void renderForeground(Screen screen, int x, int y){
		Tile t = getBackgroundTile(x,y);
		Vector2 pos = Tile.toWorldPosition(x, y);
		pos.y+=1;
		screen.render(pos.x, pos.y, t.sprite.tile, null, 0, 0);
	}
	
	public void renderParticles(Screen screen){
		for(int i=0;i<particles.size();i++){
			particles.get(i).render(screen);
		}
	}
	public void renderMarkers(Screen screen){
		for(int i=0;i<markers.size();i++){
			Marker m = markers.get(i);
			screen.renderMarker(m.position.x, m.position.y, (int)m.offset.x, (int)m.offset.y, m.sprite.tile, null, 0);
			//screen.renderWorldSpaceUI(m.position.x, m.position.y, m.offset.x, m.offset.y, m.sprite.tile, -1);
		}
	}
	public Entity getTopEntity(){
		int yMax = 0;
		Entity e = null;
		for(int i=0;i<entities.size();i++){
			if(entities.get(i).position.y < yMax){
				e = entities.get(i);
				yMax = e.position.y;
			}
		}
		return e;
	}
	public void setTile(Tile tile, int x, int y){
		if(!inBounds(x,y)) return;
		if((tile.layer & Tile.LAYER_BACKGROUND) != 0) 
			background[x + y * width] = tile;
		else 
			foreground[x + y * width] = tile;
		tileCount++;
	}
	public void setTiles(Tile tile, int xStart, int yStart, int xEnd, int yEnd){
		for(int x=xStart;x<xEnd;x++){
			for(int y=yStart;y<yEnd;y++){
				setTile(tile, x, y);
			}
		}
		
	}
	public void add(Entity e){
		this.entities.add(e);
	}
	public Tile[] getTiles(){
		return this.background;
	}
	public Tile getBackgroundTile(int x, int y){
		int index = x+y*width;
		if(!inBounds(x,y)) return null;
		return background[index];
	}
	public Tile getForegroundTile(int x, int y){
		int index = x+y*width;
		if(!inBounds(x,y)) return null;
		return foreground[index];
	}
	public Entity getEntity(int x, int y){
		for(int i=0;i<entities.size();i++){
			if(entities.get(i).position.x == x && entities.get(i).position.y == y)
				return entities.get(i);
		}
		return null;
	}
	public EntityMob[] getMobs(){
		ArrayList<EntityMob> mobs = new ArrayList<EntityMob>();
		for(int i=0;i<entities.size();i++){
			if(entities.get(i) instanceof EntityMob)
				mobs.add((EntityMob)entities.get(i));
		}
		return mobs.toArray(new EntityMob[mobs.size()]);
	}
	public Entity checkCollisionWithNearEntities(Entity origin){
		int r = Screen.SPRITE_SIZE;
		
		int xPos = origin.position.x;
		int yPos = origin.position.y;
		
		for(int i=0;i<entities.size();i++){
			Entity e = entities.get(i);
			if(e != origin){
				if(origin.collider.intersect(e.collider)){
					return entities.get(i);
				}
			}
		}
		return null;
	}
	public Tile checkCollisionWithNearTiles(Entity origin){
		int r = 96;
		int xPos = (int)origin.position.x/Screen.SPRITE_SIZE;
		int yPos = (int)origin.position.y/Screen.SPRITE_SIZE;
		for(int x=xPos-2;x<xPos+2;x++){
			for(int y=yPos-2;y<yPos+2;y++){
				Tile t = getForegroundTile(x, y);
				if(t == null || !t.isSolid()) continue;
				if(origin.collision(x*16, y*16, 0, 0)){
					return t;
				}
			}
		}
		return null;
	}
	
	public boolean checkCollisionWithAnything(Entity origin){
		int r = 96;
		for(int x=(int)origin.position.x-r;x<origin.position.x+r;x++){
			for(int y=(int)origin.position.y-r;y<origin.position.y+r;y++){
				Tile t = getBackgroundTile(x, y);
				if(t != null){
					if(origin.collision(x, y, 0, -16) && t.isSolid()){
						return true;
					}
				}
				Entity e = getEntity(x,y);
				if(e != null && e != origin){
					if(origin.collider.intersect(e.collider)){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public Entity findNearestEntity(Entity origin){
		int r = 96;
		for(int x=(int)origin.position.x-r;x<origin.position.x+r;x++){
			for(int y=(int)origin.position.y-r;y<origin.position.y+r;y++){
				Entity e = getEntity(x,y);
				if(e != null && e != origin){
					return e;
				}
			}
		}
		return null;
	}
	
	public Vector2 findTileMostToTheLeft(Tile type){
		for(int x=0;x<width;x++){
			for(int y=height;y>0;y--){
				Tile t = getBackgroundTile(x,y);
				if(t == type)
					return new Vector2(x,y);
			}
		}
		return null;
	}
	
	public void changeIndexPositions(Entity e1, Entity e2){
		int index = entities.indexOf(e1);
		entities.set(entities.size()-1, e1);
		entities.set(index, e2);
	}
	private boolean inBounds(int x, int y){
		//return(index >= 0 && index < map.length);
		return (x >= 0 && x < width && y >= 0 && y < height);
	}
	private boolean inBounds(int x, int y, int xMin, int yMin, int xMax, int yMax){
		//return(index >= 0 && index < map.length);
		return (x >= xMin && x < xMax && y >= yMin && y < yMax);
	}
	public void setMarker(int x, int y, Sprite sprite){
		markers.add(new Marker(new Vector2(x,y),sprite, new Vector2(24,42)));
	}
	public void setMarker(Entity e, Sprite sprite){
		markers.add(new Marker(e.position,sprite, new Vector2(24,42)));
	}
	public EntityPlayer getLocalPlayer(){
		return this.player;
	}
	public NetEntityPlayer getNetworkPlayer(){
		return this.player;
	}

	public void remove(Entity entity) {
		entities.remove(entity);
	}

	public Entity[] getEntities(int xMin, int yMin, int xMax, int yMax) {
		ArrayList<Entity> e = new ArrayList<Entity>();
		for(int x=xMin;x<xMax;x++){
			for(int y=yMin;y<yMax;y++){
				e.add(getEntity(x,y));
			}
		}
		return e.toArray(new Entity[e.size()]);
	}
	public Entity[] getEntities(Entity origin, int radius) {
		ArrayList<Entity> e = new ArrayList<Entity>();
		int xMin = (int)origin.position.x - radius;
		int yMin = (int)origin.position.y - radius;
		int xMax = (int)origin.position.x + radius;
		int yMax = (int)origin.position.y + radius;
		for(int x=xMin;x<xMax;x++){
			for(int y=yMin;y<yMax;y++){
				e.add(getEntity(x,y));
			}
		}
		return e.toArray(new Entity[e.size()]);
	}
	public Vector2 getRandomLocation(Tile type){
		Random r = new Random();	
		Tile t = null;
		Vector2 pos = Vector2.zero();
		while(t != type){
			int x = r.nextInt(width);
			int y = r.nextInt(height);
			pos = new Vector2(x, y);
			t = getBackgroundTile(x,y);
		}
		return pos;
	}

	public void addParticle(ParticleSystem ps) {
		this.particles.add(ps);
	}
	public void removeParticle(ParticleSystem ps){
		this.particles.remove(ps);
	}
	
	/*public Tile[] getNearTiles(Entity e, int radius){
	int xp = 0, yp = 0;
	int xFlip = 1, yFlip = 1;
	
	int w = radius;
	int h = radius;
	
	int maxX = e.position.x+w;
	int maxY = e.position.y+h;
	
	int minX = e.position.x-w;
	int minY = e.position.y-h;
	
	if(maxX < 0 || maxY < 0) return new Tile[0];
	
	Tile[] nearTiles = new Tile[maxX*maxY];
	
	for(int y=minY;y<maxY;y++){
		for(int x=minX;x<maxX;x++){
			if(inBounds(x,y,width,height) && inBounds(x,y,maxX,maxY)){
				nearTiles[x+y*maxX] = getTile(x,y);
			}
		}
	}
	return nearTiles;
}*/
}
