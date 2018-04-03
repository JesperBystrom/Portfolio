using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.Networking;

public class Player_Climb : MonoBehaviour {

	public bool canClimb = false;
	public bool moveCamera = false;
	[SerializeField] private bool climbUp = false;
	private bool climbDown = false;

	private Player_Movement playermovement;
	private Rigidbody rb;

	private GameObject ground;
	public GameObject ladder;
	public bool climbing = false;


	public Transform cameraTransformClimb;

	public GameObject trigger;
	private bool hasMovedPlayer = false;

	void Start () {
		cameraTransformClimb = transform.Find("CameraPosWhenClimbing");
		playermovement = GetComponent<Player_Movement>();
		rb = GetComponent<Rigidbody>();
	}


	void FixedUpdate () {
		

		float zAxis = Input.GetAxisRaw("Vertical");

		if(moveCamera){

			//playermovement.playerCamera.transform.position = cameraTransformClimb.position;
			//playermovement.playerCamera.transform.rotation = cameraTransformClimb.rotation;
			playermovement.playerCamera.transform.position = Vector3.Lerp(playermovement.playerCamera.transform.position, cameraTransformClimb.position, 0.2f);
			playermovement.playerCamera.transform.rotation = Quaternion.Slerp(playermovement.playerCamera.transform.rotation,cameraTransformClimb.rotation,0.2f);
			transform.rotation = Quaternion.LookRotation(-ladder.transform.up);

			if(!hasMovedPlayer){
				transform.position = new Vector3(ladder.transform.position.x, transform.position.y, ladder.transform.position.z);
				hasMovedPlayer = true;
			}
		}

		if(climbUp){
			//transform.Translate(Vector3.forward * 0.1f);
			//rb.AddForce(ladder.transform.forward);
			//transform.Translate(-transform.right * 0.2f);
			transform.position += transform.forward * 2.5f;
			climbUp = false;

		}

		if(climbDown){
			if(!trigger.GetComponent<TriggerManager>().isTriggered){
				climbDown = false;
				canClimb = false;
				Debug.Log("You can now climb again!");
			}
		}

		if(canClimb && !climbDown){
			if(Vector3.Distance(new Vector3(0, transform.position.y, 0), new Vector3(0, ground.transform.position.y, 0)) < 0.5f){
				climbDown = true;
				canClimb = false;
			}

			if(transform.position.y > ladder.transform.position.y + ladder.GetComponent<Renderer>().bounds.size.y/3){
				climbUp = true;
				canClimb = false;
			}

			if(zAxis != 0){
				moveCamera = true;
				climbing = true;

				transform.Translate(new Vector3(0,zAxis,0) * .05f);
				playermovement.playerMovementActivate = false;
				playermovement.cameraActivate = false;
				rb.isKinematic = true;

			} else {
				climbing = false;
			}
		} else if(!canClimb) {
			rb.isKinematic = false;
			//playermovement.cameraActivate = true;
			moveCamera = false;
			hasMovedPlayer = false;
		}
	}

	void OnCollisionEnter(Collision col){
		if(col.gameObject.tag == "Climbable"){
			canClimb = true;
		}

		if(col.gameObject.tag == "Ground"){
			ground = col.gameObject;
			climbUp = false;
		}
	}
}
