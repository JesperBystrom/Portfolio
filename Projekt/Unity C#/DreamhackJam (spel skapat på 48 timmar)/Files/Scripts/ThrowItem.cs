using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ThrowItem : MonoBehaviour {

	Rigidbody2D rb;
	float speed = 45f;
	float rot = 0;
	bool inc = false;

	void Start () {
		rb = GetComponent<Rigidbody2D>();
	}

	void Update () {
		//rb.velocity = new Vector2(Mathf.Cos(135), Mathf.Sin(135)) * 20f * Time.deltaTime;
		rb.velocity = new Vector2(-1, -1) * speed * Time.deltaTime;
		if(rot < 180){
			inc = false;
		} else {
			inc = true;
		}
		if(Mathf.Abs(rb.velocity.x) > 0.5f && Mathf.Abs(rb.velocity.y) > 0.5f){
			if(inc){
				rot += 10f;
			} else {
				rot -= 2f;
			}
		} else {
			rot = Mathf.Lerp(rot, 0, 0.01f);
		}
		transform.Rotate(new Vector3(0,0,rot));
		if(speed > 0){
			speed -= 1f;
		}
	}
}
