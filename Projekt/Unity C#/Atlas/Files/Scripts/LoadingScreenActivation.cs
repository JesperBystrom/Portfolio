using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class LoadingScreenActivation : MonoBehaviour {

	public LoadIcon loadingIcon;
	public Text message;
	private bool force = false;
	private int timer = 0;
	private bool fail = false;

	void Start () {
		if(!fail)
			message.text = "Loading";
		else
			message.text = "Oops! Country could not be found";
		loadingIcon.gameObject.SetActive(true);	
		loadingIcon.animator = loadingIcon.GetComponent<Animator>();
	}

	void Update () {
		if(loadingIcon.animator.GetCurrentAnimatorStateInfo(0).IsName("Tween") && loadingIcon.animator.GetCurrentAnimatorStateInfo(0).normalizedTime > 1.1f){
			disable();
		}
		if(force){
			disable();
		}

		if(message.text.Contains("Loading")){
			timer++;
			if(timer % 60 == 0)
				message.text += ".";
		}
	}

	public void activate(){
		if(fail) return;
		message.text = "Loading";
		loadingIcon.gameObject.SetActive(true);
		loadingIcon.img.color = Color.white;
	}

	public void disable(){
		loadingIcon.reset();
		loadingIcon.gameObject.SetActive(false);
		gameObject.SetActive(false);
		force = false;
		fail = false;
	}

	public void forceDisable(){
		force = true;
	}

	public void failed(string text){
		loadingIcon.gameObject.SetActive(true);
		fail = true;
		loadingIcon.GetComponent<Image>().color = Color.red;
		message.text = text;
		Debug.Log("Failed!");
		Debug.Log(message.text);
		StartCoroutine(wait());
	}

	public void finish(){
		loadingIcon.finish();
	}

	public IEnumerator wait(){
		yield return new WaitForSeconds(1.5f);
		forceDisable();
	}
}
