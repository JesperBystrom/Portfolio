package entities;

import main.Main;
import packets.Packet05EntityMove;
import tools.Vector2;

public class EntityLiving extends Entity {

	public Vector2 destination;
	
	public EntityLiving(Main main) {
		super(main);
	}

	public EntityLiving() {
		super();
	}

	@Override
	public void update(){
		super.update();
	}
	
	public void moveToRandomLocation(int radius){
		velocity = Vector2.zero();
		destination = chooseNewPoint(radius);
		position.x = destination.x;
		position.y = destination.y;
		main.getServer().sendPacketToEveryone(new Packet05EntityMove(entityId, destination.x, destination.y));
	}
	private Vector2 chooseNewPoint(int radius){
		return main.getMap().getPoint(position.x, position.y, radius);//main.getMap().getPoint(position.x, position.y, radius);
	}
}
