package framework;

public class AmmoCount {
	private int ammoMax;
	private int ammoCur;
	private int delay;
	private int delayCur;
	private boolean reload;
	private int reloadAmount;
	
	public AmmoCount(int aM, int aC, int d, int dC, boolean r, int rA){
		ammoMax = aM;
		ammoCur = aC;
		delay = d;
		delayCur = dC;
		reload = r;
		reloadAmount = rA;
	}
	
	public void update(){
		if(delayCur > 0) delayCur--;
		if(reload && ammoCur == ammoMax){
			reload = false;
		}
		if(ammoCur == 0){
			reload = true;
		}
		if(reload && delayCur == 0){
			ammoCur += reloadAmount;
			delayCur = delay;
		}
	}
	
	public int getDelay(int n){
		return delay;
	}
	
	public int getCurDelay(){
		return delayCur;
	}
	
	public boolean ammoAvailable(){
		if(delayCur == 0 && ammoCur > 0 && !reload){
			return true;
		}else{
			return false;
		}
	}
	
	public void minusAmmo(){
		ammoCur--;
		delayCur = delay;
	}
	
	public int getPerc(){
		if(ammoMax == 0 || ammoCur == 0) return 0;
		return (int) (ammoCur * 360 / ammoMax);
	}
}
