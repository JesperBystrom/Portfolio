package inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

import main.Game;

public class InputManager implements KeyListener {
	
	public HashMap<Integer, Key> keys = new HashMap<Integer, Key>();
	public Key up;
	public Key right;
	public Key down;
	public Key left;
	public Key space;
	public Key inventoryOpen;
	
	public InputManager(Game game){
		game.addKeyListener(this);
		keys.put(KeyEvent.VK_UP, new Key(KeyEvent.VK_UP));
		keys.put(KeyEvent.VK_RIGHT, new Key(KeyEvent.VK_RIGHT));
		keys.put(KeyEvent.VK_DOWN, new Key(KeyEvent.VK_DOWN));
		keys.put(KeyEvent.VK_LEFT, new Key(KeyEvent.VK_LEFT));
		keys.put(KeyEvent.VK_SPACE, new Key(KeyEvent.VK_SPACE));
		keys.put(KeyEvent.VK_C, new Key(KeyEvent.VK_C));
		
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		toggle(arg0.getKeyCode(), true);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		toggle(arg0.getKeyCode(), false);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
	}
	
	protected void toggle(int keyCode, boolean bool){
		Key k = keys.get(keyCode);
		if(k != null)
			k.toggle(bool);
	}

	public void releaseAllKeys() {
		for (Key key : keys.values()) {
			key.release();
		}
	}
	
	public Key getKey(int keyCode){
		return keys.get(keyCode);
	}

}
