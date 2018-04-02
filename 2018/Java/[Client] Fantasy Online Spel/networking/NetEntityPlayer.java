package networking;

import entities.EntityLiving;
import entities.EntityMob;
import entities.EntityPlayer;
import inputs.InputManager;
import main.Font;
import main.Game;
import main.Screen;
import tools.Vector2;

public class NetEntityPlayer extends EntityPlayer {
	public NetworkClient network;
	public String name;
	public int port;
	
	public NetEntityPlayer(Game game, NetworkClient network, String name) {
		super();
		this.game = game;
		this.network = network;
		this.name = name;
		this.map = game.getMap();
		initAnimations(game.getScreen().getSpriteSheet().getSprites(16, 32, 48));
		setPosition(Vector2.zero());
	}
	public NetEntityPlayer(InputManager input, Game game, NetworkClient network, String name, int port) {
		super(input, game, game.getMap());
		this.network = network;
		this.name = name;
		this.port = port;
	}
	public NetEntityPlayer(Game game, String name) {
		super();
		this.name = name;
		this.game = game;
		this.map = game.getMap();
		initAnimations(game.getScreen().getSpriteSheet().getSprites(16, 32, 48));
		setPosition(Vector2.zero());
	}
	public NetEntityPlayer(Game game, String name, Vector2 position) {
		super();
		this.name = name;
		this.game = game;
		this.map = game.getMap();
		initAnimations(game.getScreen().getSpriteSheet().getSprites(16, 32, 48));
		setPosition(position);
	}
	
	@Override
	public void update() {
		if(health <= 0)
			kill();
		if(!hasPositionChanged()){
			animation.stop();
		}
		if(!isLocalPlayer()) return;
		super.update();
		
		if(hasPositionChanged())
			network.sendPacket(new Packet02Move(name, position.x, position.y));
	}
	
	@Override
	public void render(Screen screen) {
		Font.render(name, position.x - name.length()*2, position.y - 8, screen, true);
		if(isLocalPlayer()){
			super.render(screen);
		} else {
			screen.render(position.x, position.y, animation.getCurrentClip().getCurrentFrame().tile, null, flip, 0);
		}
	}
	
	@Override
	public void dealDamage(EntityLiving target, int damage) {
		//super.dealDamage(target, damage);
		if(target instanceof NetEntityPlayer)
			network.sendPacket(new Packet03AttackPlayer(((NetEntityPlayer) target).name, 10, direction));
		else
			network.sendPacket(new Packet07EntityHurt(target.entityId, this.port, direction));
	}
	
	public boolean isLocalPlayer(){
		return input != null;
	}
	public void moveTowards(int x, int y, int speed) {
		int xDelta = position.x - x;
		int yDelta = position.y - y;
		if(xDelta < 0)
			position.x += 1;
		if(xDelta > 0)
			position.x -= 1;
		if(yDelta < 0)
			position.y += 1;
		if(yDelta > 0)
			position.y -= 1;
	}
}
