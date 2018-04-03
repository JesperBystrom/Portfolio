using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PlaceOnTop : MonoBehaviour {

	void Start () {
		
	}

	void Update () {
		GetComponent<SpriteRenderer>().sortingOrder = -99999; //UI layer;
	}
}
