package entities;

import java.awt.event.KeyEvent;

import UI.UIElement;
import inputs.InputManager;
import inputs.Mouse;
import inputs.MouseManager;
import items.ArmorSlot;
import items.Equipment;
import items.Inventory;
import items.Item;
import main.Font;
import main.Game;
import main.Screen;
import main.Sprite;
import particles.SwordParticle;
import tools.Vector2;
import world.Map;

/*
 * @author Jesper Byström
 * Datum: 2018-02-10
 * Class: 
 * 
 */

public class EntityPlayer extends EntityLiving {

	protected InputManager input;
	
	public Inventory inventory;
	public Equipment equipment;
	
	public EntityPlayer(){
		super();
	}
	
	public EntityPlayer(InputManager input, Game game, Map map){
		super(game);
		this.input = input;
		initAnimations(game.getScreen().getSpriteSheet().getSprites(16, 32, 48));
		shader.input(0x00FF0000);
		shader.blend();
		position.x = (map.width*16)/2;
		position.y = (map.height*16)/2;
		inventory = new Inventory(game);
		equipment = new Equipment(game);
		Game.getUIManager().addUI(inventory);
		Game.getUIManager().addUI(equipment);
	}
	
	@Override
	public void update() {	
		super.update();
		velocity = Vector2.zero();
		
		Mouse mouse = MouseManager.mouse;
		UIElement selectedElement = inventory.getSelectedElement();
		//UIElement[] backgrounds = inventory.getBackgroundElements();
		mouse.x = selectedElement.position.x;
		mouse.y = selectedElement.position.y;
		if(input.getKey(KeyEvent.VK_SPACE).pressed && inventory.getOpened()){
			int index = inventory.selectedElement;
			
			if(index < inventory.items.length){
				//is inventory item
				inventory.pickupOrDropItem(inventory.getItemInventory(), index, mouse);
				
				Item item = inventory.getItem(index);
				if(item != null)
					inventory.updateElementSprite(inventory.getSelectedElement().getChild(0), item.icon);
			} else {
				//is equipment
				index -= 10;
				byte result = inventory.pickupOrDropItem(inventory.getEquipmentInventory(), index, mouse);
				if((result & 0x01) == 0){
					inventory.hideBackground(index);
				} else if((result & 0x02) == 0){
					inventory.showBackground(index);
				}
				/*if((result & 0x01) == 0){
					//if drop
					inventory.hideBackground(index);
				} else if((result & 0x02) == 0) {
					//if take
					inventory.showBackground(index);
				}*/
				
				Item item = inventory.getEquip(ArmorSlot.fromInteger(index));
				if(item != null)
					inventory.updateElementSprite(selectedElement.getChild(0), item.icon);
			}
			//System.out.println(backgrounds.length);
		}
		
		//Mouse mouse = MouseManager.mouse;
		/*UIElement[] selectable = inventory.getSelectableElements();
		for(int i=0;i<selectable.length;i++){
			UIElement parent = selectable[i];
			if(parent.selected){
				mouse.x = parent.position.x;
				mouse.y = parent.position.y;
				if(input.getKey(KeyEvent.VK_SPACE).pressed){
					
					for(int j=0;j<parent.children.size();j++){
						UIElement child = parent.children.get(j);
						int equipIndex = i - 10;
						if(child != null){
							if(i < inventory.items.length){
								if(inventory.items[i] != null && inventory.items[i] != Item.NONE && mouse.item == null){
									mouse.pickupItem(inventory.removeItem(inventory.items, inventory.items[i]));
									//mouse.pickupItem(inventory.items[i]);
									//inventory.items[i] = Item.NONE;
									//child.sprite = inventory.items[i].sprite;
								} else if(inventory.items[i] == Item.NONE && mouse.item != null) {
									inventory.setItem(inventory.items, i, mouse.dropItem());
									//inventory.items[i] = mouse.dropItem();
									//if(inventory.items[i] == null) break;
									//child.sprite = inventory.items[i].sprite;
								}
								if(inventory.items[i] != null)
									inventory.updateElementSprite(child, inventory.items[i].sprite);
							} else {
								if(inventory.equipment[equipIndex] != null && inventory.equipment[equipIndex] != Item.NONE && mouse.item == null){
									mouse.pickupItem(inventory.removeItem(inventory.equipment, inventory.equipment[equipIndex]));
									//mouse.pickupItem(inventory.equipment[ind]);
									//inventory.equipment[ind] = Item.NONE;
									//child.sprite = inventory.equipment[ind].sprite;
								} else if(inventory.equipment[equipIndex] == Item.NONE && mouse.item != null) {
									inventory.setItem(inventory.equipment, equipIndex, mouse.dropItem());
									//inventory.equipment[equipIndex] = mouse.dropItem();
									//if(inventory.equipment[equipIndex] == null) break;
									//child.sprite = inventory.equipment[equipIndex].sprite;
								}
								if(inventory.equipment[equipIndex] != null)
									inventory.updateElementSprite(child, inventory.equipment[equipIndex].sprite);
							}
						}
					}
				}
			}
		}*/
		
		speed = 1;
		if(input.getKey(KeyEvent.VK_RIGHT).pressed){
			velocity.x = speed;
		}
		if(input.getKey(KeyEvent.VK_LEFT).pressed){
			velocity.x = -speed;
		}
		if(input.getKey(KeyEvent.VK_UP).pressed){
			velocity.y = -speed;
		}
		if(input.getKey(KeyEvent.VK_DOWN).pressed){
			velocity.y = speed;
		}
		
		if(input.getKey(KeyEvent.VK_C).pressed){
			inventory.toggle();
			input.getKey(KeyEvent.VK_C).release();
		}
		if(inventory.getOpened()) {
			inventory.moveSelection(velocity.x, velocity.y, inventory.getItems().length-1, inventory.getColumns());
			input.releaseAllKeys();
			velocity = Vector2.zero();
		}
	}
	int anim = 12;
	boolean start = false;
	@Override
	public void render(Screen screen) {
		super.render(screen);
		
		for(int i=0;i<inventory.getEquipmentInventory().length;i++){
			inventory.getEquip(i).render(screen, this);
		}
		
		//inventory.render(screen, input.getKey(KeyEvent.VK_SPACE));
		//equipment.render(screen);
		
		
		
		
		if(input.getKey(KeyEvent.VK_SPACE).isPressed() && !inventory.getOpened()){
			onAction();
			map.addParticle(new SwordParticle(game, position, 100, 3, true, direction, Screen.SPRITE_SIZE, new Sprite[]{new Sprite(13), new Sprite(14), new Sprite(15)}));
			if(inventory.getEquip(ArmorSlot.SWORD) != null)
			map.addParticle(new SwordParticle(position, inventory.getEquip(ArmorSlot.SWORD).icon, 10, direction, Screen.SPRITE_SIZE));
			input.getKey(KeyEvent.VK_SPACE).block(50);
			Entity[] entities = map.getEntities(this, Screen.SPRITE_SIZE*2);
			for(Entity e : entities){
				if(e != null){
					if(attack((EntityLiving)e, 16)){
						dealDamage((EntityLiving)e, 10);
					}
				}
			}
		}
	}
}