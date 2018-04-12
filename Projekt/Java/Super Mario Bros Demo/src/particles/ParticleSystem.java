package particles;


import animation.Animation;
import main.Level;
import main.RenderHandler;
import main.Sprite;
import main.Vector2f;
import main.Level;

public class ParticleSystem {

	protected Vector2f position;
	protected Sprite sprite;
	protected float startLife;
	protected float life;
	protected float lifeDecrease;
	protected Animation animation;
	
	public ParticleSystem(Vector2f position, float life, float lifeDecrease, Sprite sprite){
		this.position = new Vector2f(position.x, position.y);
		this.sprite = sprite;
		this.life = life;
		this.startLife = life;
		this.lifeDecrease = lifeDecrease;
	}
	public ParticleSystem(Vector2f position, float life, float lifeDecrease, Sprite... sprite){
		this.position = new Vector2f(position.x, position.y);
		this.life = life;
		this.startLife = life;
		this.lifeDecrease = lifeDecrease;
		animation = new Animation(3, sprite);
	}
	
	public void emit(){
		this.life = startLife;
	}
	
	public boolean update(Level level){
		if(getLife())
			life -= lifeDecrease;
		else
			level.removeParticle(this);
		if(animation != null)
			animation.update();
		
		return getLife();
	}
	
	public void render(RenderHandler render){
		if(getLife()){
			if(animation != null){
				sprite = animation.getCurrentFrame();
			}
			render.render(position, sprite, Sprite.FLIP_NONE);
		}
	}
	
	public boolean getLife(){
		return life > 0;
	}
}
