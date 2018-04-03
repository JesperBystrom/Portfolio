using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;

public class disableInLevelEditor : MonoBehaviour {

	void Start () {
	}

	void Update () {
		if(SceneManager.GetActiveScene().name == "LevelEditor"){
			foreach(var c in GetComponentsInChildren<MonoBehaviour>()) {
				c.enabled = false;
			}
		}
	}
}
