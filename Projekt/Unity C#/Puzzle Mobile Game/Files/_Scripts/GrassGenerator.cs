using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class GrassGenerator : MonoBehaviour {

	public GameObject[] grassPrefabs = new GameObject[3];

	void Start () {

		int rand = Random.Range(0,grassPrefabs.Length);
		if(grassPrefabs[rand] != null){
			GameObject temp = Instantiate(grassPrefabs[rand]);
			temp.transform.localScale = new Vector3(4,3,4);
			//temp.GetComponent<Renderer>().material = GetComponentInChildren<Renderer>().material;
			temp.transform.position = new Vector3(transform.position.x, transform.position.y + temp.transform.lossyScale.y-temp.GetComponentInChildren<Renderer>().bounds.size.y, transform.position.z);
		}
	}
}
