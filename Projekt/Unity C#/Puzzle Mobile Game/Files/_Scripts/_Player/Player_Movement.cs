using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public enum e_animationSpeed { 
	IDLE=1,
	JUMP = 1,
	CLIMB=3, 
	PUSH=2, 
	WALK=6
};

public class Player_Movement : MonoBehaviour {

	public float movespeed;
	public float acceleration;
	public float deacceleration;
	public float maxSpeed;
	public Camera playerCamera;



	public float xAxis;
	public float zAxis;
	public Animator playerAnimation;
	private bool animationChosen;

	[SerializeField] private float randomAnimation;
	public float stopPushAnimationTime;
	private float pushAnimationSpeed = 2f;
	public bool activateAnimations = true;


	private PlayerPhysics_Push playerPhy_push;
	private Physics_CubePush cubePhy;
	public bool playPushAnimation = false;

	public float cameraOffsetX;
	public float cameraOffsetY;
	public float cameraOffsetZ;
	public bool cameraActivate;

	private Rigidbody rb;

	private Quaternion rot;
	public bool collidingWithPushable = false;

	private GameObject colliderObject;
	public bool playerMovementActivate;
	private float gameGravity = -9.82f;

	private bool climbing = false;

	private Player_Climb playerClimb;

	private int playAnimation;
	private enum e_animations { IDLE=0, WALK=1, PUSH=2, CLIMB=3, JUMP=4 };

	public Vector3 cameraIntendedLocation;
	public Quaternion cameraIntendedRotation;

	public Vector3 rootPosition;
	public Quaternion rootRotation;

	public bool isGrounded = false;
	private string anim = "";
	public LayerMask mask;

	float oldY = 0;

	void Start () {
		if(SceneManager.GetActiveScene().name == "LevelEditor"){
			foreach(var c in GetComponentsInChildren<MonoBehaviour>()) {
				c.enabled = false;
			}
			GetComponent<UniqueID>().enabled = true;
		}
		mask = 1 << 8;
		mask = ~mask;
		playerCamera = GameObject.Find("Main Camera").GetComponent<Camera>();

		cameraActivate = true;
		playerAnimation = GetComponent<Animator>();
		randomAnimation = Random.Range(1,2);
		playerPhy_push = GetComponent<PlayerPhysics_Push>();
		cubePhy = GetComponent<Physics_CubePush>();
		rb = GetComponent<Rigidbody>();
		playerClimb = GetComponent<Player_Climb>(); 

		cameraIntendedLocation = new Vector3(transform.position.x-cameraOffsetX, transform.position.y+cameraOffsetY, transform.position.z-cameraOffsetZ);
		cameraIntendedRotation = Quaternion.Euler(25, 0, 0);

		rootPosition = transform.position;
		rootRotation = transform.rotation;

		playerMovementActivate = true;

	}

	void FixedUpdate(){
		if(Input.GetKey(KeyCode.Space) && isGrounded2()){
			float y = rb.velocity.y;
			y = 15;
			rb.velocity = new Vector3(rb.velocity.x, y, rb.velocity.z);
			//rb.AddForce(Vector3.up * 2500f);
			isGrounded = false;
		}
	}

	void Update () {

		//# Movement

		if(isMoving() == false){
			if(movespeed > 0){
				movespeed -= deacceleration;
			}
		} else {
			if(movespeed < maxSpeed){
				movespeed += acceleration;
			}
			rot = Quaternion.LookRotation(new Vector3(xAxis, 0, zAxis));
		}
			

		if(playerMovementActivate){
			xAxis = Input.GetAxisRaw("Horizontal");
			zAxis = Input.GetAxisRaw("Vertical");
			movespeed = Mathf.Clamp(movespeed, 0, maxSpeed);
			transform.Translate(Vector3.forward * movespeed * Time.deltaTime);
			//#Rotation
			transform.rotation = Quaternion.Slerp(transform.rotation, rot, movespeed * Time.deltaTime);
		}



		//# Camera
		if(cameraActivate){

			cameraIntendedLocation = new Vector3(transform.position.x-cameraOffsetX, transform.position.y+cameraOffsetY, transform.position.z-cameraOffsetZ); //classic for offsets: 0, 13.1, 14.91
			cameraIntendedRotation = Quaternion.Euler(40, 0, 0); // classic = 25,0,0

			playerCamera.transform.position = Vector3.Lerp(playerCamera.transform.position, cameraIntendedLocation, 0.05f);
			playerCamera.transform.rotation = cameraIntendedRotation; //Default camera
		}

		//# Animations

		if(Input.GetKeyDown(GetComponent<KeyManager>().key_action) && collidingWithPushable){
			playerAnimation.SetBool("isPushing", true);
			playerMovementActivate = false;
		}
	}

	public bool isMoving(){
		if(xAxis != 0 || zAxis != 0){
			playerAnimation.SetBool("isMoving", true);
			return true;
		} else {
			if(playerAnimation != null){
				playerAnimation.SetBool("isMoving", false);
			}
			return false;
		}
	}

	public bool isMovingForward(){
		if(zAxis == -1){
			return true;
		} else {
			return false;
		}
	}
		
	GameObject objectCollidingWith(){
		return colliderObject;
	}

	public bool isGrounded2(){
		Debug.DrawRay(this.transform.position, Vector3.down * 0.2f);
		Ray ray = new Ray(this.transform.position, Vector3.down);
		RaycastHit rayHit;
		if(Physics.Raycast(ray, out rayHit, 0.2f, mask.value)){
			return true;
		}
		return false;
	}

	void OnCollisionEnter(Collision col){
		colliderObject = col.gameObject;

		if(col.gameObject.tag == "Pushable"){
			collidingWithPushable = true;
		} else {
			collidingWithPushable = false;
		}
	}

	/*void OnCollisionEnter(Collision col){
		rb.AddForce(-col.collider.gameObject.transform.forward);
	}*/

	public bool isAnimationFinished(string animation){
		if(animation.Equals("Push_1")){
			if(Time.time > stopPushAnimationTime){
				return true;
			}
		}
		return false;
	}

	public void disable(){
		activateAnimations = false;
		playerMovementActivate = false;
		cameraActivate = false;
		movespeed = 0;
	}

	/*void OnCollisionEnter(Collision col){

		if(col.gameObject.tag == "Pushable"){
			collidingWithPushable = true;
		} else {
			collidingWithPushable = false;
		}
	}*/
}
