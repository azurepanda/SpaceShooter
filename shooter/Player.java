package shooter;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.KeyEvent;

public class Player extends Ship{
	
	private final Color baseColor = Color.decode("#580EAD");
	private final Color shipColor = Color.decode("#A69A00");
	private final Color turretColor = Color.decode("#FFF140");
	private final Color particleColor = Color.decode("#35D59D");
	private final Color bulletColor = Color.decode("#FFEC00");
	
	public Player(int key, VisibleAmmoCount ac, Layer layer){
		super(key, layer);
		this.ac = ac.getAmmoCount();
		x = 300;
		y = 300;
		turnSpeed = 0.12f;
		friction = 0.92f;
		acel = 1.4f;
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
		Point p = mm.getMouseLoc();
		double xDiff = p.x - x; 
		double yDiff = p.y - y; 
		if(mm.mouseDown(0)){
			if(ac.ammoAvailable()){
				ac.minusAmmo();
				double deg = (-Math.atan2(yDiff, xDiff) + Math.PI/2);
				int key = sm.getCurrentScene().lowestAvailableKey(Layer.TOP);
				Bullet b = new Bullet(key, Layer.TOP, deg, x, y, bulletColor);
				sm.getCurrentScene().addSprite(key, b, Layer.TOP);
			}			
		}
		turretAngle = (Math.atan2(yDiff, xDiff) - Math.PI/2);
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
			for(int n = 0; n < pCount; n++){
				int key = sm.getCurrentScene().lowestAvailableKey(Layer.PARTICLE);
				sm.getCurrentScene().addSprite(key, new Particle(this, key, Layer.PARTICLE, x, y, -xvel - ((3/pCount) * n), -yvel - ((3/pCount) * n), 0.98f, 3, 6, particleColor, new Dimension(pSize, pSize)), Layer.PARTICLE);
			}
		}
		updateTPoly();
		updateSPoly();
	}
}
