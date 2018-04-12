package tiles;

import main.Sprite;

public class TileSolid extends Tile {

	public TileSolid(Sprite sprite) {
		super(sprite);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean getSolid(){
		return true;
	}
}
