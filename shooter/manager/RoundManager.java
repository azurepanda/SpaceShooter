package manager;

import java.awt.image.BufferedImage;

import framework.Layer;
import framework.Sprite;

import scene.GameScene;
import sprite.Enemy;

public class RoundManager {
	private int currentRound = 0;
	private int[][] roundData = {{2},{3},{3}};
	private GameScene gs;
	private SceneManager sm;
	private BufferedImage[] explosion;
	
	public RoundManager(GameScene gs, SceneManager sm, BufferedImage[] explosion){
		this.gs = gs;
		this.sm = sm;
		this.explosion = explosion;
	}
	
	public void round(){
		if(nextRound()){
			currentRound++;
			for(int n = 0; n < roundData[currentRound-1][0]; n++){
				int key = gs.lowestAvailableKey(Layer.TOP);
				Enemy e = new Enemy(key, Layer.TOP, sm, explosion);
				gs.addSprite(key, e, Layer.TOP);
			}	
		}	
	}	
	public boolean nextRound(){
		for(Sprite s : sm.getCurrentScene().getSpriteList(Layer.TOP).values()){
			if(s instanceof Enemy){
				if(!((Enemy) s).dead()){
					return false;
				}
			}
		}
		return true;
	}
}
