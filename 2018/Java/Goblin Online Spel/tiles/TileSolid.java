package tiles;

import main.Screen;
import main.Sprite;

/*
 * @author Jesper Byström
 * Datum: 2018-02-10
 * Class: 
 * 
 */

public class TileSolid extends Tile {
	
	public TileSolid(Sprite sprite, byte layer) {
		super(sprite, layer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isSolid() {
		return true;
	}

}
