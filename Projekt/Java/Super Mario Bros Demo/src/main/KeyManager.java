package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyManager implements KeyListener {
	
	private boolean[] keys = new boolean[255];
	private int holdTime = 0;
	
	public KeyManager(Game game){
		game.getWindow().getCanvas().addKeyListener(this);
	}
	
	@Override
	public void keyPressed(KeyEvent key) {
		keys[key.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent key) {
		keys[key.getKeyCode()] = false;
	}

	@Override
	public void keyTyped(KeyEvent key) {
		
	}
	
	public boolean getKey(int keyCode){
		return keys[keyCode];
	}
	
	public boolean getAnyArrowKey(){
		return keys[KeyEvent.VK_RIGHT] || keys[KeyEvent.VK_LEFT];
	}
}
