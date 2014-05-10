package framework;
import java.awt.Graphics2D;

import manager.GamePanel;
import manager.KeyboardManager;
import manager.MouseManager;
import manager.SceneManager;





public abstract class Sprite {
	protected int key;
	protected final Layer layer;
	protected float x;
	protected float y;
	
	public Sprite(int key, Layer layer){
		this.key = key;
		this.layer = layer;
	}
	
	public void remove(SceneManager sm){
		sm.getCurrentScene().removeSprite(key, layer);
	}
	
	public abstract void paint(GamePanel gp, Graphics2D g2d, SceneManager sm);
	
	public abstract void update(GamePanel gp, KeyboardManager km, MouseManager mm, SceneManager sm);
	
	public float getX(){
		return x;
	}
	
	public float getY(){
		return y;
	}
	
}
