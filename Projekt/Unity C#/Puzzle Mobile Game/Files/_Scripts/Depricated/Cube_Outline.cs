using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Cube_Outline : MonoBehaviour {

	public Renderer render;
	public Material notOutlineMat;
	public Material outlineMat;
	public GameObject player;
	private bool outline = false;

	// Use this for initialization
	void Start () {
		render = this.gameObject.GetComponentInChildren<Renderer>();
		player = GameObject.FindWithTag("Player");
	}
	
	// Update is called once per frame
	void Update () {

		if(Vector3.Distance(player.transform.position, transform.position) < 6f){
			outline = true;
			Debug.Log("outline!");
		} else {
			outline = false;
		}

		if(outline){
			render.sharedMaterial = outlineMat;
		} else {
			render.sharedMaterial = notOutlineMat;
		}
	}
}
