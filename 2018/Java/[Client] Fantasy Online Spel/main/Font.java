package main;

import tools.Vector2;
import tools.Vector2f;

/*
 * @author Jesper Byström
 * Datum: 2018-02-17
 * Class: 
 * 
 */

public class Font {
	public static final int START = (8*26);
	
	private static String input = "ABCDEFGHIJKLMNOPQRSTUVWXYZ 0123456789.,:;'\"!?$%()-=+";
	
	public static void render(String text, int x, int y, Screen screen, boolean inWorldSpace){
		int widthBetweenCharacters = 8;
		int[] tiles = getTiles(text);
		//System.out.println(tiles[0]);
		
		for(int i=0;i<text.length();i++){
			screen.renderFont(x + (i*widthBetweenCharacters), y, tiles[i], 0, inWorldSpace);
		}
	}
	
	private static int[] getTiles(String text){
		int[] tiles = new int[text.length()];
		char[] c = input.toCharArray();
		
		for(int i=0;i<c.length;i++){
			for(int j=0;j<text.length();j++){
				//System.out.println(c[i]);
				if(c[i] == text.toUpperCase().charAt(j)){
					tiles[j] = i;
				}
				if(tiles[j] == 57 || tiles[j] == 58) tiles[j] = 26;
			}
		}
		return tiles;
	}
	public static void renderAll(Screen screen){
		String text = input;
		int[] tiles = getTiles(text);
		int x = 0;
		int y = 0;
		for(int i=0;i<text.length();i++){
			if(x >= screen.width-16){
				y += 8;
				x = 0;
			}
			screen.renderFont(x, y, tiles[i], 0, false);
			x += 8;
		}
	}
}
