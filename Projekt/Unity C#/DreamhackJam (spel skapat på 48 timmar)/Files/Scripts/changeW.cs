using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class changeW : MonoBehaviour
{
    public GameObject[] objects;
    public Sprite[] sprites;
    public int[] spriteChanse;
    public GameObject light;
    // Use this for initialization
    void Start()
    {
        for (int i = 0; i < objects.Length; i++)
        {
            if (objects[i].transform.localPosition.y < 0.5) {
                if (Random.Range(0, 100) > 70) {
                    objects[i].GetComponent<SpriteRenderer>().sprite = sprites[3];
                    GameObject oLight = Instantiate(light, new Vector3(objects[i].transform.position.x, objects[i].transform.position.y, -0.5f), Quaternion.identity) as GameObject;
                    oLight.transform.SetParent(objects[i].transform.parent);
                    oLight.transform.localPosition = new Vector3(objects[i].transform.localPosition.x, objects[i].transform.localPosition.y, -0.5f);
                    continue;
                }
            }
            objects[i].GetComponent<SpriteRenderer>().sprite = sprites[Random.Range(0, sprites.Length)];
        }
    }

    // Update is called once per frame
    void Update()
    {

    }
}
