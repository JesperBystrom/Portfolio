using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class RandomCountryGenerator : MonoBehaviour {

	public RawImage flag;
	public Text name;
	public World world;
	private Country currentCountry;

	public Country generateNewRandomCountry(){
		Country randomCountry = world.countries[Random.Range(0, world.countries.Length-1)];
		setCountry(randomCountry);
		return randomCountry;
	}
	public void setCountry(Country c){
		this.name.text = c.name;
		this.flag.texture = c.flag;
		currentCountry = c;
	}
	public Country getCountry(){
		return currentCountry;
	}
}
