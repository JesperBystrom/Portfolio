using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class UI_Init : MonoBehaviour {

	private GameObject pickupUI;

	void Start () {
		pickupUI = (GameObject)Resources.Load("UI/pickupUI");
		Instantiate(pickupUI).transform.position = new Vector3(15.7f, 74.9f, 66.5f);
		/*
		obj.transform.SetParent(transform);
		obj.transform.position = new Vector3(-228.4f, -130.9f, 13.3f);
		pickupUI.transform.localPosition = obj.transform.position;*/
	}
}
