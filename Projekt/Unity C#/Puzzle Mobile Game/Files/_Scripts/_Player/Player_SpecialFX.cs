using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Player_SpecialFX : MonoBehaviour {

	public GameObject[] particles = new GameObject[2];


	private float startParticleTime = 0.3f;
	private float spawnParticleTimer;
	private Player_Movement playerMovement;

	private float airTimer = 0f;
	private float oldY;
	private bool inAir = false;
	private bool spawnedParticles = false;

	void Start () {
		spawnParticleTimer = startParticleTime;
		playerMovement = GetComponent<Player_Movement>();
		oldY = transform.position.y;
	}

	void Update () {
			
		/*if((Mathf.Abs(transform.position.y - oldY) > 1)){
			inAir = true;
		}*/
		if(GetComponent<Player_Movement>().isGrounded2() == false){
			inAir = true;
			spawnedParticles = false;
		} else {
			inAir = false;
		}

		if(GetComponent<Player_Movement>().isGrounded2() && !spawnedParticles){
			Instantiate(particles[1]).transform.position = new Vector3(transform.position.x, transform.position.y+1f, transform.position.z);
			oldY = transform.position.y;
			inAir = false;
			spawnedParticles = true;
		}

		if(!isInAir()){
			if(playerMovement.movespeed >= playerMovement.maxSpeed){
				spawnParticleTimer -= 1 * Time.deltaTime;
				if(spawnParticleTimer <= 0){
					Instantiate(particles[0]).transform.position = new Vector3(transform.position.x + 0.5f, transform.position.y+1f, transform.position.z) - Vector3.back * 0.1f;
					spawnParticleTimer = startParticleTime;
				}
			}
		}
	}
	void OnCollisionEnter(Collision col){
	}

	public bool isInAir(){
		return inAir;
	}
}
