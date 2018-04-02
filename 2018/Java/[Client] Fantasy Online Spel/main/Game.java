package main;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JFrame;

import UI.UIManager;
import animations.AnimationThread;
import entities.EntityTest;
import generator.RandomGenerator;
import inputs.InputManager;
import inputs.MouseManager;
import networking.NetEntityPlayer;
import networking.NetworkClient;
import networking.Packet00Login;
import tiles.Tile;
import tools.Tools;
import tools.Vector2;
import world.Map;

/*
 * @author Jesper Byström
 * Datum: 2018-02-10
 * Class: 
 * 
 */

public class Game extends Canvas implements Runnable {
	
	public static final int HEIGHT = 120; //borderSize = 5 (org: 120)
	public static final int WIDTH = 160; //borderSize = 13 (org: 160)
	public static final int SCALE = 3;
	public static final int REQUESTED_FPS = 60;
	public static final boolean DEBUG = true;
	public static final AnimationThread animationThread = new AnimationThread();
	
	public boolean running = true;
	private InputManager input = new InputManager(this);
	private MouseManager mouse = new MouseManager(this);
	public Screen screen = new Screen(WIDTH, HEIGHT, this);
	private Map map = new Map(16, 16, this);
	public NetworkClient client = new NetworkClient(this);
	private RandomGenerator generator;
	private int ticks = 0;
	private final static UIManager uiManager = new UIManager();
	private Scanner scanner = new Scanner(System.in);
	private float roundTimer = 0;
	
	public static void main(String[] args){
		new Thread(new Game()).start();
		new Thread(animationThread).start();
	}
	
	public Game(){
		JFrame frame = new JFrame("Title");
		this.setMinimumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		this.setMaximumSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		this.setPreferredSize(new Dimension(WIDTH*SCALE, HEIGHT*SCALE));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BorderLayout());
		frame.add(this, BorderLayout.CENTER);
		frame.pack();
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);
		frame.setFocusable(true);
		frame.setVisible(true);
		
		int r = 0;
		Random random = new Random();
		for(int i=0;i<map.height;i++){
			for(int j=0;j<map.width;j++){
				map.setTile(Tile.GRASS, j, i);
			}
		}
		for(int i=0;i<map.height;i++){
			r++;
			for(int j=0;j<map.width;j++){
				if(r % (random.nextInt(60)+1) == 0){
					map.setTile(Tile.TREE, (j), i);
				}
			}
		}
		map.setTile(Tile.TREE, 17, 16);
		//map.setTile(Tile.TREE, 160*2, 96);
		//generator = new RandomGenerator(map);
		EntityTest e = new EntityTest(this);
		map.add(e);
	}
	public void update(){
		//Render the dots
		dot += getTicks()>=60 ? "." : "";
		dot = dot.equals("....") ? "" : dot;
		
		if(!client.isConnected() || map.getLocalPlayer() == null) return;
		
		Point p = getMousePosition();
		if(p != null){
			mouse.updateMousePosition(new Vector2(p.x/Game.SCALE, p.y/Game.SCALE));
		}
		
		map.update();
		screen.scroll.x = map.getLocalPlayer().position.x - (screen.width)/2;
		screen.scroll.y = map.getLocalPlayer().position.y - (screen.height)/2;
		
		int x = (map.getLocalPlayer().position.x - (screen.width)/2);
		int y = (map.getLocalPlayer().position.y - (screen.height)/2);
		screen.scroll.x = Tools.clamp(x, -500, map.width*6);
		screen.scroll.y = Tools.clamp(y, -500, map.height*8);
		
		//screen.scroll.x = Tools.clamp((int) screen.scroll.x, 0, map.realWidth);
		//screen.scroll.y = Tools.clamp((int) screen.scroll.y, 0, map.realHeight);
	}
	
	String dot = "";
	public void render(){
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			requestFocus();
			return;
		}
		screen.clear();
		Graphics g = bs.getDrawGraphics();
		
		if(!client.isConnected()){
			Font.render("Awaiting server " + dot, 24 - (dot.length()*8), screen.height/2, screen, false);
		} else {
			map.render(screen);
		}
		
		getUIManager().render(screen);
		if(MouseManager.mouse.icon != null){
			MouseManager.mouse.render(screen);
		}
		
		int w = WIDTH*SCALE;
		int h = HEIGHT*SCALE;
		int xo = (getWidth() - w) / 2;
		int yo = (getHeight() - h) / 2;
		if(roundStarted()){
			String time = String.format("%.2f", ((roundTimer/1000)/60));
			Font.render("Time: " + time, 0, 0, screen, false);
			roundTimer -= 1;
		}
		g.drawImage(screen.image, xo, yo, w, h, null);
		g.dispose();
		
		bs.show();
	}
	
	@Override
    public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / REQUESTED_FPS;

        ticks = 0;
        int frames = 0;
        int otherTicks = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        System.out.println("Please enter a username");
        
        String username = "Player" + (int)((Math.random()+1) * 100);//scanner.nextLine();
        client.username = username;
        Packet00Login loginPacket = new Packet00Login(username, client.ip, client.port, (map.width*16)/2, (map.height*16)/2);
        client.sendPacket(loginPacket);
        
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = true;
            
            while (delta >= 1) {
                ticks++;
                otherTicks++;
                update();
                animationThread.run();
                delta -= 1;
                shouldRender = true;
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (shouldRender) {
                frames++;
                render();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                //System.out.println(ticks + " ticks, " + frames + " frames");
                //debug(DebugLevel.INFO, ticks + " ticks, " + frames + " frames");
                //System.out.println("RENDER: " + RENDER_TIMES);
                frames = 0;
                ticks = 0;
                if(!client.isConnected()){
               	 client.sendPacket(loginPacket);
               }
            }
            if(otherTicks >= 1000){
            	//System.out.println("Connections: " + network.getPlayers().length);
            	otherTicks = 0;
            }
        }
    }

	public Map getMap(){
		return this.map;
	}
	
	public Screen getScreen(){
		return this.screen;
	}
	
	public InputManager getKeyboardInput() {
		return input;
	}
	
	public MouseManager getMouseManager(){
		return mouse;
	}
	
	public int getTicks(){
		return ticks;
	}
	
	public static UIManager getUIManager(){
		return uiManager;
	}

	public void startRound(int time) {
		roundTimer = time;
	}
	public boolean roundStarted(){
		return roundTimer > 0;
	}
}
