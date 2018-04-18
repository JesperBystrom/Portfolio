package states;

public class Transition {
	private State connectedTo;
	private boolean bool;
	
	public Transition(State connectedTo, boolean bool){
		this.connectedTo = connectedTo;
		this.bool = bool;
	}
	
	public State transition(){
		if(getBool())
			return connectedTo;
		return State.EMPTY;
	}
	
	public boolean isConnectedTo(State connectedTo){
		return this.connectedTo == connectedTo;
	}
	
	public void setBool(boolean bool){
		this.bool = bool;
	}
	
	public boolean getBool(){
		return bool;
	}
}
