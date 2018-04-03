package packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import entities.NetEntityPlayer;

public class Packet09Disconnect extends Packet {

	public String name;
	
	public Packet09Disconnect(){
		
	}
	
	public Packet09Disconnect(NetEntityPlayer p) {
		this.name = p.name;
	}

	@Override
	public void write(DataOutputStream dataOut) throws IOException {
		dataOut.writeByte(Packet.DISCONNECT);
		dataOut.writeUTF(name);
	}

	@Override
	public void read(DataInputStream dataIn) throws IOException {
		name = dataIn.readUTF();
	}

}
