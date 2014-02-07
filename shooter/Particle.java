package shooter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.Random;


public class Particle extends Sprite{
	private float x;
	private float y;
	private float xvel;
	private float yvel;
	private float acel;
	private Color color;
	private Dimension size;
	private int life;
	private int dur;
	private int start;
	private Ship s;
	
	public Particle(Ship s, int key, Layer layer, float x, float y, float xvel, float yvel, float acel, int start, int dur, Color color, Dimension size){
		super(key, layer);
		this.s = s;
		this.x = x;
		this.y = y;
		Random rand = new Random();
		this.xvel = xvel * rand.nextFloat() * 0.2f + acel;
		this.yvel = yvel * rand.nextFloat() * 0.2f + acel;
		this.acel = acel;
		this.start = start;
		this.dur = dur;
		this.color = color;
		this.size = size;
		this.life = 0;
	}
	
	@Override
	public void paint(GamePanel gp, Graphics2D g2d, SceneManager sm) {
		if(life > start){
			g2d.setColor(color);
			g2d.fillRect((int) x, (int) y, (int) size.width, (int) size.height);
		}
	}

	@Override
	public void update(GamePanel gp, KeyboardManager km, MouseManager mm, SceneManager sm) {
		life++;
		xvel *= acel;
		yvel *= acel;
		Random rand = new Random();
		xvel *= rand.nextFloat() * 0.01f + acel;
		yvel *= rand.nextFloat() * 0.01f + acel;	
		if(life > start){
			x += xvel;
			y += yvel;	
		}else{
			x = s.getX() + (1-2*rand.nextFloat()) * 20;
			y = s.getY() + (1-2*rand.nextFloat()) * 20;
		}
		if(life > dur) this.remove(sm);
	}
}
