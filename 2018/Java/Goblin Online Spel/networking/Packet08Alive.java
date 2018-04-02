package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet08Alive extends Packet {

	public Packet08Alive(){
		
	}
	
	@Override
	public void write(DataOutputStream dataOut) throws IOException {
		dataOut.writeByte(Packet.ALIVE);
	}

	@Override
	public void read(DataInputStream dataIn) throws IOException {
	}

}
