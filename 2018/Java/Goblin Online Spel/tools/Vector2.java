package tools;

/*
 * @author Jesper Byström
 * Datum: 2018-02-10
 * Class: 
 * 
 */

public class Vector2 {
	public int x;
	public int y;
	public Vector2(){
	}
	public Vector2(int x, int y){
		this.x = x;
		this.y = y;
	}
	public Vector2 duplicate() {
		return new Vector2(x,y);
	}
	public static Vector2 zero(){
		return new Vector2(0,0);
	}
	public static Vector2 one(){
		return new Vector2(1,1);
	}
	public static Vector2 baseTileSize(){
		return new Vector2(16, 16);
	}
	public float length(){
		return (float)Math.abs(Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2)));
	}
	public int distance(Vector2 vec){
		int z = (int)Math.abs(Math.sqrt(Math.pow(x-vec.x, 2)+Math.pow(y-vec.y, 2)));
		//int z = (int)Math.abs(Math.sqrt(Math.pow(x+y,2)-Math.pow(vec.x+vec.y, 2)));
		return z;
	}
	public int normalize(Vector2 max){
		int normalized = (((int)x-0)/(1-0));
		return normalized;
	}
}
