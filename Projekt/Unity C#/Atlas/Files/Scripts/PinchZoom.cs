using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class PinchZoom : MonoBehaviour {

	public float orthoZoomSpeed = 0.001f;
	private Camera camera;
	public bool isDragging = false;

	float startX;
	float startY;
	private float speed = 0.0001f;
	Main main;

	void Start () {
		camera = Camera.main;
		startX = Input.mousePosition.x;
		startY = Input.mousePosition.y;
		main = GetComponent<Main>();
	}

	public void pinchZoom(){
		Touch touchZero = Input.GetTouch(0);
		Touch touchOne = Input.GetTouch(1);

		Vector2 touchZeroPrevPos = touchZero.position - touchZero.deltaPosition;
		Vector2 touchOnePrevPos = touchOne.position - touchOne.deltaPosition;

		float prevTouchDeltaMag = (touchZeroPrevPos - touchOnePrevPos).magnitude;
		float touchDeltaMag = (touchZero.position - touchOne.position).magnitude;

		float deltaMagDiff = prevTouchDeltaMag - touchDeltaMag;

		if(camera.orthographic){
			camera.orthographicSize += deltaMagDiff * orthoZoomSpeed;
			camera.orthographicSize = Mathf.Max(camera.orthographicSize, 0.1f);
		}
	}

	public void moveCameraMobile(){
		Touch t = Input.GetTouch(0);
		if(t.deltaPosition.x > 1 && t.deltaPosition.y > 1){
			isDragging = true;
		}
		camera.transform.position = new Vector3(Mathf.Clamp(camera.transform.position.x, -5, 5), Mathf.Clamp(camera.transform.position.y, -5, 5), 0);
		camera.transform.position += new Vector3(t.deltaPosition.x * 0.01f,t.deltaPosition.y * 0.01f,0);
	}

	public void moveCameraPC(){
		float deltaX = (Input.mousePosition.x - startX);
		float deltaY = (Input.mousePosition.y - startY);

		//camera.transform.position = new Vector3(Mathf.Clamp(camera.transform.position.x, -5f, 5),Mathf.Clamp(camera.transform.position.y, -3f, 3),0);
		camera.transform.position += new Vector3(deltaX*speed, deltaY*speed, -1);
	}

	public bool canMoveScreen(){
		return true;//!main.informationUi.activeInHierarchy;
	}

	void Update () {
		if(Input.touchCount == 2){
			pinchZoom();
		}

		//temporary
		if(Input.GetMouseButton(0) && canMoveScreen()){
			moveCameraPC();
		} else {
			startX = Input.mousePosition.x;
			startY = Input.mousePosition.y;
		}

		if(Input.touchCount == 1 && canMoveScreen()){
			//moveCameraMobile();
		}
	}
}
