package etc;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class Tools {
	public static int[] imageToPixels(BufferedImage image){
		int[] pixels = new int[image.getWidth()*image.getHeight()];
		return image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
	}
	
	public static float clamp(float val, float min, float max){
		if(val < min)
			return min;
		if(val > max)
			return max;
		return val;
	}
	
	public static float lerp(float val, float target, float t){
		return (1-t) * val + t * target;
	}
}
