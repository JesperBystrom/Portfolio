using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[CreateAssetMenu(fileName = "New Unit", menuName = "Unit")]
public class Unit : ScriptableObject {
	public int damage;
	public UnitComponent prefab;
	public Ability[] abilities;
	private UnitComponent unit;
	private Territory territory;
	private Ability ability;

	public void place(Territory territory){
		this.territory = territory;
		GameObject o = Instantiate(prefab.gameObject);
		this.unit = o.GetComponent<UnitComponent>();
		updatePosition();
	}

	public void select(){

	}

	public void move(Territory territory){
		this.territory.removeUnit(this);
		territory.setUnit(this);
		this.territory = territory;
		updatePosition();
	}

	public void update(){
		if(ability != null)
			ability.update();
	}

	public void updatePosition(){
		Vector3 pos = territory.gameObject.transform.position;
		unit.gameObject.transform.position = new Vector3(pos.x, pos.y + territory.gameObject.transform.lossyScale.y, pos.z);
	}

	public Renderer getRenderer(){
		return unit.getRenderer();
	}

	public Renderer[] getRenderers(){
		return unit.getRenderers();
	}

	public GameObject getGameObject(){
		if(unit != null)
			return unit.gameObject;
		return null;
	}

	public Unit createNewInstance(){
		return Instantiate(this);
	}

	public void prepareAbility(Ability ability){
		this.ability = ability;
	}

	public void attack(Territory territory){

	}

	public void executeAbility(Territory territory){
		if(canAttack()){
			attack(territory);
		} else if(canMove()){
			move(territory);
		}
		this.ability = null;
	}

	public bool hasPreparedAbility(){
		return this.ability != null;
	}

	public Territory getTerritory(){
		return territory;
	}

	public Ability getPreparedAbility(){
		return this.ability;
	}

	public bool canAttack(){
		if(this.ability != null)
			return !this.ability.neutral;
		return false;
	}

	public bool canMove(){
		if(this.ability != null)
			return this.ability.neutral;
		return false;
	}
}
