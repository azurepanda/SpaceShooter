package sprite;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;

import manager.GamePanel;
import manager.KeyboardManager;
import manager.MouseManager;
import manager.SceneManager;
import debuffs.CorrosiveDebuff;
import debuffs.Debuff;
import framework.Damage;
import framework.DamageType;
import framework.Layer;
import framework.Sprite;


public class Rocket extends Bullet{

	private Ship target;
	private double xVel;
	private double yVel;
	private double friction;
	private CorrosiveDebuff debuff;
	
	public Rocket(int key, Layer layer, double d, float x, float y, Color c, boolean friendly, int speed, int damage, BufferedImage[] explosion) {
		super(key, layer, d, x, y, c, friendly, speed, damage, 0, 0, 0, explosion);
		this.width = 8;
		this.height = 8;
		this.friction = 0.5;
		try {
			debuff = new CorrosiveDebuff(33, 50, 5);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public Debuff getDebuff(){
		return debuff;
	}
	
	@Override
	public void update(GamePanel gp, KeyboardManager km, MouseManager mm, SceneManager sm) {
		life++;
		if(target == null){
			Collection<Sprite> sa = sm.getCurrentScene().getSpriteList(Layer.TOP).values();
			for(Sprite s : sa){
				if(s instanceof Ship){
					if(!((Ship) s).dead()){
						if(!(friendly == ((Ship) s).getFriendly())){
							double xD = ((Ship) s).getX() - x;
							double yD = ((Ship) s).getY() - y;
							if(Math.sqrt(Math.pow(xD, 2) + Math.pow(yD, 2)) < 400){
								target = (Ship) s;
							}
						}
					}		
				}
			}
		}else{
			double xD = target.getX() - x;
			double yD = target.getY() - y;
			direction = (-Math.atan2(yD, xD) + Math.PI/2);
		}
		float acel = (float) (Math.sqrt(life)*Math.sqrt(speed)*1/2);
		if(!exploded){
			xVel += Math.sin(direction) * acel;
			yVel += Math.cos(direction) * acel;
			xVel *= friction;
			yVel *= friction;
			x += xVel;
			y += yVel;
		}else if(!sounded){
			sm.getAudioManager().playBlast();
			sounded = true;
		}
	}
	
	@Override
	public Damage getDamage(){
		return new Damage((int) (damage * (Math.sqrt(life)*Math.sqrt(Math.sqrt(Math.pow(xVel, 2) + Math.pow(yVel, 2)))*1/2)), DamageType.BULLET);
	}
}
