package main;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.util.ArrayList;
import java.util.HashMap;

import entities.Entity;
import entities.EntityMob;
import entities.NetEntityPlayer;
import packets.Packet;
import packets.Packet00Login;
import packets.Packet01Message;
import packets.Packet02Move;
import packets.Packet03AttackPlayer;
import packets.Packet04PlayerInformation;
import packets.Packet06EntitySpawn;
import packets.Packet07EntityHurt;
import packets.Packet08Alive;
import packets.Packet79Test;
import tools.Vector2;

public class NetworkHandler {

	public NetworkServer server;
	public ArrayList<NetEntityPlayer> responses = new ArrayList<NetEntityPlayer>();
	
	public NetworkHandler(NetworkServer server){
		this.server = server;
	}
	
	public void parsePacket(DatagramPacket packet){
		
		ByteArrayInputStream byteIn = new ByteArrayInputStream(packet.getData());
		DataInputStream dataIn = new DataInputStream(byteIn);
		
		try {
			byte tag = dataIn.readByte(); //this reads the tag example: 0x00 0x01 0x79
			switch(tag){
			case Packet.LOGIN:
				handlePacket(new Packet00Login(), dataIn, packet);
				break;
			case Packet.MESSAGE:
				handlePacket(new Packet01Message(), dataIn);
				break;
			case Packet.MOVE:
				handlePacket(new Packet02Move(), dataIn, packet);
				break;
			case Packet.ATTACK:
				handlePacket(new Packet03AttackPlayer(), dataIn, packet);
				break;
			case Packet.PLAYER_INFO:
				handlePacket(new Packet04PlayerInformation(), dataIn, packet);
				break;
			case Packet.ENTITY_HURT:
				handlePacket(new Packet07EntityHurt(), dataIn, packet);
				break;
			case Packet.ALIVE:
				handlePacket(new Packet08Alive(), packet);
				break;
			case Packet.TEST:
				handlePacket(new Packet79Test(), dataIn);
				break;
			}
			tag = -1;
			dataIn.reset();
			byteIn.reset();
		} catch (IOException e){
			e.printStackTrace();
		}
	}

	//Login
	private void handlePacket(Packet00Login packet, DataInputStream dataIn, DatagramPacket dataPacket) throws IOException{
		packet.read(dataIn);
		System.out.println("["+packet.getIP()+":"+packet.getPort()+"] New Connection: " + packet.getName() + ", " + packet.getX() + ", " + packet.getY());
		NetEntityPlayer p = new NetEntityPlayer(packet.getName(), dataPacket.getAddress(), dataPacket.getPort());
		p.position = new Vector2(packet.getX(), packet.getY());
		server.addConnection(p);
		System.out.println(server.connections.size());
		for(NetEntityPlayer c : server.getPlayers()){
			server.sendPacketToEveryone(new Packet00Login(c.name, c.position.x, c.position.y, dataPacket.getPort(), server.main.roundTimer));
		}
		for(Entity e : server.main.getMap().getEntities()){
			server.sendPacketToPlayer(p, new Packet06EntitySpawn(e.entityId, e.entityType, e.position.x, e.position.y));
		}
	}
	//Message
	private void handlePacket(Packet01Message packet, DataInputStream dataIn) throws IOException{
		packet.read(dataIn);
		System.out.println(packet.getMessage());
	}
	//Move
	private void handlePacket(Packet02Move packet, DataInputStream dataIn, DatagramPacket dataPacket) throws IOException {
		packet.read(dataIn);
		//System.out.println(packet.getName() + ", " + server.getPlayers().length);
		
		NetEntityPlayer p = server.lookUpPlayer(packet.getName());
		p.position.x = packet.getX();
		p.position.y = packet.getY();
		server.sendPacketToEveryone(packet);
	}
	//Attack
	private void handlePacket(Packet03AttackPlayer packet, DataInputStream dataIn, DatagramPacket dataPacket) throws IOException {
		packet.read(dataIn);
		NetEntityPlayer p = server.lookUpPlayer(packet.getName());
		if(p != null)
			server.sendPacketToPlayer(p, packet);
		else
			System.out.println("Failed to send attack packet");
	}
	private void handlePacket(Packet04PlayerInformation packet, DataInputStream dataIn, DatagramPacket dataPacket) throws IOException {
		packet.read(dataIn);
		NetEntityPlayer p = server.lookUpPlayer(packet.getName());
		server.sendPacketToEveryoneExcept(p, packet);
	}
	private void handlePacket(Packet07EntityHurt packet, DataInputStream dataIn, DatagramPacket dataPacket) throws IOException {
		packet.read(dataIn);
		EntityMob m = server.lookUpMob(packet.entityId);
		if(m != null){
			m.hurt(1, packet.port);
			server.sendPacketToEveryone(packet);
		}
	}
	private void handlePacket(Packet08Alive packet, DatagramPacket dataPacket) {
		NetEntityPlayer p = server.lookUpPlayer(dataPacket.getPort());
		p.connected = true;
	}
	//Test
	private void handlePacket(Packet79Test packet, DataInputStream dataIn) throws IOException{
		packet.read(dataIn);
	}
}
