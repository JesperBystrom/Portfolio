package packets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Packet01Message extends Packet {

	private String message;
	
	public Packet01Message(){
	}
	public Packet01Message(String message){
		this.message = message;
	}
	
	@Override
	public void write(DataOutputStream dataOut) throws IOException {
		dataOut.writeByte(Packet.MESSAGE);
		dataOut.writeUTF(message);
	}

	@Override
	public void read(DataInputStream dInput) throws IOException {
		this.message = dInput.readUTF();
	}
	
	public String getMessage() {
		return message;
	}
}
