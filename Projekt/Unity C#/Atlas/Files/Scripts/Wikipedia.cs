using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Xml;
using System.Net;
using System.Text.RegularExpressions;
using System.Security;
using System.Threading;
using System;
using UnityEngine.UI;
using UnityEngine;

public class CountryJson{

}
	
[Serializable]
public class TotalPopulation
{
	public string date;
	public int population;
}

[Serializable]
public class Population
{
	public TotalPopulation[] total_population;
}
[Serializable]
public class Thumbnail
{
	public string source;
	public int width;
	public int height;
}
[Serializable]
public class InvalidNumber
{
	public int pageid;
	public int ns;
	public string title;
	public Thumbnail thumbnail;
	public string pageimage;
}
[Serializable]
public class Pages
{
	public InvalidNumber InvalidNumber;
}
[Serializable]
public class Query
{
	public Pages pages;
}
[Serializable]
public class RootObject
{
	public string batchcomplete;
	public Query query;
}

public class Wikipedia : MonoBehaviour {

	private Main main;
	private World world;
	private Country currentCountry;

	public void Start(){
		this.main = GetComponent<Main>();
		this.world = main.world;
	}

	public IEnumerator loadInformation(GameObject countryObject){
		currentCountry = world.getCountry(countryObject.name);
		if(currentCountry == null) {
			main.informationFail();
			yield break;
		}
		currentCountry.continent = countryObject.transform.parent.name;
		currentCountry.name = countryObject.name;




		////////////////////READING POPULATION///////////////////////////

		countryObject.name = Regex.Replace(countryObject.name, "[_]", " ");
		WWW population = new WWW("http://api.population.io/1.0/population/"+countryObject.name+"/today-and-tomorrow/?format=json");
		yield return population;


		Population o = JsonUtility.FromJson<Population>(population.text);
		if(o.total_population == null){
			//Retry
			String name = world.getAssociatedName(currentCountry);
			Debug.Log("Failed to find, trying again with: " + name);
			population = new WWW("http://api.population.io/1.0/population/"+name+"/today-and-tomorrow/?format=json");
			yield return population;
		}
		if(population.text.Contains("<!DOCTYPE html>")){
			main.informationFail();
			yield break;
		}

		Debug.Log(population.text);

		o = JsonUtility.FromJson<Population>(population.text);

		if(o.total_population == null) 
			currentCountry.population = 0; 
		else 
			currentCountry.population = o.total_population[0].population;








		////////////////////READING WIKIPEDIA INFORMATION///////////////////////////

		WWW countryInformation = new WWW("http://en.wikipedia.org/w/api.php?format=xml&action=query&prop=extracts&titles="+currentCountry.name);
		yield return countryInformation;

		XmlDocument doc = new XmlDocument();
		doc.LoadXml(countryInformation.text);
		Debug.Log(doc.GetElementsByTagName("extract").Count <= 0);
		if(countryInformation == null){
			main.informationFail();
			yield break;
		}
		string str = doc.GetElementsByTagName("extract")[0].InnerText;
		str = parseWebsite(str);

		int maxStringLength = 3000;

		for(int i=1;i<5;i++){
			int ss = i * maxStringLength/5;
			str = str.Insert(ss, "\n\n\n");
		}
		str = str.Substring(0, maxStringLength);

		currentCountry.text = str;








		/*////////////////////READING FLAG/////////////////////////

		WWW flagInfo = new WWW("https://en.wikipedia.org/w/api.php?action=query&titles="+currentCountry.name+"&prop=pageimages&format=json&pithumbsize=100");
		yield return flagInfo;

		Regex r = new Regex("\\d{5,7}");
		String t = flagInfo.text;
		t = r.Replace(flagInfo.text, "InvalidNumber", 1);
		RootObject root = JsonUtility.FromJson<RootObject>(t);

		WWW flag = new WWW(root.query.pages.InvalidNumber.thumbnail.source);
		yield return flag;

		currentCountry.flag = flag.texture;*/









		////////////////////FINISHING///////////////////////////
		if(main != null){
			//main.setCountryFlag(c.flag);
			main.showUi();
			main.updateContentUi(currentCountry);
		}
		Debug.Log("Information loaded");
	}

	public IEnumerator readPopulation(){
		WWW population = new WWW("http://api.population.io/1.0/population/"+currentCountry.name+"/today-and-tomorrow/?format=json");
		yield return population;

		Population o = JsonUtility.FromJson<Population>(population.text);
		Debug.Log(o.total_population);
		if(o.total_population[0] == null) 
			currentCountry.population = 0; 
		else 
			currentCountry.population = o.total_population[0].population;
	}

	public IEnumerator readFlag(Country c){
		WWW flagInfo = new WWW("https://en.wikipedia.org/w/api.php?action=query&titles="+c.name+"&prop=pageimages&format=json&pithumbsize=100");
		yield return flagInfo;

		Regex r = new Regex("\\d{5,7}");
		String t = flagInfo.text;
		t = r.Replace(flagInfo.text, "InvalidNumber", 1);
		RootObject root = JsonUtility.FromJson<RootObject>(t);

		WWW flag = new WWW(root.query.pages.InvalidNumber.thumbnail.source);
		yield return flag;

		c.flag = flag.texture;
	}

	/*
	public IEnumerator readImageFromTitle(string title){
		string url = "https://en.wikipedia.org/w/api.php?action=query&titles=China&prop=pageimages&format=json&pithumbsize=100";
		WWW www = new WWW(url);
		yield return www;
		string str = www.text;
		Regex regex = new Regex("<[^>]+>");
		str = regex.Replace(str, string.Empty);

		string imageUrl = parseFlag(str);

		Debug.Log(imageUrl);
		WWW w = new WWW(imageUrl);
		yield return w;
		main.setCountryInformationTexture(w.texture);
		//https://upload.wikimedia.org/

	}*/

	/*
	public IEnumerator readFlag(string name){
		WWW flagInfo = new WWW("https://en.wikipedia.org/w/api.php?action=query&titles="+name+"&prop=pageimages&format=json&pithumbsize=100");
		yield return flagInfo;

		Regex r = new Regex("\\d{5,7}");
		String t = flagInfo.text;
		t = r.Replace(flagInfo.text, "InvalidNumber", 1);
		RootObject root = JsonUtility.FromJson<RootObject>(t);

		WWW flag = new WWW(root.query.pages.InvalidNumber.thumbnail.source);
		yield return flag;
	}
	*/

	/*
	public String getTitle(){
		return country.title;
	}

	public String getText(){
		return country.text;
	}

	public Country getCountry(){
		return this.country;
	}
	*/

	public string parseWebsite(string str){
		return new Regex("<[^>]+>").Replace(str, string.Empty);
	}
	/*

	public string parseFlag(string str){
		str = parseWebsite(str);
		bool foundStart = false;
		bool foundEnd = false;
		string imageUrl = "";
		string recordedSize = "";
		int width = 0;
		int height = 0;

		for(int i=0;i<str.Length;i++){
			if(str[i].Equals('h') && str[i+1].Equals('t') && str[i+2].Equals('t')){
				foundStart = true;
			}

			if(foundStart && !foundEnd){
				if(str[i].Equals('g') && str[i-1].Equals('n') && str[i-2].Equals('p')){
					foundEnd = true;
				}
				imageUrl += str[i];
			}

			if(foundStart && foundEnd){
				if(width == 0){
					if(char.IsNumber(str[i])){
						recordedSize += str[i];
					} else {
						width = (int)str[i];
						recordedSize = "";
					}
				}
				if(height == 0){
					if(char.IsNumber(str[i])){
						recordedSize += str[i];
					} else {
						height = (int)str[i];
						recordedSize = "";
						break;
					}
				}
			}
		}
		return imageUrl;
	}
	*/
}