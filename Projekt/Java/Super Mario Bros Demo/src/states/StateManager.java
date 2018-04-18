package states;

import java.util.ArrayList;

public class StateManager {

	private ArrayList<State> states = new ArrayList<State>();
	private State currentState;
	
	public void transition(State state){
		ArrayList<Transition> transitions = currentState.getTransitions();
		transitions.forEach(t -> {
			State s = t.transition();
			if(s != State.EMPTY){
				currentState = s;
			}
		});
	}
	
	public void addState(State state){
		states.add(state);
	}

	public State createState(String tag){
		State state = new State(tag);
		addState(state);
		return state;
	}
	
	public State getState(String tag) {
		for(int i=0;i<states.size();i++){
			if(states.get(i).getTag().equals(tag))
				return states.get(i);
		}
		return State.EMPTY;
	}

	public void setCurrentState(State state) {
		this.currentState = state;
	}

	public State getCurrentState() {
		return currentState;
	}
}
