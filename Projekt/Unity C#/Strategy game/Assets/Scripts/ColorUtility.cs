using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public static class ColorUtility {

	public static Color getColor(Renderer r){
		return r.material.color;
	}

	public static void setColor(Renderer r, Color c){
		r.material.color = c;
	}

	public static void setColor(Renderer[] renderers, Color c){
		foreach(Renderer r in renderers){
			r.material.color = c;
		}
	}
}
