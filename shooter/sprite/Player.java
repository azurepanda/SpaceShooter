package sprite;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import manager.GamePanel;
import manager.KeyboardManager;
import manager.MouseManager;
import manager.SceneManager;
import framework.AmmoCount;
import framework.Layer;


public class Player extends Ship{
	
	private final Color baseColor = Color.decode("#580EAD");
	private final Color shipColor = Color.decode("#A69A00");
	private final Color turretColor = Color.decode("#FFF140");
	private final Color particleColor = Color.decode("#35D59D");
	private final Color bulletColor = Color.decode("#00FF33");
	private final Color rocketColor = Color.decode("#66CCFF");
	private AmmoCount acS;
	private AmmoCount acT;
	
	public Player(int key, VisibleAmmoCount ac,  Layer layer, SceneManager sm, BufferedImage[] explosion){
		super(key, layer, sm, explosion);
		this.ac = ac.getAmmoCountN();
		this.acS = ac.getAmmoCountS();
		this.acT = ac.getAmmoCountT();
		x = 300;
		y = 300;
		turnSpeed = 0.12f;
		friction = 0.92f;
		acel = 0.85f;
		friendly = true;
		maxHealth = 10000;
		maxShield = 3000;
		regenRate = 20;
	}
	
	@Override
	public void paint(GamePanel gp, Graphics2D g2d, SceneManager sm) {	
		g2d.setColor(baseColor);
		g2d.fillOval((int) x-30, (int) y-30, 60, 60);
		
		g2d.setColor(Color.black);
		g2d.setStroke(new BasicStroke(1));
		g2d.drawOval((int) x-30, (int) y-30, 60, 60);
		
		g2d.setColor(shipColor);
		g2d.fillPolygon(new Polygon(sxPoints, syPoints, snPoints));
		
		g2d.setColor(Color.black);
		g2d.setStroke(new BasicStroke(2));
		g2d.drawPolygon(new Polygon(sxPoints, syPoints, snPoints));
		
		g2d.setColor(turretColor);
		g2d.fillPolygon(new Polygon(txPoints, tyPoints, tnPoints));
		
		g2d.setColor(Color.black);
		g2d.setStroke(new BasicStroke(1));
		g2d.drawPolygon(new Polygon(txPoints, tyPoints, tnPoints));
	}

	@Override
	public void updateS(GamePanel gp, KeyboardManager km, MouseManager mm, SceneManager sm) {
		if(!dead()){
			acS.update();
			acT.update();
		}
		Point p = mm.getMouseLoc();
		double xDiff = p.x - x; 
		double yDiff = p.y - y; 
		if(mm.mouseDown(0)){
			if(ac.ammoAvailable()){
				ac.minusAmmo();
				double deg = (-Math.atan2(yDiff, xDiff) + Math.PI/2);
				int key = sm.getCurrentScene().lowestAvailableKey(Layer.PROJECTILE);
				Bullet b = new Bullet(key, Layer.PROJECTILE, deg, x, y, bulletColor, true, bulletSpeed, 300, 0.1, 0.05, 1.75, explosion);
				sm.getAudioManager().playShot();
				sm.getCurrentScene().addSprite(key, b, Layer.PROJECTILE);
			}			
		}
		if(mm.mouseDown(1)){
			if(acS.ammoAvailable()){
				acS.minusAmmo();
				double deg = (-Math.atan2(yDiff, xDiff) + Math.PI/2);
				int key = sm.getCurrentScene().lowestAvailableKey(Layer.PROJECTILE);
				Rocket r = new Rocket(key, Layer.PROJECTILE, deg, x, y, rocketColor, true, bulletSpeed, 20, explosion);
				sm.getAudioManager().playShot();
				sm.getCurrentScene().addSprite(key, r, Layer.PROJECTILE);
			}			
		}
		float oldAcel = acel;
		turretAngle = (Math.atan2(yDiff, xDiff) - Math.PI/2);
		if(km.KeyDown(KeyEvent.VK_SHIFT)){
			acel = 1.4f;
		}
		if(km.KeyDown(KeyEvent.VK_A)){
			shipAngle -= turnSpeed;
		}
		if(km.KeyDown(KeyEvent.VK_D)){
			shipAngle += turnSpeed;
		}
		if(km.KeyDown(KeyEvent.VK_W)){
			xvel -= Math.sin(shipAngle) * acel;
			yvel += Math.cos(shipAngle) * acel;
		}	
		if(km.KeyDown(KeyEvent.VK_S)){
			xvel += Math.sin(shipAngle) * acel;
			yvel -= Math.cos(shipAngle) * acel;
		}
		xvel *= friction;
		yvel *= friction;
		x += xvel;
		y += yvel;
		if(xvel > minSpeed || yvel > minSpeed || xvel < -minSpeed || yvel < -minSpeed){
			int key = sm.getCurrentScene().lowestAvailableKey(Layer.PARTICLE);
			for(int n = 0; n < pCount; n++){		
				sm.getCurrentScene().addSprite(key+n, new Particle(this, key+n, Layer.PARTICLE, x, y, -xvel - ((3/pCount) * n), -yvel - ((3/pCount) * n), 0.98f, 3, 6, particleColor, new Dimension(pSize, pSize), 20), Layer.PARTICLE);
			}
		}
		updateTPoly();
		updateSPoly();
		acel = oldAcel;
	}
}
