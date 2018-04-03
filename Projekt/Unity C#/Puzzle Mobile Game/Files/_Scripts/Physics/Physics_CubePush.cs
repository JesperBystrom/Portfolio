using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class Physics_CubePush : MonoBehaviour {

	private GameObject player;
	private PlayerPhysics_Push playerPhysics;

	private Vector3 newpos;
	private Ray[] ray = new Ray[(int)e_hitDirection.Size];
	private int directionBeingPushed;

	private bool hasBeenPushed = false;
	private bool hasMoved = false;


	void Start(){
		if(SceneManager.GetActiveScene().name.Equals("LevelEditor")){
			this.enabled = false;
		}
	}

	private void initRays(){
		ray[(int)e_hitDirection.Forward] = new Ray(transform.position, Vector3.forward);
		ray[(int)e_hitDirection.Backward] = new Ray(transform.position, Vector3.back);
		ray[(int)e_hitDirection.Right] = new Ray(transform.position, Vector3.right);
		ray[(int)e_hitDirection.Left] = new Ray(transform.position, Vector3.left);
		ray[(int)e_hitDirection.Down] = new Ray(transform.position, Vector3.down);
	}

	void Update(){

		if(player == null){
			player = GameObject.FindWithTag("Player");
		} else {
			playerPhysics = player.GetComponent<PlayerPhysics_Push>();
		}
			
		initRays();

		/*
		Debug.DrawRay(this.transform.position, Vector3.forward * 6f, Color.blue);
		Debug.DrawRay(this.transform.position, Vector3.back * 6f, Color.blue);
		Debug.DrawRay(this.transform.position, Vector3.left * 6f, Color.blue);
		Debug.DrawRay(this.transform.position, Vector3.right * 6f, Color.blue);
		Debug.DrawRay(this.transform.position, Vector3.down * 6f, Color.blue);*/

		//This will loop through all EXCEPT for the last one (down)

		/*for(int i=0;i<(int)e_hitDirection.Size-1;i++){
			RaycastHit rayHit;
			if(Physics.Raycast(ray[i], out rayHit, playerPhysics.gridSize)){
				if(rayHit.collider.gameObject != player && rayHit.collider.gameObject != this.gameObject){
					clear();
					Debug.Log("Cannot move there!" + rayHit.collider.gameObject);
				}
			}
		}*/
	}


	private bool hasPlayerFinishedAnimation(){
		float animTime = player.GetComponent<Animator>().GetCurrentAnimatorStateInfo(0).normalizedTime;
		bool isPushAnimation = player.GetComponent<Animator>().GetCurrentAnimatorStateInfo(0).IsName("Push_1");

		Debug.Log("time:"+animTime);

		if(animTime > 0.5f && isPushAnimation) {
			player.GetComponent<Animator>().SetBool("isPushing",false);
			return true;
		}
		return false;
	}

	private void clear(){
		hasBeenPushed = false;
		hasMoved = false;
		setNewPosition(this.transform.position);

		for(int i=0;i<(int)e_hitDirection.Size;i++){
			ray[i] = new Ray(transform.position, Vector3.up);
		}
	}

	private void moveObject(Vector3 newpos){
		Vector3 velocity = Vector3.zero;
		this.transform.position = Vector3.SmoothDamp(this.transform.position, newpos, ref velocity, 0.1f);
	}

	private bool hasReachedDestination(Vector3 newpos){
		if(Vector3.Distance(this.transform.position, newpos) < 0.1f){
			return true;
		}
		return false;
	}

	public void setPushPositionAndRay(int dir, Vector3 rayDirection){
		if(!hasBeenPushed && Input.GetKey(KeyCode.Tab)){
			clear();
			float gridSize = playerPhysics.gridSize;
			switch(dir){
			case (int)e_hitDirection.Forward:
				setNewPosition(new Vector3(transform.position.x, transform.position.y, transform.position.z+gridSize));
				break;

			case (int)e_hitDirection.Backward:
				setNewPosition(new Vector3(transform.position.x, transform.position.y, transform.position.z-gridSize));
				break;

			case (int)e_hitDirection.Left:
				setNewPosition(new Vector3(transform.position.x-gridSize, transform.position.y, transform.position.z));
				break;

			case (int)e_hitDirection.Right:
				setNewPosition(new Vector3(transform.position.x+gridSize, transform.position.y, transform.position.z));
				break;
			}
			hasBeenPushed = true;
			ray[dir] = new Ray(transform.position, rayDirection);

			for(int i=0;i<(int)e_hitDirection.Size-1;i++){
				RaycastHit rayHit;
				if(Physics.Raycast(ray[i], out rayHit, playerPhysics.gridSize)){
					if(rayHit.collider.gameObject != player && rayHit.collider.gameObject != this.gameObject){
						clear();
						Debug.Log("Cant go there " + rayHit.collider.gameObject);
					}
				}
			}
		}
	}

	void LateUpdate(){

		Debug.DrawRay(transform.position, Vector3.down * 6f, Color.blue);

		if(hasBeenPushed){

			if(hasPlayerFinishedAnimation()){
				hasMoved = true;
				player.GetComponent<Player_Movement>().playerMovementActivate = true;
			}

			if(hasMoved){
				moveObject(this.newpos);
			}
			//new Vector3(transform.position.x, transform.position.y - playerPhysics.gridSize, transform.position.z)
			if(hasReachedDestination(this.newpos)){
				clear();
				ray[(int)e_hitDirection.Down] = new Ray(transform.position, Vector3.down);
				RaycastHit hit;
				if((Physics.Raycast(ray[(int)e_hitDirection.Down], out hit)) == false){
					Debug.Log("there's no floor here");
					StartCoroutine(insertObject());
				}
			}
		}
	}
		
	private IEnumerator insertObject(){
		Vector3 insertedpos = new Vector3(transform.position.x, transform.position.y - (playerPhysics.gridSize+0.42f), transform.position.z);
		while(transform.position != insertedpos){
			Vector3 velocity = Vector3.zero;
			transform.position = Vector3.SmoothDamp(transform.position, insertedpos, ref velocity, 0.05f);
			yield return null;
		}
	}

	private void setNewPosition(Vector3 newpos){
		this.newpos = newpos;
	}







































	/*enum hitDirection { None, Forward, Backward, Left, Right, hitLength };
	private GameObject player;
	public PlayerPhysics_Push playerPhysics;

	public bool push;
	private Vector3 newpos;
	private GameObject pushObject;
	private float gridSize = 6f;
	private bool moveCube = false;
	[SerializeField]private bool insertCube = false;
	private float insertY;
	private bool collidingWithGround = false;
	private GameObject groundBlock;

	public float fallSpeed;

	private enum e_direction { FORWARD, BACK, RIGHT, LEFT, SIZE };
	private Ray[] ray = new Ray[(int)e_direction.SIZE];

	void Start () {
		player = GameObject.FindWithTag("Player");
		playerPhysics = player.GetComponent<PlayerPhysics_Push>();
		fallSpeed = 0.05f;
	}

	void Update () {


		moveCube = shouldObjectMove(pushObject, newpos, moveCube);

		pushTheObject(push, pushObject,newpos,moveCube,GameObject.FindWithTag("Ground"));

		ray[(int)e_direction.FORWARD] = new Ray(transform.position, Vector3.forward);
		ray[(int)e_direction.BACK] = new Ray(transform.position, Vector3.back);
		ray[(int)e_direction.RIGHT] = new Ray(transform.position, Vector3.right);
		ray[(int)e_direction.LEFT] = new Ray(transform.position, Vector3.left);

		for(int i=0;i<(int)e_direction.SIZE;i++){

			RaycastHit rayHit;
			bool hasHit = Physics.Raycast(ray[i], out rayHit, 6f);

			if(hasHit){
				if(rayHit.collider.gameObject.transform.position == newpos && rayHit.collider.gameObject.CompareTag("Pushable")){
					Debug.Log("Cant go there!");
					disablePush();
				}
			}
		}


		if(hasObjectReachedDestination(push, pushObject,newpos)){
			insertY = setCubeInsertCoordinates(push, pushObject, insertY, insertCube);
			//insertCube = shouldInsertObject(pushObject);

			Transform rayTransform = pushObject.transform;
			RaycastHit rayHit;
			Ray ray = new Ray(rayTransform.position, Vector3.down);
			bool hitGround = Physics.Raycast(ray, out rayHit, 10f);
			insertCube = hitGround;

			Debug.Log("They match!");
			disablePush();
		}

		if(insertCube && pushObject != null){
			Vector3 velocity = Vector3.zero;
			Vector3 pos = new Vector3(pushObject.transform.position.x, insertY, pushObject.transform.position.z);
			pushObject.transform.position = Vector3.SmoothDamp(pushObject.transform.position, pos, ref velocity, fallSpeed);

			if(pushObject.transform.position == pos){
				insertCube = false;
				Debug.Log("Inserted");
			}
		}

	}

	void disablePush(){
		push = false;
		moveCube = false;
	}

	public void setPushPosition(Transform trans, int dir){
		if(!push && Input.GetKey(player.GetComponent<KeyManager>().key_action)){
			switch(dir){
			case (int)hitDirection.Right:
				newpos = new Vector3(trans.position.x+gridSize, trans.position.y,trans.position.z);
				break;

			case (int)hitDirection.Left:
				newpos = new Vector3(trans.position.x-gridSize, trans.position.y, trans.position.z);
				break;

			case (int)hitDirection.Forward:
				newpos = new Vector3(trans.position.x, trans.position.y, trans.position.z+gridSize);
				break;

			case (int)hitDirection.Backward:
				newpos = new Vector3(trans.position.x, trans.position.y, trans.position.z-gridSize);
				break;
			}
			pushObject = this.gameObject;
			push = true;
		}
	}


	public void pushTheObject(bool push, GameObject obj, Vector3 newerPos, bool moveObject, GameObject ground){
		if(!isObjectBeingPushed(push, obj)){ return; }

		bool shouldMove = shouldObjectMove(obj, newerPos, moveObject);

		if(shouldMove){
			Vector3 velocity = Vector3.zero;
			obj.transform.position = Vector3.SmoothDamp(obj.transform.position, newerPos, ref velocity, 0.1f);
		}
	}

	bool shouldObjectMove(GameObject obj, Vector3 newerPos, bool moveObject){
		if(!isObjectBeingPushed(push, obj)){ return false; }
		float animTime = player.GetComponent<Animator>().GetCurrentAnimatorStateInfo(0).normalizedTime;
		bool animName = player.GetComponent<Animator>().GetCurrentAnimatorStateInfo(0).IsName("Push_1");

		if(obj.transform.position != new Vector3(newerPos.x,newerPos.y,newerPos.z) && animTime > 0.5f && animName && !moveObject) {
			moveObject = true;
		}
		return moveObject;
	}


	bool hasObjectReachedDestination(bool push, GameObject obj, Vector3 newerPos){
		if(!isObjectBeingPushed(push, obj)){ return false; }

		if(Vector3.Distance(obj.transform.position, newerPos) < 0.1f) {
			return true;
		} else {
			return false;
		}
	}

	bool isObjectBeingPushed(bool push, GameObject obj){
		if(push && obj != null){
			return true;
		} else if(!push && obj != null){
			return false;
		}
		return false;
	}

	bool isHittingGround(GameObject obj){
		if(!isObjectBeingPushed(push, obj)) { return false; }

		Transform rayTransform = obj.transform;
		RaycastHit rayHit;
		Ray ray = new Ray(rayTransform.position, Vector3.down);
		bool hitGround = Physics.Raycast(ray, out rayHit, 10f);
		return hitGround;
	}

	bool shouldInsertObject(GameObject obj){
		if(!isHittingGround(obj)){
			return true;
		} else {
			return false;
		}
	}

	float setCubeInsertCoordinates(bool push, GameObject obj, float y, bool insert){
		Transform rayTransform = obj.transform;
		RaycastHit rayHit;
		Ray ray = new Ray(rayTransform.position, Vector3.down);
		bool hitGround = Physics.Raycast(ray, out rayHit, 10f);
		Debug.DrawRay(rayTransform.position, Vector3.down * 10f);

		MeshRenderer pushObjectRenderer = pushObject.GetComponent<MeshRenderer>();
		y = pushObject.transform.position.y - pushObjectRenderer.bounds.size.y - 1.5f;
		return y;
	}

	bool insertObject(GameObject obj, bool push, bool insert, Vector3 pos){
		if(!isObjectBeingPushed(push, obj)) { return false; }
		return false;
	}
	*/
}
