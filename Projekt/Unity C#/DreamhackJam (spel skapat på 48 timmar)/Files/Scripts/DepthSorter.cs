using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DepthSorter : MonoBehaviour {

	SpriteRenderer render;

	void Start () {
		render = GetComponent<SpriteRenderer>();
	}

	void Update () {
		/*Vector2 spriteSize = new Vector2(render.bounds.max.x - render.bounds.min.x, render.bounds.max.y - render.bounds.min.y);
		render.sortingOrder = Mathf.Abs((int)this.transform.position.y + spriteSize/2)*/
	}
}
