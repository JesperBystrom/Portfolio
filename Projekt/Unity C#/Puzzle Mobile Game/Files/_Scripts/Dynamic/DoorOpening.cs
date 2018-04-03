using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class DoorOpening : MonoBehaviour {

	public bool open = false;

	private float rotY;
	private float YDestination;
	[SerializeField]private float acc;

	private enum e_YRotationWhen { CLOSE = -90, OPEN = 0 };

	private ButtonManager buttonManager;
	public GameObject buttonParent;
	public int obstacles = 0;

	void Start () {

		if(SceneManager.GetActiveScene().name == "LevelEditor"){
			foreach(var c in GetComponentsInChildren<MonoBehaviour>()) {
				c.enabled = false;
			}
			this.enabled = false;
			buttonParent = this.gameObject;
			GetComponent<UniqueID>().enabled = true;
		}

		buttonManager = buttonParent.GetComponent<ButtonManager>();
		rotY = transform.rotation.y;
		YDestination = 0;
	}

	void enableChildren(){
		transform.Find("Win").transform.Find("Picture").GetComponent<Renderer>().enabled = true;
		transform.Find("Win").transform.Find("White Square").GetComponent<Renderer>().enabled = true;
	}

	void disableChildren(){
		transform.Find("Win").transform.Find("Picture").GetComponent<Renderer>().enabled = false;
		transform.Find("Win").transform.Find("White Square").GetComponent<Renderer>().enabled = false;
	}

	void Update () {

		if(obstacles <= 0){
			open = true;
		}

		if(Mathf.Round(YDestination) == Mathf.Round(rotY)){
			acc = 0;
		}

		if(open){

			enableChildren();

			YDestination = (int)e_YRotationWhen.OPEN;
			if(rotY < YDestination){
				acc += 0.03f;
				rotY += 1f * acc;
			}
		} else {
			
			disableChildren();

			YDestination = (int)e_YRotationWhen.CLOSE;
			if(rotY > YDestination){
				acc += 0.1f;
				rotY -= 1f * acc;
			}
		}


		//rotY = Mathf.Lerp(rotY, YDestination, 0.05f); 
		//transform.rotation = Quaternion.Slerp(transform.rotation, new Quaternion(transform.rotation.x, rotY, transform.rotation.z, 1f), 0.2f);
		transform.rotation = Quaternion.AngleAxis(rotY, Vector3.up);
	}
}
