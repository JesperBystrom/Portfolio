using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Impact : MonoBehaviour {

	void Start () {
		
	}

	void Update () {
		if(transform.localScale.x > 0 && transform.localScale.y > 0){
			this.transform.localScale = new Vector2(transform.lossyScale.x - 0.1f * Time.timeScale, transform.lossyScale.y - 0.1f * Time.timeScale);
		} else if(transform.localScale.x <= 0 && transform.localScale.y <= 0) {
			Destroy(this.gameObject);
		}
	}
}
