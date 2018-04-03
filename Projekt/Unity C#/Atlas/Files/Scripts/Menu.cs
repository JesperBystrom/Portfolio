using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class Menu : MonoBehaviour {

	public Animator animator;
	void Start () {
	}

	void Update () {
		if(animator.GetCurrentAnimatorStateInfo(0).IsName("Click") && animator.GetCurrentAnimatorStateInfo(0).normalizedTime > 0.25f){
			//finished
			SceneManager.LoadScene("Main");
		}
	}
}
