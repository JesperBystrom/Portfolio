package animations;

import java.util.ArrayList;

import main.Game;

public class Animation {
	
	private AnimationClip[] clips;
	private AnimationClip currentClip;
	private int speed = 1;
	
	public Animation(AnimationClip[] clips, int speed){
		this.clips = clips;
		this.speed = speed;
		Game.animationThread.addAnimation(this);
	}
	public void play(AnimationClip clip){
		currentClip = clip;
		clip.play();
	} 
	public void stop(){
		getCurrentClip().stop();
	}
	public AnimationClip getCurrentClip(){
		return currentClip;
	}
	public void update(AnimationThread thread) {
		if(currentClip != null)
			currentClip.update(speed, thread);
	}
	public AnimationClip getClip(int index){
		return clips[index];
	}
	public boolean getFinished() {
		return(currentClip.equals(clips[clips.length-1]) && currentClip.getCurrentFrame() == currentClip.frames[currentClip.frames.length-1] && currentClip.goingToNextFrame());
	}
}

//private ArrayList<Sprite> clip = new ArrayList<Sprite>();
/*public void addFrame(Sprite sprite){
	clip.add(sprite);
}*/
/*public void createClip(Sprite standard, int xStart, int xEnd, int y){
	int len = Math.abs(xStart - xEnd);
	for(int i=0;i<len;i++){
		//standard.positionInSheet.x += i;
		this.clip.add(standardnew Sprite(xStart+i, y, standard.size.x, standard.size.y, standard.scale.x, standard.scale.y, standard.color));
	}
}*/

/*public AnimationClip createClip(int frames){
	return new AnimationClip(frames);
}*/
/*
public Animation(Sprite standard, int stopFrame, int xStart, int xEnd, int y, int speed){
	play();
	createClip(standard, xStart, xEnd, y);
	Game.animationThread.addAnimation(this);
	this.speed = speed;
}*/
