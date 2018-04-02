package entities;

import main.Main;
import packets.Packet05EntityMove;
import packets.Packet06EntitySpawn;
import tools.Vector2;

/*
 * @author Jesper Byström
 * Datum: 2018-02-10
 * Class: 
 * 
 */

public class EntityMob extends EntityLiving {
	private EntityState state = EntityState.IDLE;
	private EntityLiving chasing;
	private int moveTimer = 240;
	
	public EntityMob(Main main) {
		super(main);
	}
	
	@Override
	public void update() {
		
		velocity = Vector2.zero();
		
		super.update();
		speed = main.getTicks()&1;
		moveTimer += (Math.random()+1);
		if(state == EntityState.IDLE && moveTimer >= 240){
			moveToRandomLocation(4);
			for(NetEntityPlayer p : main.getServer().getPlayers()){
				int xLen = Math.abs(p.position.x - position.x);
				int yLen = Math.abs(p.position.y - position.y);
				System.out.println(xLen + " | " + yLen);
				if(xLen < 16 && yLen < 16){
					chasing = p;
					state = EntityState.CHASING;
				}
			}
			moveTimer = 0;
		}
		if(state == EntityState.CHASING){
			chase();
		}
		/*if(destination != null){
			move(destination.x, destination.y);
		}*/
	}
	
	public void move(int xTowards, int yTowards){
		float xDelta = position.x - xTowards;
		float yDelta = position.y - yTowards;
		
		int xVel = 0, yVel = 0;
		
		if(xDelta < 0)
			xVel++;
		if(xDelta > 0)
			xVel--;
		if(yDelta < 0)
			yVel++;
		if(yDelta > 0)
			yVel--;
		
		velocity.x = xVel * speed;
		velocity.y = yVel * speed;
		
		//position.x += velocity.x;
		//position.y += velocity.y;
		//if(game.getTicks() % 2 == 0)
		//super.move(velocity.x, velocity.y);
	}
	
	@Override
	public void moveToRandomLocation(int radius){
		velocity = Vector2.zero();
		destination = main.getMap().getPoint(position.x, position.y, radius);//main.getMap().getPoint(position.x, position.y, radius);
		
		moveTimer = Math.abs((position.x - destination.x) + (position.y - destination.y)) + 60;
		
		position.x = destination.x;
		position.y = destination.y;
		onMove();
	}
	public void chase(){
		destination = chasing.position;
		onMove();
	}
	@Override
	public void hurt(int amount, int port) {
		super.hurt(amount, port);
		NetEntityPlayer p = main.getServer().lookUpPlayer(port);
		chasing = p;
		state = EntityState.CHASING;
	}
	
	public void setState(EntityState state){
		this.state = state;
	}
	
	public EntityState getState(){
		return state;
	}
	
	public void onMove(){
		main.getServer().sendPacketToEveryone(new Packet05EntityMove(entityId, destination.x, destination.y));
	}
}
