import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

/*
 * 
 * Description: Some fun project that prints out images in console 
 * For big images, turn off limited 
 * Date: 2018-04-12
 * @author: Jesper Byström
 * 
 */

public class Main {

	private BufferedImage image;
	private int[] pixels;
	
	public static void main(String[] args) {
		new Main();
	}
	
	public Main(){
		try {
			image = ImageIO.read(new FileInputStream("res/cat.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		pixels = image.getRGB(0, 0, image.getWidth(), image.getHeight(), null, 0, image.getWidth());
		ConsoleImage img = new ConsoleImage(pixels, image.getWidth(), image.getHeight());
		img.render();
	}
}
