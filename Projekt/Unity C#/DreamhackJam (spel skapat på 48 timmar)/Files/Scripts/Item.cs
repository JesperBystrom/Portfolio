using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Item : MonoBehaviour {

	float size;

	void OnTriggerStay2D(Collider2D col){
		if(col.gameObject.CompareTag("Player")){
			GameObject child = transform.GetChild(0).gameObject;
			child.SetActive(true);
			if(child.transform.localScale.x < 1.5f && child.transform.localScale.y < 1.5f){
				child.transform.localScale = new Vector2(child.transform.lossyScale.x+0.1f, child.transform.lossyScale.y+0.1f);
			}
		}
	}
	void OnTriggerExit2D(Collider2D col){
		if(col.gameObject.CompareTag("Player")){
			GameObject child = transform.GetChild(0).gameObject;
			StartCoroutine(scaleDown(child));
		}
	}

	IEnumerator scaleDown(GameObject child){
		while(child.transform.localScale.x > 0f && child.transform.localScale.y > 0f){
			child.transform.localScale = new Vector2(child.transform.lossyScale.x-0.1f, child.transform.lossyScale.y-0.1f);
			yield return null;
		}
		child.SetActive(false);
	}
}
