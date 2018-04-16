package main;

enum TimerState {
	START, PAUSED
}

public class Timer {

	private float time;
	private float startTime;
	private TimerState state;
	private Delegate delegate;
	
	public Timer(float time){
		this.time = time;
		this.startTime = time;
	}
	
	public void update(Game game){
		if(!getStarted()) return;
		if(game.getTicks() % 10 == 0){
			if(getFinished()){
				stop();
			}
			time -= 1;
		}
	}
	
	public void start(){
		state = TimerState.START;
	}
	
	public void pause(){
		state = TimerState.PAUSED;
	}
	
	public void stop(){
		pause();
		time = startTime;
	}
	
	public boolean getStarted(){
		return state == TimerState.START;
	}
	
	public boolean getFinished(){
		return time <= 0;
	}
	
	public float getTime(){
		return time;
	}
}
