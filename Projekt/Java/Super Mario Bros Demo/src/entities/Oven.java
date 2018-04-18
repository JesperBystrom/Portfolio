package entities;

import states.State;
import states.StateManager;
import states.Transition;

/*
 * 
 * Prototype of State system for Entities
 * 
 */

public class Oven {

	private StateManager stateManager = new StateManager();
	private boolean buttonState = false;
	private int temperature = 200;
	
	public Oven(){
		State off = stateManager.createState("off");
		State heating = stateManager.createState("heating");
		State idling = stateManager.createState("idling");
		
		off.addTransition(new Transition(stateManager.getState("heating"), buttonState == false)); //if button is pressed && it was false
		heating.addTransitions(
				new Transition(stateManager.getState("off"), buttonState == true), //if button is pressed && it is true
				new Transition(stateManager.getState("idling"), temperature >= 400)
		);
		idling.addTransitions(
				new Transition(stateManager.getState("off"), buttonState == true), //if button is pressed && it is true
				new Transition(stateManager.getState("idling"), temperature < 400)
		);
		stateManager.setCurrentState(off);
		pressButton();
		
		if(temperature >= 400)
			stateManager.transition(stateManager.getState("idling"));
		
		System.out.println(stateManager.getCurrentState().getTag());
		
	}
	
	public void pressButton(){
		this.buttonState ^= true;
		stateManager.transition(stateManager.getState("heating"));
	}
	
}
