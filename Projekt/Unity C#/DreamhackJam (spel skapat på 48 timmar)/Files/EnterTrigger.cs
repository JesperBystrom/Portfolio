using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnterTrigger : MonoBehaviour {

	Renderer parentRenderer;

	void Start(){
		parentRenderer = GetComponentInParent<Renderer>();
	}

	void OnTriggerEnter2D(Collider2D col){
		if(col.CompareTag("Player")){
			if(!parentRenderer.enabled){
				parentRenderer.enabled = false;
			}
		}
	}

	void OnTriggerExit2D(Collider2D col){
		if(col.CompareTag("Player")){
			parentRenderer.enabled = true;
		}
	}
}
