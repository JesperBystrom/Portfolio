package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet03AttackPlayer extends Packet {

	private String name;
	private int damage;
	private int direction;
	
	public Packet03AttackPlayer(){
		
	}
	
	public Packet03AttackPlayer(String name, int damage, int direction){
		this.name = name;
		this.damage = damage;
		this.direction = direction;
	}
	
	@Override
	public void write(DataOutputStream dataOut) throws IOException {
		dataOut.writeByte(Packet.ATTACK);
		dataOut.writeUTF(name);
		dataOut.writeInt(damage);
		dataOut.writeInt(direction);
	}

	@Override
	public void read(DataInputStream dataIn) throws IOException {
		name = dataIn.readUTF();
		damage = dataIn.readInt();
		direction = dataIn.readInt();
	}
	
	public String getName(){
		return name;
	}

	public int getDirection() {
		return direction;
	}
	
	public int getDamage(){
		return damage;
	}

}
