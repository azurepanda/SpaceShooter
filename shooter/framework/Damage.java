package framework;

public class Damage {
	
	private int damage;
	private DamageType damageType;
	
	public Damage(int damage, DamageType damageType){
		this.damage = damage;
		this.damageType = damageType;
	}
	
	public int getAmount(){
		return damage;
	}
	
	public DamageType getDamageType(){
		return damageType;
	}
	
	public void setDamageType(DamageType dT){
		this.damageType = dT;
	}
}
