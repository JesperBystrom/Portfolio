package entities;

public enum EntityState {
	IDLING, MOVING, JUMPING, TURNING, GROWING, DYING, INVINCIBLE, WIN;
	
	private boolean lockedState;
	
	public boolean getLocked(){
		return lockedState;
	}

	public void lock() {
		lockedState = true;
	}
	
	public void unlock() {
		lockedState = false;
	}
}
