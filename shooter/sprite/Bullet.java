package sprite;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.Random;

import manager.GamePanel;
import manager.KeyboardManager;
import manager.MouseManager;
import manager.SceneManager;
import framework.Damage;
import framework.DamageType;
import framework.Layer;
import framework.Sprite;


public class Bullet extends Sprite{
	protected int width;
	protected int height;
	protected double direction;
	protected int speed;
	protected Color c;
	protected boolean friendly;
	protected int damage;
	protected double critChance;
	protected double critDamage;
	protected double variance;
	protected BufferedImage[] explosion;
	protected int explosionFrame = 0;
	protected boolean exploded = false;
	protected boolean sounded = false;
	protected int life = 0;
	
	public Bullet(int key, Layer layer, double d, float x, float y, Color c, boolean friendly, int speed, int damage, double variance, double critChance, double critDamage, BufferedImage[] explosion){
		super(key, layer);
		super.x = x;
		super.y = y;
		this.width = 4;
		this.height = 4;
		this.direction = d;
		this.c = c;
		this.friendly = friendly;
		this.speed = speed;
		this.damage = damage;
		this.explosion = explosion;
		this.variance = variance;
		this.critChance = critChance;
		this.critDamage = critDamage;
	}
	
	@Override
	public void paint(GamePanel gp, Graphics2D g2d, SceneManager sm) {
		//g2d.drawImage(explosion[explosionFrame], (int) x - 50,  (int) y - 50, null);
		if(!exploded){
			g2d.setColor(c);
			g2d.fillOval((int) x + (width / 2), (int) y + (height / 2), width, height);
			g2d.setColor(Color.black);
			g2d.drawOval((int) x + (width / 2) - 1, (int) y + (height / 2) - 1, width + 2, height + 2);
			g2d.setColor(Color.cyan);
		}else{
			g2d.drawImage(explosion[explosionFrame], (int) x - 50,  (int) y - 50, null);
			explosionFrame++;
			if(explosionFrame > 8) this.remove(sm);
		}	 
	}
	
	public void remove(SceneManager sm, boolean inRange){
		exploded = true;
		if(!inRange) sounded = true;
		if(explosionFrame >= 8) sm.getCurrentScene().removeSprite(key, layer);
	}
	
	@Override
	public void update(GamePanel gp, KeyboardManager km, MouseManager mm, SceneManager sm) {
		life++;
		if(life > 900) sm.getCurrentScene().removeSprite(key, layer);
		if(!exploded){
			x += Math.sin(direction) * speed;
			y += Math.cos(direction) * speed;
		}else if(!sounded){
			sm.getAudioManager().playBlast();
			sounded = true;
		}
	}
	
	public boolean getExploded(){
		return exploded;
	}
	
	public Damage getDamage(){
		DamageType dT;
		dT = DamageType.BULLET;
		int adjustedDamage = damage;
		int potentialDifference = (int) (adjustedDamage * variance);
		int halfPD = potentialDifference / 2;
		int randDiff = new Random().nextInt(potentialDifference);
		int change = (randDiff >= halfPD) ? randDiff - halfPD : -randDiff;
		adjustedDamage += change;
		double chance = Math.random();
		if(chance <= critChance){
			adjustedDamage *= critDamage;
			dT = DamageType.CRIT;
		}
		return new Damage(adjustedDamage, dT);
	}
	
	public int getSpeed(){
		return speed;
	}
	
	public boolean getFriendly(){
		return friendly;
	}
	
	public Point getLoc(){
		return new Point((int) x + width/2, (int) y+height/2);
	}
}
