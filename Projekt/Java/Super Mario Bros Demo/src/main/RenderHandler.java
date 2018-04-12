package main;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RenderHandler {
	
	private int[] pixels;
	private static SpriteSheet sheet;
	private Game game;
	
	public RenderHandler(Game game, int[] pixels){
		this.game = game;
		this.pixels = pixels;
		try {
			sheet = new SpriteSheet(ImageIO.read(new File("res/spritesheet.png")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void render(Vector2f position, Sprite sprite, byte flip){
		float xp = position.x;
		float yp = position.y;
		xp -= game.scroll.x;
		yp -= game.scroll.y;
		for(int y=0;y<sprite.height;y++){
			for(int x=0;x<sprite.width;x++){
				
				int xf = x;
				if(flip == Sprite.FLIP_X){
					xf = (sprite.width-4)-x;
				}
//				int pixel = this.pixels[(xx+x)+(yy+y)*width];
//				int color = ((pixel >> 16) & 0xFF);
//				if(color <= 0) continue;
//				pixels[xx+yy*w] = colors[(color/85)-1];
				
				int pixel = sprite.pixels[x+y*sprite.width];
				int color = ((pixel >> 16) & 0xFF);
				
				if(!Level.getInBounds(x+xp, 0, Game.WIDTH, y+yp, 0, Game.HEIGHT) || color == 0x00) continue;
				
				pixels[((int)(xf+xp+sprite.xOffset))+((int)(y+yp+sprite.yOffset))*Game.WIDTH] = sprite.color[(color/85)-1];//sheet.pixels[(x+8)+y*sheet.width];
			}
		}
	}
	
	public void renderCollider(BoxCollider col, float xo, float yo){
		Vector2f position = new Vector2f(col.position.x, col.position.y);
		position.x += xo;
		position.y += yo;
		
		position.x -= game.scroll.x;
		position.y -= game.scroll.y;
		for(int y=0;y<col.height;y++){
			for(int x=0;x<col.width;x++){
				
				if(!Level.getInBounds(x+position.x, 0, Game.WIDTH, y+position.y, 0, Game.HEIGHT)) continue;
				
				pixels[((int)(x+position.x))+((int)(y+position.y))*Game.WIDTH] = 0xFF0000;//sheet.pixels[(x+8)+y*sheet.width];
			}
		}
	}
	
	public void clear() {
		for(int i=0;i<pixels.length;i++){
			pixels[i] = CColor.SKY;
		}
	}
	
	public static SpriteSheet getSpriteSheet(){
		return sheet;
	}
	
	public Game getGame(){
		return game;
	}
}
