package inputs;

import items.Item;
import main.Screen;
import main.Sprite;

public class Mouse {
	public Item item;
	public int x;
	public int y;
	public Sprite icon;
	
	public boolean button_1 = false;
	public boolean button_2 = false;
	
	public static final byte BUTTON_1 = 0x0001;
	public static final byte BUTTON_2 = 0x0002;
	
	public void render(Screen screen){
		screen.renderUI(x-8, y-8, icon.tile, null, 0, 1, 1);
	}
	
	public void removeIcon(){
		icon = null;
	}
	
	public void pickupItem(Item item){
		icon = item.icon;
		this.item = item;
	}
	
	public Item dropItem(){
		Item i = item;
		item = null;
		icon = null;
		return i;
	}
	
	public boolean intersects(int x, int y){
		return (this.x >= x && this.x < (x+16) && this.y >= y && this.y < (y+16));
	}
	
	public void release(byte button){
		if((button & BUTTON_1) != 0){
			button_1 = false;
		} else {
			button_2 = false;
		}
	}
	
	public boolean getPressed(byte button){
		if((button & BUTTON_1) != 0){
			return button_1;
		} else {
			return button_2;
		}
	}

	public boolean getEmpty() {
		return item == null;
	}
}
