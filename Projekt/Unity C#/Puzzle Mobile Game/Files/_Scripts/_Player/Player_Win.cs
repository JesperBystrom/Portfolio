using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityStandardAssets.ImageEffects;
using UnityEngine.SceneManagement;

enum e_fadeDirection {
	IN = -1,
	OUT = 1
};
	

public class Player_Win : MonoBehaviour {


	private string[] bodyParts = { 
		"Head", 
		"Body",
		"UpperArm_Right",
		"Arm_Right",
		"Hand_Right",
		"UpperArm_Left",
		"Arm_Left",
		"Hand_Left",
		"Leg_Left",
		"Leg_Right",
		"Foot_Right",
		"Foot_Left"
	};

	public GameObject winObject;
	public BlurOptimized blur;
	public GameObject fadeOutWhite;
	public float intensitySpeed = 0.05f;
	public bool winLevel = false;

	private DoorOpening door;


	private float fadeOutAlpha;
	private Renderer fadeOutRenderer;

	private float x;
	private float y;
	private float z;

	//Light light;


	void Start () {
		winObject = GameObject.FindWithTag("Win");
		fadeOutWhite = GameObject.FindWithTag("Fade");
		fadeOutRenderer = fadeOutWhite.GetComponent<Renderer>();
		//light = winObject.transform.Find("Light").GetComponent<Light>();
		door = GameObject.FindWithTag("door").GetComponent<DoorOpening>();
		//light.enabled = true;
		StartCoroutine(fade((int)e_fadeDirection.IN));

		x = transform.lossyScale.x;
		y = transform.lossyScale.y;
		z = transform.lossyScale.z;


	}
	void Update () {
		
		winObject = GameObject.FindWithTag("Win");
		fadeOutWhite = GameObject.FindWithTag("Fade");
		fadeOutRenderer = fadeOutWhite.GetComponent<Renderer>();
		door = GameObject.FindWithTag("door").GetComponent<DoorOpening>();

		Debug.Log("WINLEVEL: " + winLevel);

		if(fadeOutAlpha >= 1){
			StartCoroutine(fade((int)e_fadeDirection.IN));
		}

		if(door.open){
			Win();
		} else {
			//light.intensity -= intensitySpeed * 4;
		}
	}

	void OnTriggerStay(Collider col){
		Debug.Log("in trigger: " + winObject);
		if(isColliderWinObject(col) && door.open){
			winLevel = true;
		}
	}

	void OnTriggerEnter(Collider col){
		if(isColliderWinObject(col) && door.open){
			StartCoroutine(fade((int)e_fadeDirection.OUT));
		}
	}

	/*void OnTriggerExit(Collider col){
		if(isColliderWinObject(col)){
			winLevel = false;
		}
	}*/

	bool isColliderWinObject(Collider col){
		return col.gameObject == winObject;
	}

	void transColor(int index){
		float r = transform.Find(bodyParts[index]).GetComponent<Renderer>().material.color.r;
		float g = transform.Find(bodyParts[index]).GetComponent<Renderer>().material.color.g;
		float b = transform.Find(bodyParts[index]).GetComponent<Renderer>().material.color.b;

		float spd = 0.005f;

		r += spd;
		g += spd;
		b += spd;

		/*Debug.Log("red: " + r);
		Debug.Log("green: " + g);
		Debug.Log("blue: " + b);*/

		Color color = new Color(r,g,b);
		transform.Find(bodyParts[index]).GetComponent<Renderer>().material.color = color;
		transform.Find(bodyParts[index]).GetComponent<Renderer>().material.SetColor("_OutlineColor", color);
	}

	void setBodyPartColors(){

		for(int i=0;i<bodyParts.Length;i++){
			transColor(i);
		}

	}
		
	//THIS IS WHERE
	//YOU WIN THE LEVEL
	private void movePlayer(){

		setBodyPartColors();

		Debug.Log("END OF LEVEL");



		Player_Movement movement = GetComponent<Player_Movement>();
		movement.disable();

		GetComponent<Rigidbody>().isKinematic = true;
		transform.Translate(transform.forward * 0.03f);

		Vector3 camPos = movement.playerCamera.transform.position;
		Quaternion camRot = movement.playerCamera.transform.rotation;

		camPos = Vector3.Slerp(camPos, door.transform.Find("CameraPosEndOfLevel").transform.position, 0.05f);
		camRot = Quaternion.Slerp(camRot, door.transform.Find("CameraPosEndOfLevel").transform.rotation, 0.05f);
		movement.playerCamera.transform.position = camPos;
		movement.playerCamera.transform.rotation = camRot;

		x = Mathf.Lerp(x, 0, 0.01f);
		y = Mathf.Lerp(y, 0, 0.01f);
		z = Mathf.Lerp(z, 0, 0.01f);

		transform.localScale = new Vector3(x,y,z);

		Debug.Log("PLAYER MOVEMENT ACTIVATE: " + movement.playerMovementActivate);
	}

	private void changeLevel(){
		GameObject.Find("GameManager").GetComponent<LevelManager>().loadNext();
	}

	public bool hasFaded(){
		return (fadeOutAlpha <= 0f || fadeOutAlpha >= 1);
	}

	public IEnumerator fade(int dir){

		while(true){
			Debug.Log("DIRECTION: " + dir);
			fadeOutRenderer.enabled = true;
			fadeOutAlpha = fadeOutRenderer.material.color.a;
			fadeOutAlpha += 0.01f * dir;

			float r = fadeOutRenderer.material.color.r, 
			g = fadeOutRenderer.material.color.g, 
			b = fadeOutRenderer.material.color.b;

			fadeOutRenderer.material.color = new Color(r,g,b, fadeOutAlpha);

			if(hasFaded()){
				break;
			}

			yield return null;
		}
	}


	private void Win(){
		if(winLevel){
			GetComponent<Player_Movement>().playerMovementActivate = false;
			movePlayer();

			if(fadeOutAlpha >= 0.98f){
				changeLevel();
				blur.enabled = false;
			} else {
				blur.enabled = true;
				blur.blurSize += 0.01f;
			}
		} else {
			blur.enabled = false;
		}
	}
}
