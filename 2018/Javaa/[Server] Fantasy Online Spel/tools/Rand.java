package tools;

import java.util.Random;

public class Rand {

	private static Random r = new Random();
	public static int range(int min, int max){
		return r.nextInt((max-min) + 1) + min;
	}
}
