using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Player_WinLevel : MonoBehaviour {

	public GameObject winPad;
	public Light light;


	private bool winLevel = false;

	[SerializeField] private float rotSpeed;
	private float rotIncrease = 1f;
	private float scaleZX = 0.8f;
	private float scaleY = 0.8f;
	private MeshCollider meshcol;
	private BoxCollider boxcol;

	void Start () {
		meshcol = GetComponent<MeshCollider>();
		boxcol = GetComponent<BoxCollider>();
		light.enabled = false;
	}

	void Update () {
		float distance = Vector3.Distance(transform.position, winPad.transform.position);

		if(Input.GetKeyDown(GetComponent<KeyManager>().key_action) && distance < 0.5f){
			winLevel = true;
		}
		if(rotSpeed > 1f){
			rotIncrease = 1f * rotSpeed * Time.deltaTime;
		}

		if(rotSpeed > 10f){
			transform.localScale = new Vector3(scaleZX,scaleY,scaleZX);
			light.enabled = true;
			light.intensity += 1f * Time.deltaTime;
			light.transform.position = new Vector3(transform.position.x, transform.position.y + 4f, transform.position.z);
			float yTemp = light.areaSize.y + 2f;
			light.areaSize = new Vector2(light.areaSize.x, yTemp);
			if(scaleZX > 0 && scaleY < 1.5f){
				scaleZX -= 0.5f * Time.deltaTime;
				scaleY += 0.5f * Time.deltaTime;
				if(meshcol.enabled){
					meshcol.enabled = false;
				}
				if(!boxcol.enabled){
					boxcol.enabled = true;
				}
			} else {
				GetComponent<Rigidbody>().AddForce(Vector3.up * 60f);
				GetComponent<Player_Movement>().cameraActivate = false;
				boxcol.enabled = false;
				light.range += 1f * Time.deltaTime;
			}
		}


		if(winLevel){
			rotSpeed += rotIncrease;
			transform.Rotate(0,rotSpeed,0);
		}
	}

	/*void OnCollisionEnter(Collision col){
		if(col.gameObject == winPad){
			winLevel = true;
		}
	}

	void OnCollisionExit(Collision col){
		if(col.gameObject == winPad){
			winLevel = false;
		}
	}*/
}
