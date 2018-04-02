package shaders;

import main.Game;

/*
 * @author Jesper Byström
 * Datum: 2018-02-10
 * Class: 
 * 
 */

public abstract class Shader {
	
	public static final int EFFECT_NONE = 0;
	public static final int EFFECT_REPLACEMENT = 1;
	public static final int EFFECT_BLEND = 2;
	public int effect = EFFECT_NONE;
	public int[] pixels;
	protected int color = ColorShader.DEFAULT_COLOR;
	protected boolean running = true;
	
	public void start(){
		running = true;
	}
	public void stop(){
		running = false;
	}
	public abstract int getPixel(int x, int y, int w, int xOffset, int yOffset);
	
	public boolean setPixel(int[] pixels, int pixelToSet, double xPixel, double yPixel, int w, int h){
		return xPixel < 0 || xPixel >= (w+96) || yPixel < 0 || yPixel >= (h+96);
	}
	public int[] getPixels(){
		return this.pixels;
	}
	public void setEffect(int effect){
		this.effect = effect;
	}
}
