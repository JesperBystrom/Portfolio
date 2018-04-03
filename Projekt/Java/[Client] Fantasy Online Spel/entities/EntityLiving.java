package entities;

import main.BoxCollider;
import main.Game;
import main.Screen;
import particles.TextParticle;
import shaders.Shader;
import tools.Tools;
import tools.Vector2;
import world.Map;

public class EntityLiving extends Entity {
	
	public float hitTimer;
	
	public EntityLiving(){
		super();
	}
	
	public EntityLiving(Game game) {
		super(game);
	}

	@Override
	public void update() {
		super.update();
		
		switch(direction){
		case Entity.RIGHT:
			this.animation.play(animation.getClip(1));
			direction = Entity.RIGHT;
			flip = Entity.RIGHT;
			break;
		case Entity.LEFT:
			this.animation.play(animation.getClip(1));
			direction = Entity.LEFT;
			flip = Entity.LEFT;
			break;
		case Entity.UP:
			this.animation.play(animation.getClip(2));
			direction = Entity.UP;
			break;
		case Entity.DOWN:
			this.animation.play(animation.getClip(0));
			direction = Entity.DOWN;
			break;
		}
		setDirection(velocity.x < 0, velocity.x > 0, velocity.y < 0, velocity.y > 0);
		
		if(!isMoving())
			this.animation.stop();
		else
			move(velocity.x, velocity.y);
		
		if(hitTimer > 0){
			hitTimer -= 1;
			velocity.x = 0;
			velocity.y = 0;
		} else {
			knockback.x = 0;
			knockback.y = 0;
			shader.setEffect(Shader.EFFECT_NONE);
		}
		moving = false;
	}

	@Override
	public void render(Screen screen) {
		screen.render(position.x, position.y, animation.getCurrentClip().getCurrentFrame().tile, shader, flip, 0);
	}
	
	public void move(float xv, float yv){
		//Tile[] tiles = Game.getInstance().getMap().getNearTiles(this, 96);
		moving = true;
		if(knockback.x != 0)
			moveReal(knockback.x, 0);
		else
			moveReal(xv, 0);
		if(knockback.y != 0)
			moveReal(0, knockback.y);
		else
			moveReal(0, yv);
	}
	
	protected void moveReal(float xv, float yv){
		int xOld = position.x;
		int yOld = position.y;
		this.position.x += xv;
		this.position.y += yv;
		//if(this instanceof EntityPlayer) return;
		
		if(map.checkCollisionWithNearTiles(this) != null){
			position.x = xOld;
			position.y = yOld;
		}
		if(map.checkCollisionWithNearEntities(this) != null){
			position.x = xOld;
			position.y = yOld;
		}
	}
	
	public void dealDamage(EntityLiving e, int damage){
		if(e.hitTimer > 0) return;
		Vector2 dir = Tools.getRealDirection(this.direction);
		//System.out.println("I hit another entity: " + e.getClass().getName());
		e.hurt(damage, dir);
	}
	public void hurt(int damage, Vector2 dir){
		System.out.println("HIT_TIMER: " + hitTimer);
		if(hitTimer > 0 ) return;
		shader.effect = Shader.EFFECT_REPLACEMENT;
		hitTimer = 3;
		map.addParticle(new TextParticle(position, 30, ""+damage));
		knockback(dir);
	}
	public void hurt(int damage, int direction){
		switch(direction){
		case Entity.UP:
			hurt(damage, new Vector2(0, -1));
			break;
		case Entity.RIGHT:
			hurt(damage, new Vector2(1, 0));
			break;
		case Entity.DOWN:
			hurt(damage, new Vector2(0, 1));
			break;
		case Entity.LEFT:
			hurt(damage, new Vector2(-1, 0));
			break;
		}
	}
	
	public void knockback(Vector2 dir){
		knockback.x = (int) (dir.x * 8);
		knockback.y = (int) (dir.y * 8);
	}
	
	public boolean attack(Entity e, int range){
		if(e == this) return false;
		boolean isColliding = false;
		int size = range;
		if(direction == Entity.RIGHT)  if(e.collider.intersect(new BoxCollider(null, new Vector2(position.x+range, position.y), size, size))) isColliding = true;
		if(direction == Entity.LEFT) if (e.collider.intersect(new BoxCollider(null, new Vector2(position.x-range, position.y), size, size))) isColliding = true;
		if(direction == Entity.UP) if (e.collider.intersect(new BoxCollider(null, new Vector2(position.x, position.y-range), size, size))) isColliding = true;
		if(direction == Entity.DOWN) if (e.collider.intersect(new BoxCollider(null, new Vector2(position.x, position.y+range), size, size))) isColliding = true;
		return isColliding;
	}
	public void onAction(){
		animation.getCurrentClip().gotoNextFrame();
	}
}
