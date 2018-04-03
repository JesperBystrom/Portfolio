using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class scale : MonoBehaviour {

	float s = 0;
	public bool open = false;
	public List<string> queuedDialogues = new List<string>();
	public List<Sprite> queuedFaces = new List<Sprite>();
	public bool readNext = false;
	public bool isQueued = false;
	public bool isFaceQueued = false;

	void Update () {

		if(queuedFaces.Count > 0 && !open && readNext){
			transform.Find("FacePanel").Find("FaceImage").GetComponent<Image>().sprite = queuedFaces[0];
			isFaceQueued = true;
		}

		if(queuedDialogues.Count > 0 && !open && readNext){
			open = true;
			GetComponentInChildren<Message>().fullstring = queuedDialogues[0];
			isQueued = true;
		}

		if(open){
			readNext = false;
			GameObject.FindWithTag("Player").GetComponent<PlayerMovement>().deactivateMovement = true;
			GameObject.FindWithTag("Player").GetComponent<PlayerMovement>().deactivateRotation = true;
			if(s < 1){
				s += 0.1f;
			}
		} else {
			if(s > 0){
				s -= 0.1f;
			} else {
				s = 0;
				GetComponentInChildren<Message>().charAt = 0;
				GetComponentInChildren<Message>().fullstring = "";
				GetComponentInChildren<Message>().str = "";
				GameObject.FindWithTag("Player").GetComponent<PlayerMovement>().deactivateMovement = false;
				GameObject.FindWithTag("Player").GetComponent<PlayerMovement>().deactivateRotation = false;
				readNext = true;
			}
		}
		this.transform.localScale = new Vector2(this.transform.lossyScale.x,s);
	}
}
