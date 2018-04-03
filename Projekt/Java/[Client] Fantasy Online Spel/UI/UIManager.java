package UI;

import java.util.ArrayList;

import main.Screen;

public class UIManager {
	public ArrayList<UI> uiCollection = new ArrayList<UI>();
	
	public void addUI(UI ui){
		uiCollection.add(ui);
	}
	
	public void render(Screen screen){
		for(int i=0;i<uiCollection.size();i++){
			if(uiCollection.get(i).shouldRender()){
				uiCollection.get(i).render(screen);
			}
		}
	}
}
