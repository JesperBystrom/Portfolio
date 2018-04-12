import java.util.ArrayList;

/*
 * 
 * Date: 2018-04-12
 * @author: Jesper Byström
 * 
 */

public class Main {

	public int[] array = {
		10,5,15,7,2,9,31
	};
	
	public ArrayList<Node> nodes = new ArrayList<Node>();
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main(){
		Node root = new Node(array[0]);
		nodes.add(root);
		addToTree(root, 1);
		
		//findNode(root, 15);
		traverse(root);
		//print();
		
	}
	
	public void addToTree(Node root, int i){
		if(i == array.length) i = array.length-1;
		Node current = root;
		Node n = new Node(array[i++]);
		nodes.add(n);
		while(true){
			if(n.val < current.val){
				if(current.left != null){
					current = current.left;
					continue;
				}
				current.left = n;
				addToTree(root, i);
			}
			else if(n.val > current.val){
				if(current.right != null){
					current = current.right;
					continue;
				}
				current.right = n;
				addToTree(root, i);
			}
			break;
		}
	}
	
	public boolean findNode(Node current, int val){
		if(val > current.val){
			//right side
			return findNode(current.right, val);
		} else if(val < current.val){
			//left side
			return findNode(current.left, val);
		}
		System.out.println(current.val + ", " + val);
		if(current.val == val) {
			System.out.println("Found it");
			return true;
		}
		return false;
	}
	
	public void traverse(Node current){
		if(current != null){
			traverse(current.left);
			System.out.print(current.val + ", ");
			traverse(current.right);
		}
	}
	
	public void print(){
		System.out.println();
		for(int j=0;j<nodes.size();j++){
			Node l = nodes.get(j).left;
			Node r = nodes.get(j).right;
			System.out.print("Node: " + nodes.get(j).val);
			if(l != null)
				System.out.print(", " + l.val);
			else
				System.out.print(", " + l);
			if(r != null)
				System.out.print(", " + r.val);
			else
				System.out.print(", " + r);
			System.out.println();
		}
	}
}

class Node {
	int val;
	Node left;
	Node right;
	
	public Node(int val){
		this.val = val;
	}
}
