package inputs;

public class Key {

	public boolean pressed = false;
	private int time;
	private boolean flag = false;
	private int keyCode;
	
	public Key(int keyCode) {
		this.keyCode = keyCode;
	}
	public void toggle(boolean bool) {
		if(time > 0) return;
		pressed = bool;
	}
	public boolean isTapped(){
		if(flag){
			flag = false;
			release();
		}
		boolean p = pressed;
		if(p)
			flag = p;
		return p;
	}
	public void block(int time){
		this.time = time;
		pressed = false;
	}
	
	public boolean isPressed(){
		if(time > 0){
			time -= 1;
			if(time <= 0){
				time = 0;
			}
		}
		boolean p = pressed && (time<=0);
		return p;
	}
	
	public void release(){
		pressed = false;
	}
}
