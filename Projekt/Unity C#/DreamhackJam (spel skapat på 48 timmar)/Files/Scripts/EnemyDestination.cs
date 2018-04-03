using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemyDestination : MonoBehaviour {

	public GameObject collidingWith;

	void OnCollisionEnter2D(Collision2D col){
		if(col.gameObject != GameObject.FindWithTag("Enemy")){
			//collidingWith = col.gameObject;
		}
	}
}
