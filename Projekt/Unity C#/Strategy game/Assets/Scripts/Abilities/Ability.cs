using System.Collections;
using System.Collections.Generic;
using UnityEngine;

[CreateAssetMenu(fileName = "New Ability", menuName = "Ability")]
public class Ability : ScriptableObject {
	public string name;
	public int damage;
	public int range;
	public int tilesToHurt;
	public GameObject particle;
	public bool neutral;

	private int useTimer = 0;
	private int territoryIndex = 0;
	private Territory[] territories;

	public void execute(Territory[] territories){
		this.territories = territories;
		useTimer = 60;
		Debug.Log("wew");
	}

	private void execute(Territory territory){
		GameObject o = Instantiate(particle);
		o.transform.position = territory.gameObject.transform.position;
		Debug.Log("executed");
	}

	public void update(){
		if(useTimer > 0 && territories != null){
			Debug.Log(territories.Length + ", " + territoryIndex);
			execute(territories[territoryIndex]);
			territoryIndex++;
			if(territoryIndex >= territories.Length){
				territoryIndex = 0;
			} else {
				useTimer = 60;
			}
			useTimer--;
			Debug.Log(useTimer);
		}
	}
}
