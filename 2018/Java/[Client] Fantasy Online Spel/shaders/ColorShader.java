package shaders;

/*
 * @author Jesper Byström
 * Datum: 2018-02-10
 * Class: 
 * 
 */

public class ColorShader extends Shader {

	public static final int DEFAULT_COLOR = 0xFFFFFFFF;
	
	public void input(int color) {
		this.color = color;
	}
	
	@Override
	public void stop() {
		super.stop();
		this.effect = Shader.EFFECT_NONE;
	}
	public void replace(){
		this.effect = Shader.EFFECT_REPLACEMENT;
	}
	public void blend(){
		this.effect = Shader.EFFECT_BLEND;
	}

	@Override
	public int getPixel(int x, int y, int w, int xOffset, int yOffset) {
		int pixelIndex = (x+xOffset) + (y+yOffset) * w;
		switch(effect){
		case Shader.EFFECT_BLEND:
			return pixels[pixelIndex] * color;
		case Shader.EFFECT_REPLACEMENT:
			return color;
		default:
			return pixels[pixelIndex];
		}
	}

	@Override
	public boolean setPixel(int[] pixels, int pixelToSet, double x, double y, int w, int h) {
		if(super.setPixel(pixels, pixelToSet, x, y, w, h)) return false;
		pixels[(int)(x + y*w)] = pixelToSet;
		return true;
	}

	public void setColor(int color) {
		this.color = color;
	}
}
