using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class Astar {

	private Node[] nodes;
	private Map map;
	public Node start;
	public Node end;
	private GenerateAStarGrid grid;
	private static readonly int CELL_SIZE = 16;
	private ArrayList path = new ArrayList();
	private bool finished = false;

	public Astar(Map map){
		Territory[] territories = map.getTerritories();
		this.map = map;
		nodes = new Node[territories.Length];
		for(int i=0;i<nodes.Length;i++){
			nodes[i] = new Node(new Vector3(territories[i].gameObject.transform.position.x, territories[i].gameObject.transform.position.z, 0));
		}
	}

	public Astar(Node[] nodes, GenerateAStarGrid grid){
		this.nodes = nodes;
		this.grid = grid;
		Debug.Log(nodes.Length);

	}

	public void findPath(Node start, Node end){
		this.start = start;
		this.end = end;
		if(start.Equals(end)) return;
		Debug.Log(start + ", " + end + ", " + nodes[nodes.Length-1].pos.x + ", " + nodes[nodes.Length-1].pos.y);
		updateNodes(start);
		recursive(start);
		finished = true;
	}

	public void recursive(Node current){
		float lowestFCost = 999;
		float lowestHCost = 999;
		Node lowest = current;
		foreach(Node n in nodes){
			if(n.getFCost() == 0) continue;
			if(n.getFCost() < lowestFCost && !n.closed){
				lowestFCost = n.getFCost();
				lowestHCost = n.getHCost();
				lowest = n;
			}
		}
		if(!lowest.Equals(start))
			path.Add(lowest);
		
		if(!lowest.Equals(end)){
			lowest.close();
			updateNodes(lowest);
			recursive(lowest);
		} else {
			Debug.Log("Found end");
			foreach(Node n in nodes){
				n.reset();
			}
		}
	}

	public void updateNodes(Node node){
		Node left = getNode(node.pos.x-1, node.pos.y, node.pos.z);
		Node right = getNode(node.pos.x+1, node.pos.y, node.pos.z);
		Node front = getNode(node.pos.x, node.pos.y-1, node.pos.z);
		Node back = getNode(node.pos.x, node.pos.y+1, node.pos.z);
		Node topRight = getNode(node.pos.x+1, node.pos.y-1, node.pos.z);
		Node topLeft = getNode(node.pos.x-1, node.pos.y-1, node.pos.z);
		Node bottomLeft = getNode(node.pos.x-1, node.pos.y+1, node.pos.z);
		Node bottomRight = getNode(node.pos.x+1, node.pos.y+1, node.pos.z);

		if(left != null)
			left.update(start,end);
		if(right != null)
			right.update(start,end);
		if(front != null)
			front.update(start,end);
		if(back != null)
			back.update(start,end);
		if(topRight != null)
			topRight.update(start,end);
		if(topLeft != null)
			topLeft.update(start,end);
		if(bottomLeft != null)
			bottomLeft.update(start,end);
		if(bottomRight != null)
			bottomRight.update(start,end);
	}

	public Node getNode(float x, float y, float z){
		foreach(Node n in nodes){
			if(n.pos.x == x && n.pos.y == y && n.pos.z == z)
				return n;
		}
		return null;
	}

	private void updateText(){
		foreach(Node n in nodes){
			GameObject o = grid.getRealNode(n);
			if(o  != null)
				o.transform.GetChild(0).GetComponent<Text>().text = n.getFCost() + "";
		}
	}

	public Node[] getPath(){
		Node[] r = (Node[])path.ToArray(typeof(Node));
		path.Clear();
		return r;
	}

	public bool getFinished(){
		return finished;
	}
}
