using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class AbilityButtonHandler : MonoBehaviour {
	public CharacterPage characterPage;
	public GameObject abilityTemplate;

	void Start(){
		abilityTemplate.SetActive(false);
	}

	public void onOpen(){
		Ability[] abilities = characterPage.getUnit().abilities;
		int index = 0;
		foreach(Ability a in abilities){
			GameObject o = Instantiate(abilityTemplate);
			o.SetActive(true);
			o.GetComponent<AbilityComponent>().setAbility(a);
			RectTransform rect = o.GetComponent<RectTransform>();
			o.transform.SetParent(abilityTemplate.transform.parent);
			rect.anchoredPosition = Vector2.zero;
			rect.anchoredPosition += new Vector2(((rect.sizeDelta.x+10)*index++), 0);
		}
	}

	public void onButtonPress(AbilityComponent abilityComponent){
		characterPage.getUnit().prepareAbility(abilityComponent.getAbility());
		Debug.Log("pressing " + abilityComponent.getAbility().name);
	}
}
