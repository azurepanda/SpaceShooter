package sprite;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;

import debuffs.Debuff;

import manager.GamePanel;
import manager.KeyboardManager;
import manager.MouseManager;
import manager.SceneManager;

import framework.Layer;
import framework.Sprite;


public class HealthBar extends Sprite{
	private float x;
	private float y;
	private int width;
	private int height;
	private Ship s;
	private int health = 10000;
	private int maxHealth = 10000;
	private int shield = 3000;
	private int maxShield = 3000;
	private int regenRate = 20;
	private final Color shieldColour = Color.decode("#00CCFF");
	
	public HealthBar(int key, Layer layer, Ship s){
		super(key, layer);
		this.width = 80;
		this.height = 15;
		this.s = s;
	}
	
	@Override
	public void paint(GamePanel gp, Graphics2D g2d, SceneManager sm) {
		if(!s.dead()){
			x = s.getX() - 70;
			y = s.getY() - 85;
			//Border
			g2d.setStroke(new BasicStroke(2));
			g2d.setColor(Color.black);
			
			g2d.drawRect((int) x + (width / 2) - 1, (int) y + (height / 2) - (height / 3) - 1, width + 1, (height/3) + 1);
			g2d.setColor(shieldColour);
			double perc = ((double) shield) / ((double) maxShield);
			g2d.fillRect((int) x + (width / 2), (int) y + (height / 2) - (height / 3), (int) (width * perc), (height/3));
			
			g2d.setColor(Color.black);
			g2d.drawRect((int) x + (width / 2) - 1, (int) y + (height / 2) - 1, width + 1, height + 1);
			g2d.setStroke(new BasicStroke(1));
			//Fill	
			g2d.setColor(Color.green);
			perc = ((double) health) / ((double) maxHealth);
			g2d.fillRect((int) x + (width / 2), (int) y + (height / 2), (int) (width * perc), height);
			
			int pos = 0;
			for(Debuff b : s.getDebuffManager().getDebuffs()){
				g2d.drawImage(b.getImage(), (int) x + (width / 2) - 1 + pos, (int) y + (height / 2) - (height / 3) - 1 - 4, null); //4 is height	
				pos += 4;//5 is the width of all debuff icons but we want overlap
			}
		}
	}

	@Override
	public void update(GamePanel gp, KeyboardManager km, MouseManager mm, SceneManager sm) {
		if(!s.dead()){
			if(shield < maxShield){
				if((maxShield-shield) < regenRate){
					shield = maxShield;
				}else{
					shield += regenRate;
				}
			}
		}
	}
	
	public void minusHealth(int h, boolean ignoreShield){
		if(shield > 0 && !ignoreShield){
			int unabsorbed = -(shield - h);
			shield -= h;
			if(unabsorbed > 0){
				health -= unabsorbed;
			}
		}else{
			health -= h;
		}
	}
	
	public int getShield(){
		return shield;
	}
	
	public int getHealth(){
		return health;
	}
	
	public void setMaxHealth(int n){
		maxHealth = n;
		health = n;
	}
	
	public void setMaxShield(int n){
		maxShield = n;
		shield = n;
	}
	
	public void setRegenRate(int n){
		regenRate = n;
	}
}
