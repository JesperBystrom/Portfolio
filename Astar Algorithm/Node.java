import java.util.Random;

public class Node {
	public int x;
	public int y;
	public int gCost;
	public int hCost;
	public boolean wall;
	boolean closed = false;
	Random r = new Random();
	
	public Node(int x, int y){
		this.x = x;
		this.y = y;
		if(randomizeWall())
			wall = true;
	}
	
	public void update(Node start, Node end){
		gCost = (int)Math.round(Math.sqrt((Math.pow(x-start.x, 2)) + (Math.pow(y-start.y, 2))));
		hCost = Math.abs(x-end.x) + Math.abs(y-end.y);//(int)Math.round(Math.sqrt((Math.pow(x-end.x, 2)) + (Math.pow(y-end.y, 2))));
	}
	
	public boolean equals(Node n){
		return (this.x == n.x && this.y == n.y);
	}
	
	public int getFCost(){
		return gCost + hCost;
	}
	public String getFCostPrint(){
		return (getFCost()/10) + "";
	}
	public String getGCostPrint(){
		return (gCost/10) + "";
	}
	public String getHCostPrint(){
		return (hCost/10) + "";
	}
	public void close() {
		closed = true;
	}
	public boolean getClosed(){
		return closed;
	}
	public double distance(Node n){
		return Math.round(Math.sqrt((Math.pow(x-n.x, 2)) + (Math.pow(y-n.y, 2))));
	}
	public boolean randomizeWall(){
		return (r.nextInt(3) == 0 && x < (Window.WIDTH-64) && y < (Window.HEIGHT-64) && x > 64 && y > 64);
	}
}
