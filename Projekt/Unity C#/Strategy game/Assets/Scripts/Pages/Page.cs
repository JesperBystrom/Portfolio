using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Page : MonoBehaviour {
	public PageController controller;
	public void open(){
		controller.open();

	}

	public void close(){
		controller.close();
	}
}
