package animations;

import main.Game;
import main.Sprite;

public class AnimationClip {
	
	public Sprite[] frames;
	private int currentFrame = 0;
	private int timer = 0;
	private boolean playing = false;
	private boolean nextFrame = false;
	
	public AnimationClip(int len){
		this.frames = new Sprite[len];
	}
	public Sprite getCurrentFrame(){
		if(currentFrame < frames.length)
			return frames[this.currentFrame];
		return null;
	}
	public void update(int speed, AnimationThread thread) {
		if(!isPlaying()) return;
		timer += speed;
		nextFrame = false;
		if(timer >= 60){
			gotoNextFrame();
			timer = 0;
		}
		if(currentFrame >= this.frames.length){
			currentFrame = 0;
		}
		
	}
	public void play() {
		playing = true;
	}
	public void stop() {
		playing = false;
	}
	public void addFrame(Sprite sprite) {
		for(int i=0;i<frames.length;i++){
			if(frames[i] == null){
				frames[i] = sprite;
				break;
			}
		}
	}
	public boolean isPlaying(){
		return playing;
	}
	public void gotoNextFrame() {
		currentFrame++;
		if(currentFrame >= this.frames.length){
			currentFrame = 0;
		}
	}
	public boolean goingToNextFrame(){
		return timer >= 30;
	}
}
