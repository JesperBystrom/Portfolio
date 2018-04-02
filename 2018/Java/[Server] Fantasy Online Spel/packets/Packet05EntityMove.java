package packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet05EntityMove extends Packet {

	public int entityId;
	public int xTarget;
	public int yTarget;
	
	public Packet05EntityMove(){
		
	}
	
	public Packet05EntityMove(int entityId, int xTarget, int yTarget){
		this.entityId = entityId;
		this.xTarget = xTarget;
		this.yTarget = yTarget;
	}
	
	@Override
	public void write(DataOutputStream dataOut) throws IOException {
		dataOut.writeByte(Packet.ENTITY_MOVE);
		dataOut.writeInt(entityId);
		dataOut.writeInt(xTarget);
		dataOut.writeInt(yTarget);
	}

	@Override
	public void read(DataInputStream dataIn) throws IOException {
		entityId = dataIn.readInt();
		xTarget = dataIn.readInt();
		yTarget = dataIn.readInt();
	}

}
