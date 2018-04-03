using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Spin : MonoBehaviour {

	float rotY;

	void Start(){
		rotY = this.transform.rotation.y;
	}

	public void spin(int speed){
		rotY += speed;
		this.transform.Rotate(transform.rotation.x,rotY,transform.rotation.z);
		//this.transform.rotation = Quaternion.Euler(new Vector3(this.transform.rotation.x,rotY,this.transform.rotation.z));
	}
}
