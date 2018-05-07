using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class UnitManager : MonoBehaviour{
	public CharacterPage characterPage;
	public Unit warrior;
	private Unit selected;

	public void selectUnit(Unit unit){
		this.selected = unit;
		characterPage.open(unit);
	}

	public Unit getSelectedUnit(){
		return selected;
	}

	public void unSelectUnit(){
		this.selected = null;
		characterPage.close();
	}

	public bool hasSelectedUnit(){
		return this.selected != null;
	}
}
