package animation;

import main.Game;
import main.Sprite;

public class Animation {
	public Sprite[] clip;
	private int frame = 0;
	private float speed;
	private float timer;
	
	public Animation(Sprite... clip){
		this.clip = clip;
		setSpeed(1);
	}
	public Animation(float speed, Sprite... clip){
		this.speed = speed;
		this.clip = clip;
	}
	
	public void update(){
		timer += speed*1.2f;
		if(timer >= 10){
			nextFrame();
			timer = 0;
		}
	}
	
	public void nextFrame(){
		if(frame < clip.length-1)
			frame++;
		else
			frame = 0;
	}
	
	public Sprite getCurrentFrame(){
		return clip[frame];
	}
	
	public void setSpeed(float speed){
		this.speed = speed;
	}

	public void setCurrentFrame(int frame) {
		this.frame = frame;
	}
}
