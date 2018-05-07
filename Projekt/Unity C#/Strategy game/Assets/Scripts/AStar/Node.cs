using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Node {
	public Vector3 pos;
	public bool closed = false;
	private float gCost;
	private float hCost;
	private float fCost;

	public Node(float x, float y, float z){
		pos = new Vector3(x,y,z);
	}

	public Node(Vector3 pos){
		this.pos = pos;
	}

	public void calculateGCost(Node node){
		this.gCost = Vector3.Distance(node.pos, pos);/*(node.pos.x - pos.x) + (node.pos.y-pos.y);*///Mathf.Sqrt(Mathf.Pow(node.pos.x-pos.x, 2) + Mathf.Pow(node.pos.y-pos.y, 2));//Mathf.Abs(node.pos.x-pos.x+node.pos.y-pos.y);//Vector3.Distance(pos, startNode.pos);//(int)Mathf.Sqrt(Mathf.Pow(Mathf.Abs(pos.x-startNode.pos.x), 2) + Mathf.Pow(Mathf.Abs(pos.y-startNode.pos.y), 2) + Mathf.Pow(Mathf.Abs(pos.z-startNode.pos.z), 2));
	}

	public void calculateHCost(Node node){
		this.hCost = Vector3.Distance(node.pos, pos);/*(node.pos.x - pos.x) + (node.pos.y-pos.y);*///Mathf.Sqrt(Mathf.Pow(node.pos.x-pos.x, 2) + Mathf.Pow(node.pos.y-pos.y, 2));//Mathf.Abs(node.pos.x-pos.x+node.pos.y-pos.y);//Vector3.Distance(pos, endNode.pos);//(int)Mathf.Sqrt(Mathf.Pow(Mathf.Abs(pos.x-endNode.pos.x), 2) + Mathf.Pow(Mathf.Abs(pos.y-endNode.pos.y), 2) + Mathf.Pow(Mathf.Abs(pos.z-endNode.pos.z), 2));
	}

	public void calculateFCost(){
		this.fCost = getGCost() + getHCost(); 
	}

	public float getGCost(){
		return gCost;
	}

	public float getHCost(){
		return hCost;
	}

	public float getFCost(){
		return fCost;
	}

	public void close(){
		closed = true;
	}

	public void update(Node start, Node end){
		calculateGCost(start);
		calculateHCost(end);
		calculateFCost();
	}

	public void reset(){
		this.gCost = 0;
		this.hCost = 0;
		this.fCost = 0;
		closed = false;
	}
}