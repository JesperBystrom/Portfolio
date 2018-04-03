using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class KillParticles : MonoBehaviour {

	ParticleSystem ps;

	void Start () {
		ps = GetComponent<ParticleSystem>();
	}

	void Update () {
		if(!ps.IsAlive()){
			Destroy(this.gameObject);
		}
	}
}
