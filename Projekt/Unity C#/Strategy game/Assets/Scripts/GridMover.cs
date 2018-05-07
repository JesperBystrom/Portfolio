using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GridMover : MonoBehaviour {

	public GameObject gridObject;
	private Vector3 pos = Vector3.zero;
	private bool moved = false;

	void Start () {
		
	}

	void Update () {
		moved = false;
		GameObject o = Mouse.getInstance().hoverObject();
		if(o != null){
			if(o.CompareTag("Grid")){
				if(pos != gridObject.transform.position){
					gridObject.transform.position = new Vector3(o.transform.position.x, gridObject.transform.position.y, o.transform.position.z);
					moved = true;
					Debug.Log("moved");
				}
				pos = o.transform.position;
			}
		}
	}

	public bool hasMoved(){
		return moved;
	}
}
