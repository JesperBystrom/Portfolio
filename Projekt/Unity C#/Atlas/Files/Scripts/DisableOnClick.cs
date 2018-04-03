using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DisableOnClick : OnClick {

	public override void onClick() {
		Debug.Log("Hi");
		gameObject.SetActive(false);
	}
}
