using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class GenerateAStarGrid : MonoBehaviour {

	public GameObject prefab;
	private Astar pathfinder;
	private ArrayList realNodes = new ArrayList();
	private int timer = 60;

	void Start () {
		ArrayList nodes = new ArrayList();
		int w = Screen.width/50;
		int h = Screen.height/50;
		for(int i=0;i<w;i++){
			for(int j=0;j<h;j++){
				GameObject o = Instantiate(prefab);
				o.transform.SetParent(prefab.transform.parent);
				o.GetComponent<RectTransform>().anchoredPosition = Vector2.zero;
				o.GetComponent<RectTransform>().anchoredPosition += new Vector2(50 * i, 50 * -j);
				Node n = new Node(i, j, 0);
				realNodes.Add(o.GetComponent<RectTransform>());
				nodes.Add(n);
			}
		}
		pathfinder = new Astar((Node[])nodes.ToArray(typeof(Node)), this);
		pathfinder.findPath(pathfinder.getNode(0,0,0), pathfinder.getNode(16,5,0));
		getRealNode(pathfinder.start).GetComponent<Image>().color = Color.green;
		getRealNode(pathfinder.end).GetComponent<Image>().color = Color.green;

	}

	void Update(){
		timer--;
		if(timer <= 0){
			pathfinder.findPath(pathfinder.getNode(0,0,0), pathfinder.getNode(16,5,0));
			timer = 60;
		}
	}

	public GameObject getRealNode(Node n){
		foreach(RectTransform r in realNodes){
			if(r.anchoredPosition.x == n.pos.x*50 && r.anchoredPosition.y == -n.pos.y*50)
				return r.gameObject;
		}
		return null;
	}
}
