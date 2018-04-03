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
using System.Linq;

[Serializable]
public class Country {
	public string date;
	public int population;
	public string text = "";
	public string name;
	public string continent;
	public Texture2D flag;
	public GameObject gameObject;

	public Country(string name){
		this.name = name;
	}
	public Country(string name, Texture2D flag){
		this.name = name;
		this.flag = flag;
	}
}

public class PopulationCountryTable {
	public string[] countries;
}

public class World : MonoBehaviour {

	private string alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public FastTravel travel;
	public Country[] countries = new Country[128];
	private string[] allNames = new string[128];
	private Texture2D[] txts = new Texture2D[128];
	private List<GameObject> countryObjects = new List<GameObject>();
	public string[] associatedNames;
	private int index;
	private bool finished = false;

	IEnumerator readPopulationCountryTable(){
		WWW www = new WWW("http://api.population.io/1.0/population/countries/today-and-tomorrow/?format=json");
		yield return www;
		PopulationCountryTable popCountries = JsonUtility.FromJson<PopulationCountryTable>(www.text);
		associatedNames = popCountries.countries;
	}

	void Start(){
		/*for(int i=0;i<countries.Length;i++){
			if(flags.Length > i)
				countries[i].flag = flags[i];
		}*/
		StartCoroutine(readPopulationCountryTable());
		for(int i=0;i<transform.childCount;i++){
			GameObject c = gameObject.transform.GetChild(i).gameObject;
			for(int j=0;j<c.transform.childCount;j++){
				countryObjects.Add(c.transform.GetChild(j).gameObject);
				StartCoroutine(readFlag(c.transform.GetChild(j).gameObject));
			}
		}
		index = 0;

		/*GameObject[] sortedList;
		sortedList = countryObjects.OrderBy(countryObjects=>countryObjects.name).ToArray();

		Texture2D[] flags = getFlags();
		Debug.Log(flags.Length + " | " + sortedList.Length);
		for(int i=0;i<sortedList.Length;i++){
			if(sortedList[i] != null){
				allNames[i] = sortedList[i].name;
				countries[i] = new Country(sortedList[i].name, flags[i]);
			}
		}*/

		//Array.Sort(allNames);

		/*for(int i=0;i<allNames.Length;i++){
			countries[i] = new Country(allNames[i]);
		}*/

		/*foreach(string s in allNames){
			string firstLetter = s.Substring(0,1).ToCharArray()[0].ToString();
			for(int i=0;i<alphabet.Length;i++){
				Array.Sort();
			}
			/*for(int i=0;i<alphabet.Length;i++){
				if(s.Substring(0,1).Equals(alphabet[i])){
					
				}
			}*/
		//}

		StartCoroutine(wait());
	}

	public void saveTextureAsPng(string filename, Texture2D txt){
		if(txt == null) return;
		string path = Application.dataPath + "/Flags/" + filename + ".png";
		Debug.Log("Saved file to: " + path);
		byte[] bytes = txt.EncodeToPNG();
		var f = File.Open(path, FileMode.Create);
		BinaryWriter writer = new BinaryWriter(f);
		writer.Write(bytes);
		f.Close();
	}

	IEnumerator wait(){
		yield return new WaitForSeconds(2);
		travel.fill(this);
	}
	/*for(int i=0;i<countries.Length;i++){
			saveTextureAsPng(countries[i].name, countries[i].flag);
		}
		Debug.Log("Saved country files");
		*/

	public string getAssociatedName(Country c){
		foreach(string s in associatedNames){
			if(s.StartsWith(c.name)){
				return s;
			}
		}
		return "";
	}

	public Country getCountry(string name){
		foreach(Country c in countries){
			if(c.name.Equals(name)){
				return c;
			}
		}
		return null;
	}

	public string parseWebsite(string str){
		return new Regex("<[^>]+>").Replace(str, string.Empty);
	}

	public Texture2D[] getFlags(){
		return Resources.LoadAll<Texture2D>("Flags/");
	}


	public IEnumerator readFlag(GameObject obj){
		WWW flagInfo = new WWW("https://en.wikipedia.org/w/api.php?action=query&titles="+obj.name+"&prop=pageimages&format=json&pithumbsize=100");
		yield return flagInfo;

		String t = flagInfo.text;
		int matchCount = 0;
		int num0 = 5;
		int num1 = 7;
		while(matchCount <= 0){
			Regex r = new Regex("\\d{"+num0+","+num1+"}");
			t = r.Replace(flagInfo.text, new MatchEvaluator((match) => {
				matchCount++;
				return match.Result("InvalidNumber");
			}), 1, 0);
			if(num0 != num1){
				num1--;
			} else {
				num0--;
				num1--;
			}
			if(num0 == 0 || num1 == 0)
				break;
			if(matchCount > 0)
				break;
		}
			
		RootObject root = JsonUtility.FromJson<RootObject>(t);

		WWW flag = null;

		if(root.query.pages.InvalidNumber.thumbnail != null){
			if(root.query.pages.InvalidNumber.thumbnail.source != null){
				flag = new WWW(root.query.pages.InvalidNumber.thumbnail.source);
				yield return flag;
			}
		}

		if(flag != null){
			countries[index] = new Country(obj.name, flag.texture);
			countries[index].gameObject = obj;
			index++;
		}
	}

	public bool isFinished(){
		return finished;
	}

	public string parseFlag(string str){
		str = parseWebsite(str);
		bool foundStart = false;
		bool foundEnd = false;
		string imageUrl = "";
		string recordedSize = "";
		int width = 0;
		int height = 0;

		for(int i=0;i<str.Length;i++){
			if(str[i].Equals('h') && str[i+1].Equals('t') && str[i+2].Equals('t') && str[i+3].Equals('p') && str[i+4].Equals('s')){
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
}