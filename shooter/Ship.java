package shooter;

import java.awt.Graphics2D;
import java.awt.Point;

public abstract class Ship extends Sprite{
	protected float x;
	protected float y;
	protected float xvel;
	protected float yvel;
	protected float acel;
	protected float friction;
	protected final int pCount = 60;
	protected final int pSize = 3;
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
	
	public Ship(int key, Layer layer) {
		super(key, layer);
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

	public abstract void paint(GamePanel gp, Graphics2D g2d, SceneManager sm);
	
	public void update(GamePanel gp, KeyboardManager km, MouseManager mm, SceneManager sm){
		ac.update();
		if(shipAngle > 2*Math.PI) shipAngle %= 2*Math.PI;
		if(shipAngle < -2*Math.PI) shipAngle %= -2*Math.PI;
		updateS(gp, km, mm, sm);
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
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
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
}
