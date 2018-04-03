using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BlackBombDust : MonoBehaviour {

	public float dir;
	public float speed;
	float lifetime;

	void Start () {
		lifetime = Random.Range(10,20);
		Renderer render = GetComponent<Renderer>();
		float a = render.material.color.a;
		a = 0.1f;
		GetComponent<Renderer>().material.color = new Color(render.material.color.r,render.material.color.g,render.material.color.b,a);

	}

	void Update () {
		transform.Translate(new Vector2(Mathf.Cos(dir), Mathf.Sin(dir)) * speed * Time.deltaTime);
		if(speed > 0){
			speed -= Random.Range(3,4) * Time.deltaTime;
		}
		if(speed < 0.5f && speed > -0.5f){
			speed = 0;
		}
		lifetime -= 1 * Time.deltaTime;
		if(lifetime <= 0){
			Destroy(this.gameObject);
		}
	}
}
