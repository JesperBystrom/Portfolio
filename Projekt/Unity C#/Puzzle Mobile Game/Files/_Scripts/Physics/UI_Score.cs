using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class UI_Score : MonoBehaviour {

	enum e_UIAnimation { OPEN, CLOSE };

	public Text scoreText;
	public Renderer pickupUIRenderer;
	private float score;
	private float lastScore;
	private float UITimer = 0f; // seconds
	public float maxScale;

	public float horSpeed;
	public float verSpeed;

	private Coroutine timerCouroutine;

	void Start(){
		scoreText = GameObject.Find("Canvas").transform.Find("scoreText").GetComponent<Text>();
		//pickupUIRenderer = GameObject.Find("pickupUI" + "(Clone)").GetComponentInChildren<Renderer>();
		//pickupUIRenderer = GameObject.FindWithTag("PickupUI").GetComponentInChildren<Renderer>();
	}

	public void UpdateUI(){
		resetUITimer();

		if(!isUIEnabled()){
			enableUI();

			timerCouroutine = StartCoroutine(StartUITimer());
			StartCoroutine(animateUI((int)e_UIAnimation.OPEN));
		}
	}

	private IEnumerator StartUITimer(){
		while(UITimer > 0f){
			UITimer -= 1f * Time.deltaTime;

			yield return null;
		}
	}

	private bool isUITimerFinished(float timer){
		if(timer <= 0f){
			return true;
		}
		return false;
	}

	private void resetUITimer(){
		UITimer = 3f;
	}

	private void disableUI(){
		pickupUIRenderer.enabled = false;
		scoreText.enabled = false;
		//image.enabled = false;
	}

	private IEnumerator animateUI(int anim){

		if(anim == (int)e_UIAnimation.CLOSE){

			if(timerCouroutine != null){
				StopCoroutine(timerCouroutine);
			}

			while(pickupUIRenderer.transform.lossyScale.x > 0){
				float x = pickupUIRenderer.transform.localScale.x;
				float y = pickupUIRenderer.transform.localScale.y;
				float z = pickupUIRenderer.transform.localScale.z;

				x -= horSpeed;
				z -= horSpeed;
				y += verSpeed;

				x = Mathf.Clamp(x, 0, maxScale);
				z = Mathf.Clamp(z, 0, maxScale);

				pickupUIRenderer.transform.localScale = new Vector3(x,y,z);
				yield return null;
			}
			if(pickupUIRenderer.transform.lossyScale.x <= 0){
				disableUI();
			}
		}

		if(anim == (int)e_UIAnimation.OPEN){
			while(pickupUIRenderer.transform.lossyScale.x < maxScale){

				float x = pickupUIRenderer.transform.localScale.x;
				float y = pickupUIRenderer.transform.localScale.y;
				float z = pickupUIRenderer.transform.localScale.z;

				x += horSpeed;
				z += horSpeed;
				y -= verSpeed;

				x = Mathf.Clamp(x, 0, maxScale);
				z = Mathf.Clamp(z, 0, maxScale);

				pickupUIRenderer.transform.localScale = new Vector3(x,y,z);
				yield return null;
			}

			if(pickupUIRenderer.transform.lossyScale.x >= maxScale){
				enableUI();
			}
		}
	}

	private bool isUIEnabled(){
		if(pickupUIRenderer.enabled && scoreText.enabled){
			return true;
		}
		return false;
	}

	private void enableUI(){
		pickupUIRenderer.enabled = true;
		scoreText.enabled = true;
		//image.enabled = true;
	}
	

	void Update () {
		
		score = GetComponent<Player_Pickup>().score;
		scoreText.text = score.ToString();

		if(isUITimerFinished(UITimer)){
			//WaitForSeconds("StartUITimer()",1);
			StartCoroutine(animateUI((int)e_UIAnimation.CLOSE));
			resetUITimer();
		}
	}
}
