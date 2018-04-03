using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DoorFloat : MonoBehaviour {

	private float y = 0f;
	public float wave;
	public float spd;

	void Start () {
		
	}

	void Update () {
		y += spd;
		transform.position = new Vector3(transform.position.x, transform.position.y + (Mathf.Sin(y)/2) * wave, transform.position.z);
	}
}
