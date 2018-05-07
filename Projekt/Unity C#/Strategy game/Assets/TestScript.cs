using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TestScript : MonoBehaviour {

	// Use this for initialization
	void Start () {
		
	}

	// Update is called once per frame
	private float x = 0;
	void Update () {
		float s = Mathf.Sin(x+0.01f) / 10;
		x += 0.1f;
		Debug.Log(s);
		transform.localScale = new Vector3(1 + s, 1 + s, 1 + s);
	}
}
