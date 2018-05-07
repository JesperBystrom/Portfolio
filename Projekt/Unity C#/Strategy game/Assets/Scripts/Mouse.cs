using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Mouse {

	private static Mouse instance = new Mouse();
	private GameObject oldObject;

	public GameObject hoverObject(){
		Ray ray = Camera.main.ScreenPointToRay(Input.mousePosition);
		RaycastHit hit;
		if(Physics.Raycast(ray, out hit, 10000)){
			return hit.collider.gameObject;
		}
		return null;
	}

	public bool compareOldNode(GameObject o){
		if(this.oldObject != o){
			this.oldObject = o;
			return true;
		}
		return false;
	}

	public static Mouse getInstance(){
		if(instance == null)
			instance = new Mouse();
		return instance;
	}
}
