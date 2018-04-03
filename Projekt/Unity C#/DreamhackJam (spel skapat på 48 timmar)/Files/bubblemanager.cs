using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class bubblemanager : MonoBehaviour {

	public GameObject bubble;
	float timer = 4f;
	Vector3 origin;
	bool setorigin = false;

	void Start () {
		origin = bubble.transform.position;
	}

	void Update () {

		if(timer <= 0 && bubble != null){
			GameObject b = Instantiate(bubble);
			b.transform.SetParent(this.gameObject.transform);
			b.transform.position = new Vector2(origin.x + Random.Range(-32,32), origin.y);

			timer = 4f;
		} else {
			timer -= 1 * Time.deltaTime;
		}
		Debug.Log(timer);
	}
}
