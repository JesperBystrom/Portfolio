package entities;

import java.net.InetAddress;

import main.Main;

public class NetEntityPlayer extends EntityPlayer {
	
	public String name;
	public InetAddress ip;
	public int port;
	public boolean connected = true;
	
	public NetEntityPlayer(){
		super();
	}
	
	public NetEntityPlayer(Main main){
		super(main);
	}
	
	public NetEntityPlayer(String name, InetAddress ip, int port) {
		super();
		this.name = name;
		this.ip = ip;
		this.port = port;
	}
	
	public boolean equals(NetEntityPlayer p){
		return this.ip == p.ip && this.port == p.port;
	}
}
