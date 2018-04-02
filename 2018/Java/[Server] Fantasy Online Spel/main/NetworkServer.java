package main;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;

import entities.EntityMob;
import entities.NetEntityPlayer;
import packets.Packet;
import packets.Packet09Disconnect;

public class NetworkServer implements Runnable{

	public Main main;
	public DatagramSocket socket;
	public NetworkHandler handler = new NetworkHandler(this);
	public ArrayList<NetEntityPlayer> connections = new ArrayList<NetEntityPlayer>();
	
	public NetworkServer(Main main){
		try {
			socket = new DatagramSocket(5525);
		} catch (IOException e){
			e.printStackTrace();
		}
		this.main = main;
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		while(true){
			DatagramPacket packet = new DatagramPacket(new byte[1024], 1024);
			try {
				socket.receive(packet);
				//System.out.println(dataIn.readUTF());
				//handler.parsePacket(packet);
			} catch (IOException e){
				e.printStackTrace();
			}
			handler.parsePacket(packet);
		}
	}
	public void sendData(byte[] data){
		try {
			DatagramPacket dPacket = new DatagramPacket(data, data.length);
			socket.send(dPacket);
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	public void sendPacketToPlayer(NetEntityPlayer netPlayer, Packet packet){
		try {
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			DataOutputStream dataOut = new DataOutputStream(byteOut);
			packet.write(dataOut);
			DatagramPacket dataPacket = new DatagramPacket(byteOut.toByteArray(), byteOut.toByteArray().length, netPlayer.ip, netPlayer.port);
			socket.send(dataPacket);
			//System.out.println("Sending packet from server to client...");
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	public void sendPacketToEveryone(Packet packet){
		for(NetEntityPlayer p : getPlayers()){
			sendPacketToPlayer(p, packet);
		}
	}
	public void sendPacketToEveryoneExcept(NetEntityPlayer netPlayer, Packet packet){
		for(NetEntityPlayer p : getPlayers()){
			if(!p.name.equals(netPlayer.name))
				sendPacketToPlayer(p, packet);
		}
	}
	public void addConnection(NetEntityPlayer netPlayer){
		connections.add(netPlayer);
	}
	public NetEntityPlayer[] getPlayers(){
		return connections.toArray(new NetEntityPlayer[connections.size()]);
	}
	public NetEntityPlayer lookUpPlayer(String name){
		NetEntityPlayer[] players = getPlayers();
		for(NetEntityPlayer p : players){
			if(p.name.equals(name)){
				return p;
			}
		}
		return null;
	}
	
	public void disconnectPlayersWhoDontRespond(ArrayList<NetEntityPlayer> pings){
		NetEntityPlayer[] players = getPlayers();
		for(int i=0;i<pings.size();i++){
			for(NetEntityPlayer playerConnection : players){
				if(pings.get(i).equals(playerConnection)){
					playerConnection.connected = true;
				}
			}
		}
		for(NetEntityPlayer playerConnection : players){
			if(!playerConnection.connected){
				disconnect(playerConnection);
				sendPacketToEveryone(new Packet09Disconnect(playerConnection));
			}
			playerConnection.connected = false;
		}
		
		//[5][3][2][0][4]
		//[0][1][2][3][4][5]
	}
	public NetEntityPlayer lookUpPlayer(int localPort){
		for(NetEntityPlayer p : getPlayers()){
			if(p.port == localPort){
				return p;
			}
		}
		return null;
	}
	
	public EntityMob lookUpMob(int entityId){
		EntityMob[] mobs =  main.getMap().getMobs();
		for(EntityMob m : mobs){
			if(m.entityId == entityId)
				return m;
		}
		return null;
	}

	public void disconnect(NetEntityPlayer p) {
		System.out.println("Player disconnected");
		p.connected = false;
		connections.remove(p);
		sendPacketToEveryone(new Packet09Disconnect(p));
	}
}
