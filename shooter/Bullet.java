package shooter;

import java.awt.Color;
import java.awt.Graphics2D;

public class Bullet extends Sprite{
	private float x;
	private float y;
	private int width;
	private int height;
	private double direction;
	private int speed;
	private Color c;
	
	public Bullet(int key, Layer layer, double d, float x, float y, Color c){
		super(key, layer);
		this.x = x;
		this.y = y;
		this.width = 4;
		this.height = 4;
		speed = 30;
		this.direction = d;
		this.c = c;
	}
	
	@Override
	public void paint(GamePanel gp, Graphics2D g2d, SceneManager sm) {
		g2d.setColor(c);
		g2d.fillOval((int) x + (width / 2), (int) y + (height / 2), width, height);
	}

	@Override
	public void update(GamePanel gp, KeyboardManager km, MouseManager mm, SceneManager sm) {
		x += Math.sin(direction) * speed;
		y += Math.cos(direction) * speed;
	}
}
