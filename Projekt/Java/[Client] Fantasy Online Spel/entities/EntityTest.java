package entities;

import java.util.Random;

import main.Game;
import main.Screen;

public class EntityTest extends EntityMob {

	int x = 32, y;
	public double xa=0, ya, za;
	public double xx=32, yy, zz;
	Random random = new Random();
	int time = 0;
	public EntityTest(Game game) {
		super(game);
		xx = x;
		yy = y;
		zz = 2;
		xa = random.nextGaussian() * 0.3;
		ya = random.nextGaussian() * 0.2;
		za = random.nextFloat() * 0.7 + 2;
	}
	@Override
	public void update() {
		bounce();
		this.collider.position.x = x;
		this.collider.position.y = y;
	}
	public void bounce(){
		time++;
		if(time > 60){
			xa = random.nextGaussian() * 0.3;
			ya = random.nextGaussian() * 0.2;
			za = random.nextFloat() * 0.7 + 2;
			zz = 2;
			time = 0;
		}
			
		xx += xa;
		yy += ya;
		zz += za;
		if (zz < 0) {
			zz = 0;
			za *= -0.5;
			xa *= 0.6;
			ya *= 0.6;
		}
		za -= 0.15;
		x = (int) xx;
		y = (int) yy;
	}
	@Override
	public void render(Screen screen) {
		screen.render(x, y-(int)zz, 2, null, 1, 0);
	}
}
