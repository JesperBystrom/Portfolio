using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class UI_Spin : MonoBehaviour {

	float y = 0f;
	float x = 0f;
	float z = 0;
	public float rotationSpeed;

	void Start () {
		
	}

	void Update () {
		y += rotationSpeed;
		transform.rotation = Quaternion.Euler(x,y,z);
	}
}
