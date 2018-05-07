package main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import animation.Animation;
import entities.FlagPole;
import entities.Player;

public class Game implements Runnable {
	public static final int HEIGHT = 120*4;
	public static final int WIDTH = 128*4;
	public static final int SPRITE_SIZE = 16;
	public static final float GRAVITY = 0.3f;
	public static final boolean DEBUG = false;
	private BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
	private RenderHandler renderHandler = new RenderHandler(this, pixels);
	private ArrayList<Animation> animations = new ArrayList<Animation>();
	private boolean running = true;
	private ArrayList<Timer> timers = new ArrayList<Timer>();
	public Player player = new Player(this);
	//public Mob mob = new Mob(this);
	private Level level = new Level(this, 250, 14);
	private static Window window;
	private KeyManager keyManager;
	private long ticks;
	public static State state = State.GAME;
	public static int SPEED = 0;
	private static float pauseTimer = 0;
	public Vector2f scroll = new Vector2f(0,0);
	
	public static enum State {
		PAUSED, GAME
	}
	
	public static void main(String[] args){
		new Thread(new Game()).start();
	}
	
	public Game(){
		window = new Window(this);
		keyManager = new KeyManager(this);
		level.addEntity(player);
		scroll.x = Window.getXMiddle(player.position);
		scroll.y = -128;
	}
	
	public void update(){
		for(int i=0;i<timers.size();i++){
			timers.get(i).update(this);
		}
		if(Game.paused()){
			pauseTimer--;
			if(pauseTimer <= 0)
				Game.resume();
			return;
		}
		level.update();
		
		if(Window.getXMiddle(player.position) > scroll.x){
			//renderHandler.scroll.x = Tools.lerp(renderHandler.scroll.x, renderHandler.scroll.x+player.velocity.x, 0.4f);
			scroll.x += player.velocity.x;
		}
		animations.forEach(a -> a.update());
	}
	
	public void render(){
		
		BufferStrategy bs = window.getBufferStrategy();
		Graphics g = bs.getDrawGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		renderHandler.clear();
		level.render(renderHandler);
		
		if(Game.DEBUG){
			List<BoxCollider> colliders = getLevel().getColliders(player.collider, 16);
			colliders.forEach(i -> {
				i.render(renderHandler);
			});
		}
		
		g.drawImage(image, 0, 0, WIDTH, HEIGHT, null);
		int w = WIDTH*2;
		int h = HEIGHT*2;
		int xo = (window.getCanvas().getWidth() - w)/2;
		int yo = (window.getCanvas().getHeight() - h)/2;
		g.drawImage(image, xo, yo, w, h, null);
		bs.show();
	}
	
	@Override
	public void run() {
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 60;
        
        ticks = 0;
        int frames = 0;
        int otherTicks = 0;

        long lastTimer = System.currentTimeMillis();
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = true;
            
            while (delta >= 1) {
                ticks++;
                otherTicks++;
                update();
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
                System.out.println(ticks + " ticks, " + frames + " frames");
                frames = 0;
                ticks = 0;
            }
            if(otherTicks >= 1000){
            	otherTicks = 0;
            }
        }
    }
	
	public RenderHandler getRenderHandler(){
		return renderHandler;
	}
	
	public Level getLevel(){
		return level;
	}

	public Window getWindow() {
		return window;
	}
	
	public KeyManager getKeyManager(){
		return keyManager;
	}
	
	public float getWall(){
		return scroll.x + Game.WIDTH/4;
	}

	public long getTicks() {
		return ticks;
	}

	public void addAnimation(Animation animation) {
		animations.add(animation);
	}
	
	public static void pause(float timer){
		state = State.PAUSED;
		pauseTimer = timer;
		SPEED = 0;
	}
	
	public static void resume(){
		state = State.GAME;
		SPEED = 1;
	}
	
	public static boolean paused(){
		return state == State.PAUSED;
	}

	public void addTimer(Timer timer) {
		timers.add(timer);
	}

	public void removeTimer(Timer timer) {
		timers.remove(timer);
	}
}
