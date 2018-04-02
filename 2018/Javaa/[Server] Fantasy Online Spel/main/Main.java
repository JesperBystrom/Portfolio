package main;
import java.util.Random;

import entities.EntityHuman;
import entities.NetEntityPlayer;
import packets.Packet08Alive;

public class Main implements Runnable {

	private Map map = new Map(this, 16, 16);
	private NetworkServer server;
	int timer = 600;
	public int roundTimer = 600000;
	private int ticks = 0;
	private boolean running = true;
	
	public static void main(String[] args){
		new Thread(new Main()).start();
	}
	
	public Main(){
		server = new NetworkServer(this);
		Random r = new Random();
		for(int i=0;i<1;i++){
			//Entity e = new EntityHuman(this);
			//e.position = new Vector2(r.nextInt(20*16), r.nextInt(20*16));
			//map.addEntity(e);
		}
		
	}
	boolean flag = false;
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
            boolean shouldRender = false;
            
            while (delta >= 1) {
                ticks++;
                otherTicks++;
                delta -= 1;
                shouldRender = true;
            }
            
            if(shouldRender){
            	update();
            }

            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                //System.out.println(ticks + " ticks, " + frames + " frames");
                //debug(DebugLevel.INFO, ticks + " ticks, " + frames + " frames");
                //System.out.println("RENDER: " + RENDER_TIMES);
                frames = 0;
                ticks = 0;
               }
            }
            if(otherTicks >= 1000){
            	//System.out.println("Connections: " + network.getPlayers().length);
            	otherTicks = 0;
            }
        }
	public void update(){
		timer++;
		if(timer >= 600){
			getMap().addEntity(new EntityHuman(this));
			timer = 0;
			
			for(int i=0;i<server.connections.size();i++){
				NetEntityPlayer p = server.connections.get(i);
				if(p.connected == false){
					server.disconnect(p);
					continue;
				}
				p.connected = false;
			}
			if(server.connections.size() > 0){
				server.sendPacketToEveryone(new Packet08Alive());
			}
		}
		map.update();
	}

	public Map getMap(){
		return map;
	}
	
	public NetworkServer getServer(){
		return server;
	}
	
	public int getTicks(){
		return ticks;
	}
}
