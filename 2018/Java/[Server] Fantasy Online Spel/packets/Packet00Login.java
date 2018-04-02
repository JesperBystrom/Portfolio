package packets;

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
	
	public Packet00Login(String name,int x, int y, int port, int roundTimer){
		this.name = name;
		this.x = x;
		this.y = y;
		this.port = port;
		this.roundTimer = roundTimer;
	}
	public Packet00Login(String name){
		this.name = name;
		this.x = -1;
		this.y = -1;
	}
	
	@Override
	public void write(DataOutputStream dataOut) throws IOException {
		dataOut.writeByte(Packet.LOGIN);
		dataOut.writeUTF(name);
		dataOut.writeInt(port);
		dataOut.writeInt(roundTimer);
		if(!localConnect()){
			dataOut.writeInt(x);
			dataOut.writeInt(y);
		}
	}

	@Override
	public void read(DataInputStream dataIn) throws IOException {
		name = dataIn.readUTF();
		ip = dataIn.readUTF();
		port = dataIn.readInt();
		if(!localConnect()){
			x = dataIn.readInt();
			y = dataIn.readInt();
		}
	}
	public String getIP(){
		return ip;
	}
	public int getPort(){
		return port;
	}
	public String getName(){
		return this.name;
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
}
