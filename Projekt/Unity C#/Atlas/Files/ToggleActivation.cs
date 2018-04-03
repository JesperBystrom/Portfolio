using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ToggleActivation : MonoBehaviour {

	public void toggle(){
		gameObject.SetActive(!gameObject.activeInHierarchy);
	}
}
