package tools;

/*
 * @author Jesper Byström
 * Datum: 2018-02-10
 * Class: 
 * 
 */

public class Vector2f {
	public float x;
	public float y;
	public Vector2f(float x, float y){
		this.x = x;
		this.y = y;
	}
	public Vector2f duplicate() {
		return new Vector2f(x,y);
	}
	public static Vector2f zero(){
		return new Vector2f(0,0);
	}
	public static Vector2f one(){
		return new Vector2f(1,1);
	}
	public static Vector2f baseTileSize(){
		return new Vector2f(16, 16);
	}
	public float length(){
		return (float)Math.abs(Math.sqrt(Math.pow(this.x, 2) + Math.pow(this.y, 2)));
	}
	public static float distance(Vector2f vec1, Vector2 vec2){
		Vector2f newVector = new Vector2f(vec1.x-vec2.x,vec1.y-vec2.y);
		return (float)Math.abs(Math.sqrt(newVector.x*newVector.x + newVector.y*newVector.y));
		//return (float)Math.abs(Math.sqrt(Ma))
	}
}
