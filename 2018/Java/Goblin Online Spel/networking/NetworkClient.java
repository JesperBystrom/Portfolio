package networking;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Scanner;

import entities.EntityMob;
import main.Game;

public class NetworkClient implements Runnable{

	public DatagramSocket socket;
	public String ip;
	public InetAddress address;
	public int port;
	public NetworkHandler handler = new NetworkHandler(this);
	public ArrayList<NetEntityPlayer> connections = new ArrayList<NetEntityPlayer>();
	public ArrayList<EntityMob> monsters = new ArrayList<EntityMob>();
	public Game game;
	public NetEntityPlayer localPlayer;
	private boolean connected = false;
	
	
	Scanner scanner = new Scanner(System.in);
	public String username;
	
	public NetworkClient(Game game){
		try {
			socket = new DatagramSocket();
			address = InetAddress.getByName("127.0.0.1"); //81.235.208.251
			ip = address.getHostAddress();
			port = 5525;
			this.game = game;
		} catch (IOException e){
			e.printStackTrace();
		}
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		while(true){
			try {
				DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
				socket.receive(packet);
				handler.parsePacket(packet);
			} catch (IOException e){
				e.printStackTrace();
			}
		}
	}
	public void sendPacket(Packet packet){
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			DataOutputStream dataOut = new DataOutputStream(byteOut);
			
			packet.write(dataOut);
			byte[] data = byteOut.toByteArray();
			socket.send(new DatagramPacket(data, data.length, address, port));
			dataOut.flush();
			byteOut.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void addConnection(NetEntityPlayer netEntityPlayer) {
		connections.add(netEntityPlayer);
		game.getMap().add(netEntityPlayer);
	}
	public void addMonster(EntityMob mob){
		monsters.add(mob);
		game.getMap().add(mob);
	}
	public void removeMonster(EntityMob mob){
		monsters.remove(mob);
		game.getMap().remove(mob);
	}
	public NetEntityPlayer[] getPlayers(){
		return connections.toArray(new NetEntityPlayer[connections.size()]);
	}
	public EntityMob[] getMonsters(){
		return monsters.toArray(new EntityMob[monsters.size()]);
	}
	public NetEntityPlayer lookUpPlayer(String name){
		for(NetEntityPlayer p : getPlayers()){
			if(p.name.equals(name)){
				return p;
			}
		}
		return null;
	}
	
	public EntityMob lookUpMob(int entityId){
		for(EntityMob e : getMonsters()){
			if(e.entityId == entityId)
				return e;
		}
		return null;
	}

	public void connect() {
		connected = true;
	}
	public boolean isConnected(){
		return connected;
	}
}
