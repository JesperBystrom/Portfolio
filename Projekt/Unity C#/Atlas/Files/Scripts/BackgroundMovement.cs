using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BackgroundMovement : MonoBehaviour {

	public float hspeed = 0.1f;
	public float vspeed = 0f;

	void Start () {
	}

	void Update () {
		this.transform.position += new Vector3(hspeed, vspeed, 0);
	}
}
