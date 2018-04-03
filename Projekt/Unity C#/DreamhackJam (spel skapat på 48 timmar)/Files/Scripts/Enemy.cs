using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.AI;

enum e_enemyStates{
	IDLE,
	CHASE,
	ATTACK
}

public class Enemy : MonoBehaviour {

	int state = (int)e_enemyStates.CHASE;
	float xdir;
	float ydir;
	int movespeed = 3;
	GameObject player;
	Renderer renderer;
	public GameObject blood;
	Rigidbody2D rb;
	public GameObject prefabBullet;

	float health = 3f;
	bool hit = false;
	float attackTimer;
	float changeDestinationTimer;
	Vector2 target;

	float timerUntilStartAgain = 3f;

	GameObject marker;
	public GameObject fieldToWalkOn;
	SpriteRenderer render;
	public GameObject impact;
	Vector2 shootTowards;
	float minAttackTime = 150;
	float maxAttackTime = 850;
	Sprite sprite;
	bool isDead;

	public GameObject bloodPrefab;
	public LayerMask layerMask;

	bool bounced = false;

	void Start () {
		attackTimer = Random.Range(minAttackTime,maxAttackTime);
		player = GameObject.FindWithTag("Player");
		renderer = GetComponent<Renderer>();
		rb = GetComponent<Rigidbody2D>();
		marker = (GameObject)Instantiate(Resources.Load("prefabs/EnemyDestination"));
		Random.seed = System.DateTime.Now.Millisecond;
		render = GetComponent<SpriteRenderer>();
		sprite = render.sprite;
	}

	void Update () {

		render.sortingOrder = Mathf.Abs((int)((this.transform.position.y + (sprite.bounds.max.x - sprite.bounds.min.x) / 2) * 20));

		float dist = Vector2.Distance(this.transform.position, player.transform.position);

		if(dist > 1f){
			state = (int)e_enemyStates.IDLE;
		} else if(dist < 1f && dist > 0.6f) {
			state = (int)e_enemyStates.CHASE;
		}
		else if(dist < 0.6f) {
			state = (int)e_enemyStates.ATTACK;
		}

		float min = -this.GetComponent<BoxCollider2D>().bounds.size.x;
		float radius = this.GetComponent<BoxCollider2D>().bounds.size.x;

		if(!hit && !isDead){
			this.transform.position = Vector2.MoveTowards(this.transform.position, target, 0.1f*movespeed*Time.deltaTime);
		}

		if(Vector2.Distance(player.transform.position, this.transform.position) < 0.3f){
			changeDestinationTimer -= 2;
		}

		Vector2 norm = (player.transform.position - this.transform.position).normalized;

		if(norm.x > 0){ render.flipX = true; } else { render.flipX = false; }

		if(state != (int)e_enemyStates.IDLE && state != (int)e_enemyStates.ATTACK){

			changeDestinationTimer--;
			if(changeDestinationTimer <= 0){
				if(fieldToWalkOn != null){
					target = new Vector2(fieldToWalkOn.transform.position.x + Random.Range(min,radius), fieldToWalkOn.transform.position.y);
					Debug.Log("Y: " + fieldToWalkOn.transform.position.y);
					changeDestinationTimer = 200f;
					marker.GetComponent<EnemyDestination>().collidingWith = null;
				}
			}
		}
		float x = rb.velocity.x;
		float y = rb.velocity.y;


		float wew = 5;
		if(x > 0){
			x -= wew * Time.deltaTime;
		} else if(x < 0){
			x += wew * Time.deltaTime;
		}
		if(y > 0){
			y -= wew * Time.deltaTime;
		} else if(y < 0){
			y += wew * Time.deltaTime;
		}
		if(x < 0.1f && x > -0.1f){ x = 0; }
		if(y < 0.1f && y > -0.1f){ y = 0; }

		rb.velocity = new Vector2(x,y);
		if(marker.GetComponent<EnemyDestination>().collidingWith != null){ changeDestinationTimer = 0; }
		marker.transform.position = target;

		switch(state){
		case (int)e_enemyStates.IDLE:
			break;
		case (int)e_enemyStates.CHASE:
			//this.transform.position = Vector2.MoveTowards(this.transform.position, target, 0.1f*movespeed*Time.deltaTime);
			break;

		case (int)e_enemyStates.ATTACK:
                //this.transform.position = Vector2.MoveTowards(this.transform.position, target, 0.1f*movespeed*Time.deltaTime);
                if (!isDead)
                {
                    RaycastHit2D hit;
                    if (hit = Physics2D.Raycast(transform.position, -(transform.position - player.transform.position), Vector2.Distance(transform.position, player.transform.position), layerMask.value))
                    {
                        Debug.Log("hit");
                        Debug.DrawRay(transform.position, -(transform.position - player.transform.position), Color.red);
                        Debug.Log("WALL: " + hit.collider.gameObject.name);
                    }
                    else
                    {
                        Debug.DrawRay(transform.position, -(transform.position - player.transform.position), Color.blue);
                        attackTimer--;
                        if (attackTimer > 40 && attackTimer < 50)
                        {
                            shootTowards = player.transform.position;
                        }
                        if (attackTimer <= 0)
                        {
                            attackTimer = Random.Range(minAttackTime, maxAttackTime);
                            Bullet b = Instantiate(prefabBullet).GetComponent<Bullet>();
                            b.targetPos = shootTowards;
                            b.transform.position = this.transform.position;
                        }
                    }
                }
                break;

		}

    }

	int choose(int param1, int param2){
		float rand = Random.Range(0,1);
		if(rand == 0) { return param1; }
		if(rand == 1) { return param2; }
		return 0;
	}

	void kill(){
		//this.enabled = false;
		isDead = true;
		//Time.timeScale = 0.1f;
		Instantiate(impact).transform.position = this.transform.position;

		player.GetComponent<PlayerMovement>().timeScaleDelay = 0.2f;

		Vector2 norm = (player.transform.position - this.transform.position).normalized;
		rb.velocity = -norm * 155f * player.GetComponent<PlayerMovement>().damage * Time.deltaTime;
	}

	void OnTriggerEnter2D(Collider2D col){
	}

	//Take damage
	void OnTriggerStay2D(Collider2D col){
		if(isDead && col.gameObject.CompareTag("Wall") && !bounced){
			if(rb.velocity.x != 0 || rb.velocity.y != 0){
				Vector2 norm = (player.transform.position - this.transform.position).normalized;
				rb.velocity = norm * 75f * Time.deltaTime;
				bounced = true;
			}
		}
		if(col.gameObject.CompareTag("Walkable")){
			fieldToWalkOn = col.gameObject;
		}
		if(col.gameObject.CompareTag("Player")){
			PlayerMovement playermovement = col.gameObject.GetComponent<PlayerMovement>();

			if(playermovement.isDashing && !hit && playermovement.animationTimer <= 1 && !isDead){
				Vector2 norm = (player.transform.position - this.transform.position).normalized;
				rb.velocity = -norm * 50f * player.GetComponent<PlayerMovement>().damage * Time.deltaTime;
				for(int i=0;i<25;i++){
					BloodController b = Instantiate(bloodPrefab).GetComponent<BloodController>();
					b.transform.position = this.transform.position;
					b.dir = Random.Range(0,360);
					b.speed = Random.Range(0.5f,1);
				}
				//rb.velocity = -transform.forward * 1000f;
				//Time.timeScale = 0.25f;
				hit = true;
				Camera.main.GetComponent<MainCamera>().screenshake();
				StartCoroutine(flash());
				Instantiate(blood).transform.position = this.transform.position;
				health--;
				if(health <= 0){
					kill();
				}
			}
		}
	}

	IEnumerator flash(){
		int val = 10;
		for(int i=0;i<val;i++){
			renderer.material.color = Color.red;
			yield return null;
		}
		renderer.material.color = Color.white;
		hit = false;
	}
}
