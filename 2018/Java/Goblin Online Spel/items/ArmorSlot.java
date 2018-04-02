package items;

public enum ArmorSlot {
	BOOTS(0, 10),SHIRT(1, 11),HELMET(2, 9),SHIELD(3, 255),SWORD(4, 255);
	int id;
	int tile;
	ArmorSlot(int id, int tile){
		this.id = id;
		this.tile = tile;
	}
	public static ArmorSlot fromInteger(int id){
		switch(id){
		case 0:
			return ArmorSlot.BOOTS;
		case 1:
			return ArmorSlot.SHIRT;
		case 2:
			return ArmorSlot.HELMET;
		case 3:
			return ArmorSlot.SHIELD;
		case 4:
			return ArmorSlot.SWORD;
		}
		return null;
	}
}