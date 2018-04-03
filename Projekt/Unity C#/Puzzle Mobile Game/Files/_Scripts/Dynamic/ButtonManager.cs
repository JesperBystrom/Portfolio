using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ButtonManager : MonoBehaviour {

	public GameObject button_red;
	public GameObject button_holder;
	public DoorOpening door;



	private Renderer render;

	private enum e_buttonCoordinates { X, Y, Z };
	private enum e_buttonTypes { RED, HOLDER };

	//Add new coordinates here for more buttons
	private float[,] redButtonCoordinates = new float[,] { 
		{ 34.89f, 27.41f}, // X
		{ 1.6f, 14.88f}, // Y
		{ 8.14f, 6.92f} // Z
	};
	private float doorsOpened; //Add more here if you're adding more buttons
	private bool openDoor = false;	
	private GameObject player;

	private GameObject[,] buttons;

	void Start () {
		GameObject.DontDestroyOnLoad(this.gameObject);
		player = GameObject.FindWithTag("Player");
		buttons = new GameObject[0,2]; //(buttonAmount, DO NOT TOUCH SECOND PARAMETER)

		for(int i=0;i<buttons.GetLength(0);i++){
			buttons[i,(int)e_buttonTypes.RED] = Instantiate(button_red);
			buttons[i,(int)e_buttonTypes.HOLDER] = Instantiate(button_holder);

			Debug.Log(buttons[i,(int)e_buttonTypes.RED].GetComponent<Buttons>().ID = i);

			render = buttons[i,(int)e_buttonTypes.RED].GetComponent<Renderer>();

			buttons[i,(int)e_buttonTypes.RED].transform.position = new Vector3(getButtonX(i), getButtonY(i), getButtonZ(i));
			buttons[i,(int)e_buttonTypes.HOLDER].transform.position = new Vector3(getButtonX(i), getButtonY(i) - (render.bounds.size.y * buttons[i,(int)e_buttonTypes.RED].transform.lossyScale.y)/2, getButtonZ(i));
		}
	}

	void Update () {
		
		bool openDoor = true;
		if(!player.GetComponent<Player_Win>().winLevel){
			for(int i=0;i<buttons.GetLength(0);i++){
				if(!buttons[i,(int)e_buttonTypes.RED].GetComponent<Buttons>().isOn()){
					openDoor = false;
				}
			}
		}

		if(openDoor){
			door.open = true;
		} else {
			door.open = false;
		}
	}

	float getButtonX(int index){
		return redButtonCoordinates[(int)e_buttonCoordinates.X, index];
	}
	float getButtonY(int index){
		return redButtonCoordinates[(int)e_buttonCoordinates.Y, index];
	}
	float getButtonZ(int index){
		return redButtonCoordinates[(int)e_buttonCoordinates.Z, index];
	}
}
