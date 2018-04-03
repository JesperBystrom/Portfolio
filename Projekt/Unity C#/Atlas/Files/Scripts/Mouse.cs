using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.EventSystems;

public class Mouse : MonoBehaviour {

	float startX;
	float swipeDuration = 0;
	float swipeSpeed = 0;
	private Camera camera;
	private Main main;


	[HideInInspector]
	public bool canClick = true;

	[HideInInspector]
	public GameObject objectHoveringOver;

	void Start () {
		startX = Input.mousePosition.x;
		camera = Camera.main;
		main = GetComponent<Main>();
	}

	void Update () {
		if(Input.GetMouseButton(0)){
			float dirX = Mathf.Sign(startX - Input.mousePosition.x);
			swipeDuration++;
			swipeSpeed = Mathf.Abs((startX - Input.mousePosition.x)/swipeDuration);

		} else {
			startX = Input.mousePosition.x;
			swipeSpeed = 0;
			swipeDuration = 0;
		}

		Vector3 mousePos = Camera.main.ScreenToWorldPoint(Input.mousePosition);
		Vector2 mousePos2D = new Vector2(mousePos.x, mousePos.y); 
		RaycastHit2D hit = Physics2D.Raycast(mousePos2D, Vector2.up, 0.1f);
		if(hit && canClick && !EventSystem.current.IsPointerOverGameObject()){
			if(hit.collider.CompareTag("Country") && !main.isUIEnabled()){
				this.objectHoveringOver = hit.collider.gameObject;
			}
		} else {
			this.objectHoveringOver = null;
		}
	}

	public bool isOverCountry(){
		if(this.objectHoveringOver == null) return false;

		if(this.objectHoveringOver.CompareTag("Country")){
			return true;
		}
		return false;
	}
	public bool isOverCountry(GameObject o){
		if(this.objectHoveringOver.Equals(o)){
			return true;
		}
		return false;
	}

	public void enableClicking(){
		canClick = true;
	}
	public void disableClicking(){
		canClick = false;
	}

	public GameObject getCountry(){
		return this.objectHoveringOver;
	}
	public bool canHoverOverCountry(){
		return canClick && !EventSystem.current.IsPointerOverGameObject();
	}
}
