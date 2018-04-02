package inputs;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import main.Game;
import tools.Vector2;

public class MouseManager implements MouseListener {

	public static final int BUTTON_0 = 0;
	public static final int BUTTON_1 = 1;
	
	public boolean[] pressed = new boolean[2];
	public static Mouse mouse = new Mouse();
	
	public MouseManager(Game game){
		game.addMouseListener(this);
	}
	
	public void updateMousePosition(Vector2 vec){
		mouse.x = vec.x;
		mouse.y = vec.y;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == e.BUTTON1){
			mouse.button_1 = true;
		} else {
			mouse.button_2 = true;
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == e.BUTTON1){
			mouse.button_1 = false;
		} else {
			mouse.button_2 = false;
		}
	}
}
