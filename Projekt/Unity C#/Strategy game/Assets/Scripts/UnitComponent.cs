using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class UnitComponent : MonoBehaviour {

	private Renderer[] renderers;

	void Start () {
		renderers = new Renderer[transform.childCount];
		for(int i=0;i<transform.childCount;i++){
			renderers[i] = transform.GetChild(i).GetComponent<Renderer>();
		}

		//renderer = GetComponent<Renderer>();
	}

	public Renderer[] getRenderers(){
		return renderers;
	}

	public Renderer getRenderer(){
		return renderers[0];
	}
}
