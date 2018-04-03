using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Bubbles : MonoBehaviour {

	float angle = 0;
	float s = 0;

	void Start () {
		
	}
		
	void Update () {

		if(Camera.main.gameObject.GetComponent<MainCamera>().timerUntilShake <= 0){
			Destroy(this.gameObject);
		}

		angle++;
		float x = this.GetComponent<RectTransform>().position.x;
		float y = this.GetComponent<RectTransform>().position.y;
		x += Mathf.Cos(angle*0.05f) * 0.1f;
		y += 0.1f;
		this.GetComponent<RectTransform>().position = new Vector2(x,y);
		this.transform.localScale = new Vector2(s, s);
		if(s < 1){
			s += 0.01f;
		}

	}
}
