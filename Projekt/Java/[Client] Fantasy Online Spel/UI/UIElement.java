package UI;

import java.util.ArrayList;

import main.Sprite;
import tools.Vector2;

public class UIElement {

	public static final int PREVIOUSLY_CREATED = 0;

	public static final UIElement EMPTY = new UIElement();

	public Vector2 position = Vector2.zero();
	public Sprite sprite;
	public boolean selected;
	public UIElement parent;
	public ArrayList<UIElement> children = new ArrayList<UIElement>();
	public int color;
	public int w;
	public int h;
	
	/*public UIElement(Vector2 position, int w, int h, int color){
		this.position = position;
		this.color = color;
		this.w = w;
		this.h = h;
	}
	public UIElement(Vector2 position, Sprite sprite){
		this.position = position;
		this.sprite = sprite;
	}
	*/
	public UIElement(){
		
	}
	public UIElement(UIBuilder builder){
		this.position = builder.getPosition();
		this.sprite = builder.getSprite();
		this.color = builder.getColor();
		this.w = builder.getWidth();
		this.h = builder.getHeight();
		this.children = builder.getChildren();
	}
	public void setParent(UIElement e) {
		this.position = e.position;
		this.parent = e;
	}
	public UIElement addChild(UIElement e){
		children.add(e);
		return e;
	}
	public void highlight() {
		selected = true;
	}
	public UIElement getChild(int index){
		return children.get(index);
	}
	public boolean shouldRender(){
		return true;
	}
}
