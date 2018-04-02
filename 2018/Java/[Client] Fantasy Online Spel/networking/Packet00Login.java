package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;

import tools.Vector2;

public class Packet00Login extends Packet {

	private String name;
	private int x;
	private int y;
	private String ip;
	private int port;
	private int roundTimer = 0;
	
	public Packet00Login(){
	}
	
	public Packet00Login(String name, String ip, int port, int x, int y){
		this.name = name;
		this.ip = ip;
		this.port = port;
		this.x = x;
		this.y = y;
	}
	public Packet00Login(String name, String ip, int port){
		this.name = name;
		this.ip = ip;
		this.port = port;
		this.x = -1;
		this.y = -1;
	}
	
	@Override
	public void write(DataOutputStream dataOut) throws IOException {
		dataOut.writeByte(Packet.LOGIN);
		dataOut.writeUTF(name);
		dataOut.writeUTF(ip);
		dataOut.writeInt(port);
		if(!localConnect()){
			dataOut.writeInt(x);
			dataOut.writeInt(y);
		}
	}

	@Override
	public void read(DataInputStream dataIn) throws IOException {
		name = dataIn.readUTF();
		port = dataIn.readInt();
		roundTimer = dataIn.readInt();
		if(!localConnect()){
			x = dataIn.readInt();
			y = dataIn.readInt();
		}
	}
	
	public String getName(){
		return this.name;
	}
	public String getIP(){
		return ip;
	}
	public int getPort(){
		return port;
	}
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public boolean localConnect(){
		return (x == -1 && y == -1);
	}
	public int getRoundTimer(){
		return roundTimer;
	}
}
