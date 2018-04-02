package networking;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class Packet {
	
	public static final byte LOGIN = 0x0000;
	public static final byte MESSAGE = 0x0001;
	public static final byte MOVE = 0x0002;
	public static final byte ATTACK = 0x0003;
	public static final byte PLAYER_INFO = 0x0004;
	public static final int ENTITY_MOVE = 0x0005;
	public static final int ENTITY_SPAWN = 0x0006;
	public static final int ENTITY_HURT = 0x0007;
	public static final int ALIVE = 0x0008;
	public static final int DISCONNECT = 0x0009;
	public static final byte TEST = 0x0079;

	public static final byte SEND_TO_ALL = 0x00;
	public static final byte SEND_TO_PLAYER = 0x01;
	
	public NetEntityPlayer sender;
	public byte sendTo = SEND_TO_PLAYER;
	
	public byte[] parseData(byte syntax, byte[] data){
		byte[] buffer = new byte[data.length + 1];
		System.arraycopy(data, 0, buffer, 1, data.length);
		buffer[0] = syntax;
		return buffer;
	}
	public byte[] copyArray(byte[] data, byte[] buffer, int start, int length){
		int j = start;
		for(int i=0;i<data.length;i++){
			data[i] = buffer[j++];
		}
		return buffer;
	}
	
	public static String packStrings(String ... args){
		String result = "";
		for(String s : args){
			result += s + ":";
		}
		return result;
	}
	public static String[] unpackStrings(String s){
		return s.split(":");
	}
	
	public abstract void write(DataOutputStream dataOut) throws IOException;
	public abstract void read(DataInputStream dataIn) throws IOException;
}
