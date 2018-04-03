using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public enum e_hitDirection { Forward, Backward, Left, Right, Down, Size };

public class PlayerPhysics_Push : MonoBehaviour {

	private bool[] hitArray;
	public float gridSize;
	private bool hasSetPos = false;
	private Vector3 newpos;
	public bool push = false;
	public KeyCode key_push;

	[SerializeField]private GameObject cube;
	private Physics_CubePush cubePhysics;
	private Player_Movement playerMovement;


	void Start () {
		hitArray = new bool[(int)e_hitDirection.Size];
		playerMovement = GetComponent<Player_Movement>();
	}

	void Update () {

		//Declare rayhits
		RaycastHit rayHitForward;
		RaycastHit rayHitBackwards;
		RaycastHit rayHitLeft;
		RaycastHit rayHitRight;

		//Create a vector3 for the ray
		Vector3 dir = new Vector3(transform.position.x,transform.position.y+2f,transform.position.z);

		//Initilize rays
		Ray rayForward = new Ray(dir, Vector3.forward);
		Ray rayBackwards = new Ray(dir, Vector3.back);
		Ray rayLeft = new Ray(dir, Vector3.left);
		Ray rayRight = new Ray(dir, Vector3.right);

		//Initilize the arrays with the raycasts
		hitArray[(int)e_hitDirection.Right] = Physics.Raycast(rayRight, out rayHitRight,2);
		hitArray[(int)e_hitDirection.Left] = Physics.Raycast(rayLeft, out rayHitLeft,2);
		hitArray[(int)e_hitDirection.Forward] = Physics.Raycast(rayForward, out rayHitForward,2);
		hitArray[(int)e_hitDirection.Backward] = Physics.Raycast(rayBackwards, out rayHitBackwards,2);


		//Move the object depending on the direction of raycast

		/*if(push){
			float pushX = Mathf.Round(pushObject.transform.position.x);
			float pushY = Mathf.Round(pushObject.transform.position.y);
			float pushZ = Mathf.Round(pushObject.transform.position.z);

			MeshRenderer pushObjectRenderer = pushObject.GetComponent<MeshRenderer>();
			GameObject marker;
			marker = GameObject.FindWithTag("Marker");

			if(pushObject.transform.position != new Vector3(newpos.x,newpos.y,newpos.z) && Time.time > GetComponent<Player_Movement>().stopPushAnimationTime/1.25){
				Vector3 velocity = Vector3.zero;
				pushObject.transform.position = Vector3.SmoothDamp(pushObject.transform.position, newpos, ref velocity, 0.1f);
			} else if(Vector3.Distance(pushObject.transform.position, marker.transform.position) < 6f || isInsideMarker(marker, pushObjectRenderer)) {
				Debug.Log("they match!");

				if(isInsideMarker(marker, pushObjectRenderer)){
					Debug.Log("Above you");
					//MAKE PUSHBJECT FALL DOWN
					pushObject.transform.position = new Vector3(pushObject.transform.position.x, pushObject.transform.position.y - pushObjectRenderer.bounds.size.y-0.2f, pushObject.transform.position.z);
				}
				push = false;
			}
		}*/

		//#Right
		if(hitArray[(int)e_hitDirection.Right]){
			if(isRayHittingPushable(rayHitRight)){
				playerMovement.collidingWithPushable = true;
				cube = rayHitRight.collider.gameObject;
				cubePhysics = cube.GetComponent<Physics_CubePush>();
				cubePhysics.setPushPositionAndRay((int)e_hitDirection.Right, Vector3.right);
				Debug.Log("RIGHT");
			}
		}

		//#Left
		else if(hitArray[(int)e_hitDirection.Left]){
			if(isRayHittingPushable(rayHitLeft)){
				playerMovement.collidingWithPushable = true;
				cube = rayHitLeft.collider.gameObject;
				cubePhysics = cube.GetComponent<Physics_CubePush>();
				cubePhysics.setPushPositionAndRay((int)e_hitDirection.Left, Vector3.left);
				Debug.Log("LEFT");
			}
		}

		//#Forward
		else if(hitArray[(int)e_hitDirection.Forward]){
			if(isRayHittingPushable(rayHitForward)){
				playerMovement.collidingWithPushable = true;
				cube = rayHitForward.collider.gameObject;
				cubePhysics = cube.GetComponent<Physics_CubePush>();
				cubePhysics.setPushPositionAndRay((int)e_hitDirection.Forward, Vector3.forward);
				Debug.Log("FORWARD");
			}
		}

		//#Backwards
		else if(hitArray[(int)e_hitDirection.Backward]){
			if(isRayHittingPushable(rayHitBackwards)){
				playerMovement.collidingWithPushable = true;
				cube = rayHitBackwards.collider.gameObject;
				cubePhysics = cube.GetComponent<Physics_CubePush>();
				cubePhysics.transform.position = new Vector3(cubePhysics.transform.position.x, cubePhysics.transform.position.y, cubePhysics.transform.position.z-gridSize);
				cubePhysics.setPushPositionAndRay((int)e_hitDirection.Backward, Vector3.back);
				Debug.Log("BACKWARD");
			}
		} else {
			playerMovement.collidingWithPushable = false;
		}
	}

	bool isRayHittingPushable(RaycastHit hit){
		if(hit.collider.gameObject.tag == "Pushable"){
			return true;
		} else {
			return false;
		}
	}

	/*bool isInsideMarker(GameObject marker, MeshRenderer pushObjectRend){
		if(pushObject.transform.position.x >= marker.transform.position.x-pushObjectRend.bounds.size.x && pushObject.transform.position.x <= marker.transform.position.x+pushObjectRend.bounds.size.x){
			if(pushObject.transform.position.z >= marker.transform.position.z-pushObjectRend.bounds.size.x && pushObject.transform.position.z <= marker.transform.position.z+pushObjectRend.bounds.size.x){
				return true;
			}
		}
		return false;
	}*/

	/*void setPushPosition(Transform trans, int dir){
		if(!push && Input.GetKey(key_push)){
			switch(dir){
			case (int)e_hitDirection.Right:
				newpos = new Vector3(trans.position.x+gridSize, trans.position.y,trans.position.z);
				break;

			case (int)e_hitDirection.Left:
				newpos = new Vector3(trans.position.x-gridSize, trans.position.y, trans.position.z);
				break;

			case (int)e_hitDirection.Forward:
				newpos = new Vector3(trans.position.x, trans.position.y, trans.position.z+gridSize);
				break;

			case (int)e_hitDirection.Backward:
				newpos = new Vector3(trans.position.x, trans.position.y, trans.position.z-gridSize);
				break;
			}
			pushObject = trans.gameObject;
			push = true;
		}
	}*/
}
