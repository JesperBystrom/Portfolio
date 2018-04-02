package tools;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.Random;
/*
 * @author Jesper Byström
 * Datum: 2018-02-10
 * Class: 
 * 
 */

public class Tools {
	private static Random random = new Random();
	public static int choose(int... numbers){
		int r = random.nextInt(numbers.length);
		return numbers[r];
	}
	
	public static byte[] objectToByteArray(Object object){
		ByteArrayOutputStream bOutput = new ByteArrayOutputStream();
		ObjectOutput o = null;
		try {
			o = new ObjectOutputStream(bOutput);
			o.writeObject(bOutput);
			o.flush();
			return bOutput.toByteArray();
		} catch (IOException e){
			e.printStackTrace();
		}
		return null;
	}
	public static Object byteArrayToObject(byte[] b){
		ByteArrayInputStream bInput = new ByteArrayInputStream(b);
		ObjectInput o = null;
		try {
			o = new ObjectInputStream(bInput);
			return o.readObject();
		} catch (IOException e){
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	public static int getByteBufferIndex(int index){
		return index * 4;
	}
	public static int clamp(int val, int min, int max){
		if(val < min) return min;
		if(val > max) return max;
		return val;
	}
}
