package manager;

import java.util.ArrayList;

import debuffs.Debuff;


import sprite.Ship;

public class DebuffManager {

	private ArrayList<Debuff> currentDebuffs;
	
	public DebuffManager(){
		currentDebuffs = new ArrayList<Debuff>();
	}
	
	public void addDebuff(Debuff d){
		currentDebuffs.add(d);
	}
	
	public ArrayList<Debuff> getDebuffs(){
		return currentDebuffs;
	}
	
	public void update(Ship s, SceneManager sm){
		for(int n = 0; n < currentDebuffs.size(); n++){
			Debuff d  = currentDebuffs.get(n);
			if(d.getTime() > d.getDuration()){
				currentDebuffs.remove(n);
			}else{
				currentDebuffs.get(n).update(s, sm);
			}
		}
	}
}
