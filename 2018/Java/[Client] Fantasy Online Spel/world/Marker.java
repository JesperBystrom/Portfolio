package world;

import main.Sprite;
import tools.Vector2;

/*
 * @author Jesper Byström
 * Datum: 2018-02-13
 * Class: 
 * 
 */

public class Marker {
	public Vector2 position;
	public Sprite sprite;
	public Vector2 offset;
	public Marker(Vector2 position, Sprite sprite, Vector2 offset) {
		this.position = position;
		this.sprite = sprite;
		this.offset = offset;
	}
}
