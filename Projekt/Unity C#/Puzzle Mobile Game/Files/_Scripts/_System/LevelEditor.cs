using System.Collections;
using System.Collections.Generic;
using UnityEngine;


public class LevelEditor : MonoBehaviour {

	public Camera camera;
	public GameObject grid;
	private GameObject player;
	private Player_Movement playermovement;
	public GameObject ground;

	private enum e_dir  {
		FORWARD,
		BACKWARD,
		RIGHT,
		LEFT,
		SIZE
	};

	private enum e_vectorDirs {
		FORWARD,
		BACKWARD,
		RIGHT,
		LEFT,
		SIZE
	}
	private float gridSize = 6f;
	private Vector3 cameraPos;
	public SaveLoad saveLoad;
	public UI_ObjectSwap objectSwap;
	private bool moveObjectDown = false;
	private bool moveUp = false;
	private float yRoot;

	private string[] groundBlocks = {
		"GroundNoGrass(Clone)",
		"GroundGrass(Clone)"
	};
		

	void Start () {
		//player = GameObject.FindWithTag("Player");
		//playermovement = player.GetComponent<Player_Movement>();
		cameraPos = new Vector3(17f, 59.71f, -20.55f);
		yRoot = grid.transform.position.y;
	}

	private void removeObjectIfEqual(string compareName, GameObject compareObject){
		for(int i=2;i<DataToBeSaved.objectList.Count;i += 3){
			string name = DataToBeSaved.objectNames[i/3];
			float x = DataToBeSaved.objectList[i-2];
			float y = DataToBeSaved.objectList[i-1];
			float z = DataToBeSaved.objectList[i];

			Vector3 pos = new Vector3(x,y,z);

			if(name.Equals(compareName) && pos == compareObject.transform.position){
				Destroy(compareObject);
			}
		}
	}
		
	void Update () {

		GUI.UnfocusWindow();

		camera.transform.position = cameraPos;
		camera.transform.rotation = Quaternion.Euler(90f,0,0);

		Ray mouseRay = camera.ScreenPointToRay(Input.mousePosition);
		RaycastHit rayHit;
		if(Physics.Raycast(mouseRay, out rayHit) && Input.GetMouseButtonDown(0)){
			Debug.Log("Hit something!" + rayHit.collider.gameObject.name);
			if(rayHit.collider.gameObject.name.EndsWith("(Clone)")){
				int ID = rayHit.collider.gameObject.GetComponent<UniqueID>().ID;

				if(saveLoad.allObjectsInRoom[ID] != null){
					int IDThatDissapered = ID;

					saveLoad.allObjectsInRoom.RemoveAt(ID);

					saveLoad.numberOfInstances--;

					Destroy(rayHit.collider.gameObject);

					for(int i=0;i<saveLoad.allObjectsInRoom.Count;i++){
						if(saveLoad.allObjectsInRoom[i].GetComponent<UniqueID>().ID > IDThatDissapered){
							saveLoad.allObjectsInRoom[i].GetComponent<UniqueID>().ID -= 1;
						}
					}

				} else {
					Debug.Log("the id is null");
				}
			}
		}
		Debug.Log("INSTANCE NUMBER: " + saveLoad.numberOfInstances);
			


		//Spawn objects
		if(Input.GetKeyDown(KeyCode.Space)){
			Debug.Log("COUNT_OBJECT: " + saveLoad.allObjectsInRoom.Count);

			GameObject temp = Instantiate(objectSwap.objects[objectSwap.curArrayIndex]);
			temp.AddComponent<UniqueID>();

			//Set it to the grid position.
			temp.transform.position = new Vector3(grid.transform.position.x,grid.transform.position.y, grid.transform.position.z);

			if(temp.name.Equals("door(Clone)")){
				temp.transform.position = new Vector3(grid.transform.position.x,grid.transform.position.y, grid.transform.position.z) - Vector3.left * 3f;
				Debug.Log("THIS IS A DOOR");
			}

			for(int i=0;i<saveLoad.allObjectsInRoom.Count;i++){
				for(int j=0;j<saveLoad.allObjectsInRoom.Count;j++){
					if(saveLoad.allObjectsInRoom[i].name.Contains("Grass")){
						ground = saveLoad.allObjectsInRoom[i];
						Debug.Log("THIS IS A GRASS BLOCK");
						break;
					}
				}
			}

			//If the layer is 1, move the object down towards the ground.

			Renderer tempRenderer = temp.GetComponent<Renderer>();
			if(tempRenderer == null){
				tempRenderer = temp.GetComponentInChildren<Renderer>();
			}

			if(saveLoad.layer == 1 || saveLoad.layer == 2){
				Debug.Log(ground.transform.localScale.y);
				Debug.Log("GROUND Y: " + ground.transform.position.y);

				Renderer groundRenderer = ground.GetComponent<Renderer>();
				if(groundRenderer == null){
					groundRenderer = ground.GetComponentInChildren<Renderer>();
				}

				while(temp.transform.position.y < (ground.transform.position.y + groundRenderer.bounds.size.y)){
					temp.transform.position = new Vector3(temp.transform.position.x, temp.transform.position.y + 1, temp.transform.position.z);
					Debug.Log("IS IT THERE YET");
				}
			}

			if(saveLoad.layer == 2){
				for(int i=0;i<saveLoad.allObjectsInRoom.Count;i++){
					if(temp.tag.Equals(saveLoad.allObjectsInRoom[i].tag)){

						RaycastHit hit;

						if(Physics.Raycast(new Ray(temp.transform.position, Vector3.forward*100f), out hit)){
							temp.transform.position = new Vector3(hit.transform.position.x, hit.transform.position.y, hit.transform.position.z - (gridSize-2f));
						}
						if(Physics.Raycast(new Ray(temp.transform.position, Vector3.back*100f), out hit)){
							temp.transform.position = new Vector3(hit.transform.position.x, hit.transform.position.y, hit.transform.position.z + (gridSize-2f));
						}
						if(Physics.Raycast(new Ray(temp.transform.position, Vector3.left*100f), out hit)){
							Debug.Log("hi");
							temp.transform.position = new Vector3(hit.transform.position.x - (gridSize-2f), hit.transform.position.y, hit.transform.position.z);
						}
						if(Physics.Raycast(new Ray(temp.transform.position, Vector3.right*100f), out hit)){
							Debug.Log("hir");
							temp.transform.position = new Vector3(hit.transform.position.x + (gridSize-2f), hit.transform.position.y, hit.transform.position.z);
						}
					}
				}
				//temp.transform.position = new Vector3(temp.transform.position.x - tempRenderer.bounds.size.x, temp.transform.position.y, temp.transform.position.z);
			}



			//Increase the amount of instances and add the object to the list
			saveLoad.numberOfInstances++;
			temp.GetComponent<UniqueID>().ID = saveLoad.numberOfInstances;
			saveLoad.allObjectsInRoom.Add(temp);
		}

		if(Input.GetKey(KeyCode.X)){
			rotateDir("X");
		}
		if(Input.GetKey(KeyCode.Y)){
			rotateDir("Y");
		}
		if(Input.GetKey(KeyCode.Z)){
			rotateDir("Z");
		}

		for(int i=0;i<(int)e_dir.SIZE;i++){
			moveGrid(i);
			moveCamera(i);
		}
	}

	private void rotateDir(string dir){

		int inst = (saveLoad.numberOfInstances-1);

		Quaternion rot = saveLoad.allObjectsInRoom[inst].transform.rotation;

		float rotX = rot.x;
		float rotY = rot.y;
		float rotZ = rot.z;

		if(dir.Equals("X")){ rotX += 1; }
		if(dir.Equals("Y")){ rotY += 1; }
		if(dir.Equals("Z")){ rotZ += 1; }

		rot = Quaternion.Euler(rotX, rotY, rotZ);

		saveLoad.allObjectsInRoom[inst].transform.Rotate(rotX, rotY, rotZ);
	}

	public void resetYPosition(){
		grid.transform.position = new Vector3(grid.transform.position.x, yRoot, grid.transform.position.z);
	}

	public void moveGridUp(){
		grid.transform.position += Vector3.up * gridSize/4;
	}

	public void moveGridDown(){
		grid.transform.position += Vector3.down * gridSize/4;
	}

	void moveGrid(int dir){
		Vector3 pos = grid.transform.position;

		switch(dir){
		case (int)e_dir.FORWARD:
			if(Input.GetKeyDown(KeyCode.W)){
				pos += Vector3.forward * gridSize;
			}
			break;

		case (int)e_dir.BACKWARD:
			if(Input.GetKeyDown(KeyCode.S)){
				pos += Vector3.back * gridSize;
			}
			break;

		case (int)e_dir.RIGHT:
			if(Input.GetKeyDown(KeyCode.D)){
				pos += Vector3.right * gridSize;
			}
			break;

		case (int)e_dir.LEFT:
			if(Input.GetKeyDown(KeyCode.A)){
				pos += Vector3.left * gridSize;
			}
			break;
		}
		grid.transform.position = pos;
	}

	void moveCamera(int dir){
		Vector3 pos = cameraPos;

		switch(dir){
		case (int)e_dir.FORWARD:
			if(Input.GetKey(KeyCode.UpArrow)){
				pos += Vector3.forward;
			}
			break;

		case (int)e_dir.BACKWARD:
			if(Input.GetKey(KeyCode.DownArrow)){
				pos += Vector3.back;
			}
			break;

		case (int)e_dir.RIGHT:
			if(Input.GetKey(KeyCode.RightArrow)){
				pos += Vector3.right;
			}
			break;

		case (int)e_dir.LEFT:
			if(Input.GetKey(KeyCode.LeftArrow)){
				pos += Vector3.left;
			}
			break;
		}
		cameraPos = pos;
	}
}
