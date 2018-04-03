using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Player_Pickup : MonoBehaviour {
	
	public float score;

	void Start () {
		
	}

	void Update () {
		
	}

	void OnCollisionEnter(Collision col){
		if(col.gameObject.tag == "Pickup"){
			score++;
			Destroy(col.gameObject);
			GetComponent<UI_Score>().UpdateUI();
		}
	}
}
