package entities;

public enum EntityType {
	HUMAN;

	public static EntityType fromInteger(int type) {
		
		switch(type){
		case 0:
			return HUMAN;
		}
		return null;
	}
}
