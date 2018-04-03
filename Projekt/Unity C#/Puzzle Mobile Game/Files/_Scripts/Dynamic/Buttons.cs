using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Buttons : MonoBehaviour {

	private Renderer render;
	private Shader shader;

	private bool scale = false;
	private bool left = false;


	private GameObject collidingWith;
	private float exitTimer = 1f;
	public float ID;

	[SerializeField] private bool oon = false;

	private List<GameObject> objectsThatPushButton = new List<GameObject>();

	void Start () {
		objectsThatPushButton.AddRange(GameObject.FindGameObjectsWithTag("ButtonPusher"));
		objectsThatPushButton.AddRange(GameObject.FindGameObjectsWithTag("Player"));

		render = GetComponent<Renderer>();
		shader = GetComponent<Shader>();
	}

	void Update () {

		oon = isOn();

		if(scale){
			transform.localScale =  Vector3.Lerp(transform.localScale, new Vector3(transform.lossyScale.x, 0.2f, transform.lossyScale.z), 0.2f);
		} else {
			transform.localScale =  Vector3.Lerp(transform.localScale, new Vector3(transform.lossyScale.x, 0.8f, transform.lossyScale.z), 0.2f);
		}

		if(left){
			if(exitTimer > 0){
				exitTimer -= 1 * Time.deltaTime;
			} else {
				exitTimer = 1f;
				render.material.color = Color.red;
				scale = false;
			}
		} else {
			exitTimer = 1f;
		}

	}

	void OnCollisionEnter(Collision col){
		for(int i=0;i<objectsThatPushButton.Count;i++){
			if(objectsThatPushButton[i] == col.gameObject){
				render.material.color = Color.green;
				scale = true;
				left = false;
				collidingWith = col.gameObject;
			}
		}
	}

	void OnCollisionExit(Collision col){
		left = true;
		Debug.Log(exitTimer);
	}

	public bool isOn(){
		if(scale){
			return true;
		} else {
			return false;
		}
	}
}
