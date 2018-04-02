package main;

import java.util.ArrayList;
import java.util.Random;

import entities.Entity;
import entities.EntityMob;
import packets.Packet06EntitySpawn;
import tools.Rand;
import tools.Tools;
import tools.Vector2;

public class Map {

	public static final byte UN_WALKABLE = 0x00;
	public static final byte WALKABLE = 0x01;
	public Main main;
	
	private int width, height;
	private int gridWidth, gridHeight;
	private int[] grid;
	private ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public Map(Main main, int width, int height){
		this.main = main;
		this.width = width;
		this.height = height;
		grid = new int[width * height];
		for(int i=0;i<grid.length;i++){
			grid[i] = WALKABLE;
		}
	}
	
	public void update() {
		for(Entity e : getEntities()){
			e.update();
		}
	}
	
	public Vector2 getPoint(int xStart, int yStart, int radius){
		int x0 = (xStart>>4)-radius;
		int x1 = (xStart>>4)+radius;
		int y0 = (yStart>>4)-radius;
		int y1 = (yStart>>4)+radius;
		
		x0 = Tools.clamp(x0, 0, width-1);
		x1 = Tools.clamp(x1, 0, width-1);
		y0 = Tools.clamp(y0, 0, height-1);
		y1 = Tools.clamp(y1, 0, height-1);
		
		int xTile = Rand.range(x0, x1);
		int yTile = Rand.range(y0, y1);
		int tile = -1;
		while((tile = grid[xTile + yTile * width]) != WALKABLE){
			System.out.println("Cant walk there");
			xTile = Rand.range(x0, x1);
			yTile = Rand.range(y0, y1);
		}
		return new Vector2(xTile*16, yTile*16);
	}
	
	/*public Vector2 getRandomGrid(int radius){
		int tile = grid[getPoint(radius)];
		int xTile = tile % 16;
		int yTile = tile / 16;
		//int tile = grid[getPoint()];
		if(xTile == WALKABLE && yTile == WALKABLE)
			return new Vector2(getPoint(), getPoint());
		return Vector2.zero();
	}*/
	
	/*
	public Vector2 getPoint(int x0, int y0, int radius){
		Random r = new Random();
		int xRand=-1, yRand=-1;
		//int xRand = r.nextInt(((x0 + radius) - (x0 - radius)) + 1) + (x0-radius);
		//int yRand = r.nextInt(((y0 + radius) - (y0 - radius)) + 1) + (y0-radius);
		//if(!inBoundsOfMap(xRand, yRand)) getPoint(x0,y0,radius);
		return new Vector2(xRand, yRand);
	}*/
	
	public void choose(){
		
	}
	
	public boolean inBoundsOfMap(int x, int y){
		return (x < width && x >= 0 && y < height && y >= 0);
	}
	
	public int getNextEntityId(){
		return entities.size();
	}
	
	public void addEntity(Entity e){
		e.entityId = getNextEntityId();
		entities.add(e);
		//System.out.println("Added a new entity: " + e.entityId);
		main.getServer().sendPacketToEveryone(new Packet06EntitySpawn(e.entityId, e.entityType, e.position.x, e.position.y));
		System.out.println("Added entity: " + e.entityId + ", " + e.entityType.toString());
	}
	
	public Entity[] getEntities(){
		return entities.toArray(new Entity[entities.size()]);
	}
	
	public EntityMob[] getMobs(){
		ArrayList<EntityMob> mobs = new ArrayList<EntityMob>();
		for(Entity e : getEntities()){
			if(e instanceof EntityMob)
				mobs.add((EntityMob)e);
		}
		return mobs.toArray(new EntityMob[mobs.size()]);
	}

	public void removeEntity(Entity entity) {
		System.out.println("Removed entity: " + entity.entityId);
		entities.remove(entity);
	}
}
