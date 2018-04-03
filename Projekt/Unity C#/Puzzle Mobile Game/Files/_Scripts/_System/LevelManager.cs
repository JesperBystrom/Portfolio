using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.SceneManagement;
using System.IO;

using UnityStandardAssets.ImageEffects;

public class LevelManager : MonoBehaviour {

	public GameObject cam;
	public Camera playerCamera;
	public GameObject player;
	public Player_Movement playerMovement;
	public GameObject door;

	private bool hasLevelChanged;
	private float timePassed = 0f;

	private Transform cameraAwake;
	private float timeUntilCameraMove = 3f; // Seconds
	bool restartingLevel = false;

	bool inDev = true;

	void Start(){

		hasLevelChanged = false;

		player = (GameObject)Resources.Load("hooman_v6");
		playerMovement = player.GetComponent<Player_Movement>();
		cam = (GameObject)Resources.Load("Main Camera");//GameObject.Find("Main Camera").GetComponent<Camera>();
		playerCamera = cam.GetComponent<Camera>();

		playerMovement.cameraActivate = false;
		cameraAwake = player.transform.Find("CameraPosStartOfLevel").transform;
		playerCamera.transform.position = cameraAwake.position;
		playerCamera.transform.rotation = cameraAwake.rotation;

		int t = SaveLoad.numberOfLevels(new DirectoryInfo(SaveLoad.getPath()));
		Debug.Log("numberOfFiles: " + (t+1));
		GetComponent<SaveLoad>().Save("level_" + (t+1));
		GetComponent<SaveLoad>().level = "level_0";
		loadNext();
	}

	void Update(){
		player = GameObject.FindWithTag("Player");
		if(player){
			playerMovement = player.GetComponent<Player_Movement>();
		}
		if(Input.GetKeyDown(KeyCode.T)){
			loadNext();
		}
		if(Input.GetKeyDown(KeyCode.R)){
			restartLevel();
		}

		if(player){
			if(player.GetComponent<Player_Win>().hasFaded()){
				restartingLevel = false;
			}
			if(player.transform.position.y < -10f && !restartingLevel){
				StartCoroutine(player.GetComponent<Player_Win>().fade((int)e_fadeDirection.OUT));
				restartLevel();
				restartingLevel = true;
			}

			if(playerMovement.isMoving()){
				playerMovement.cameraActivate = true;
				playerMovement.playerMovementActivate = true;
				playerMovement.activateAnimations = true;
			}
		}

		if(hasLevelChanged){
			timePassed++;
		}
	}

	public void loadNext(){
		//GetComponent<SaveLoad>().level = "level_1";

		string str = GetComponent<SaveLoad>().level.Replace("level_", "");
		int levelNumber = int.Parse(str);
		levelNumber += 1;

		GetComponent<SaveLoad>().level = "level_" + levelNumber;
		GetComponent<SaveLoad>().Load();

		hasLevelChanged = true;
	}

	public void restartLevel(){
		
		string str = GetComponent<SaveLoad>().level.Replace("level_", "");
		int levelNumber = int.Parse(str);

		GetComponent<SaveLoad>().level = "level_" + levelNumber;

		if(player.GetComponent<Player_Win>().hasFaded()){
			GetComponent<SaveLoad>().Load();
		}
	}
}
