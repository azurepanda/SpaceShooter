package scene;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import manager.Camera;
import manager.GamePanel;
import manager.KeyboardManager;
import manager.RoundManager;
import manager.SceneManager;

import framework.AmmoCount;
import framework.Layer;
import framework.Scene;

import sprite.Background;
import sprite.Player;
import sprite.VisibleAmmoCount;

public class GameScene extends Scene{

	private RoundManager rm;
	private Background b;
	private VisibleAmmoCount ac;
	private Player p;
	private Camera c;
	private BufferedImage explosionI;
	private BufferedImage[] explosion;
	
	public GameScene(SceneManager sm) {		
		try {
			explosionI = ImageIO.read(new File("gfx/explosion.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		explosion = new BufferedImage[81];
		for(int x = 0; x < 9; x++){
			for(int y = 0; y < 9; y++){
				explosion[(x*9)+y] = explosionI.getSubimage(x*100, y*100, 100, 100);
			}
		}
		try {
		    BufferedImage bi = explosion[0];
		    File outputfile = new File("saved.png");
		    ImageIO.write(bi, "png", outputfile);
		} catch (IOException e) {
		}
		
		int key = lowestAvailableKey(Layer.BACK);
		b = new Background(key, Layer.BACK);
		this.addSprite(key, b, Layer.BACK);
		
		key = lowestAvailableKey(Layer.HUD);
		ac = new VisibleAmmoCount(key, Layer.HUD, new AmmoCount(60, 60, 2, 0, false, 2), new AmmoCount(6, 6, 4, 0, false, 1), new AmmoCount(1, 1, 1, 0, false, 1));
		this.addSprite(key, ac, Layer.HUD);
		
		key = lowestAvailableKey(Layer.TOP);
		p = new Player(key, ac, Layer.TOP, sm, explosion);
		this.addSprite(key, p, Layer.TOP);
			
		c = new Camera(p);
		rm = new RoundManager(this, sm, explosion);
	}

	public Player getPlayer(){
		return p;
	}
	
	public Background getBackground(){
		return b;
	}
	
	public Camera getCamera(){
		return c;
	}
	
	public void updateCamera(GamePanel gp, SceneManager sm){
		c.update(gp, sm);
	}
	
	@Override
	public void update(GamePanel gp, KeyboardManager km, SceneManager sm) {
		rm.round();
	}
}
