using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class CircleAnimationActivation : MonoBehaviour {

	Animator animator;
	public LoadingScreenActivation loadingScreen;
	private Vector2 positionStart;
	private Vector2 sizeStart;
	private RectTransform rect;
	private Main main;
	private GameObject country;
	private bool loadingInfo = false;

	void Start () {
		animator = GetComponent<Animator>();
		animator.Play(Main.ANIMATION_CIRCLE_NAME);
		rect = GetComponent<RectTransform>();
		sizeStart = rect.sizeDelta;
		positionStart = rect.anchoredPosition;
	}

	void Update () {
		if(animator != null){
			if(animator.GetCurrentAnimatorStateInfo(0).IsName(Main.ANIMATION_CIRCLE_NAME) && animator.GetCurrentAnimatorStateInfo(0).normalizedTime > Main.ANIMATION_CIRCLE_END){
				main.StartCoroutine(main.wiki.loadInformation(country));
				loadingInfo = true;
			}
			if(animator.GetCurrentAnimatorStateInfo(0).IsName(Main.ANIMATION_CIRCLE_NAME) && animator.GetCurrentAnimatorStateInfo(0).normalizedTime > Main.ANIMATION_CIRCLE_END){
				Debug.Log("loading screen activated");
				loadingScreen.gameObject.SetActive(true);
				loadingScreen.activate();

				rect.sizeDelta = sizeStart;
				rect.anchoredPosition = positionStart;
				this.gameObject.SetActive(false);
			}
		}
	}

	//This has to run every time object is being setactive
	public void loadCountry(Main main, GameObject country){
		animator = GetComponent<Animator>();
		animator.Play(Main.ANIMATION_CIRCLE_NAME);
		this.main = main;
		this.country = country;
		loadingInfo = false;
		main.mouse.enableClicking();
	}
}
