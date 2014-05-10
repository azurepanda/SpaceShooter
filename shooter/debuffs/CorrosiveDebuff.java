package debuffs;

import java.io.IOException;

import manager.SceneManager;
import sprite.DamageText;
import sprite.Ship;
import framework.Damage;
import framework.DamageType;
import framework.Layer;

public class CorrosiveDebuff extends Debuff{
	
	private int frequency;
	private int power;
	
	public CorrosiveDebuff(int d, int p, int f) throws IOException{
		super(d, "corrosiveDebuff");		
		this.power = p;
		this.frequency = f;
	}
	
	@Override
	public void update(Ship s, SceneManager sm){
		time++;
		if(time % frequency == 0) effect(s, sm);
	}
	
	@Override
	public void effect(Ship s, SceneManager sm) {
		s.getHealthManager().minusHealth(power, false);
		int tKey = sm.getCurrentScene().lowestAvailableKey(Layer.TOP);
		sm.getCurrentScene().addSprite(tKey, new DamageText(tKey, Layer.TOP, s, new Damage(power, DamageType.DOT)), Layer.TOP);
	}
}
