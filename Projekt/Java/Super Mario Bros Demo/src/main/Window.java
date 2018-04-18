package main;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Window {
	
	private static Canvas canvas = new Canvas();
	
	public Window(Game game){
		JFrame frame = new JFrame();
		frame.setPreferredSize(new Dimension(Game.WIDTH,Game.HEIGHT));
		frame.setMaximumSize(new Dimension(Game.WIDTH, Game.HEIGHT));
		frame.setMinimumSize(new Dimension(Game.WIDTH, Game.HEIGHT));
		frame.setTitle("Game");
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(canvas);
		frame.pack();
		frame.setVisible(true);
	}
	
	public Canvas getCanvas(){
		return canvas;
	}
	
	public BufferStrategy getBufferStrategy(){
		BufferStrategy bs = canvas.getBufferStrategy();
		if(bs == null){
			canvas.createBufferStrategy(3);
			bs = canvas.getBufferStrategy();
		}
		return canvas.getBufferStrategy();
	}
	
	public static float getXMiddle(Vector2f position){
		return position.x - Game.WIDTH/2;
	}
	
	public static float getYMiddle(Vector2f position){
		return position.y - Game.HEIGHT/2;
	}
}
