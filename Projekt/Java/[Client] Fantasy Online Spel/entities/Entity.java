package entities;

import animations.Animation;
import animations.AnimationClip;
import main.BoxCollider;
import main.Game;
import main.Screen;
import main.Sprite;
import particles.ParticleSystem;
import shaders.ColorShader;
import shaders.Shader;
import tiles.Tile;
import tools.Vector2;
import tools.Vector2f;
import world.Map;

/*
 * @author Jesper Byström
 * Datum: 2018-02-10
 * Class: 
 * 
 */

public class Entity {
	
	public static final int UP = 3;
	public static final int RIGHT = 1;
	public static final int DOWN = 0;
	public static final int LEFT = 2;
	
	public Vector2 position = new Vector2(1,1);
	public BoxCollider collider = new BoxCollider(this, position, Screen.SPRITE_SIZE, Screen.SPRITE_SIZE);
	public int speed = 1;
	public Vector2 velocity = Vector2.zero();
	public Animation animation;
	public int direction;
	public Vector2 knockback = Vector2.zero();
	public Vector2 oldPosition = Vector2.zero();
	protected boolean moving = false;
	protected Map map;
	protected Game game;
	protected ColorShader shader = new ColorShader();
	public int health = 3;
	public int flip = 0;
	public int entityId;
	
	public Entity(){
		
	}
	
	public Entity(Game game){
		this.game = game;
		this.map = game.getMap();
	}
	
	public boolean isMoving(){
		return velocity.x != 0 || velocity.y != 0 || knockback.x != 0 || knockback.y != 0;
	}
	
	public void initAnimations(Sprite[][] sprites){
		int xLen = sprites.length; // debug value for player 2
		int yLen = sprites[0].length; // debug value for player 4
		
		AnimationClip[] clips = new AnimationClip[yLen];
		
		for(int y=0;y<yLen;y++){
			clips[y] = new AnimationClip(xLen);
			for(int x=0;x<xLen;x++){
				clips[y].addFrame(sprites[x][y]);
			}
		}
		this.animation = new Animation(clips, 8);
		this.animation.play(animation.getClip(direction));
	}
	
	public void setDirection(boolean leftCondition, boolean rightCondition, boolean upCondition, boolean downCondition){
		if(leftCondition)
			direction = Entity.LEFT;
		if(rightCondition)
			direction = Entity.RIGHT;
		if(upCondition)
			direction = Entity.UP;
		if(downCondition)
			direction = Entity.DOWN;
	}
	
	public boolean collision(int x, int y, int xo, int yo){
		
		int x0 = this.position.x;
		int y0 = this.position.y;
		int w0 = this.collider.width-4;
		int h0 = this.collider.height-4;
		
		int x1 = x;
		int y1 = y;
		
		return(((x0-w0) < (x1)) && ((x0+w0) > (x1)) && ((y0-h0) < (y1)) && ((y0+h0) > (y1)));
		
		/*int x0 = this.position.x;
		int y0 = this.position.y;
	
		int xOffset = Screen.SPRITE_SIZE/4;
		int yOffset = Screen.SPRITE_SIZE/4;
		
		int x1 = x-16;
		int y1 = y-16;
		int w0 = collider.width;
		int h0 = collider.height;
		int w1 = Screen.SPRITE_SIZE;
		int h1 = Screen.SPRITE_SIZE;
		
		if (x0 < x1 + w1 && x0 + w0 > x1 && y0 < y1 + h1 && h0 + y0 > y1)
			return true;
		
		return false;*/
	}
	
	public Shader getActiveShader(){
		return shader;
	}
	
	public void update(){
		if(health <= 0){
			kill();
		}
	}
	
	public void render(Screen screen){
	}
	
	public void onBoxCollision(Entity e) {
	}
	
	public void onTileCollision(Tile t) {
	}
	
	public void playDirectionalAnimations(int xNew, int yNew, int xOld, int yOld){
		int xDir = xNew - xOld;
		int yDir = yNew - yOld;
		if(xDir < 0){
			this.flip = Entity.LEFT;
			this.direction = Entity.LEFT;
			//this.animation.play(this.animation.getClip(1));
		}
		
		if(xDir > 0 ){
			this.flip = Entity.RIGHT;
			this.direction = Entity.RIGHT;
			//this.animation.play(this.animation.getClip(1));
		}
		
		if(yDir > 0){
			//this.animation.play(this.animation.getClip(0));
			this.direction = Entity.DOWN;
		}
		
		if(yDir < 0){
			//this.animation.play(this.animation.getClip(2));
			this.direction = Entity.UP;
		}
	}
	
	public void kill(){
		map.remove(this);
	}
	
	public void setPosition(Vector2 position) {
		this.position = position;
		this.collider.position = position;
	}
	
	public boolean hasPositionChanged(){
		if (oldPosition.x != position.x || oldPosition.y != position.y){
			oldPosition = position.duplicate();
			return true;
		}
		return false;
	}
}