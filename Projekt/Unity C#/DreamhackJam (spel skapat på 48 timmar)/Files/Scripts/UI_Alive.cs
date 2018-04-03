using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class UI_Alive : MonoBehaviour {

    public Sprite[] playerSprites;
    public float switchTime; //Time in seconds

    //Private vars
    private double lastTime;
    private int currentPicture = 1;


    // Use this for initialization
    void Start()
    {
        lastTime = Time.time;
    }

    // Update is called once per frame
    void Update()
    {
        //startSwitching();


        if (Time.time - lastTime > switchTime)
        {

            gameObject.GetComponent<Image>().sprite = playerSprites[currentPicture];
            lastTime = Time.time;

            if (currentPicture > playerSprites.Length - 2)
            {
                currentPicture = 0;
            }
            else
            {
                currentPicture++;
            }
        }
        else
        {
        }
    }
}
