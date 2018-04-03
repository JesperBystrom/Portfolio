package items;

import tools.Tools;

public enum Equipmenttest {

	SWORD(0,1,0,0,0),
	WAND(1,0,0,0,1);
	
	private final int id;
	private final int damage;
	private final int strength;
	private final int dexterity;
	private final int intellect;
	
	Equipmenttest(int id, int damage, int strength, int dexterity, int intellect){
		this.id = id;
		this.damage = damage;
		this.strength = strength;
		this.dexterity = dexterity;
		this.intellect = intellect;
	}
	
	public int getMagicDamage(){
		return intellect / 2;
	}
	public int getPhysicalDamage(){
		return Tools.clamp(damage, dexterity, strength);
	}
	public int id(){
		return id;
	}
	public int damage(){
		return damage;
	}
	public int strength(){
		return strength;
	}
	public int dexterity(){
		return dexterity;
	}
	public int intellect(){
		return intellect;
	}
}
