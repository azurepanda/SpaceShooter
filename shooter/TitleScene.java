package shooter;
import java.awt.event.KeyEvent;


public class TitleScene extends Scene{

	private Background b;
	private TitleText tt;
	
	public TitleScene(SceneManager sm){
		int key = lowestAvailableKey(Layer.BACK);
		b = new Background(key, Layer.BACK);
		this.addSprite(key, b, Layer.BACK);	
		
		key = lowestAvailableKey(Layer.HUD);
		tt = new TitleText(key, Layer.HUD);
		this.addSprite(key, tt, Layer.HUD);
	}

	@Override
	public void update(GamePanel gp, KeyboardManager km, SceneManager sm) {
		if(km.KeyDown(KeyEvent.VK_SPACE)){
			sm.setCurrentScene(GameScene.class);
		}
	}
}
