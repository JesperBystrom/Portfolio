package tiles;

import main.Screen;
import main.Sprite;

/*
 * @author Jesper Byström
 * Datum: 2018-02-10
 * Class: 
 * 
 */

public class TileBasic extends Tile {

	
	public TileBasic(Sprite sprite, byte layer) {
		super(sprite, layer);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isSolid() {
		return false;
	}
}
