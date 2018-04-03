using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using Lean.Touch;

public class Main : MonoBehaviour {

	public const float ANIMATION_CIRCLE_END = 0.3f;
	public const string ANIMATION_CIRCLE_NAME = "Click";


	public GameObject informationUI;
	public Text titleText;
	public Text descriptionText;
	public Text populationValueText;
	public Text continentText;
	public Text countryText;
	public RawImage countryInformationPicture;
	public Image[] tint;

	private GameObject country;
	private Camera camera;
	private float ortoTarget;
	private float ortoOrigin;

	private float zoomSpeed = 0.05f;
	private float cameraSpeed = 0.1f;

	private float taps = 0f;
	private float tapTimer = 0.1f;
	public PolygonFiller currentCountryTarget;

	Vector2 cameraOrigin;
	[HideInInspector]
	public Wikipedia wiki;
	public Mouse mouse;
	public CircleAnimationActivation circle;
	public LoadingScreenActivation loadingScreen;
	public World world;
	public GameObject hamburgerMenu;
	public Game game;

	void Start () {
		camera = Camera.main;
		hideUi();
		wiki = GetComponent<Wikipedia>();
	}

	private void resetTap(){
		taps = 0;
		tapTimer = 0.5f;
	}

	void Update () {
		//Onclick
		if(Input.GetMouseButtonDown(0)){
			taps++;
		}
		if(taps > 0){
			tapTimer -= 1f * Time.deltaTime;
		}
		if(tapTimer <= 0){
			resetTap();
		}

		Debug.Log("COUNTRY: " + mouse.getCountry());
		if(Input.GetMouseButtonDown(0) && taps > 0 && mouse.getCountry() != null){
			//if(Input.touchCount > 0){
				//if(Input.GetTouch(0).phase != TouchPhase.Moved){
					//onCountrySelect();
					//hamburgerMenu.SetActive(false);
				//}
			//}
		}
	}

	public bool hasReachedPosition(Vector3 pos){
		if(Vector3.Distance(camera.transform.position, pos) < 0.1f){
			return true;
		} 
		return false;
	}

	public void showUi(){
		informationUI.SetActive(true);
	}
	public void hideUi(){
		informationUI.SetActive(false);
		hamburgerMenu.SetActive(true);

		if(currentCountryTarget != null)
			currentCountryTarget.untarget();
	}
	public bool isUIEnabled(){
		return informationUI.activeInHierarchy;
	}
	public void setCountryFlag(Texture2D t){
		if(t == null) return;
		Color c = t.GetPixel(0,0);
		foreach(Image i in this.tint){
			i.color = c;
		}
		if(c.maxColorComponent >= 1) {
			this.titleText.color = Color.black;
			Debug.Log("Too bright! making the text black");
		}
		Debug.Log("gfdgdfdf: " + c.maxColorComponent);
		this.countryInformationPicture.texture = t;
	}
	public void updateContentUi(Country country){
		this.titleText.text = country.name;
		this.populationValueText.text = country.population.ToString();
		this.countryText.text = country.name;
		this.continentText.text = country.continent;

		setCountryFlag(country.flag);

		if(country.text.Length > 1){
			this.descriptionText.text = country.text;
		}
		loadingScreen.finish();
		Debug.Log("Finished loading");
	}

	public void informationFail(){
		loadingScreen.gameObject.SetActive(true);
		loadingScreen.failed("Oops! Country could not be found");
		currentCountryTarget.untarget();
		mouse.enableClicking();
	}

	public void onCountrySelect(){
		if(game != null){
			mouse.enableClicking();
			currentCountryTarget = mouse.getCountry().GetComponent<PolygonFiller>();
			if(!currentCountryTarget.isTargeted()){
				if(mouse.getCountry().name.Equals(game.randomGenerator.getCountry().name)){
					Debug.Log(game.getFailedPoints());
					if(game.getFailedPoints() >= 3){
						currentCountryTarget.target(currentCountryTarget.matFail);
						game.resetFail();
						Debug.Log("FAILED");
					}
					else {
						currentCountryTarget.target();
					}
					currentCountryTarget = null;
					game.randomGenerator.generateNewRandomCountry();
				} else {
					game.incrementFailedPoints();
				}
				if(game.getFailedPoints() >= 3){
					game.randomGenerator.getCountry().gameObject.GetComponent<PolygonFiller>().warning();
				}
			}
		} else {
			circle.gameObject.SetActive(true);
			circle.loadCountry(this, mouse.getCountry());
			currentCountryTarget = mouse.getCountry().GetComponent<PolygonFiller>();
			currentCountryTarget.target();
			//mouse.getCountry().GetComponent<PolygonFiller>().target();
			mouse.disableClicking();
		}
	}

	public void deactivateCountry(){
		if(currentCountryTarget == null) return;

		if(this.currentCountryTarget != null)
			this.currentCountryTarget.blink = false;
		currentCountryTarget.target();
		currentCountryTarget.StartCoroutine(currentCountryTarget.wait());
		currentCountryTarget = null;
	}

}
