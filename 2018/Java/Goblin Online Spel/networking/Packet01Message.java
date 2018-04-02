package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Packet01Message extends Packet {

	private String message;
	
	public Packet01Message() {
		// TODO Auto-generated constructor stub
	}

	public Packet01Message(String message) {
		this.message = message;
	}
	
	public void write(DataOutputStream dataOut) throws IOException {
		dataOut.writeByte(Packet.MESSAGE);
		dataOut.writeUTF(message);
	}

	@Override
	public void read(DataInputStream dataIn) throws IOException {
		dataIn.readUTF();
	}

	public String getMessage() {
		return message;
	}
}
