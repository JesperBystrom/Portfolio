using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Ladder : MonoBehaviour {

	/*
		Fix animations for the vines.
	*/

	[SerializeField] private GameObject player;
	[SerializeField] private Rigidbody playerRB;
	private bool canClimb = false;

	public Transform cameraTransform;
	private Player_Movement playermovement;

	private Transform vines;

	private bool moveCamera;

	void Start () {
		player = GameObject.FindWithTag("Player");
		playerRB = player.GetComponent<Rigidbody>();
		playermovement = player.GetComponent<Player_Movement>();
		vines = GetComponentInParent<Transform>();
	}
		
	void FixedUpdate () {
		float zAxis = Input.GetAxisRaw("Vertical");

		if(moveCamera){
			playermovement.playerCamera.transform.position = Vector3.Lerp(playermovement.playerCamera.transform.position, cameraTransform.position, 0.2f);
			playermovement.playerCamera.transform.rotation = Quaternion.Slerp(playermovement.playerCamera.transform.rotation,cameraTransform.rotation,0.2f);
			player.transform.rotation = Quaternion.LookRotation(new Vector3(Mathf.Round(transform.position.x), Mathf.Round(player.transform.position.y), Mathf.Round(transform.position.z)) - new Vector3(Mathf.Round(player.transform.position.x), Mathf.Round(player.transform.position.y), Mathf.Round(player.transform.position.z)));
		}

		if(canClimb){
			
			if(zAxis != 0){
				moveCamera = true;
				player.transform.Translate(new Vector3(0,zAxis,0) * .2f);
				playermovement.playerMovementActivate = false;
				playermovement.cameraActivate = false;
				playerRB.isKinematic = true;
			}
		} else if(!canClimb) {
			playerRB.isKinematic = false;
			//player.GetComponent<Player_Movement>().playerMovementActivate = true;
		}

		Debug.Log(canClimb);
	}

	void OnCollisionEnter(Collision col){
		if(col.gameObject == player){
			canClimb = true;
		}
	}

	void OnCollisionExit(Collision col){
	}
}
