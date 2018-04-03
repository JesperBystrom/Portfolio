using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Message : MonoBehaviour {

	public string fullstring = "this is a test string";
	public string str = "";
	string charToAdd;
	public int charAt;
	float timer = 0.5f;

	void Start () {
		
	}
	void Update () {
		if(Input.GetKey(KeyCode.X)){
			timer = 0;
		}
		if(timer <= 0){
			if(charAt < fullstring.Length){
				char[] b = fullstring.ToCharArray();
				charToAdd = b[charAt].ToString();
				str += charToAdd;
				charAt++;
				charToAdd = "";
				timer = 0.5f;
			}
		} else {
			timer -= 20*Time.deltaTime;
		}
		this.GetComponent<Text>().text = str;
		if(str.Equals(fullstring)){
			transform.GetChild(0).gameObject.SetActive(true);
			if(Input.GetKeyDown(KeyCode.Z)){
				this.GetComponentInParent<scale>().open = false;
				if(GetComponentInParent<scale>().queuedDialogues.Count > 0 && GetComponentInParent<scale>().isQueued){
					GetComponentInParent<scale>().queuedDialogues.RemoveAt(0);
					GetComponentInParent<scale>().isQueued = false;
				}

				if(GetComponentInParent<scale>().queuedFaces.Count > 0 && GetComponentInParent<scale>().isFaceQueued){
					GetComponentInParent<scale>().queuedFaces.RemoveAt(0);
					GetComponentInParent<scale>().isFaceQueued = false;
				}
			}
		}
	}
}
