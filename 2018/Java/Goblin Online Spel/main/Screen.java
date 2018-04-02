package main;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.imageio.ImageIO;

import entities.Entity;
import shaders.ColorShader;
import shaders.Shader;
import tools.Vector2;
import tools.Vector2f;

/*
 * @author Jesper Byström
 * Datum: 2018-02-10
 * Class: 
 * 
 */

public class Screen {
	//private SpriteSheet sheet;
	
	public SpriteSheet tileSheet;
	public SpriteSheet fontSheet;
	
	public BufferedImage image;
	private int[] pixels;
	private Game game;
	public static final int ALPHA_MASK = 0xFFFF00FF;
	public static final int ALPHA_MASK_2 = 0xFF700F70;
	
	public static final byte FLIP_NONE = 0x75; 
	public static final byte FLIP_X = 0x00;
	public static final byte FLIP_Y = 0x01;
	
	public static final int SPRITE_SIZE = 16;
	
	public Vector2f scroll = Vector2f.zero();
	public int width;
	public int height;
	public int center;
	
	public float worldDarkness = 1;
	public float UIDarkness = 1;
	
	public Screen(int width, int height, Game game){
		this.width = width;
		this.height = height;
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		this.game = game;
		BufferedImage img = null;
		BufferedImage fonts = null;
		pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
		try {
			img = ImageIO.read(new FileInputStream("res/spritesheet.png"));
			fonts = ImageIO.read(new FileInputStream("res/fontsheet.png"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		tileSheet = new SpriteSheet(img.getRGB(0, 0, img.getWidth(), img.getHeight(), null, 0, img.getWidth()), img.getWidth(), img.getHeight(), 16);
		fontSheet = new SpriteSheet(fonts.getRGB(0, 0, fonts.getWidth(), fonts.getHeight(), null, 0, fonts.getWidth()), fonts.getWidth(), fonts.getHeight(), 8);
	}
	
	double v = 0;
	
	private void renderMain(int xp, int yp, int tile, Shader shader, int flip, SpriteSheet sheet, double angle, float darken, float scale){
		xp -= scroll.x;
		yp -= scroll.y;
		
		int row = 16;
		if(sheet.equals(fontSheet))
			row = 27;
		
		if(shader == null)
			shader = new ColorShader();
		
		shader.pixels = sheet.pixels;
		
		int xSheet = (tile % row) * sheet.tileSize;
		int ySheet = (tile / row) * sheet.tileSize;
		
		for(int y=0;y<sheet.tileSize;y++){
			double yPixel = y*scale+yp;
			
			for(int x=0;x<sheet.tileSize;x++){
				double xPixel = x*scale+xp;
				if(flip == Entity.LEFT)
					xPixel = (xp+(16*scale)) - x*scale;
				
				if(angle > 0){
					double c = Math.cos(angle);
					double s = Math.sin(angle);
					xPixel = ((x * c) - (y * s)) + xp;
					yPixel = ((x * s)+(y * c)) + yp;
				}
				int sheetPixel = getPixel(sheet.pixels, x, y, sheet.width, xSheet, ySheet);
				int shadedPixel = shader.getPixel(x,y,sheet.width,xSheet,ySheet);
				
				//if(color != 0xFF) shadedPixel -= color;
				/*int r = (shadedPixel & 0xFF00000);
				int g = (shadedPixel & 0x00FF00);
				int b = (shadedPixel & 0x0000FF);
				r = r >> 1;
				g = g >> 1;
				b = b >> 1;*/
				if(darken < 1){
					float ratio = darken;
					int r = (int)(((shadedPixel >> 16) & 255) * ratio);
					int g = (int)(((shadedPixel >> 8) & 255) * ratio);
					int b = (int)((shadedPixel & 255) * ratio);
					
					shadedPixel = (r << 16) | (g << 8) | (b << 1);
				}
				
				for(int xs=0;xs<scale;xs++){
					for(int ys=0;ys<scale;ys++){
						if(!canRender(sheetPixel)) continue;
						if(xPixel+xs < 0 || xPixel+xs >= (width) || yPixel+ys < 0 || yPixel+ys >= height) continue;
						pixels[(int)((xPixel+xs) + (yPixel+ys) * width)] = shadedPixel;
						//shader.setPixel(pixels, shadedPixel, (int)xPixel, (int)yPixel, width, height);
					}
				}
			}
		}
	}
	
	public void render(int xp, int yp, int tile, Shader shader, int flip, double angle){
		renderMain(xp, yp, tile, shader, flip, tileSheet, Math.toRadians(angle), worldDarkness, 1);
	}
	public void renderUI(int xp, int yp, int tile, Shader shader, int flip, float scale, float darken){
		renderMain((int)(xp+scroll.x), (int)(yp+scroll.y), tile, shader, flip, tileSheet, 0, darken, scale);
	}
	public void renderFont(int xp, int yp, int tile, int flip, boolean world){
		if(world)
			renderMain((int)(xp), (int)(yp), tile, null, flip, fontSheet, 0, UIDarkness, 1);
		else
			renderMain((int)(xp+scroll.x), (int)(yp+scroll.y), tile, null, flip, fontSheet, 0, UIDarkness, 1);
	}
	
	private boolean canRender(int pixel){
		return pixel != ALPHA_MASK && pixel != ALPHA_MASK_2;
	}
	
	public void renderMarker(int xWorld, int yWorld, int xOffset, int yOffset, int tile, Shader shader, int flip){
		int xScreen = ((int) (xWorld - scroll.x));
		int yScreen = ((int) (yWorld - scroll.y));
		
		if(xScreen >= width-xOffset) xScreen = width-xOffset;
		if(xScreen <= 0) xScreen = 0;
		if(yScreen >= height-yOffset) yScreen = height-yOffset;
		if(yScreen <= 0) yScreen = 0;
		//System.out.println(xScreen + " | yScreen: " + yScreen + " | width: " + width + " | height: " + height);
		
		renderUI(xScreen, yScreen, tile, shader, flip, 1, 1);
	}
	public boolean onScreen(int xPixel, int yPixel){
		if(xPixel >= width || xPixel < 0) return false;
		if(yPixel >= height || yPixel < 0) return false;
		return true;
	}
	public void renderCollider(BoxCollider col, int color, boolean outline){
		int xStart = (int)col.position.x;
		int xEnd = (int)col.position.x + col.width;
		int yStart = (int)col.position.y;
		int yEnd = (int)col.position.y + col.height;
		
		if(outline){
			renderOutline(xStart, yStart, col.width, col.height, color);
			return;
		}
		
		for(int x=xStart;x<xEnd;x++){
			for(int y=yStart;y<yEnd;y++){
				renderColor(x,y, 1, 1, color);
			}
		}
	}
	public void renderOutline(int xp, int yp, int w, int h, int color){
		for(int x=xp;x<w+xp;x++){
			for(int y=yp;y<h+yp;y++){
				renderColor(xp, y, 1, 1, color);
				renderColor(x, yp, 1, 1, color);
				renderColor(xp+w, y, 1, 1, color);
				renderColor(x, yp+h, 1, 1, color);
			}
		}
	}
	public void renderOutlineUI(int xp, int yp, int w, int h, int color){
		xp += scroll.x;
		yp += scroll.y;
		renderOutline(xp,yp,w,h,color);
	}
	public void renderColor(int xp, int yp, int w, int h, int color){
		xp -= scroll.x;
		yp -= scroll.y;
		Shader s = new ColorShader();
		for(int y=0;y<h;y++){
			int yPixel = y+yp;
			if(yPixel < 0 || yPixel >= height) continue;
			for(int x=0;x<w;x++){
				int xPixel = x+xp;
				if(xPixel < 0 || xPixel >= width) continue;
				pixels[xPixel + yPixel * width] = color;
			}
		}
	}
	
	public void renderColorUI(int xp, int yp, int w, int h, int color){
		renderColor((int)(xp+scroll.x), (int)(yp+scroll.y), w, h, color);
	}
	
	public Vector2 getTilesSheet(int tile){
		return new Vector2((tile % Screen.SPRITE_SIZE)*Screen.SPRITE_SIZE, (tile / Screen.SPRITE_SIZE)*Screen.SPRITE_SIZE);
	}
	public int getPixel(int[] pixels, int x, int y, int w, int xOffset, int yOffset){
		return pixels[(x+xOffset) + (y+yOffset) * w];
	}
	public int getRGB(int pixel){
		return pixel << 16 + pixel << 8 + pixel;
	}
	public SpriteSheet getSpriteSheet(){
		return this.tileSheet;
	}
	public void clear(){
		for(int i=0;i<pixels.length;i++){
			pixels[i] = 0;
		}
	}
	public void darken(float value){
		
	}
	//int[] pixels, int xp, int yp, int xSheet, int ySheet, int w, int h
	
	
	/*public void setPixel(int pixel, int x, int y, int xx, int yy){
		//if(x < game.camera.position.x && y < game.camera.position.y && x > game.camera.position.x-game.camera.xViewPort && y > game.camera.position.y - game.camera.yViewPort){
			this.pixels[(x+game.camera.position.x) + (y+game.camera.position.y) * width] = pixel;
		//}
	}*/
	
	
	/*public void render(int xp, int yp, int tile){
		xp -= scroll.x;
		yp -= scroll.y;
		int xSheet = (tile % Screen.SIZE_SPRITE_DEFAULT)*Screen.SIZE_SPRITE_DEFAULT;
		int ySheet = (tile / Screen.SIZE_SPRITE_DEFAULT)*Screen.SIZE_SPRITE_DEFAULT;
		
		for(int y=0;y<Screen.SIZE_SPRITE_DEFAULT;y++){
			for(int x=0;x<Screen.SIZE_SPRITE_DEFAULT;x++){
				int sheetPixel = getPixel(sheet.pixels, x, y, sheet.width, xSheet, ySheet);
				if(sheetPixel == ALPHA_MASK || sheetPixel == ALPHA_MASK_2) continue;
				for(int ys=0;ys<SCALE;ys++){
					int yPixel = y*SCALE + yp + ys;
					if(yPixel < 0 || yPixel >= height) continue;
					for(int xs=0;xs<SCALE;xs++){
						int xPixel = x*SCALE + xp + xs;
						if(xPixel < 0 || xPixel >= width) continue;
						this.pixels[(xPixel) + (yPixel) * width] = sheet.pixels[(x+xSheet) + (y+ySheet) * sheet.width];
					}
				}
			}
		}
	}*/
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*public void render(int xp, int yp, Vector2 tile){
		Vector2 newTile = new Vector2(tile.x*Tile.WIDTH, tile.y*Tile.HEIGHT);
		for(int y=0;y<Tile.HEIGHT;y++){
			
			//int yy = y*Game.SCALE + yp; //- this.game.camera.position.y;
			int yy = ((y - this.game.camera.position.y + this.game.camera.yViewPort)+yp);
			
			if(yy >= screenHeight-16 || yy < 0) continue;
			//System.out.println("screenheight: " + screenHeight + " | " + yy);
			
			for(int x=0;x<Tile.WIDTH;x++){
				
				//int xx = x*Game.SCALE + xp;// - this.game.camera.position.x;
				int xx = ((x - this.game.camera.position.x + this.game.camera.xViewPort)+xp);
				
				if(xx >= screenWidth-16 || xx < 0) continue;
				
				//System.out.println("screenWidth: " + screenWidth + " | " + xx);
				
				for(int yScale=0;yScale<Game.SCALE;yScale++){
					for(int xScale=0;xScale<Game.SCALE;xScale++){
						this.pixels[(x*Game.SCALE + xScale) + (yy + yScale) * screenWidth] = this.entitiesSheet.pixels[(x+newTile.x)+(y+newTile.y)*entitiesSheet.width];
					}
				}
			}
		}
	}*/
	/*
	public void render(int[] pixels, int xp, int yp, int xSheet, int ySheet, int xScale, int yScale, int w, int h, int colorTint, int blendColor){
		xp -= scroll.x;
		yp -= scroll.y;
		
		for(int y=0;y<h;y++){
			
			int yy = y + yp + (y*(yScale-1));
			
			for(int x=0;x<w;x++){
				
				int xx = x + xp + (x*(xScale-1));
				
				for(int ys=0;ys<yScale;ys++){
					int yyy = yy + ys;
					//int yyy = (int) ((yy+ys)+Math.sin((v+x)*0.5f)*2);
					//int yyy = (int) ((yy+ys)+((Math.random()+1))*((Math.sin(v*x))%4));
					if(yyy >= height || yyy < 0) continue;
					if(yyy < scroll.y - height) continue;
					for(int xs=0;xs<xScale;xs++){
						v += 0.0001f;
						if(v >= 10) v = 0;
						int xxxx = (int)Math.cos(v);
						int len = (int) (Math.sqrt(Math.pow((xx+xs-EntityPlayer.position.x),2)+Math.pow((yyy-EntityPlayer.position.y),2)));
						len /= 200;
						int s = (int) Math.sin(v);
						int xxx = xx + xs;
						//int xxx = (int) ((xx+xs) * len);
						//int xxx = (int) ((xx+xs)+((Math.random()+1))*((Math.sin(v*y))%4));
						if(xxx >= width || xxx < 0) continue;
						
						int sheetPixel = getPixel(this.sheet.pixels, x, y, this.sheet.width, xSheet, ySheet);
						//int screenPixel = getPixel(pixels, xx+xs,yy+ys, width, 0, 0);
						

						if(sheetPixel != ALPHA_MASK && sheetPixel != ALPHA_MASK_2)
							if(colorTint == 0)
								pixels[(int) (xxx+(yyy)*width)] = ((sheetPixel) & blendColor);
							else 
								pixels[(xx+xs)+(yy+ys)*width] = colorTint;
					}
				}
			}
		}
	}
	*/
}
