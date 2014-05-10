package sprite;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.util.Collection;

import manager.Camera;
import manager.DebuffManager;
import manager.GamePanel;
import manager.KeyboardManager;
import manager.MouseManager;
import manager.SceneManager;

import framework.AmmoCount;
import framework.DamageType;
import framework.Layer;
import framework.Scene;
import framework.Sprite;


import scene.GameScene;

public abstract class Ship extends Sprite{
	protected float xvel;
	protected float yvel;
	protected float acel;
	protected float friction;
	protected final int pCount = 200;
	protected final int pSize = 2;
	protected final float minSpeed = 4;
	protected double turretAngle;
	protected double shipAngle;
	protected float turnSpeed;
	protected int[] sxPoints;
	protected int[] syPoints;
	protected final int tnPoints = 3;
	protected int[] txPoints;
	protected int[] tyPoints;
	protected final int snPoints = 6;
	protected AmmoCount ac;
	protected boolean once = false;
	protected HealthBar hbar;
	protected boolean friendly;
	protected boolean dead = false;
	protected int bulletSpeed = 15;
	protected int maxHealth;
	protected int maxShield;
	protected int regenRate;
	protected BufferedImage[] explosion;
	protected DebuffManager dm;
	
	public Ship(int key, Layer layer, SceneManager sm, BufferedImage[] explosion) {
		super(key, layer);
		this.explosion = explosion;
		dm = new DebuffManager();
		xvel = 0;
		yvel = 0;
		shipAngle = 0;
		turretAngle = 0;
		sxPoints = new int[snPoints];
		syPoints = new int[snPoints];
		txPoints = new int[tnPoints];
		tyPoints = new int[tnPoints];
		updateTPoly();
		updateSPoly();
	}

	public HealthBar getHealthManager(){
		return hbar;
	}
	
	public DebuffManager getDebuffManager(){
		return dm;
	}
	
	public abstract void paint(GamePanel gp, Graphics2D g2d, SceneManager sm);
	
	public void update(GamePanel gp, KeyboardManager km, MouseManager mm, SceneManager sm){
		if(shipAngle > 2*Math.PI) shipAngle = 0;
		if(shipAngle < -2*Math.PI) shipAngle = 0;
		if(!dead){
			ac.update();
			dm.update(this, sm);
			updateS(gp, km, mm, sm);
		}
		
		if(!once){
			Scene s = sm.getScene(GameScene.class);
			int hkey = s.lowestAvailableKey(Layer.TOP);
			hbar = new HealthBar(hkey, Layer.TOP, this);
			hbar.setMaxHealth(maxHealth);
			hbar.setMaxShield(maxShield);
			hbar.setRegenRate(regenRate);
			s.addSprite(hkey, hbar, Layer.TOP);
			once = true;
		}
		Collection<Sprite> sa = sm.getCurrentScene().getSpriteList(Layer.PROJECTILE).values();
		for(Sprite s : sa){
			if(s instanceof Bullet){
				Bullet b = ((Bullet) s);
				if(!b.getExploded()){
					if(!(friendly == b.getFriendly())){
						if(new Polygon(sxPoints, syPoints, snPoints).contains(b.getLoc())){
							Camera c = ((GameScene) sm.getCurrentScene()).getCamera();
							Dimension size = gp.getSize();
							Rectangle r = new Rectangle((int) (-c.getTrans().x - (0.1*size.width)), (int) (-c.getTrans().y - (0.1*size.width)), (int) (1.2*size.width), (int) (1.2*size.height));
							if(r.contains(b.getLoc())){
								b.remove(sm, true);
								hbar.minusHealth(b.getDamage().getAmount(), false);
								int tKey = sm.getCurrentScene().lowestAvailableKey(Layer.TOP);
								sm.getCurrentScene().addSprite(tKey, new DamageText(tKey, Layer.TOP, this, b.getDamage()), Layer.TOP);
							}else{
								b.remove(sm, false);
								hbar.minusHealth(b.getDamage().getAmount(), false);
							}
							if(s instanceof Rocket) dm.addDebuff(((Rocket) s).getDebuff());
						}
					}
				}		
			}
		}
		if(hbar.getHealth() <= 0) dead = true;
	}
	
	public boolean dead(){
		return dead;
	}
	
	public abstract void updateS(GamePanel gp, KeyboardManager km, MouseManager mm, SceneManager sm);

	public void updateSPoly() {
		sxPoints[0] = (int) (x + 0 * Math.cos(shipAngle) - 80*Math.sin(shipAngle));
		syPoints[0] = (int) (y + 0 * Math.sin(shipAngle) + 80*Math.cos(shipAngle));
		sxPoints[1] = (int) (x + 40 * Math.cos(shipAngle) - 0*Math.sin(shipAngle));
		syPoints[1] = (int) (y + 40 * Math.sin(shipAngle) + 0*Math.cos(shipAngle));
		sxPoints[2] = (int) (x + 40 * Math.cos(shipAngle) - -40*Math.sin(shipAngle));
		syPoints[2] = (int) (y + 40 * Math.sin(shipAngle) + -40*Math.cos(shipAngle));
		sxPoints[3] = (int) (x + 0 * Math.cos(shipAngle) - 0*Math.sin(shipAngle));
		syPoints[3] = (int) (y + 0 * Math.sin(shipAngle) + 0*Math.cos(shipAngle));
		sxPoints[4] = (int) (x + -40 * Math.cos(shipAngle) - -40*Math.sin(shipAngle));
		syPoints[4] = (int) (y + -40 * Math.sin(shipAngle) + -40*Math.cos(shipAngle));
		sxPoints[5] = (int) (x + -40 * Math.cos(shipAngle) - 0*Math.sin(shipAngle));
		syPoints[5] = (int) (y + -40 * Math.sin(shipAngle) + 0*Math.cos(shipAngle));
	}

	public void updateTPoly() {
		txPoints[0] = (int) (x + 0 * Math.cos(turretAngle) - 30*Math.sin(turretAngle));
		tyPoints[0] = (int) (y + 0 * Math.sin(turretAngle) + 30*Math.cos(turretAngle));
		txPoints[1] = (int) (x + 20 * Math.cos(turretAngle) - -10*Math.sin(turretAngle));
		tyPoints[1] = (int) (y + 20 * Math.sin(turretAngle) + -10*Math.cos(turretAngle));
		txPoints[2] = (int) (x + -20 * Math.cos(turretAngle) - -10*Math.sin(turretAngle));
		tyPoints[2] = (int) (y + -20 * Math.sin(turretAngle) + -10*Math.cos(turretAngle));
	}

	public float getXVel(){
		return xvel;
	}
	
	public float getYVel(){
		return yvel;
	}
	
	public Point getXY(){
		return new Point((int) x, (int) y);
	}
	
	public AmmoCount getAmmoCount(){
		return ac;
	}

	public boolean getFriendly() {
		return friendly;
	}
}
