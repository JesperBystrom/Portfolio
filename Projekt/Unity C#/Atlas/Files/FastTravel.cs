using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;
using UnityEngine.EventSystems;
public class FastTravel : MonoBehaviour {

	public Main main;
	public GameObject template;
	private bool filled = false;
	public LoadingScreenActivation loadingScreen;
	private CanvasGroup cGroup;
	private Animator animator;
	private bool isOver = false;

	void Start(){
		loadingScreen.gameObject.SetActive(true);
		cGroup = GetComponent<CanvasGroup>();
		animator = GetComponent<Animator>();
	}

	void Update(){
		if(filled && loadingScreen.loadingIcon.gameObject.activeInHierarchy)
			loadingScreen.loadingIcon.finish();
		if(loadingScreen.loadingIcon.isFinished()){
			loadingScreen.gameObject.SetActive(false);
		}

		AnimatorStateInfo state = animator.GetCurrentAnimatorStateInfo(0);

		if(EventSystem.current.IsPointerOverGameObject() && Input.GetMouseButtonDown(0) && cGroup.alpha <= 0.5f){
			animator.Play("AlphaIncrease");
			if(main.currentCountryTarget != null){
				main.deactivateCountry();
			}
		}
	}

	public void fill(World world){
		for(int i=0;i<world.countries.Length;i++){
			if(world.countries[i].flag == null) continue;
			GameObject o = Instantiate(template);
			o.transform.SetParent(transform.Find("Scrollbar").Find("Content"));
			o.transform.SetAsLastSibling();
			o.transform.Find("Flag").GetComponent<RawImage>().texture = world.countries[i].flag;//Sprite.Create(world.countries[i].flag, new Rect(0,0,900, 360), new Vector2(0.5f, 0.5f));
			o.transform.Find("Text").GetComponent<Text>().text = world.countries[i].name;
			o.SetActive(true);
			RectTransform rect = o.GetComponent<RectTransform>();
			rect.anchoredPosition = new Vector2(0, (-200*i));
			rect.localScale = Vector2.one;
		}
		filled = true;
	}

	public void decrease(){
		Debug.Log("decreasing..");
		animator.Play("AlphaDecrease");
	}

	/*public void OnPointerEnter(PointerEventData eventData){
		if(eventData.pointerEnter != null){
			isOver = true;
		}
	}*/
}
