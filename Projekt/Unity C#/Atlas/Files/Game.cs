using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

/*
Welcome to find the country
You will be assigned a random country and your goal is to find that country

When you do find the country, tap it and you get score! 

If you select the wrong country too many times, the correct country will start blinking red and you get no points!

If you want to limit yourself to a certain continent, head to the settings tab and change which continents should be included!

Good luck! 
*/
public class Tutorial {
	private Text tutorialText;
	private Text tapToContinueText;
	private GameObject countryUI;
	private GameObject tutorialArrow;
	private GameObject exampleCountry;
	private int phase;
	private PolygonFiller po;
	private CameraPan camPan;
	private Game game;

	public Tutorial(Text tutorialText, Text tapToContinueText, GameObject countryUI, GameObject tutorialArrow, Game game){
		this.tutorialText = tutorialText;
		this.tapToContinueText = tapToContinueText;
		this.countryUI = countryUI;
		this.tutorialArrow = tutorialArrow;
		this.exampleCountry = GameObject.Find("Sweden");
		this.po = exampleCountry.GetComponent<PolygonFiller>();
		this.camPan = Camera.main.GetComponent<CameraPan>();
		this.game = game;
	}

	public void gotoNextPhase(){
		phase++;
		onPhaseChange();
	}
	public void onPhaseChange(){
		switch(phase){
		case 0:
			setText("Welcome to find the country");
			break;

		case 1:
			//Make the country UI blink or something between yellow outline and blue outline
			setText("You will be assigned a random country and your goal is to find that country. When you do find the country, tap it and you get score!");
			countryUI.SetActive(true);
			//setArrow(0,-350,90);
			break;

			//Pan
		case 2:
			setText("");
			//disableArrow();
			countryUI.SetActive(false);
			camPan.tutorialPan(exampleCountry, this);
			tapToContinueText.gameObject.SetActive(false);
			break;

		case 3:
			//Make Sweden blink red
			setText("If you select the wrong country too many times, the correct country will start blinking red and you get no points!");
			po.startBlink(po.matFail);
			tapToContinueText.gameObject.SetActive(true);
			break;


		case 4:
			//Make sweden solid green
			setText("Otherwise if you get the correct country, it will become green");
			po.stopBlink();
			po.target();
			break;

		case 5:
			//idk
			setText("If you want to limit yourself to a certain continent, head to the settings tab and change which continents should be included!\n Good luck");
			po.untarget();
			break;

		case 6:
			setText("");
			camPan.panToOriginalLocation();
			break;
		}
	}

	public void exit(){
		countryUI.SetActive(true);
		game.exitTutorial();
	}

	public void setText(string text){
		this.tutorialText.text = text;
	}

	public void toggleCountryUI(bool toggle){
		countryUI.SetActive(toggle);
	}

	public void setArrow(int x, int y, int angle){
		tutorialArrow.SetActive(true);
		RectTransform rect = tutorialArrow.GetComponent<RectTransform>();
		rect.anchoredPosition = new Vector2(x,y);
		tutorialArrow.transform.rotation = Quaternion.Euler(tutorialArrow.transform.rotation.x, tutorialArrow.transform.rotation.y, angle);
	}

	public void disableArrow(){
		tutorialArrow.SetActive(false);
	}
}

public class Game : MonoBehaviour {

	public Text tutorialText;
	public Text tapToContinueText;
	public GameObject tutorialShade;
	public GameObject countryUI;
	public RandomCountryGenerator randomGenerator;
	public GameObject tutorialArrow;
	[HideInInspector]
	public Tutorial tutorialClass;
	private Spin tutorialArrowSpin;
	private bool tutorial = true;
	private int failedPoints;
	void Start () {
		tutorialClass = new Tutorial(tutorialText, tapToContinueText, countryUI, tutorialArrow, this);
		tutorialArrowSpin = tutorialArrow.GetComponent<Spin>();
	}

	void Update () {
		if(Input.GetMouseButtonDown(0)){
			tutorialClass.gotoNextPhase();
		}
		//tutorialArrowSpin.spin(2);
	}

	public void exitTutorial(){
		tutorialText.gameObject.SetActive(false);
		tapToContinueText.gameObject.SetActive(false);
		tutorialShade.SetActive(false);
		randomGenerator.generateNewRandomCountry();
		tutorial = false;
	}

	public void incrementFailedPoints(){
		failedPoints++;
	}

	public int getFailedPoints(){
		return failedPoints;
	}

	public void resetFail(){
		this.failedPoints = 0;
	}
}
