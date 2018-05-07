using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Team {
	public static Team NEUTRAL = new Team(Color.white);
	public static Team BLUE = new Team(Color.blue);
	public static Team RED = new Team(Color.red);

	private Color color;

	public Team(Color color){
		this.color = color;
	}

	public Color GetColor(){
		return color;
	}
}