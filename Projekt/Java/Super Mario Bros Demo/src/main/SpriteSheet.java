package main;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import etc.Tools;

public class SpriteSheet {

	public int[] pixels;
	public int width;
	public int height;
	
	public SpriteSheet(BufferedImage image){
		pixels = Tools.imageToPixels(image);
		this.width = image.getWidth();
		this.height = image.getHeight();
	}
	
//	public Sprite getSprite(int x, int y, int col1, int col2, int col3, int w, int h){
//		int[] pixels = new int[w*h];
//		for(int yy=0;yy<h;yy++){
//			for(int xx=0;xx<w;xx++){
//				pixels[xx+yy*w] = this.pixels[(xx+x)+(yy+y)*width];
//			}
//		}
//		return new Sprite(pixels, col1,col2,col3, w, h);
//	}
	public Sprite getSprite(int x, int y, int col1, int col2, int col3, int w, int h){
		int[] pixels = new int[w*h];
		int[] colors = {
			col1, col2, col3	
		};
		for(int yy=0;yy<h;yy++){
			for(int xx=0;xx<w;xx++){
				int pixel = this.pixels[(xx+x)+(yy+y)*width];
				pixels[xx+yy*w] = pixel;
			}
		}
		return new Sprite(pixels, col1,col2,col3, w, h);
	}
	public Sprite getSprite(int x, int y, int col1, int col2, int col3, int w, int h, int xo, int yo){
		int[] pixels = new int[w*h];
		int[] colors = {
			col1, col2, col3	
		};
		for(int yy=0;yy<h;yy++){
			for(int xx=0;xx<w;xx++){
				int pixel = this.pixels[(xx+x)+(yy+y)*width];
				pixels[xx+yy*w] = pixel;
			}
		}
		Sprite s = new Sprite(pixels, col1,col2,col3, w, h);
		s.xOffset = xo;
		s.yOffset = yo;
		return s;
	}
}
