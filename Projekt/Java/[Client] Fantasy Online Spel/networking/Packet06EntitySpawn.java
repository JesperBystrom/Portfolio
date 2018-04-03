package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import entities.EntityType;

public class Packet06EntitySpawn extends Packet {

	public int entityId;
	public int entityType;
	public int x;
	public int y;
	
	public Packet06EntitySpawn(){
		
	}
	
	public Packet06EntitySpawn(int entityId, EntityType entityType, int x, int y){
		this.entityId = entityId;
		this.entityType = entityType.ordinal();
		this.x = x;
		this.y = y;
	}
	
	@Override
	public void write(DataOutputStream dataOut) throws IOException {
		dataOut.writeByte(Packet.ENTITY_SPAWN);
		dataOut.writeInt(entityId);
		dataOut.writeInt(entityType);
		dataOut.writeInt(x);
		dataOut.writeInt(y);
	}

	@Override
	public void read(DataInputStream dataIn) throws IOException {
		entityId = dataIn.readInt();
		entityType = dataIn.readInt();
		x = dataIn.readInt();
		y = dataIn.readInt();
	}

}
