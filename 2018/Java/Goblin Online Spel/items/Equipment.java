package items;

import UI.UI;
import inputs.InputManager;
import main.Game;
import main.Screen;
import tools.Tools;

public class Equipment extends UI {

	public Equipment(Game game) {
		int offset = 2;
		unfocus();
//		addNewUIElement(new UIBuilder().setPosition(40, 42+offset).setSprite(7).addChild(new UIBuilder().setPosition(40, 42+offset).setSprite(9).build()).build());
//		addNewUIElement(new UIBuilder().setPosition(40, 24+offset).setSprite(7).addChild(new UIBuilder().setPosition(40, 24+offset).setSprite(9).build()).build());
//		addNewUIElement(new UIBuilder().setPosition(40, 8).setSprite(7).addChild(new UIBuilder().setPosition(40, 8).setSprite(9).build()).build());
	}
	
	@Override
	public void moveSelection(int xVal, int yVal, int max, int columns) {
		if(move(xVal, yVal, max, columns)){
			selectedElement += -yVal;
			selectedElement = Tools.clamp(selectedElement, 0, max);
		}
	}
	
	/*
	@Override
	public void move(InputManager input) {
		if(input.up.pressed){
			selected++;
			input.up.release();
		}
		if(input.down.pressed){
			selected--;
			input.down.release();
		}
	}*/
}
