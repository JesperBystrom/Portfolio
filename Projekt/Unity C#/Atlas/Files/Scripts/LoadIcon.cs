using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;


public class LoadIcon : GIFAnimator {

	private Vector3 startPosition;
	private Vector2 startSize;
	public RectTransform rect;
	public Animator animator;
	public Image img;

	void Start () {
		startSize = rect.sizeDelta;
		startPosition = rect.anchoredPosition;
		base.Start();
	}

	void Update () {
		base.Update();
		if(isFinished()){
			reset();
			gameObject.SetActive(false);
		}
	}

	public void reset(){
		animator.Play("Default");
		rect.sizeDelta = startSize;
		rect.anchoredPosition = startPosition;
	}

	public void finish(){
		animator.Play("Tween");
	}
	public bool isFinished(){
		return animator.GetCurrentAnimatorStateInfo(0).IsName("Tween") && animator.GetCurrentAnimatorStateInfo(0).normalizedTime > 1.1f;
	}
}
