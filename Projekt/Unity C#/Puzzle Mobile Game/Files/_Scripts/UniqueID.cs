using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class UniqueID : MonoBehaviour {

	public int ID;
	private SaveLoad saveLoad;
	public bool dontPutID;

	void Start () {
		saveLoad = GameObject.Find("GameManager").GetComponent<SaveLoad>();

		if(dontPutID != true){
			ID = saveLoad.numberOfInstances-1;
			dontPutID = true;
		}
	}
}
