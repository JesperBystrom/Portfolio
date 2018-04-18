package states;

import java.util.ArrayList;

public class State {

	public static final State EMPTY = new State("empty");
	private ArrayList<Transition> transitions = new ArrayList<Transition>();
	private String tag;
	
	public State(String tag){
		this.tag = tag;
	}
	
	public void addTransitions(Transition... transitions){
		for(int i=0;i<transitions.length;i++){
			this.transitions.add(transitions[i]);
		}
	}

	public void addTransition(Transition transition){
		transitions.add(transition);
	}

	public String getTag() {
		return tag;
	}
	
	public ArrayList<Transition> getTransitions() {
		return transitions;
	}
}
