using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Events;
using System;
using UnityEngine.SceneManagement;

public class PlayAnimation : MonoBehaviour {

	public string animationName;
	public bool playOnStart;
	public bool repeat;
	private Animator animator;

	[Serializable]
	public class OnFinish : UnityEvent {
	}
	public OnFinish onEvent;

	// Use this for initialization
	void Start () {
		animator = GetComponent<Animator>();
		if(playOnStart){
			animator.Play(animationName);
		}
	}
	
	// Update is called once per frame
	void Update () {
		if(!repeat){
			if(animator.GetCurrentAnimatorStateInfo(0).IsName(animationName) && animator.GetCurrentAnimatorStateInfo(0).normalizedTime >= 0.9f){
				onEvent.Invoke();
			}
		}
	}

	public void changeScene(string scene){
		SceneManager.LoadScene(scene);
	}

	public void destroy(){
		Destroy(this.gameObject);
	}

	public void replay(){
		animator.Play("Default");
	}
}
