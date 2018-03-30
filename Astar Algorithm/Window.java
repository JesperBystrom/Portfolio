import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

public class Window {

	public Main main;
	public static int HEIGHT = 120*3;
	public static int WIDTH = 160*3;
	
	public Window(Main main){
		JFrame frame = new JFrame("");
		frame.setSize(new Dimension(WIDTH+Main.CELL_SIZE, HEIGHT+Main.CELL_SIZE*3));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(main, BorderLayout.CENTER);
		frame.setLocationRelativeTo(null);
		this.main = main;
	}
	
	public void render(){
		BufferStrategy bs = main.getBufferStrategy();
		if(bs == null){
			main.createBufferStrategy(2);
			bs = main.getBufferStrategy();
		}
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		for(Node n : main.getOpenNodes()){
			g.setColor(Color.DARK_GRAY);
			if(n.equals(Main.start) || n.equals(Main.end))
				g.setColor(Color.green);
			for(int i=0;i<main.closed.size();i++){
				Node nn = main.closed.get(i);
				g.setColor(Color.blue);
				g.fillRect(nn.x, nn.y, Main.CELL_SIZE, Main.CELL_SIZE);
				g.setColor(Color.darkGray);
			}
			g.setColor(Color.pink);
			g.fillRect(main.player.x, main.player.y, Main.CELL_SIZE, Main.CELL_SIZE);
			g.setColor(Color.darkGray);
			if(n.getClosed()){
				g.setColor(Color.red);
			}
			if(n.wall)
				g.setColor(Color.yellow);
			g.fillRect(n.x, n.y, Main.CELL_SIZE, Main.CELL_SIZE);
			g.setColor(Color.white);
			g.drawRect(n.x, n.y, Main.CELL_SIZE, Main.CELL_SIZE);
			
			g.setColor(Color.black);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 10));
			g.drawString(n.getFCostPrint() + "", n.x + 1, n.y - 3);
		}
		
		bs.show();
	}
}
