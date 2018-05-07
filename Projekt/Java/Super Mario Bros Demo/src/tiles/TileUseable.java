package tiles;

import entities.Entity;
import entities.Mushroom;
import main.Sprite;
import main.SpriteFactory;
import main.SpriteFactory.SpriteType;
import main.Vector2f;
import particles.ParticleCoin;

public class TileUseable extends TileAnimation {
	
	private float uses = 1;
	private byte reward;
	
	public TileUseable(Sprite sprite, Reward reward) {
		super(sprite);
		this.reward = reward.id();
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void bump(Entity source) {
		super.bump(source);
		if(canBump()){
			SpriteFactory s = SpriteFactory.getInstance();
			Vector2f v = source.game.getLevel().getTileCoordinates((int)source.position.x>>4, ((int)source.position.y>>4)-1);
			if((reward & Reward.COIN.id()) != 0 && v != null){
				source.game.getLevel().addParticle(new ParticleCoin(v, 22f, 1f, 
																				s.getSprite(SpriteType.COIN_1),
																				s.getSprite(SpriteType.COIN_2),
																				s.getSprite(SpriteType.COIN_3),
																				s.getSprite(SpriteType.COIN_4)));
			}
			if((reward & Reward.MUSHROOM.id()) != 0 && v != null){
				Entity m = new Mushroom(source.game, v.y-16);
				m.setPosition(new Vector2f(v.x,v.y));
				source.game.getLevel().addEntity(m);
			}
			uses--;
		}
		if(!canBump()){
			this.sprite = SpriteFactory.getInstance().getSprite(SpriteType.TILE_QUESTIONMARK_USED);
		}
	}
	
	@Override
	public boolean canBump() {
		return uses > 0;
	}

}
