package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet05EntityMove extends Packet {

	public int entityId;
	public int x;
	public int y;
	
	public Packet05EntityMove(){
		
	}
	
	public Packet05EntityMove(int entityId, int x, int y){
		this.entityId = entityId;
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void write(DataOutputStream dataOut) throws IOException {
		dataOut.writeByte(Packet.ENTITY_MOVE);
		dataOut.writeInt(entityId);
		dataOut.writeInt(x);
		dataOut.writeInt(y);
	}

	@Override
	public void read(DataInputStream dataIn) throws IOException {
		entityId = dataIn.readInt();
		x = dataIn.readInt();
		y = dataIn.readInt();
	}

}
