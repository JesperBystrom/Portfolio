using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

enum e_objects {
	GROUND,
	BALL,
	PLAYER,
	DOOR
};

public class UI_ObjectSwap : MonoBehaviour {

	public int curArrayIndex;
	public GameObject[] objects = new GameObject[5];
	public Text text;

	void Start () {
	}

	void Update () {
		if(text != null){
			text.text = objects[curArrayIndex].name;
		}

		if(Input.GetKeyDown(KeyCode.F1) && curArrayIndex > 0){
			curArrayIndex -= 1;
		} else 	if(Input.GetKeyDown(KeyCode.F2) && curArrayIndex < objects.Length-1){
			curArrayIndex += 1;
		}
	}
}
