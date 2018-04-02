package generator;

import java.util.Random;

import main.Screen;
import tiles.Tile;
import tools.Vector2;
import world.Map;

public class RandomGenerator {
	public Map map;
	private Random random = new Random();
	private int x;
	private int y;
	
	public RandomGenerator(Map map){
		this.map = map;
		x = map.width/2;
		y = map.height/2;
		generateOverWorld();
	}
	
	public void putRoom(Room room, int x, int y){
		map.setTiles(Tile.BASIC, x, y, x+room.width, y+room.height);
	}
	
	public void generateTestMap(){
		generate(0,0, 3, 50);
	}
	
	public void generateOverWorld(){
		generate(1, 0, 2, 200);
	}
	
	public void generateCaves(){
		generate(4, 10, 3, 50);
	}
	
	//default: (4,10,3,50)
	public void generate(int cooridorLength, int roomOffset, int roomSize, int cooridorLife){
		for(int i=0;i<cooridorLife;i++){
			int r = random.nextInt(5);
			int rr = cooridorLength;
			if(r == 1){
				for(int j=0;j<rr;j++){
					if(x >= map.width-roomOffset)
						break;
					x++;
					map.setTiles(Tile.TREE, x-1, y-1, x+1, y+1);
				}
			}
			if(r == 2){
				for(int j=0;j<rr;j++){
					if(x <= 10)
						break;
					x--;
					map.setTiles(Tile.TREE, x-1, y-1, x+1, y+1);
				}
			}
			if(r == 3){
				for(int j=0;j<rr;j++){
					if(y >= map.height-roomOffset)
						break;
					y++;
					map.setTiles(Tile.TREE, x-1, y-1, x+1, y+1);
				}
			}
			if(r == 4){
				for(int j=0;j<rr;j++){
					if(y <= roomOffset)
						break;
					y--;
					map.setTiles(Tile.TREE, x-1, y-1, x+1, y+1);
				}
			}
			int rrr = random.nextInt(3);
			if(rrr == 1){
				int rrrr = roomSize;
				map.setTiles(Tile.BASIC, x-rrrr, y-rrrr, x+rrrr, y+rrrr);
			}
		}
		
		for(int x=0;x<map.width;x++){
			for(int y=0;y<map.height;y++){
				/*if(map.getTile(x-1, y) != Tile.TREE)
					map.setTile(Tile.TREE, x-1, y);
				if(map.getTile(x+1, y) != Tile.TREE)
					map.setTile(Tile.TREE, x+1, y);
				if(map.getTile(x, y+1) != Tile.TREE){
					map.setTile(Tile.TREE, x, y+1);
				}
				if(map.getTile(x, y-1) != Tile.TREE){
					map.setTile(Tile.TREE, x, y-1);
				}*/
				if(map.getBackgroundTile(x, y) == null){
					map.setTile(Tile.TREE, x, y);
				}
			}
		}
		for(int x=0;x<map.width;x++){
			for(int y=0;y<map.height;y++){
				if(map.getBackgroundTile(x, y+1) == Tile.BASIC && map.getBackgroundTile(x, y) == Tile.WALL){
					map.setTile(Tile.ROOF, x, y);
				}
			}
		}
		
		Vector2 pos = map.findTileMostToTheLeft(Tile.BASIC);
		if(pos != null){
			//map.getPlayer().position.x = pos.x * Screen.SIZE_SCALED;
			//map.getPlayer().position.y = pos.y * Screen.SIZE_SCALED;
			//map.getPlayer().setPosition(new Vector2(pos.x*48, pos.y*48));
		}
	}
}
