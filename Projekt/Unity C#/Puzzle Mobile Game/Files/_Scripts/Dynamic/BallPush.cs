using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BallPush : MonoBehaviour {

	private Rigidbody rb;

	public float force;

	private Vector3 startPos;

	void Start () {
		rb = GetComponent<Rigidbody>();
		startPos = transform.position;
	}

	void Update () {

		if(Input.GetKey(KeyCode.R)){
			transform.position = startPos;
		}
	}

	void OnCollisionEnter(Collision col){
		if(col.gameObject.tag == "Player"){
			rb.AddForce(col.gameObject.transform.forward * force);
		}
	}
}
