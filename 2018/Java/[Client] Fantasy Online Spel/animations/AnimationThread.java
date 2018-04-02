package animations;

import java.util.ArrayList;

import main.Game;

public class AnimationThread implements Runnable {

	private ArrayList<Animation> animations = new ArrayList<Animation>();
	private boolean running = true;
	protected int ticks;
	/*
    public synchronized void run() {
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
            }

            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
                //System.out.println(ticks + " ticks, " + frames + " frames");
                //debug(DebugLevel.INFO, ticks + " ticks, " + frames + " frames");
                //System.out.println("RENDER: " + RENDER_TIMES);
                frames = 0;
                ticks = 0;
            }
            if(otherTicks >= 1000){
            	//System.out.println("Connections: " + network.getPlayers().length);
            	otherTicks = 0;
            }
        }
    }*/
	
	@Override
	public synchronized void run(){
		update();
	}
	
	public void update() {
		for(int i=0;i<animations.size();i++){
			animations.get(i).update(this);
		}
	}
	
	public void addAnimation(Animation a){
		animations.add(a);
	}
	private Animation[] getAnimations(){
		Animation[] a = new Animation[animations.size()];
		System.arraycopy(animations.toArray(), 0, a, 0, a.length);
		return a;
	}
}
