using System.Collections;
using System.Collections.Generic;
using System.Linq;
using UnityEngine;
using UnityEngine.UI;

public class GIFAnimator : MonoBehaviour {

	private Sprite spritesheet;
	private Image image;
	private Sprite[] sprites;
	private float timer;
	private int frame;
	public void Start () {
		sprites = Resources.LoadAll<Sprite>("spritesheet");
		image = GetComponent<Image>();
	}

	public void Update () {
		timer += 60 * Time.deltaTime;
		if(timer >= 2) {
			image.sprite = sprites[frame++];
			timer = 0;
			if(frame >= sprites.Length){
				frame = 0;
			}
		}
	}
}
