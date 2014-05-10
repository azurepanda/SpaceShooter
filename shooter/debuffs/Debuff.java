package debuffs;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import manager.SceneManager;
import sprite.Ship;

public abstract class Debuff {
	protected int duration;
	protected int time;
	private BufferedImage icon;
	
	public Debuff(int d, String iconPath){
		duration = d;
		time = 0;
		try {
			this.icon = ImageIO.read(new File("gfx/" + iconPath +".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getImage(){
		return icon;
	}
	
	public int getDuration(){
		return duration;
	}
	
	public int getTime(){
		return time;
	}
	
	public void update(Ship s, SceneManager sm){
		time++;
		effect(s, sm);
	}
	
	public abstract void effect(Ship s, SceneManager sm);
}
