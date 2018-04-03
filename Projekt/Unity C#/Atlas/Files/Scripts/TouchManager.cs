using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.EventSystems;
using Lean.Touch;

/*
using UnityEngine;

namespace Lean.Touch
{
	// This modifies LeanCameraMove to be smooth
	public class LeanCameraMoveSmooth : LeanCameraMove
	{
		[Tooltip("How quickly the zoom reaches the target value")]
		public float Dampening = 10.0f;

		private Vector3 remainingDelta;

		protected override void LateUpdate()
		{
			// Store the current position
			var oldPosition = transform.localPosition;

			// Call LeanCameraMove.LateUpdate
			base.LateUpdate();

			// Add to remainingDelta
			remainingDelta += transform.localPosition - oldPosition;

			// Get t value
			var factor = LeanTouch.GetDampenFactor(Dampening, Time.deltaTime);

			// Dampen remainingDelta
			var newDelta = Vector3.Lerp(remainingDelta, Vector3.zero, factor);

			// Shift this position by the change in delta
			transform.localPosition = oldPosition + remainingDelta - newDelta;

			// Update remainingDelta with the dampened value
			remainingDelta = newDelta;
		}
	}
}
*/

/*
using UnityEngine;

namespace Lean.Touch
{
	// This modifies LeanCameraZoom to be smooth
	public class LeanCameraZoomSmooth : LeanCameraZoom
	{
		[Tooltip("How quickly the zoom reaches the target value")]
		public float Dampening = 10.0f;

		private float currentZoom;

		protected virtual void OnEnable()
		{
			currentZoom = Zoom;
		}

		protected override void LateUpdate()
		{
			// Use the LateUpdate code from LeanCameraZoom
			base.LateUpdate();

			// Get t value
			var factor = LeanTouch.GetDampenFactor(Dampening, Time.deltaTime);

			// Lerp the current value to the target one
			currentZoom = Mathf.Lerp(currentZoom, Zoom, factor);

			// Set the new zoom
			SetZoom(currentZoom);
		}
	}
}
*/

public class TouchManager : MonoBehaviour {
	
	private Mouse mouse;

	Camera camera;
	float xStart, yStart;
	float orthoZoomSpeed = 0.01f;


	void Start () {
		mouse = GetComponent<Mouse>();
		camera = Camera.main;
		xStart = Input.mousePosition.x;
		yStart = Input.mousePosition.y;
	}
		
	void Update () {
		/*if(mouse != null){
			if(!mouse.canClick || EventSystem.current.IsPointerOverGameObject()) return;
			if(Input.mousePosition.x <= 0 || Input.mousePosition.x > Screen.width) return;
			if(Input.mousePosition.y <= 0 || Input.mousePosition.y > Screen.height) return;
			//moveCameraPC();
			xStart = Input.mousePosition.x;
			yStart = Input.mousePosition.y;
			pinchZoom();
			moveCameraMobile();
			camera.transform.position = new Vector3(Mathf.Clamp(camera.transform.position.x, -5, 5), Mathf.Clamp(camera.transform.position.y, -5, 5), camera.transform.position.z);
		}*/
	}

	private void moveCameraPC(){
		float swipeDuration = 0;
		float swipeSpeed = 0;
		if(Input.GetMouseButton(0)){
			float xMouse = Input.mousePosition.x;
			float yMouse = Input.mousePosition.y;

			float xDelta = xMouse - xStart;
			float yDelta = yMouse - yStart;

			swipeDuration++;
			swipeSpeed = Mathf.Abs((xStart - Input.mousePosition.x)/swipeDuration);
	
			if(swipeSpeed > 40) return;

			camera.transform.position -= new Vector3(xDelta*0.01f, yDelta*0.01f, 0);
		}
	}
	private void moveCameraMobile(){
		Touch t = Input.GetTouch(0);
		camera.transform.position -= new Vector3(t.deltaPosition.x * 0.01f,t.deltaPosition.y * 0.01f,0);
	}

	public void pinchZoom(){
		if(Input.touchCount > 0){
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
	}
}
