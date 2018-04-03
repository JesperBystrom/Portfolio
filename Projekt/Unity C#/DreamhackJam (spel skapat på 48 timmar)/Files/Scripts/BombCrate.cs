using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BombCrate : MonoBehaviour {

	float explosionTimer = 6;
	bool isExploding = false;
	Animator animator;
	bool once = false;
	public GameObject dust;

	void Start () {
		animator = GetComponent<Animator>();
		//StartCoroutine(explode());
	}

	void Update () {

		if(isExploding){
			if(!once){
				for(int i=0;i<8;i++){
					BlackBombDust b = Instantiate(dust).GetComponent<BlackBombDust>();
					b.transform.position = this.transform.position;
					b.dir = Random.Range(0,360);
					b.speed = Random.Range(0.5f,1);
				}
				once = true;
			}

			animator.SetBool("explode", true);
			transform.localScale = new Vector3(1.5f,1.5f,1);
			explosionTimer -= ((6)*4)*Time.deltaTime;
			if(explosionTimer <= 0){
				Destroy(this.gameObject);
			}
		}

		Collider2D[] col = Physics2D.OverlapCircleAll(this.transform.position, 0.18f);
		for(int i=0;i<col.Length;i++){
			if(col[i].CompareTag("Explosive") && isExploding && explosionTimer <= 3){
				col[i].gameObject.GetComponent<BombCrate>().isExploding = true;
			}
		}

	}

	void OnCollisionEnter2D(Collision2D col){
		isExploding = true;
	}
}
