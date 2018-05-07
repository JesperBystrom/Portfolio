package main;

enum TimerState {
	START, PAUSED
}

public class Timer {

	private float time;
	private float startTime;
	private TimerState state;
	private Delegate delegate;
	private int speed = 1;
	
	public Timer(float time){
		this.time = time;
		this.startTime = time;
	}
	
	public void update(Game game){
		if(!getStarted() || game.getTicks() % (20 - (speed << 1)) != 0) return;
		if(time <= 0) return;
		time -= 1;
		System.out.println(time);
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

	public void setSpeed(int speed) {
		this.speed = speed;
	}
}
