using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using System.IO;
using System.Runtime.Serialization.Formatters.Binary;
using UnityEngine.UI;
using UnityEngine.SceneManagement;

public class SaveLoad : MonoBehaviour {

	string filePath;
	string filePathNames;
	string filePathRotation;
	//DataToBeSaved data;
	public GameObject ground;

	public UI_ObjectSwap objectSwap;

	public Text saveLevelText;
	public Text loadLevelText;

	public Button saveButton;
	public Button loadButton;

	private bool save = false;
	private bool load = false;
	public string level;

	public List<GameObject> allObjectsInRoom = new List<GameObject>();

	public float layer = 0;
	public int numberOfInstances = 0;

	void Start(){
		if(SceneManager.GetActiveScene().name == "LevelEditor"){
			objectSwap = GetComponent<UI_ObjectSwap>();

			saveButton.onClick.AddListener(OnSaveButtonClick);
			loadButton.onClick.AddListener(OnLoadButtonClick);
		}
	}

	private bool isLevelEditor(){
		if(SceneManager.GetActiveScene().name == "LevelEditor"){
			return true;
		}
		return false;
	}

	public void Save(string name=""){

		/*if(!o[i].CompareTag("MainCamera") 
				&& !o[i].CompareTag("DontDestroy")
				&& !o[i].CompareTag("Fade")){
				allObjectsInRoom.Add(o[i]);
			}*/

		if(saveLevelText.text != string.Empty && saveLevelText.text != "0"){
			name = saveLevelText.text;
		}

		GameObject[] o = SceneManager.GetActiveScene().GetRootGameObjects();
		for(int i=0;i<o.Length;i++){
			if(o[i].name.Equals(name)){
				allObjectsInRoom.Add(o[i]);
			} else {
				if(o[i].name.StartsWith("level_")){
					Destroy(o[i]);
				}
			}
		}
		Debug.Log("text_ " + name);

		DataToBeSaved.objectList.Clear();
		DataToBeSaved.objectRotation.Clear();
		DataToBeSaved.objectNames.Clear();

		for(int i=0;i<allObjectsInRoom.Count;i += 1){

			Debug.Log("<(OLD)>OBJECT_LIST_COUNT: " + DataToBeSaved.objectList.Count);

			if(allObjectsInRoom[i] != null){

				Vector3 pos = new Vector3(allObjectsInRoom[i].transform.position.x, allObjectsInRoom[i].transform.position.y, allObjectsInRoom[i].transform.position.z);

				bool objectAlreadyExist = false;
				for(int j=2;j<DataToBeSaved.objectList.Count;j += 3){
					if(new Vector3(DataToBeSaved.objectList[j-2], DataToBeSaved.objectList[j-1], DataToBeSaved.objectList[j]) == pos){
						objectAlreadyExist = true;
					}
				}

				if(!objectAlreadyExist){
					DataToBeSaved.objectList.Add(allObjectsInRoom[i].transform.position.x);
					DataToBeSaved.objectList.Add(allObjectsInRoom[i].transform.position.y);
					DataToBeSaved.objectList.Add(allObjectsInRoom[i].transform.position.z);
					DataToBeSaved.objectNames.Add(allObjectsInRoom[i].name);

					Debug.Log("ROTX: " + allObjectsInRoom[i].transform.rotation.z);

					DataToBeSaved.objectRotation.Add(allObjectsInRoom[i].transform.rotation.x);
					DataToBeSaved.objectRotation.Add(allObjectsInRoom[i].transform.rotation.y);
					DataToBeSaved.objectRotation.Add(allObjectsInRoom[i].transform.rotation.z);
				}
			}
		}
			
		filePath = Application.persistentDataPath + "/" + name + ".dat";
		filePathRotation = Application.persistentDataPath + "/" + name + "_rot.dat";
		filePathNames = Application.persistentDataPath + "/" + name + "_names.dat";
		Debug.Log(filePath);

		BinaryFormatter bf = new BinaryFormatter();
		FileStream file = new FileStream(filePath, FileMode.Create);
		FileStream fileRotation = new FileStream(filePathRotation, FileMode.Create);
		FileStream fileNames = new FileStream(filePathNames, FileMode.Create);

		bf.Serialize(file, DataToBeSaved.objectList);
		bf.Serialize(fileRotation, DataToBeSaved.objectRotation);
		bf.Serialize(fileNames, DataToBeSaved.objectNames);
		file.Close();
		fileRotation.Close();
		fileNames.Close();

		while(allObjectsInRoom.Count > 0){
			EraseLevelContent();
		}
	}

	public static int numberOfLevels(DirectoryInfo dir){
		FileInfo[] fileInfo = dir.GetFiles();
		int numberOfFiles = 0;
		foreach(FileInfo f in fileInfo){
			if(f.Extension.Contains("dat")){
				numberOfFiles++;
			}
		}
		return (numberOfFiles/3);
	}
	public static string getPath(){
		return (Application.persistentDataPath + "/");
	}

	public void Load(){

		if(isLevelEditor()){
			level = loadLevelText.text;
		}

		Debug.Log("LEVEL LOADED: " + level);

		while(allObjectsInRoom.Count > 0){
			EraseLevelContent();
		}

		filePath = Application.persistentDataPath + "/" + level + ".dat";
		filePathRotation = Application.persistentDataPath + "/" + level + "_rot.dat";
		filePathNames = Application.persistentDataPath + "/" + level + "_names.dat";

		if(File.Exists(filePath) && File.Exists(filePathRotation)){
			BinaryFormatter bf = new BinaryFormatter();
			FileStream file = new FileStream(filePath, FileMode.Open);
			FileStream fileRot = new FileStream(filePathRotation, FileMode.Open);
			FileStream fileNames = new FileStream(filePathNames, FileMode.Open);

			DataToBeSaved.objectList = (List<float>)bf.Deserialize(file);
			DataToBeSaved.objectRotation = (List<float>)bf.Deserialize(fileRot);
			DataToBeSaved.objectNames = (List<string>)bf.Deserialize(fileNames);

			Debug.Log("OBJECT_NAMES_COUNT: " + DataToBeSaved.objectNames.Count);
			Debug.Log("OBJECT_LIST_COUNT: " + DataToBeSaved.objectList.Count);

			for(int i=2;i<DataToBeSaved.objectList.Count;i += 3){
				float x = DataToBeSaved.objectList[i-2];
				float y = DataToBeSaved.objectList[i-1];
				float z = DataToBeSaved.objectList[i];

				float xRot = DataToBeSaved.objectRotation[i-2];
				float yRot = DataToBeSaved.objectRotation[i-1];
				float zRot = DataToBeSaved.objectRotation[i];

				string name = DataToBeSaved.objectNames[i/3];
				//Generate the level

				int len = 0;
				Debug.Log("NAME: " + name);
				if(name.EndsWith("Clone)")){
					len = "(Clone)".Length;
				}


				string str = name.Substring(0, name.Length - len);
				Debug.Log("PREFAB NAME: " + str);

				Object prefab = Resources.Load("_LevelPrefabs/"+str);
				GameObject temp = (GameObject)Instantiate(prefab);

				temp.AddComponent<UniqueID>();
				temp.GetComponent<UniqueID>().dontPutID = true;
				temp.transform.position = new Vector3(x,y,z);
				temp.transform.rotation = Quaternion.Euler(xRot,yRot,zRot);

				temp.GetComponent<UniqueID>().ID = allObjectsInRoom.Count;
				numberOfInstances = allObjectsInRoom.Count+1;

				allObjectsInRoom.Add(temp);
			}

			Debug.Log("COUNT: " + allObjectsInRoom.Count);

			file.Close();
			fileNames.Close();
			fileRot.Close();
		} else {
			Debug.Log(level + ".dat does not exist");
		}
	}

	private void EraseLevelContent(){
		for(int i=0;i<allObjectsInRoom.Count;i++){
			Destroy(allObjectsInRoom[i]);
			//allObjectsInRoom.RemoveAt(i);
		}
		allObjectsInRoom.Clear();
		DataToBeSaved.objectList.Clear();
		DataToBeSaved.objectNames.Clear();
		DataToBeSaved.objectRotation.Clear();
	}

	void OnMouseDown(){
		Debug.Log("Click");
		if(Input.GetMouseButton(0)){
			
		}
	}

	void Update(){
		Debug.Log("ENTER: " + Input.GetKeyDown(KeyCode.Return));

		Debug.Log("SAVE: " + save);
		Debug.Log("LOAD: " + load);

		if(Input.GetKeyDown(KeyCode.PageUp)){
			if(layer < 2){
				layer += 1;
			}
		} else if(Input.GetKeyDown(KeyCode.PageDown)){
			if(layer > 0){
				layer -= 1;
			}
		}
		Debug.Log("LAYER: " + layer);


		//UI Stuff
		if(SceneManager.GetActiveScene().name == "LevelEditor"){
			if(Input.GetMouseButton(0) || Input.GetMouseButton(1)){
				save = false;
				load = false;
				loadLevelText.text = "";
				saveLevelText.text = "";
			}

			if(save){

				if(Input.GetKeyDown(KeyCode.Return)){
					Save();
					save = false;
					saveLevelText.text = "";
				}

				writeSaveString();

			}

			if(load){
				if(Input.GetKeyDown(KeyCode.Return)){
					Load();
					load = false;
					loadLevelText.text = "";
				}

				writeLoadString();
			}
		}
	}

	void OnSaveButtonClick(){
		save = true;
		load = false;
	}

	void OnLoadButtonClick(){
		save = false;
		load = true;
	}

	bool canWrite(string str){

		if(Input.GetKey(KeyCode.Backspace)){
			if(str.Length > 0){
				return false;
			}
		}
		return true;
	}

	void writeSaveString(){
		if(canWrite(saveLevelText.text)){
			saveLevelText.text += Input.inputString;
		} else {
			if(Input.GetKeyDown(KeyCode.Backspace)){
				saveLevelText.text = saveLevelText.text.Substring(0, saveLevelText.text.Length - 1);
			}
		}
	}

	void writeLoadString(){
		if(canWrite(loadLevelText.text)){
			loadLevelText.text += Input.inputString;
		} else {
			if(Input.GetKeyDown(KeyCode.Backspace)){
				loadLevelText.text = loadLevelText.text.Substring(0, loadLevelText.text.Length - 1);
			}
		}
	}

}