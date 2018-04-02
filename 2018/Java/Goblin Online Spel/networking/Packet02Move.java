package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import tools.Vector2;

public class Packet02Move extends Packet {

	private int x, y;
	private String name;
	
	public Packet02Move(){
		
	}
	public Packet02Move(String name, int x, int y){
		this.name = name;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void write(DataOutputStream dataOut) throws IOException {
		dataOut.writeByte(Packet.MOVE);
		dataOut.writeUTF(name);
		dataOut.writeInt(x);
		dataOut.writeInt(y);
	}

	@Override
	public void read(DataInputStream dataIn) throws IOException {
		name = dataIn.readUTF();
		x = dataIn.readInt();
		y = dataIn.readInt();
	}
	
	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public String getName() {
		return name;
	}
}
