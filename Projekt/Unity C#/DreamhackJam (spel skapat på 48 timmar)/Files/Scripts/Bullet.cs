using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Bullet : MonoBehaviour {

	public Vector3 targetPos;
	public float speed = 1f;
	Vector3 dir;

	void Start () {
		dir = (this.transform.position - targetPos).normalized;
	}

	void Update () {

		transform.position -= dir * speed * Time.deltaTime * Time.timeScale;
	}
    private void OnTriggerEnter2D(Collider2D collision)
    {
        if (collision.gameObject.tag == "Wall")
        {
            Debug.Log("ded");
            Destroy(this.gameObject);
        }
    }
}
