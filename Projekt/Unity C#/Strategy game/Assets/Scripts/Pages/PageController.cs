using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

enum UIState {
	OPENED, CLOSED
}

public class PageController : MonoBehaviour {
	public AnimationClip closeAnimation;
	public AnimationClip openAnimation;
	public Page[] pages = new Page[1];

	private RawImage image;
	private RectTransform rect;
	private Animator animator;
	private UIState state = UIState.CLOSED;

	void Start(){
		this.image = GetComponent<RawImage>();
		this.rect = GetComponent<RectTransform>();
		this.animator = GetComponent<Animator>();

		if(state == UIState.CLOSED)
			close();
		else
			open();

	}

	void Update(){
	}

	public void open(){
		animator.Play(openAnimation.name);
	}

	public void close(){
		animator.Play(closeAnimation.name);
	}
}
