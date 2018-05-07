using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Map : MonoBehaviour {

	public UnitManager unitManager;
	private Territory[] territories;
	private ArrayList markedTerritories = new ArrayList();
	private Astar pathFinder;

	private void Start () {
		territories = new Territory[transform.childCount];
		for(int i = 0; i < territories.Length; i++){
			territories[i] = new Territory(Team.NEUTRAL, transform.GetChild(i).gameObject);
		}

		try {
			getTerritory(10, 9).capture(Team.RED);
			getTerritory(10, 8).capture(Team.RED);
			getTerritory(10, 7).capture(Team.RED);
			getTerritory(0, 0).capture(Team.BLUE);

			//getTerritory(-10, -9).setUnit(Instantiate(unitManager.warrior));
			//getTerritory(-5, -5).setUnit(Instantiate(unitManager.warrior));

		} catch(NullReferenceException e){
			Debug.LogError("Territory not found" + e.Message);
		}

		pathFinder = new Astar(this);
		//pathFinder.findPath(pathFinder.getNode(0,0,0), pathFinder.getNode(0, 9, 0));

	}

	private void Update(){

		/*if(Input.GetMouseButtonDown(0)){
			clear();
			GameObject go = Mouse.getInstance().hoverObject();
			if(go != null){
				Debug.Log((int)go.transform.position.x + ", " + (int)go.transform.position.z);
			}
		}*/

		if(unitManager.hasSelectedUnit()){
			unitManager.getSelectedUnit().update();
		}

		if(Input.GetMouseButtonDown(0)){		
			GameObject o = Mouse.getInstance().hoverObject();
			if(o == null) return;
			Territory territory = getTerritory((int)o.transform.position.x, (int)o.transform.position.z);
			if(territory != null){

				//Move
				if(territory.isMarked() && unitManager.hasSelectedUnit()){



					/*
					if(unitManager.getSelectedUnit().canAttack()){
						unitManager.getSelectedUnit().attack(territory);
					} else {
						unitManager.getSelectedUnit().move(territory);
					}*/
					/*unitManager.getSelectedUnit().executeAbility(territory);
					unitManager.unSelectUnit();
					clearMarkedTerritories();
					return;*/
				}
					
				if(territory.hasUnit()){
					unitManager.selectUnit(territory.getUnit());
					/*if(unitManager.getSelectedUnit().hasPreparedAbility()){
						Territory[] nearby = territory.getNearbyTerritories(this, 2);	
						foreach(Territory t in nearby){
							markedTerritories.Add(t);
						}
					}*/
				}
			}
		}

		if(unitManager.hasSelectedUnit() && unitManager.getSelectedUnit().hasPreparedAbility()){
			clearMarkedTerritories();

			Territory territory = unitManager.getSelectedUnit().getTerritory();
			Territory[] nearby = territory.getNearbyTerritories(this, unitManager.getSelectedUnit().getPreparedAbility().range);
			foreach(Territory t in nearby){
				markedTerritories.Add(t);
			}

			if(unitManager.getSelectedUnit().getPreparedAbility().tilesToHurt > 0){
				GameObject mouseNode = Mouse.getInstance().hoverObject();

				Territory[] pathTerritories = null;
				if(mouseNode != null && !Mouse.getInstance().compareOldNode(mouseNode)){
					clear();
					Vector3 currentTerritory = unitManager.getSelectedUnit().getTerritory().gameObject.transform.position;
					pathFinder.findPath(pathFinder.getNode(currentTerritory.x,currentTerritory.z,0), pathFinder.getNode(mouseNode.transform.position.x, mouseNode.transform.position.z, 0));
					Node[] path = pathFinder.getPath();
					pathTerritories = new Territory[path.Length];
					for(int i=0;i<pathTerritories.Length;i++){
						pathTerritories[i] = getTerritory((int)path[i].pos.x, (int)path[i].pos.y);
					}
					foreach(Territory t in pathTerritories){
						if(Vector3.Distance(t.gameObject.transform.position, unitManager.getSelectedUnit().getTerritory().gameObject.transform.position) < unitManager.getSelectedUnit().getPreparedAbility().range+1)
						t.mark(Territory.MarkType.ATTACK);
					}
				}
				if(Input.GetMouseButtonDown(0) && pathTerritories != null){
					unitManager.getSelectedUnit().getPreparedAbility().execute(pathTerritories);
				}
			}
				
			foreach(Territory t in markedTerritories){
				if(!t.isMarked())
					t.mark(Territory.MarkType.MOVE);
			}
		}


		//Temporary spawning
		if(Input.GetMouseButtonDown(1)){
			GameObject o = Mouse.getInstance().hoverObject();
			if(o == null) return;
			Territory territory = getTerritory((int)o.transform.position.x, (int)o.transform.position.z);
			if(territory != null){
				if(!territory.hasUnit()){
					territory.setUnit(unitManager.warrior.createNewInstance());
				} 
			}
		}
	}

	public Territory getTerritory(int x, int z){
		foreach(Territory t in territories){
			if(t.find(x,z)){
				return t;
			}
		}
		return null;
	}

	private void selectUnit(Unit unit){

	}

	private void clearMarkedTerritories(){
		foreach(Territory t in markedTerritories){
			t.unmark();
		}
		markedTerritories.Clear();		
	}

	public void clear(){
		foreach(Territory t in territories){
			t.unmark();
		}
	}

	public Territory[] getTerritories(){
		return territories;
	}
}
