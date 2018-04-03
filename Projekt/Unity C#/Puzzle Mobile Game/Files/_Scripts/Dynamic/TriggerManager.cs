using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TriggerManager : MonoBehaviour {

	public bool isTriggered = false;

	void Start () {
		
	}

	void Update () {
		
	}

	void OnTriggerStay(Collider col){
		isTriggered = true;
		Debug.Log("intrigger");
	}

	void OnTriggerExit(Collider col){
		isTriggered = false;
	}

}
