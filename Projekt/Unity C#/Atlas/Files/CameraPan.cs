using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class CameraPan : MonoBehaviour {

	private bool isPanning;
	private bool tutorialPanning;
	private GameObject toCountry;
	private Camera cam;
	private int frame = 0;
	private Vector3 location;
	private Vector3 startLocation;
	private float zoom = 0;
	private Tutorial tutorial;
	public Lean.Touch.LeanCameraMoveSmooth moveSmooth;
	public Lean.Touch.LeanCameraZoomSmooth zoomSmooth;

	// Use this for initialization
	void Start () {
		cam = this.GetComponent<Camera>();
		startLocation = this.transform.position;
	}
	
	// Update is called once per frame
	void Update () {
		if(isPanning){
			Vector3 to = new Vector3(location.x, location.y, -1);
			this.transform.position = Vector3.Lerp(this.transform.position, to, 0.1f);//new Vector3(country.transform.position.x, country.transform.position.y, -1);
			if(zoom > 0 && Vector3.Distance(this.transform.position, to) < 0.05f){
				zoomSmooth.Zoom = Mathf.Lerp(zoomSmooth.Zoom, zoom, 2*Time.deltaTime);
				if(zoomSmooth.Zoom >= zoom-0.5f){
					isPanning = false;
					zoom = 0;
					tutorial.exit();
				}
			} else {
				if(Vector3.Distance(this.transform.position, to) < 0.05f){
					isPanning = false;
				}
			}
		}
		if(tutorialPanning){
			Vector3 to = new Vector3(toCountry.transform.position.x, 2.25f, -1); //Sweden's location
			this.transform.position = Vector3.Lerp(this.transform.position, to, 2*Time.deltaTime);//new Vector3(country.transform.position.x, country.transform.position.y, -1);
	
			if(Vector3.Distance(this.transform.position, to) < 0.05f){
				zoomSmooth.Zoom = Mathf.Lerp(zoomSmooth.Zoom, 0.5f, 2*Time.deltaTime);
				if(zoomSmooth.Zoom <= 0.56f){
					tutorialPanning = false;
					tutorial.gotoNextPhase();
				}
			}
			frame++;
			if(Input.GetMouseButtonDown(0) && frame >= 10){
				finishTutorialPan();
				frame = 0;
			}
		}
	}

	public void pan(GameObject country){
		Debug.Log("Panning to: " + country.name);
		isPanning = true;
		//toCountry = country;
		location = country.transform.position;
	}
	public void tutorialPan(GameObject country, Tutorial tutorial){
		tutorialPanning = true;
		toCountry = country;
		this.tutorial = tutorial;
	}
	public void panToOriginalLocation(){
		isPanning = true;
		location = startLocation;
		zoom = 3f;
	}
	public void finishTutorialPan(){
		zoomSmooth.Zoom = 0.5f;
		this.transform.position = new Vector3(toCountry.transform.position.x, 2.25f, -1);
		//tutorial.gotoNextPhase();
		tutorialPanning = false;
	}
	public bool isTutorialPanning(){
		return tutorialPanning;
	}
}
