package particles;

import animations.Animation;
import animations.AnimationClip;
import entities.Entity;
import main.Game;
import main.Screen;
import main.Sprite;
import tools.Vector2;

public class ParticleSystem {
	public Vector2 position;
	public Sprite sprite;
	public float life;
	protected float startLife;
	
	private AnimationClip clip;
	private Animation animation;
	private boolean killOnAnimationFinish = false;
	
	public ParticleSystem(Vector2 position, Sprite sprite, float lifeInSeconds){
		this.sprite = sprite;
		this.life = lifeInSeconds;
		startLife = lifeInSeconds;
		this.position = position;
	}
	public ParticleSystem(Game game, Vector2 position, float lifeInSeconds, int clipLength, boolean killOnAnimationFinish, Sprite[] frames){
		this.sprite = new Sprite(255);
		this.life = lifeInSeconds;
		startLife = lifeInSeconds;
		this.position = position;
		
		this.clip = new AnimationClip(clipLength);
		this.animation = new Animation(new AnimationClip[]{clip}, 10);
		for(int i=0;i<frames.length;i++){
			clip.addFrame(frames[i]);
		}
		this.killOnAnimationFinish = killOnAnimationFinish;
		game.animationThread.addAnimation(animation);
	}
	
	public ParticleSystem(){
		
	}
	
	public void emit(){
		this.life = startLife;
	}
	
	public void update(Game game){
		if(animation != null){
			animation.play(clip);
			this.sprite.tile = clip.getCurrentFrame().tile;
			if((killOnAnimationFinish && animation.getFinished()))
				game.getMap().removeParticle(this);
		}
		
		if(isAlive())
			life -= 1;
		else
			game.getMap().removeParticle(this);
	}
	public void render(Screen screen){
		if(isAlive()){
			screen.renderMarker(position.x, position.y, 0, 0, sprite.tile, null, 0);
		}	
	}
	public boolean isAlive(){
		return this.life > 0;
	}
}
