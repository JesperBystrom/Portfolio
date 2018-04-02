package UI;

import java.util.ArrayList;

import main.Sprite;
import tools.Vector2;

public class UIBackgroundElement extends UIElement {
	
	public boolean render = true;
	
	public UIBackgroundElement(UIBuilder builder) {
		super(builder);
	}
	
	@Override
	public boolean shouldRender(){
		return render;
	}
}
