using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Territory {
	public GameObject gameObject;
	private Renderer renderer;
	private Team team;
	private Unit unit;
	private bool marked = false;

	public Territory(Team owner, GameObject node){
		this.team = owner;
		this.gameObject = node;
		this.renderer = node.GetComponent<Renderer>();
		ColorUtility.setColor(renderer, team.GetColor());
	}

	public class MarkType {
		public static readonly int MOVE = 2;
		public static readonly int ATTACK = 1; 
	}

	public void capture(Team team){
		this.team = team;
		ColorUtility.setColor(renderer, team.GetColor());
	}

	public bool find(int x, int z){
		return gameObject.transform.position.x == x && gameObject.transform.position.z == z;
	}

	public void mark(int type){
		float r;
		r = team.GetColor().r/type;
		float g = team.GetColor().g/2;
		float b = team.GetColor().b/2;
		ColorUtility.setColor(renderer, new Color(r,g,b));
		marked = true;
	}

	public void unmark(){
		ColorUtility.setColor(renderer, team.GetColor());
		marked = false;
	}

	public bool hasUnit(){
		return unit != null;
	}

	public void setUnit(Unit unit){
		this.unit = unit;
		if(this.unit.getGameObject() == null)
			this.unit.place(this);
	}

	public void removeUnit(Unit unit){
		this.unit = null;
	}

	public Territory[] getNearbyTerritories(Map map, int radius){
		ArrayList nearby = new ArrayList();
		for(int x=(int)gameObject.transform.position.x-radius;x<(int)gameObject.transform.position.x+(radius+1);x++){
			for(int z=(int)gameObject.transform.position.z-radius;z<(int)gameObject.transform.position.z+(radius+1);z++){
				Territory t = map.getTerritory(x,z);
				if(t != null)
					nearby.Add(t);
			}
		}
		return (Territory[])nearby.ToArray(typeof(Territory));
	}

	public Unit getUnit(){
		return unit;
	}

	public bool isMarked(){
		return marked;
	}
}
