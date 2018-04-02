package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class Packet79Test extends Packet {

	private int x=255, y=32;
	private ByteBuffer b;
	public Packet79Test(){
		
	}
	
	@Override
	public void write(DataOutputStream dataOut) {
		/*b = ByteBuffer.allocate(8);
		b.putInt(x);
		b.putInt(y);
		network.sendData(getData());
		b.rewind();
		*/
	}

	@Override
	public void read(DataInputStream dataIn) throws IOException {
	}
}
