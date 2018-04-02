package UI;

import java.util.ArrayList;

import main.Sprite;
import tools.Vector2;

public class UIBuilder {
	
	private ArrayList<UIElement> children = new ArrayList<UIElement>();
	private Class cls;
	private Vector2 position = Vector2.zero();
	private Sprite sprite;
	private int color;
	private int w;
	private int h;
	
	public ArrayList<UIElement> getChildren() {
		return children;
	}
	public Class getCls() {
		return cls;
	}
	public Vector2 getPosition() {
		return position;
	}
	public Sprite getSprite() {
		return sprite;
	}
	public int getColor() {
		return color;
	}
	public int getWidth() {
		return w;
	}
	public int getHeight() {
		return h;
	}
	
	public UIBuilder setColor(int color){
		this.color = color;
		return this;
	}
	public UIBuilder setSprite(int tile){
		this.sprite = new Sprite(tile);
		return this;
	}
	public UIBuilder setPosition(int x, int y){
		this.position = new Vector2(x,y);
		return this;
	}
	public UIBuilder setPosition(Vector2 position){
		this.position = position;
		return this;
	}
	public UIBuilder addChild(UIElement e){
		children.add(e);
		return this;
	}
	public UIBuilder setSize(int w, int h) {
		this.w = w;
		this.h = h;
		return this;
	}
	
	public UIBackgroundElement buildBackground(){
		return new UIBackgroundElement(this);
	}
	public UIElement build(){
		return new UIElement(this);
	}
}
