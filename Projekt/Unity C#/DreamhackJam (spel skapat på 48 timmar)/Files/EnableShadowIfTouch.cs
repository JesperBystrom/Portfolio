using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnableShadowIfTouch : MonoBehaviour {

	Renderer renderer;
	bool coroutinestarted = false;

	void Start(){
		renderer = GetComponent<Renderer>();
	}

	void OnTriggerEnter2D(Collider2D col){
		if(col.gameObject.CompareTag("Player") && !coroutinestarted){
			//renderer.enabled = false;
			StartCoroutine(fade(1));
		}
	}
	void OnTriggerExit2D(Collider2D col){
		if(col.CompareTag("Player") && !coroutinestarted){
			//renderer.enabled = true;
			StartCoroutine(fade(0));
		}
	}

	IEnumerator fade(int dir){
		Debug.Log("wew2");
		coroutinestarted = true;
		switch(dir){
		case 0: //in
			float a = renderer.material.color.a;
			while(a < 0.7f){ 
				a += 0.1f;
				Debug.Log("A: " + a);
				renderer.material.color = new Color(renderer.material.color.r, renderer.material.color.g, renderer.material.color.b, a);
				yield return null; 
			}
			break;
		case 1: //out
			float aa = renderer.material.color.a;
			while(aa > 0){ 
				aa -= 0.1f; 
				renderer.material.color = new Color(renderer.material.color.r, renderer.material.color.g, renderer.material.color.b, aa);
				yield return null; 
			}
			break;
		}
		coroutinestarted = false;
	}
}
