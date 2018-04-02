package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet07EntityHurt extends Packet {

	public int entityId;
	public int sourceId;
	public int direction;
	public int port;
	
	public Packet07EntityHurt(){
		
	}
	
	public Packet07EntityHurt(int entityId, int port, int direction){
		this.entityId = entityId;
		this.port = port;
		this.direction = direction;
	}
	
	@Override
	public void write(DataOutputStream dataOut) throws IOException {
		dataOut.writeByte(Packet.ENTITY_HURT);
		dataOut.writeInt(entityId);
		dataOut.writeInt(port);
		dataOut.writeInt(direction);
	}

	@Override
	public void read(DataInputStream dataIn) throws IOException {
		entityId = dataIn.readInt();
		port = dataIn.readInt();
		direction = dataIn.readInt();
	}

}
