package shaders;

/*
 * @author Jesper Byström
 * Datum: 2018-02-10
 * Class: 
 * 
 */

public class DistortedShader extends Shader {
	
	float v = 0;
	
	@Override
	public int getPixel(int x, int y, int w, int xOffset, int yOffset) {
		int pixelIndex = (x+xOffset) + (y+yOffset) * w;
		return pixels[pixelIndex];
	}
	@Override
	public boolean setPixel(int[] pixels, int pixelToSet, double x, double y, int w, int h) {
		//float len = (float) (Math.sqrt(Math.pow((x-EntityPlayer.position.x),2)+Math.pow((y-EntityPlayer.position.y),2)));
		//v += 1/10000f;
		//if(v >= 360) v = 0;
		//int xMod = (int)(x + (Math.cos(v*0.5f)*100)%8);
		int xMod = (int)x;
		int yMod = (int)y;
		if(super.setPixel(pixels, pixelToSet, xMod, y, w, h)) return false;
		pixels[xMod + yMod * w] = pixelToSet;
		return true;
	}

}
