package main;

/*
 * @author Jesper Byström
 * Datum: 2018-02-10
 * Class: 
 * 
 */

public class SpriteSheet {
	public int[] pixels;
	public int width;
	public int height;
	public int tileSize;
	
	public SpriteSheet(int[] pixels, int width, int height, int tileSize){
		this.pixels = pixels;
		this.width = width;
		this.height = height;
		this.tileSize = tileSize;
	}
	public Sprite[][] getSprites(int startTile, int w, int h){
		int xOffset = (startTile % 16); // 0
		
		Sprite[][] sprites = new Sprite[w>>4][h>>4];
		for(int i=0;i<sprites.length;i++){
			int xx = i + xOffset + 1 + 16;
			for(int j=0;j<sprites[i].length;j++){
				sprites[i][j] = new Sprite(xx + (j << 4));
			}
		}
		return sprites;
	}
}
