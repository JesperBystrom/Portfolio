package particles;

import main.Level;
import main.Sprite;
import main.Vector2f;

public class ParticleCoin extends ParticleSystem {

	private float yVel = 0;
	
	public ParticleCoin(Vector2f position, float life, float lifeDecrease, Sprite... sprite) {
		super(position, life, lifeDecrease, sprite);
		yVel = -9;
	}
	
	@Override
	public void emit() {
		super.emit();
	}
	
	@Override
	public boolean update(Level level) {
		boolean b = super.update(level);
		if(b){
			position.y += yVel;
			yVel += 0.8f;
		}
		return b;
		
	}

}
