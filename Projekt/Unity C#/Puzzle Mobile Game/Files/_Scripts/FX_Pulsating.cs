using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class FX_Pulsating : MonoBehaviour {

	private float x = 0f;
	private Coroutine routine;

	void Start () {
		routine = StartCoroutine(increase());
	}

	void Update () {

		x = 0;

		Debug.Log("X: " + x);

		transform.localScale = new Vector3(transform.lossyScale.x + x ,transform.lossyScale.y,transform.lossyScale.z);
	}

	private IEnumerator increase(){
		while(x <= 1f){
			x += 0.01f;
			yield return null;
		}
		StopCoroutine(routine);
		routine = StartCoroutine(decrease());
	}

	private IEnumerator decrease(){
		while(x > 0f){
			x -= 0.1f;
			yield return null;
		}
		StopCoroutine(routine);
		StartCoroutine(increase());
	}
}
