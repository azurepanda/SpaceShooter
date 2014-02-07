package shooter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;

public class Enemy extends Ship{
		
	private final Color baseColor = Color.decode("#66566F");
	private final Color shipColor = Color.decode("#3C4956");
	private final Color turretColor = Color.decode("#55616D");
	private final Color particleColor = Color.decode("#604BD8");
	private final Color bulletColor = Color.decode("#AC66D5");
	
	public Enemy(int key, Layer layer){
		super(key, layer);
		ac = new AmmoCount(60, 60, 2, 0, false, 2);
		x = 300;
		y = 300;
		friction = 0.92f;
		acel = 1.3f;
		turnSpeed = 0.13f;
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
		Player player = ((GameScene) sm.getCurrentScene()).getPlayer();
		Point p = player.getXY();
		double xDiff = p.x - x; 
		double yDiff = p.y - y; 
		turretAngle = (Math.atan2(yDiff, xDiff) - Math.PI/2);
		if(ac.ammoAvailable()){
			ac.minusAmmo();
			double deg = (-Math.atan2(yDiff, xDiff) + Math.PI/2);
			int key = sm.getCurrentScene().lowestAvailableKey(Layer.TOP);
			Bullet b = new Bullet(key, Layer.TOP, deg, x, y, bulletColor);
			sm.getCurrentScene().addSprite(key, b, Layer.TOP);
		}	
		double aDiff = Math.toDegrees(shipAngle - turretAngle);
		boolean correct = false;
		if(aDiff > 360){
			aDiff -= 360;
			correct = true;
		}
		if(aDiff < -360){
			aDiff += 360;
		}
		if(aDiff < 0){
			if(aDiff < 180){
				if(Math.abs(aDiff) > turnSpeed){
					shipAngle += turnSpeed;
				}else{
					shipAngle += aDiff;
				}
			}else{
				if(Math.abs(aDiff) > turnSpeed){
					shipAngle += turnSpeed;
				}else{
					//if(correct) aDiff += 360;
					shipAngle += aDiff;
				}
			}
		}else if(aDiff > 0){
			if(aDiff > 180){
				if(Math.abs(aDiff) > turnSpeed){
					shipAngle -= turnSpeed;
				}else{
					shipAngle -= aDiff;
				}
			}else{
				if(Math.abs(aDiff) > turnSpeed){
					shipAngle -= turnSpeed;
				}else{
					shipAngle -= aDiff;
				}
			}
		}
		
		if(Math.sqrt(xDiff*xDiff + yDiff*yDiff) > 300){
			xvel -= Math.sin(shipAngle) * acel;
			yvel += Math.cos(shipAngle) * acel;
		}
		xvel *= friction;
		yvel *= friction;
		x += xvel;
		y += yvel;
		if(xvel > minSpeed || yvel > minSpeed || xvel < -minSpeed || yvel < -minSpeed){
			for(int n = 0; n < pCount; n++){
				int key = sm.getCurrentScene().lowestAvailableKey(Layer.PARTICLE);
				sm.getCurrentScene().addSprite(key, new Particle(this, key, Layer.PARTICLE, x, y, -xvel - ((3/pCount) * n), -yvel - ((3/pCount) * n), 0.98f, 3, 6, particleColor, new Dimension(pSize, pSize)), Layer.PARTICLE);
			}
		}
		updateTPoly();
		updateSPoly();
	}
}
