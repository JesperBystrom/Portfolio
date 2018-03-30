import java.awt.Canvas;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
/*
 * 
 * Date: 2018-03-25
 * @Author: Jesper Byström
 * 
 */
public class Main extends Canvas implements Runnable {

	public boolean running = true;
	public static final int CELL_SIZE = 16;
	public static Node start = new Node(0,0);
	public static Node end;
	public int xMouse = 0;
	public int yMouse = 0;
	
	public Player player = new Player();
	public Window window = new Window(this);
	public ArrayList<Node> open = new ArrayList<Node>();
	public ArrayList<Node> closed = new ArrayList<Node>();
	public MouseHandler handler = new MouseHandler(this);
	
	
	public static void main(String[] args){
		new Thread(new Main()).start();
	}
	
	public Main(){
		//Generate nodes
		for(int x=0;x<Window.WIDTH;x += CELL_SIZE){
			for(int y=0;y<Window.HEIGHT;y += CELL_SIZE){
				open.add(new Node(x,y));
			}
		}
		start = open.get(0);
		end = open.get(open.size()-1);
	}
	
	Node recentlyClosed = start;
	boolean foundEnd = false;
	int currentNode = -1;
    public void pathFind(){
    	//This piece of code will make the player follow the path
    	if(foundEnd){
    		if(currentNode == -1)
    			currentNode = 0;
    		
    		if(currentNode < closed.size()){
	    		Node current = null;
	    		current = closed.get(currentNode);
    			currentNode++;
    			player.x = current.x;
    			player.y = current.y;
    		}
    		return; //So that the rest of the pathfinding code isnt playing
    	}
    	//End of player following 
    	
        Node node = null;
    	int lowestCost = 99999; //base cost
        for(Node n : getNeighbours(recentlyClosed)){
            if(n.wall || n.getClosed()) {
            	continue;
            }
            if(n.getFCost() < lowestCost){
                lowestCost = n.getFCost();
                node = n;
            	update(node);
            }
        }
        if(node != null){
            node.close();
            recentlyClosed = node;
            closed.add(node);
        	if((node.x == end.x && node.y == end.y) || end.closed){
        		foundEnd = true;
        		System.out.println("Finished");
        	}
        } else {
        	//This code will make the pathfinder to move backwards in case it gets stuck
        	if(closed.size() > 0){
	        	recentlyClosed = closed.get(closed.size()-1);
	        	closed.remove(recentlyClosed);
        	}
        }
    }
    
	public Node[] getOpenNodes(){
		return open.toArray(new Node[open.size()]);
	}
	
	public Node[] getNeighbours(Node node){
		ArrayList<Node> neighbours = new ArrayList<Node>();
		for(Node n : getOpenNodes()){
			if(node.distance(n) == CELL_SIZE){
				neighbours.add(n);
			}
		}
		return neighbours.toArray(new Node[neighbours.size()]);
	}
	
	//This will round to the grid
	public int roundToNearestNode(int val){
		return (val/CELL_SIZE)*CELL_SIZE;
	}
	
	public void resetFinder(){
		//start = end;
		currentNode = -1;
		end = new Node(xMouse, yMouse);
		foundEnd = false;
		recentlyClosed = start;
		closed.clear();
		player.x = start.x;
		player.y = start.y;
		Random r = new Random();
		for(Node n : getOpenNodes()){
			n.closed = false;
			n.wall = false;
			if(n.randomizeWall())
				n.wall = true;
		}
	}
	
	
	@Override
	public void run() {
		while(running){
			//update();
			Point p = this.getMousePosition();
			
			if(p != null){
				xMouse = roundToNearestNode(p.x);
				yMouse = roundToNearestNode(p.y);
			}
			
    		if(handler.clicked){
    			resetFinder();
    		}
			
			pathFind();
			window.render();
			try {
				Thread.sleep(4);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void update(Node start){
		for(Node n : getNeighbours(start)){
			n.update(start, end);
		}
	}
}
