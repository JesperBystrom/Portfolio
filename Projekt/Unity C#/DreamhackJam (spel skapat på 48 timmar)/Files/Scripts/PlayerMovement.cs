using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class PlayerMovement : MonoBehaviour {

	Rigidbody2D rb;
	float movespeed = 0f;
	float dashspeed = 1;
	float hspeed;
	public int direction = 1; //-1 / 1
	float increment = 1;
	public bool isDashing;
	public int combo = 0;
	int maxCombo = 3;
	float resetComboTimer;
	bool queueCombo;
    public Animator animator;
	public bool deactivateMovement;
	public bool deactivateRotation;
    SpriteRenderer renderer;
	float xAxis;
	float yAxis;
    Sprite sprite;
	float acc = 0.1f;
    public roomGen room;
	public float animationTimer = 3;
	public float timeScaleDelay;
	bool hit = false;
	float xrot;
	float yrot;
	float zrot = -20;
	bool isRotating;
	public GameObject rollDustPrefab;

	float syringeTimer = 3f;
	public GameObject syringe;
	public float r;
	public float g;
	public float b;
	public GameObject upgradeText;
	float upgradeTimer = 3f;
	Vector3 upgradeTextOrigin;
	float rollTimer;
	public GameObject greenBubbles;

	public float score;
	public float health = 100;

	public float damage = 1;
	public float injections = 0;

	Vector3 originPos;

	float rotateTimes = 20f;

	Vector2 prevPos;

	public ParticleSystem blood;
	//ssa
	void Start () {
		upgradeTextOrigin = upgradeText.GetComponent<Text>().transform.position;
		rb = GetComponent<Rigidbody2D>();
		animator = GetComponent<Animator>();
		prevPos = transform.position;
        renderer = GetComponent<SpriteRenderer>();
        sprite = renderer.sprite;
		originPos = transform.position;
    }

	void Update () {
		Debug.Log("hp: " + health);
		if(Input.GetKey(KeyCode.F1)){
			health -= 1;
		}
		xAxis = Input.GetAxisRaw("Horizontal");
		yAxis = Input.GetAxisRaw("Vertical");

        renderer.sortingOrder = Mathf.Abs((int)((this.transform.position.y + (sprite.bounds.max.x - sprite.bounds.min.x) / 2) * 20));

        rb.velocity = new Vector2(Mathf.Lerp(0, xAxis*movespeed, 0.2f), Mathf.Lerp(0,yAxis*movespeed,0.2f));
		if(!deactivateRotation){
			this.transform.localScale = new Vector3(-1*direction, 1, 1);
			if(xAxis >= 1){
				direction = 1;
			}
			if(xAxis <= -1){
				direction = -1;
			}
		}

		if(rollTimer > 0){
			rollTimer -= 20f * Time.deltaTime;
		} else {
			rollTimer = 0;
		}

		if(Input.GetKeyDown(KeyCode.X) && !isRotating && rollTimer <= 0 && !deactivateMovement){
			isRotating = true;
			rollTimer = 10f;
			rotateTimes = 0;
		}

        if (rb.velocity == Vector2.zero && animator.GetBool("isWalking")) {
            animator.SetBool("isWalking", false);
        } else if (rb.velocity != Vector2.zero && !animator.GetBool("isWalking")) {
            animator.SetBool("isWalking", true);
        }

		Vector3 mousePos = Camera.main.ScreenToWorldPoint(Input.mousePosition);

		if(timeScaleDelay > 0){
			timeScaleDelay -= 0.01f;
			Time.timeScale += 0.0001f;
		} else {
			timeScaleDelay = 0;
		}
		if(Time.timeScale < 1 && timeScaleDelay <= 0){
			Time.timeScale += 0.1f;
		}
		if(Time.timeScale >= 1){
			Time.timeScale = 1;
		}

		if(isMoving()){
			queueCombo = false;
			if(movespeed < 2f){
				movespeed += 60f * acc * Time.deltaTime;
			}
		} else {
			if(movespeed > 0f){
				movespeed -= 60f * acc * Time.deltaTime;
			}
		}
		movespeed = Mathf.Clamp(movespeed,0,2);

		if(Input.GetKey(KeyCode.Z) && !isDashing && !queueCombo && !deactivateMovement){
			combo++;
			startDashing();
			isDashing = true;
		}
		if(isDashing){
			deactivateMovement = true;
			deactivateRotation = true;
		} else {
			deactivateMovement = false;
			deactivateRotation = false;
		}
		if(animationTimer <= 0){
			animationTimer = 3f;
			if(queueCombo){
				combo++;
				startDashing();
			} else {
				isDashing = false;
				combo = 0;
			}
		}

		if(animator.GetBool("syringe")){
			freeze();
			syringeTimer -= ((0.6f*10))*animator.speed * Time.deltaTime;
		}
			

		if(syringeTimer <= 0){
			animator.SetBool("syringe", false);
			syringeTimer = 3f;
			Instantiate(syringe).transform.position = this.transform.position;
			injections++;
			Instantiate(greenBubbles).transform.position = this.transform.position;
		}
		if(isDashing && animationTimer > 0){
			animationTimer -= ((0.9f*10))*animator.speed * Time.deltaTime;
		}

		//this.GetComponent<SpriteRenderer>().material.color = new Color(0,0+injections,0);
		//this.GetComponent<SpriteRenderer>().color = new Color(1,1+injections,1);
		this.GetComponent<Renderer>().material.color = new Color(1,Mathf.Lerp(1, 1+injections, 0.2f),1);


		animator.SetInteger("combo",combo);
		animator.SetBool("isDashing",isDashing);
			
	}

	void FixedUpdate(){
		if(isRotating){
			roll();
		}
	}

	void roll(){
		if(rotateTimes < 20){
			if(rotateTimes%5==0){GameObject wew = Instantiate(rollDustPrefab); wew.transform.position = new Vector2(this.transform.position.x + 0.5f, this.transform.position.y); }
			zrot -= 10 * Time.deltaTime;
			this.transform.Rotate(new Vector3(0, 0,zrot));
			rb.velocity = new Vector2(60f*direction, 0) * Time.deltaTime;
			//transform.Translate(new Vector2(this.transform.position.x+0.0001f*direction,this.transform.position.y));
			//transform.position += new Vector2(this.transform.position.x + 0.01f * direction, this.transform.position.y);
			rotateTimes++;
		} else {
			this.transform.rotation = Quaternion.Euler(new Vector3(0,0,0));
			zrot = -20f;
			isRotating = false;
			rb.velocity = Vector2.zero;
		}
	}

	void startDashing(){
		StartCoroutine(dash(direction));
	}

	bool isMoving(){
		if(deactivateMovement){return false;}

		if(xAxis != 0 || yAxis != 0){
			return true;
		} else {
			return false;
		}
	}


	void freeze(){
		this.transform.position = prevPos;
	}

	bool isAnimationFinished(string name){
		if(animationTimer <= 0 && animator.GetCurrentAnimatorStateInfo(0).IsName(name)){
			animationTimer = 180f;
			return true;
		}
		return false;
	}


	void OnTriggerStay2D(Collider2D col){
		if(col.gameObject.CompareTag("Pickup")){
			if(Input.GetKey(KeyCode.E)){
				animator.SetBool("syringe", true);
				upgradeText.SetActive(true);
				Destroy(col.gameObject);
			}
		}
		if(col.gameObject.CompareTag("Win")){
			if(Input.GetKey(KeyCode.E)){
				transform.position = originPos;
			}
		}
	}

	void OnTriggerEnter2D(Collider2D col){
		if(col.gameObject.CompareTag("Bullet") && !isRotating){
			health--;
			StartCoroutine(flash());
			hit = true;
			Destroy(col.gameObject);
		}
	}

	IEnumerator flash(){
		int val = 100;
		for(int i=0;i<val;i++){
			if(i%((val/2)/2)/2/2 >= (((val/2)/2)/2)/2/2){
				renderer.material.color = Color.red;
			} else {
				renderer.material.color = Color.white;
			}
			yield return null;
		}
		hit = false;
		renderer.material.color = Color.white;
	}


	void LateUpdate(){
		prevPos = this.transform.position;
	}

	IEnumerator rotate(){
		for(int i=0;i<20;i++){
			if(i%5==0){GameObject wew = Instantiate(rollDustPrefab); wew.transform.position = new Vector2(this.transform.position.x + 0.5f, this.transform.position.y); }
			zrot -= 10 * Time.deltaTime;
			this.transform.Rotate(new Vector3(0, 0,zrot));
			rb.velocity += new Vector2(250f * direction, 0);
			yield return null;
		}
		this.transform.rotation = Quaternion.Euler(new Vector3(0,0,0));
		zrot = -20f;
		isRotating = false;
	}

	IEnumerator dash(int intendedDirection){
		queueCombo = false;

		switch(combo){
		case 1:
			dashspeed = 0.1f;
			break;
		case 2:
			dashspeed = 0.5f;
			break;

		case 3:
			//final blow
			dashspeed = 1f;
			break;
		}
		if(combo >= 3){
			combo = 0;
		}

		while(increment > 0){
			if(increment <= 0){break;}
			rb.velocity += new Vector2(dashspeed * increment * intendedDirection,0) * Time.deltaTime;
			increment -= (1) * Time.deltaTime;
			yield return null;
		}
		increment = 1;
		if(Input.GetKey(KeyCode.Z)){
			queueCombo = true;
		}
	}
}
