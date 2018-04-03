using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class LoadNewScene : MonoBehaviour {

	private Animator animator;
	void Start () {
		animator = GetComponent<Animator>();
		animator.Play("EntranceAnimation");
	}

	void Update () {
		if(animator.GetCurrentAnimatorStateInfo(0).IsName("EntranceAnimation") && animator.GetCurrentAnimatorStateInfo(0).normalizedTime >= 0.95f){
			Destroy(this.gameObject);
		}
	}
}
