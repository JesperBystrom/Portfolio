package main;

public class Sprite {

	public static final byte FLIP_NONE = 0x00;
	public static final byte FLIP_X = 0x01;
	public static final byte FLIP_Y = 0x02;
	
	public int[] indexes;
	public int[] pixels;
	public int width;
	public int height;
	public int[] color = new int[3];
	public int xOffset;
	public int yOffset;
	
	public Sprite(int[] pixels, int col1, int col2, int col3, int w, int h){
		this.pixels = pixels;
		this.width = w;
		this.height = h;
		this.color[0] = col1;
		this.color[1] = col2;
		this.color[2] = col3;
	}
}
