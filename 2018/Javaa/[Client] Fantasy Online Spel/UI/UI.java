package UI;

import java.util.ArrayList;

import main.Game;
import main.Screen;
import main.Sprite;
import tools.Vector2;

public class UI {
	public ArrayList<UIElement> elements = new ArrayList<UIElement>();
	public int selectedElement;
	private boolean focus = true;
	
	public static enum Axis {
		X,Y
	}
	
	public UIElement addNewUIElement(UIElement element){
		elements.add(element);
		return element;
	}
	
	public void fillEmptyColumn(int columnSize){
		for(int i=0;i<columnSize;i++){
			addNewUIElement(UIElement.EMPTY);
		}
	}
	
	public void render(Screen screen){
		for(int i=0;i<elements.size();i++){
			float light = 1f;
			UIElement e = elements.get(i);
			if(e == null | !e.shouldRender()) continue;
			if(e.selected){
				light = 0.2f;
			}
			if(e.sprite != null){
				screen.renderUI(e.position.x, e.position.y, e.sprite.tile, null, 0, 1, light);
			} else {
				screen.renderColorUI(e.position.x, e.position.y, e.w, e.h, e.color);
				screen.renderOutlineUI(e.position.x, e.position.y, e.w, e.h, 0xDDF2FF);
			}
			updateAndRenderChildren(e, screen, light);
		}
	}
	
	public void updateAndRenderChildren(UIElement parent, Screen screen, float light){
		for(int j=0;j<parent.children.size();j++){
			UIElement c = parent.children.get(j);
			c.selected = parent.selected;
			screen.renderUI(c.position.x, c.position.y, c.sprite.tile, null, 0, 1, light);
		}
	}
	
	public boolean move(int xVal, int yVal, int max, int columns){
		if(getFocus()){

			UIElement[] selectable = getSelectableElements();
			for(int i=0;i<selectable.length;i++){
				selectable[i].selected = false;
				if(i == selectedElement){
					selectable[i].highlight();
				}
			}
		}
		return getFocus();
	}
	
	public void moveSelection(int xVal, int yVal, int max, int columns){
		if(move(xVal, yVal, max, columns)){
			selectedElement += xVal + (columns*yVal);
			//System.out.println("Selected Element: " + selectedElement);
			//selectedElement = Tools.clamp(selectedElement, 0, max);
		}
	}
	
	public UIElement[] getSelectableElements(){
		ArrayList<UIElement> selectableElements = new ArrayList<UIElement>();
		
		for(int i=0;i<elements.size();i++){
			UIElement e = elements.get(i);
			if(!(e instanceof UIBackgroundElement) && e != null)
				selectableElements.add(e);
		}
		return selectableElements.toArray(new UIElement[selectableElements.size()]);
	}
	
	public UIBackgroundElement[] getBackgroundElements(int startIndex){
		ArrayList<UIElement> background = new ArrayList<UIElement>();
		
		for(int i=startIndex;i<elements.size();i++){
			UIElement e = elements.get(i);
			if((e instanceof UIBackgroundElement))
				background.add(e);
		}
		return background.toArray(new UIBackgroundElement[background.size()]);
	}
	
	public void hideBackground(int index){
		UIBackgroundElement[] background = getBackgroundElements(1);
		background[index].render = false;
	}
	public void showBackground(int index){
		UIBackgroundElement[] background = getBackgroundElements(1);
		background[index].render = true;
	}
	
	public static Vector2 center(Axis x, int w, int h){
		if(x == Axis.X)
			return new Vector2(Game.WIDTH/2 - (w/2), 0);
		if(x == Axis.Y)
			return new Vector2(0, Game.HEIGHT/2 - (h/2));
		
		return new Vector2(Game.WIDTH / 2 - (w/2), Game.HEIGHT / 2 - (h/2));
	}
	

	public void updateElementSprite(UIElement selectedElement, Sprite sprite) {
		selectedElement.sprite = sprite;
	}
	
	public UIElement getSelectedElement(){
		return getSelectableElements()[selectedElement];
	}
	
	public int getSelectedElementIndex(){
		return selectedElement;
	}
	
	public boolean shouldRender() {
		return true;
	}
	
	public void unfocus(){
		this.focus = false;
		for(int i=0;i<elements.size();i++){
			elements.get(i).selected = false;
		}
	}
	public void focus() {
		this.focus = true;
	}
	
	public boolean getFocus(){
		return focus;
	}
}
