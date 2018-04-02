package items;

import UI.UI;
import UI.UIBuilder;
import UI.UIElement;
import inputs.Mouse;
import main.Game;
import tools.Tools;

public class Inventory extends UI {
	public int selected = 0;
	public Item[] items = new Item[10];
	public Item[] equipment = new Item[5];
	private Game game;
	private boolean opened = false;
	
	public Inventory(Game game){
		addItem(Item.SWORD, items);
		addItem(Item.SWORD, items);
		addItem(Item.SWORD, items);
		addItem(Item.SWORD, items);
		addItem(Item.HELMET, items);
		fillInventoryWithEmpty(items);
		fillInventoryWithEmpty(equipment);
		
		int backgroundTile = 7;
		int x = 2, y = 5;
		
		addNewUIElement(new UIBuilder().setPosition(UI.center(Axis.X, 96, Game.HEIGHT-1)).setSize(96,Game.HEIGHT-1).setColor(0x7884AB).buildBackground());
		
		for(int i=0;i<items.length;i++){
			addNewUIElement(new UIBuilder().setPosition(x*18, y*17).setSprite(backgroundTile).addChild(new UIBuilder().setPosition(x*18, y*17).setSprite(items[i].icon.tile).build()).build());
			x++;
			if(x >= 9-2){
				y++;
				x=2;
			}
			y = Tools.clamp(y, 0, 6);
		}
		int offset = 2;
		
		//Boots
		addNewUIElement(new UIBuilder().setPosition(40, 42+offset).setSprite(backgroundTile).addChild(new UIBuilder().setPosition(40, 42+offset).setSprite(equipment[0].icon.tile).build()).build());
		addNewUIElement(new UIBuilder().setPosition(40, 42+2).setSprite(ArmorSlot.BOOTS.tile).buildBackground());
		//Shirt
		addNewUIElement(new UIBuilder().setPosition(40, 24+offset).setSprite(backgroundTile).addChild(new UIBuilder().setPosition(40, 24+offset).setSprite(equipment[1].icon.tile).build()).build());
		addNewUIElement(new UIBuilder().setPosition(40, 24+offset).setSprite(ArmorSlot.SHIRT.tile).buildBackground());
		//Helmet
		addNewUIElement(new UIBuilder().setPosition(40, 8).setSprite(backgroundTile).addChild(new UIBuilder().setPosition(40, 8).setSprite(equipment[2].icon.tile).build()).build());
		addNewUIElement(new UIBuilder().setPosition(40, 8).setSprite(ArmorSlot.HELMET.tile).buildBackground());
		//Shield
		addNewUIElement(new UIBuilder().setPosition(105, 42+offset).setSprite(backgroundTile).addChild(new UIBuilder().setPosition(105, 42+offset).setSprite(equipment[3].icon.tile).build()).build());
		addNewUIElement(new UIBuilder().setPosition(105, 42+offset).setSprite(ArmorSlot.SHIELD.tile).buildBackground());
		//Sword
		addNewUIElement(new UIBuilder().setPosition(105, 8).setSprite(backgroundTile).addChild(new UIBuilder().setPosition(105, 8).setSprite(equipment[4].icon.tile).build()).build());
		addNewUIElement(new UIBuilder().setPosition(105, 8).setSprite(ArmorSlot.SWORD.tile).buildBackground());
		
	}
	
	@Override
	public void moveSelection(int xVal, int yVal, int max, int columns) {
		if(move(xVal, yVal, max, columns)){
			//if(((selectedElement > 8 && xVal == 1) || ((selectedElement > 4 && selectedElement < 8) && yVal == 1))) return;
			if(selectedElement < 0) selectedElement = 10;
			if(selectedElement >= 10){
				selectedElement += -yVal;
			} else {
				if((selectedElement > 4 && yVal == 1) || (selectedElement > 8 && xVal == 1)) return;
				selectedElement += xVal + (yVal * columns);
			}
			if(selectedElement == 9 && yVal == 1)
				selectedElement = 0;
			selectedElement = Tools.clamp(selectedElement, -5, 14);
		}
		if(selectedElement < 0) selectedElement = 10;
	}
	
	public void fillInventoryWithEmpty(Item[] inventory){
		for(int i=0;i<inventory.length;i++){
			if(inventory[i] == null)
				inventory[i] = Item.NONE;
		}
	}
	
	public void addItem(Item item, Item[] inventory){
		for(int i=0;i<inventory.length;i++){
			if(inventory[i] == null) {
				inventory[i] = item;
				return;
			}
		}
	}
	
	public void moveItem(Item item, Item[] inventory){
		UIElement[] selectable = getSelectableElements();
		for(int i=0;i<inventory.length;i++){
			UIElement parent = selectable[i];
			for(int j=0;j<parent.children.size();j++){
				UIElement child = parent.children.get(j);
			}
		}
	}
	
	public void equipItem(){
		
	}
	
	public Item[] getItems(){
		return items;
	}

	public void toggle() {
		opened ^= true;
	}
	
	public boolean getOpened(){
		return opened;
	}
	
	@Override
	public boolean shouldRender() {
		return opened;
	}
	
	/*
	@Override
	public void moveSelection(int xVal, int yVal, int max, int columns) {
		if(move(xVal, yVal, max, columns)){
			selectedElement += xVal;
			if(selectedElement == 0 && yVal == -1)
				selectedElement = 11;
		}
	}*/
	
	public int getColumns(){
		return 5;
	}

	public Item removeItem(Item[] inventory, int index) {
		Item i = inventory[index];
		inventory[index] = Item.NONE;
		return i;
	}

	public void setItem(Item[] inventory, int index, Item item) {
		inventory[index] = item; 
	}

	public Item getItem(int index) {
		return items[index];
	}

	public boolean hasItem(Item[] inventory, int index) {
		return (inventory[index] != Item.NONE && inventory[index] != null);
	}

	public byte pickupOrDropItem(Item[] inventory, int index, Mouse mouse) {
		boolean  b = true;
		if(hasItem(inventory, index) && mouse.getEmpty()){
			mouse.pickupItem(removeItem(inventory, index));
			return 0x01;
		} else if(!hasItem(inventory, index) && !mouse.getEmpty()){
			Item i = mouse.dropItem();
			if(inventory.equals(equipment)){
				b = ((index == 0 && i.equals(Item.BOOTS)) || 
					(index == 1 && i.equals(Item.SHIRT)) || 
					(index == 2 && i.equals(Item.HELMET)) ||
					(index == 3 && i.equals(Item.SHIELD)) ||
					(index == 4 && i.equals(Item.SWORD)));
			}
			if(b)
				setItem(inventory, index, i);
			else {
				mouse.pickupItem(i);
			}
			return 0x02;
		}
		return 0;
	}

	public Item[] getItemInventory() {
		return items;
	}
	public Item[] getEquipmentInventory(){
		return equipment;
	}
	
	public Item getEquip(ArmorSlot slot) {
		return getEquipmentInventory()[slot.id];
	}
	public Item getEquip(int index) {
		return getEquipmentInventory()[index];
	}
}