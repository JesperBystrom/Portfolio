using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CharacterPage : Page {
	public AbilityButtonHandler abilityButtonHandler;
	public GameObject potrait;
	public GameObject health;
	public GameObject exp;
	public GameObject[] abilities;

	private Unit unit;

	public void open(Unit unit){
		base.open();
		this.unit = unit;
		abilities = new GameObject[unit.abilities.Length];
		abilityButtonHandler.onOpen();
		Debug.Log("opened");
	}

	public Unit getUnit(){
		return unit;
	}
}
