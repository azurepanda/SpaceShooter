package sprite;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.Random;

import manager.GamePanel;
import manager.KeyboardManager;
import manager.MouseManager;
import manager.SceneManager;
import scene.GameScene;
import framework.AmmoCount;
import framework.Layer;

public class Enemy extends Ship{
		
	private final Color baseColor = Color.decode("#66566F");
	private final Color shipColor = Color.decode("#3C4956");
	private final Color turretColor = Color.decode("#55616D");
	private final Color particleColor = Color.decode("#604BD8");
	private final Color bulletColor = Color.decode("#FF0099");
	private int tail;
	private int newTail = 0;
	private int newTailTime = 100;
	private Random rand;
	private boolean direction;
	private int directionTime = 0;
	private int newDirection = 100;
	private double aimX;
	private double aimY;
	
	public Enemy(int key, Layer layer, SceneManager sm, BufferedImage[] explosion){
		super(key, layer, sm, explosion);
		ac = new AmmoCount(60, 60, 2, 0, false, 2);
		friction = 0.92f;
		acel = 0.89f;
		turnSpeed = 0.13f;
		friendly = false;
		rand = new Random();
		x = rand.nextInt(1000)+500;
		y = rand.nextInt(700)+300;;
		tail = rand.nextInt(300)+200;
		if(rand.nextInt(100) > 50){
			direction = true;
		}else{
			direction = false;
		}
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
		
		//g2d.fillOval((int) aimX-3, (int) aimY-3, 6, 6);
	}

	@Override
	public void updateS(GamePanel gp, KeyboardManager km, MouseManager mm, SceneManager sm) {
		//useless for now will give use
		if(newTail >= newTailTime){
			tail = rand.nextInt(200)+200;
			newTailTime = rand.nextInt(200)+200;
			newTail = 0;
		}else{
			newTail++;
		}
		if(directionTime >= newDirection){
			if(direction) direction = false; else direction = true;
			directionTime = 0;
			newDirection = rand.nextInt(400)+100;
		}else{
			directionTime++;
		}
		Player player = ((GameScene) sm.getCurrentScene()).getPlayer();
		double xDiff = player.getX() - x; 
		double yDiff = player.getY() - y; 
		//turretAngle = (Math.atan2(yDiff, xDiff) - Math.PI/2);
		double moveSAngle = (Math.atan2(yDiff, xDiff) - Math.PI/2);
		acel = (float) (Math.sqrt(xDiff*xDiff + yDiff*yDiff)/500);
		double aDiff = Math.toDegrees(moveSAngle - shipAngle);
		turnSpeed = (float) Math.abs(aDiff/100);
		if(aDiff > Math.PI){
			if(Math.abs(aDiff) > turnSpeed){
				shipAngle += turnSpeed;
			}else{
				shipAngle += aDiff;
			}
		}else if(aDiff < -Math.PI){
			if(Math.abs(aDiff) > turnSpeed){
				shipAngle -= turnSpeed;
			}else{
				shipAngle -= aDiff;
			}
		}
		if(Math.sqrt(xDiff*xDiff + yDiff*yDiff) > tail){
			xvel -= Math.sin(shipAngle) * acel;
			yvel += Math.cos(shipAngle) * acel;
		}
		if(direction){
			xvel -= Math.sin(shipAngle+(90*(Math.PI/180))) * acel;
			yvel += Math.cos(shipAngle+(90*(Math.PI/180))) * acel;
		}else{
			xvel += Math.sin(shipAngle+(90*(Math.PI/180))) * acel;
			yvel -= Math.cos(shipAngle+(90*(Math.PI/180))) * acel;
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
		if(ac.ammoAvailable() && !player.dead()){
			ac.minusAmmo();
			double aQ = Math.pow(player.getXVel(), 2) + Math.pow(player.getYVel(), 2) - Math.pow(bulletSpeed, 2);
			double bQ = 2 * (player.getXVel() * (player.getX() - this.getX()) + player.getYVel() * (player.getY() - this.getY()));
			double cQ = Math.pow(player.getX() - this.getX(), 2) + Math.pow(player.getY() - this.getY(), 2);
			double discriminant = Math.pow(bQ, 2) - (4 * aQ * cQ);
			//System.out.println("Discrimin: " + discriminant + " aQ: " + aQ);
			if(discriminant >= 0){
				ac.minusAmmo();
				double t1 = (-bQ + Math.sqrt(discriminant)) / (2 * aQ);
				double t2 = (-bQ - Math.sqrt(discriminant)) / (2 * aQ);
				double t = 0;
				if(t1 <= t2){
					t = t1;
				}else{
					t = t2;
				}
				aimX = t * -player.getXVel() + player.getX();
				aimY = t * -player.getYVel() + player.getY();
				double xD = aimX - this.getX();
				double yD = aimY - this.getY();
				turretAngle = (Math.atan2(yD, xD) - Math.PI/2);
				double deg = (-Math.atan2(yD, xD) + Math.PI/2);
				//System.out.println(deg);
				int key = sm.getCurrentScene().lowestAvailableKey(Layer.PROJECTILE);
				Bullet b = new Bullet(key, Layer.PROJECTILE, deg, x, y, bulletColor, false, bulletSpeed, 100, 0.1, 0.05, 1.75, explosion);
				sm.getCurrentScene().addSprite(key, b, Layer.PROJECTILE);
			}
		}
		updateTPoly();
		updateSPoly();
	}
}
