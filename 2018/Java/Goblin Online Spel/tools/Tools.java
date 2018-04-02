package tools;

import java.util.Random;

import entities.Entity;

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
	public static double xRotation(int angle, double x, double y){
		double cs = Math.cos(angle);
		double sn = Math.sin(angle);
		return x + cs * y - sn;
	}
	public static double yRotation(int angle, double x, double y){
		double cs = Math.cos(angle);
		double sn = Math.sin(angle);
		return x + sn * y + cs;
	}
	public static Vector2 getRealDirection(int direction){
		int xDir = 0;
		int yDir = 0;
		if(direction == Entity.UP) yDir = -1;
		if(direction == Entity.RIGHT) xDir = 1;
		if(direction == Entity.DOWN) yDir = 1;
		if(direction == Entity.LEFT) xDir = -1;
		return new Vector2(xDir, yDir);
	}
	
	public static float getMilliSeconds(int seconds){
		return seconds / 1000;
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
