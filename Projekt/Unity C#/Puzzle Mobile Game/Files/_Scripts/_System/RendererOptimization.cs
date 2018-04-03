using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class RendererOptimization : MonoBehaviour {


	public string[] disableObjectsWithTags = new string[2];


	public Camera mainCamera;
	[SerializeField] private bool moveCameraDown = false;
	[SerializeField] private bool moveCameraUp = false;
	private float moveCameraUpTimer = 1f;
	public Player_Climb playerclimb;
	public GameObject player;


	void Start () {

		disableObjectsWithTags[0] = "Ground";
		mainCamera = GetComponent<Camera>();
	}


	bool isOutsideCamera(GameObject obj){
		if(obj.transform.position.x > mainCamera.transform.position.x + mainCamera.fieldOfView/2 || obj.transform.position.x < mainCamera.transform.position.x - mainCamera.fieldOfView/2 || obj.transform.position.y > mainCamera.transform.position.y + mainCamera.fieldOfView || obj.transform.position.y < mainCamera.transform.position.y - mainCamera.fieldOfView){
			return true;
		} else {
			return false;
		}
	}

	void OnTriggerStay(Collider col){

		if(col.gameObject.transform.position.y > player.transform.position.y){
			moveCameraDown = true;
			moveCameraUp = false;
		}
	}

	void OnTriggerExit(Collider col){
	}

	void Update () {

		float y = transform.position.y;
		if(moveCameraDown){
			y -= 0.2f;
		}
		else if(moveCameraUp){
			y += 0.1f;
		}
		transform.position = new Vector3(transform.position.x, y, transform.position.z);


		var objects = GameObject.FindGameObjectsWithTag(disableObjectsWithTags[0]);

		foreach(GameObject obj in objects){
			if(isOutsideCamera(obj)){
				//obj.transform.Find("Dirt").GetComponent<Renderer>().enabled = false;
				//obj.transform.Find("Grass").GetComponent<Renderer>().enabled = false;
			} else {
				//obj.transform.Find("Dirt").GetComponent<Renderer>().enabled = true;
				//obj.transform.Find("Grass").GetComponent<Renderer>().enabled = true;
			}
		}
		/*
		else {
			obj.transform.Find("Dirt").GetComponent<Renderer>().enabled = true;
			obj.transform.Find("Grass").GetComponent<Renderer>().enabled = true;
		}*/

		/*foreach(GameObject obj in objects){
			if(obj.transform.position.x > mainCamera.transform.position.x + mainCamera.fieldOfView){
				if(obj.transform.position.y > mainCamera.transform.position.y){
					if(obj.transform.position.z > mainCamera.transform.position.z){
						obj.GetComponent<Renderer>().enabled = false;
					}
				}
			}
		}*/
	}
}
