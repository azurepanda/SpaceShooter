package shooter;
import java.awt.Graphics2D;



public abstract class Sprite {
	protected int key;
	protected final Layer layer;
	
	public Sprite(int key, Layer layer){
		this.key = key;
		this.layer = layer;
	}
	
	public void remove(SceneManager sm){
		sm.getCurrentScene().removeSprite(key, layer);
	}
	
	public abstract void paint(GamePanel gp, Graphics2D g2d, SceneManager sm);
	
	public abstract void update(GamePanel gp, KeyboardManager km, MouseManager mm, SceneManager sm);
}
