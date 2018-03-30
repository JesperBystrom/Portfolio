import java.awt.Color;
import java.awt.Graphics;

public class Player {

	int x=0, y=0;
	int currentNode = 0;
	
	public void move(Node n){
		/*if(currentNode < path.length){
			if(path[currentNode].x == x && path[currentNode].y == y){
				currentNode++;
			} else {
				if((player.x - path[currentNode].x) < 0)
					player.x += 1;
				else if((player.x - path[currentNode].x) > 0)
					player.x -= 1;
				else if((player.y - path[currentNode].y) > 0)
					player.y -= 1;
				else if((player.y - path[currentNode].y) < 0)
					player.y += 1;
				else {
					player.x = path[currentNode].x;
					player.y = path[currentNode].y;
					path = null;
					return;
				}
			}
		} else {
			path = null;
			finder.foundEnd = false;
			currentNode = 0;
		}*/
	}
}
