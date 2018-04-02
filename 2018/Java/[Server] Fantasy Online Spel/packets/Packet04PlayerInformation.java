package packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet04PlayerInformation extends Packet {

	private int x;
	private int y;
	private int health;
	private String name;
	
	public Packet04PlayerInformation() {
		
	}
	
	public Packet04PlayerInformation(int health, String name){
		this.health = health;
		this.name = name;
	}
	
	@Override
	public void write(DataOutputStream dataOut) throws IOException {
		dataOut.writeByte(Packet.PLAYER_INFO);
		dataOut.writeUTF(name);
		dataOut.writeInt(health);
		dataOut.writeByte(sendTo);
	}

	@Override
	public void read(DataInputStream dataIn) throws IOException {
		name = dataIn.readUTF();
		health = dataIn.readInt();
		sendTo = dataIn.readByte();
	}

	public int getX(){
		return x;
	}
	public int getY(){
		return y;
	}
	public int getHealth(){
		return health;
	}
	public String getName(){
		return name;
	}
}
