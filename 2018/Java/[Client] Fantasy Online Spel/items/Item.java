package items;

import entities.Entity;
import main.Screen;
import main.Sprite;
import main.SpriteCollection;

enum ItemType {
	NONE, SWORD, WAND, HELMET, SHIRT, LEGGINGS, BOOTS, SHIELD
}
public class Item {

	public static int row = 64;
	
	public static final Item NONE = new Item(ItemType.NONE, 255, new int[] {0,0,0,0}, new int[] {0,0,0,0}, 0, 0);
	public static final Item SWORD = new Item(ItemType.SWORD, 4, new int[] {0,0,0,0}, new int[] {0,0,0,0}, row, row+4);
	public static final Item HELMET = new Item(ItemType.HELMET, 68, new int[] {0,0,0,0}, new int[] {0,0,0,0}, row+4, row+8);
	public static final Item SHIRT = new Item(ItemType.SHIRT, 9, new int[] {0,0,0,0}, new int[] {0,0,0,0}, 0, 0);
	public static final Item BOOTS = new Item(ItemType.BOOTS, 9, new int[] {0,0,0,0}, new int[] {0,0,0,0}, 0, 0);
	public static final Item SHIELD = new Item(ItemType.SHIELD, 9, new int[] {0,0,0,0}, new int[] {0,0,0,0}, 0, 0);
	
	private ItemType type;
	public Sprite icon;
	public Sprite[] directions = new Sprite[4];
	int[] xo, yo;
	
	public Item(ItemType type, int tile, int[] xo, int[] yo, int start, int end){
		this.type = type;
		this.icon = new Sprite(tile);
		this.xo = xo;
		this.yo = yo;
		if(end-start > directions.length) throw new IllegalArgumentException("Too many sprites");
		if(end-start <= 0){
			start = 0;
			end = 4;
		}
		for(int i=start;i<end;i++){
			int index = i-start;
			directions[index] = new Sprite(i);
		}
	}
	
	public void render(Screen screen, Entity e){
		int xo = 8;
		int yo = 2;
		
		boolean renderUp = true;
		
		switch(type){
		case SWORD:
			xo = 8;
			yo = 2;
			renderUp = false;
			break;
		case HELMET:
			xo = 0;
			yo = 4;
			break;
		}
		
		if(type != ItemType.NONE && type != ItemType.SWORD){
			switch(e.direction){
			case Entity.RIGHT:
				screen.render(e.position.x + xo, e.position.y - yo, directions[1].tile, null, Entity.RIGHT, 0);
				break;
			case Entity.LEFT:
				screen.render(e.position.x + xo, e.position.y - yo, directions[2].tile, null, Entity.RIGHT, 0);
				break;
			case Entity.UP:
				screen.render(e.position.x + xo, e.position.y - yo, directions[3].tile, null, Entity.RIGHT, 0);
				break;
			case Entity.DOWN:
				screen.render(e.position.x + xo, e.position.y - yo, directions[0].tile, null, Entity.RIGHT, 0);
				break;
			}
		}
		//if(type != ItemType.NONE)
			//screen.render(e.position.x, e.position.y + 4, directions[e.direction].tile, null, Entity.RIGHT, 0);
		
		//if(e.direction == Entity.RIGHT | e.direction == Entity.DOWN) screen.render(e.position.x + xo, e.position.y - yo, icon.tile, null, Entity.RIGHT, 0);
		//if(e.direction == Entity.LEFT | e.direction == Entity.UP) screen.render(e.position.x - xo, e.position.y - yo, icon.tile, null, Entity.LEFT, 0);
	}
}
