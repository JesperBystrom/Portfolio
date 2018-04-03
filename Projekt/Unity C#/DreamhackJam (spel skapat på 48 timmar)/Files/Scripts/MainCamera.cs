using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class MainCamera : MonoBehaviour {

	float originX;
	float originY;
    public float lerpSpeed = 18f;
    public PlayerMovement playerM;
	public float time = 1f;
	public float timerUntilWakeUp = 8f;
	public float timerUntilShake = 5f;
	bool wokenUp = false;
	float fps;
	bool askedForFps = false;
	bool waiting = false;
	public Sprite[] faces;

	bool isShaking;
	void Start () {
        this.transform.position = playerM.transform.position;

    }
    public void moveCamera(Vector3 position) {
        this.transform.position = position;
    }
	void Update () {
		fps = (1.0f/Time.deltaTime);

		if(fps < 10 && Time.time > 2 && !askedForFps){
			openDialogue("Lagging? Disable special effects in the options menu!");
			askedForFps = true;
		}
			
		waiting = isDialogueQueued();





		////////////////////////////////////////DEVMODE ONLY ////////////////////////////////////////////////////

		timerUntilShake = 0;
		timerUntilWakeUp = 0;

		////////////////////////////////////////DEVMODE ONLY ////////////////////////////////////////////////////







		if(timerUntilWakeUp <= 0 && !wokenUp && !waiting){
			openDialogue("Muscle baby! Give up now, we have your parents...", 1);
			openDialogue("NEVER", 0);

			GameObject.FindWithTag("Player").GetComponent<SpriteRenderer>().enabled = true;

			if(!waiting){
				timerUntilWakeUp = 0;
				wokenUp = true;
			}
		} else if(timerUntilWakeUp > 0){
			timerUntilWakeUp -= 1 * Time.deltaTime;
		}

		if(timerUntilShake > 0){
			timerUntilShake -= 1 * Time.deltaTime;
		} else if(!wokenUp){
			screenshake();
		}
		if(!isShaking && wokenUp && timerUntilWakeUp <= 0){
			transform.position = Vector3.Lerp(transform.position, new Vector3(playerM.transform.position.x, playerM.transform.position.y, -10f), lerpSpeed * Time.deltaTime * Time.timeScale);
		} else if(!wokenUp){
			transform.position = new Vector3(transform.position.x, transform.position.y, -10f);
		}
    }

	public void screenshake(float intensity=0.001f){
		StartCoroutine(shake(intensity));
	}

	public bool isDialogueQueued(){
		if(GameObject.FindWithTag("DialogueBox").GetComponent<scale>().queuedDialogues.Count > 0){
			return true;
		}
		return false;
	}

	public void openDialogue(string txt="no text", int face=0){
		if(GameObject.FindWithTag("DialogueBox").GetComponent<scale>().open == false){
			GameObject.FindWithTag("DialogueBox").GetComponent<scale>().open = true;
			GameObject.FindWithTag("DialogueBox").transform.Find("FacePanel").Find("FaceImage").GetComponent<Image>().sprite = faces[face];
			GameObject.FindWithTag("DialogueBox").transform.Find("Text").GetComponent<Message>().fullstring = txt;
		} else {
			//GameObject.FindWithTag("DialogueBox").transform.Find("FacePanel").Find("FaceImage").GetComponent<Image>().sprite = faces[face];
			GameObject.FindWithTag("DialogueBox").GetComponent<scale>().queuedDialogues.Add(txt);
			GameObject.FindWithTag("DialogueBox").GetComponent<scale>().queuedFaces.Add(faces[face]);
		}
	}

	IEnumerator shake(float intensity){
		for(int i=0;i<50;i++){
			transform.position += new Vector3(Random.Range(-intensity,intensity), Random.Range(-intensity,intensity), 0);
			yield return null;
		}
	}
}
