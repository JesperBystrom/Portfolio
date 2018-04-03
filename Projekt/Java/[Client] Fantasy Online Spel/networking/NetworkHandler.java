package networking;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;

import entities.Entity;
import entities.EntityHuman;
import entities.EntityMob;
import entities.EntityType;
import tools.Tools;
import tools.Vector2;
import world.Map;

public class NetworkHandler {

	public NetworkClient client;
	public DataInputStream dataIn;
	
	public NetworkHandler(NetworkClient client){
		this.client = client;
	}
	
	public void parsePacket(DatagramPacket packet){
		
		ByteArrayInputStream byteIn = new ByteArrayInputStream(packet.getData());
		dataIn = new DataInputStream(byteIn);
		
		try {
			byte tag = dataIn.readByte(); //this reads the tag example: 0x00 0x01 0x79
			switch(tag){
			case Packet.LOGIN:
				handlePacket(new Packet00Login());
				break;
			case Packet.MESSAGE:
				handlePacket(new Packet01Message());
				break;
			case Packet.MOVE:
				handlePacket(new Packet02Move());
				break;
			case Packet.ATTACK:
				handlePacket(new Packet03AttackPlayer());
				break;
			case Packet.PLAYER_INFO:
				handlePacket(new Packet04PlayerInformation());
				break;
			case Packet.ENTITY_MOVE:
				handlePacket(new Packet05EntityMove());
				break;
			case Packet.ENTITY_SPAWN:
				handlePacket(new Packet06EntitySpawn());
				break;
			case Packet.ENTITY_HURT:
				handlePacket(new Packet07EntityHurt());
				break;
			case Packet.ALIVE:
				handlePacket(new Packet08Alive());
				break;
			case Packet.DISCONNECT:
				handlePacket(new Packet09Disconnect());
				break;
			case Packet.TEST:
				handlePacket(new Packet79Test());
				break;
			}
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	public void handlePacket(Packet00Login packet) throws IOException{
		packet.read(dataIn);
		//System.out.println(packet.getName());
		if(packet.getName().equalsIgnoreCase(client.username)){
			System.out.println("We have the same name as client username: " + client.username + ", " + client.isConnected());
			if(!client.isConnected()){
				Map map = client.game.getMap();
				//String username = ("Player" + (int)((Math.random()+1) * 5000));
				map.player = new NetEntityPlayer(client.game.getKeyboardInput(), client.game, client, client.username, packet.getPort());
				client.localPlayer = map.player;
				client.game.startRound(packet.getRoundTimer());
				map.add(map.player);
				client.connect();
				System.out.println("You're now connected to the game server: " + packet.getName() + ", " + map.player);
			}
		} else {
			boolean found = false;
			for(NetEntityPlayer c : client.getPlayers()){
				if(c.name.equalsIgnoreCase(packet.getName()))
					found = true;
			}
			if(!found){
				System.out.println("A new player has joined: " + packet.getName() + " | x: " + packet.getX() + " | y: " + packet.getY());
				client.addConnection(new NetEntityPlayer(client.game, packet.getName(), new Vector2(packet.getX(), packet.getY())));
			}
		}
	}
	
	public void handlePacket(Packet01Message packet) throws IOException{
		packet.read(dataIn);
		System.out.println(packet.getMessage());
	}
	
	public void handlePacket(Packet02Move packet) throws IOException{
		packet.read(dataIn);
		NetEntityPlayer p = client.lookUpPlayer(packet.getName());
		if(p != null){
			int xDir = (packet.getX() - p.position.x);
			int yDir = (packet.getY() - p.position.y);
			
			int z = (int)Math.sqrt(Math.pow(xDir, 2) + Math.pow(yDir, 2));
			
			p.playDirectionalAnimations(packet.getX(), packet.getY(), p.position.x, p.position.y);
			
			p.position.x = packet.getX();
			p.position.y = packet.getY();
			//p.moveTowards(packet.getX(), packet.getY(), z);
		}
	}
	
	public void handlePacket(Packet03AttackPlayer packet) throws IOException{
		packet.read(dataIn);
		NetEntityPlayer p = client.game.getMap().getNetworkPlayer();
		p.hurt(1, Tools.getRealDirection(packet.getDirection()));
		client.sendPacket(new Packet04PlayerInformation(p.health, p.name));
	}
	
	public void handlePacket(Packet04PlayerInformation packet) throws IOException{
		packet.read(dataIn);
		NetEntityPlayer p = client.lookUpPlayer(packet.getName());
		if(p != null){
			p.health = packet.getHealth();
			System.out.println("Player lost some health: " + packet.getName() + " | " + packet.getHealth());
			if(p.health <= 0)
				p.kill();
		}
	}
	
	public void handlePacket(Packet05EntityMove packet) throws IOException{
		packet.read(dataIn);
		for(EntityMob m : client.game.getMap().getMobs()){
			if(m.entityId == packet.entityId){
				m.moveTowards(packet.x, packet.y);
			}
		}
	}
	
	public void handlePacket(Packet06EntitySpawn packet) throws IOException{
		packet.read(dataIn);
		
		NetEntityPlayer p = client.game.getMap().getNetworkPlayer();
		
		switch(EntityType.fromInteger(packet.entityType)){
		case HUMAN:
			EntityMob e = new EntityHuman(client.game, packet.x, packet.y);
			e.entityId = packet.entityId;
			client.addMonster(e);
			break;
		}
	}
	
	private void handlePacket(Packet07EntityHurt packet) throws IOException {
		packet.read(dataIn);
		EntityMob mob = client.lookUpMob(packet.entityId);
		System.out.println("[CLIENT] mob getting hurt: " + mob.entityId + ", " + packet.direction + ", " + mob.health);
		mob.hurt(1, packet.direction);
		mob.health -= 1;
		if(mob.health <= 0) client.removeMonster(mob);
	}
	
	private void handlePacket(Packet08Alive packet) {
		client.sendPacket(new Packet08Alive());
	}
	
	private void handlePacket(Packet09Disconnect packet) throws IOException {
		packet.read(dataIn);
		System.out.println("A player disconnected " + packet.name);
		NetEntityPlayer p = client.lookUpPlayer(packet.name);
		client.game.getMap().remove(p);
	}
	
	public void handlePacket(Packet79Test packet) throws IOException{
		packet.read(dataIn);
	}
}