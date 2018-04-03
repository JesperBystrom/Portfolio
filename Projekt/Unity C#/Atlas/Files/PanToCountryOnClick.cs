using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.EventSystems;

public class PanToCountryOnClick : MonoBehaviour {

	Button btn;
	Text countryName;

	void Start () {
		btn = GetComponent<Button>();
		btn.onClick.AddListener(click);
		countryName = transform.Find("Text").GetComponent<Text>();
	}

	void Update(){
	}

	public void click(){
		string l = countryName.text;
		GameObject o = GameObject.Find(l);
		o.GetComponent<PolygonFiller>().blink = true;
		Camera.main.GetComponent<CameraPan>().pan(o);
		transform.root.Find("Fast Travel").GetComponent<FastTravel>().decrease();
	}
}
